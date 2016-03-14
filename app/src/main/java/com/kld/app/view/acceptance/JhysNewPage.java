package com.kld.app.view.acceptance;

import com.kld.app.service.IAcceptanceDeliveryService;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.util.SysConfig;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.ATG.domain.SysmanageRealgiveoil;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.ws.rs.core.Application;
import javax.xml.bind.SchemaOutputResolver;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Created by niyang on 2016/2/15.
 */
public class JhysNewPage extends JOptionPane implements Watcher  {
    private JDialog frame;
    private JTabbedPane tabbedPane;
    private IAcceptanceDeliveryService deliveryService;
    private SysManageDic sysManageDic;
    private AcceptSevices acceptSevices;
    public JDialog getFrame() {
        return frame;
    }
    private AcceptanceDeliveryBills cbill;
    private MyThread thread;
    private HashMap<String,Component> componentHashMap=new HashMap<String, Component>();
    private static final Logger LOG = Logger.getLogger(JhysNewPage.class);
    public JhysNewPage(AcceptanceDeliveryBills bill){
        this.cbill=bill;
        Main.jhys2Map=new HashMap<String, Jhys2>();

    }
    public void Init(){
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("进货验收");
        frame.setResizable(false);
        frame.setSize(680, 600);
        frame.setBounds(0, 0, 680, 600);
        //frame.setBackground(Color.green);
        Inittabpanel();
        OpenThread();
        Main.setCenter(frame);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             *
             * @param e
             */
            @Override
            public void windowClosed(WindowEvent e) {
                //StopThread();
                System.out.println("Closed");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                StopThread();
                System.out.println("windowClosing");
                frame.dispose();
            }
        });
        frame.setVisible(true);
    }

    public void OpenThread() {
        Main.watch.addWetcher("A", this);
        System.out.println("注册观察者");
        //region 开启线程
        System.out.println("xckq");
        thread=new MyThread();
        thread.start();
        //endregion
    }

    public void StopThread(){
        thread.stop();
    }

    /**
     * 从系统获取未处理出库单信息
     *
     * @return
     * @throws Exception
     */
    public List<AcceptanceDeliveryBills> getAcceptanceDeliveryBill() {
        List<AcceptanceDeliveryBills> bills = new ArrayList<AcceptanceDeliveryBills>();
        // 从本地系统查询数据
        deliveryService = (IAcceptanceDeliveryService) Context.getInstance().getBean("acceptanceDeliveryServiceImpl");
        bills=deliveryService.getbillsfromLocalByIsComplete("0");
        return bills;
    }

    public void reLoad(){
        StopThread();
        frame.removeAll();
        frame.repaint();
        Inittabpanel();
        OpenThread();
    }

    public void Inittabpanel(){
        if (sysManageDic==null){
            sysManageDic=Context.getInstance().getBean(SysManageDic.class);
        }
        //todo
        SysManageDict sysManageDict=sysManageDic.GetByCode("wysj");
        if (sysManageDict!=null) {
            Main.wysj = Long.parseLong(sysManageDict.getValue());
        }else{
            Main.wysj=900000;
        }
        JButton btn3=new JButton("+");
        btn3.setBounds(608, 5, 52, 25);
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabbedPane.getTabCount() == 5) return;
                //todo 开新页面 选择出库单
                openCkd();
            }
        });
        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(null);
        tabbedPane.setBounds(0, 60, 680, 518);

        frame.getContentPane().add(btn3);
        frame.getContentPane().add(tabbedPane);
        if (cbill!=null) {
           /* Jhys2 jhys2 = new Jhys2(cbill, this);
            tabbedPane.addTab(cbill.getDeliveryno(), jhys2);*/
            addbill(cbill);
        }
        List<AcceptanceDeliveryBills> bills=getAcceptanceDeliveryBill();
        for (AcceptanceDeliveryBills bill:bills){
            if (bill.getBegintime()!=null) {
                addbill(bill);
            }
        }
    }

    private void  openCkd(){
        CkdcxOpen ckdcxOpen=new CkdcxOpen(this);
        ckdcxOpen.getFrame().setVisible(true);
    }

    public void commplete(String deliveryno){
        if (tabbedPane.getTabCount()>1) {
            Component component = tabbedPane.getSelectedComponent();
            componentHashMap.put(deliveryno, component);
        }
        try {
            System.out.println(Main.CC.hashCode());
            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10005.toString());
            Map<String, String> map = new HashMap<String, String>();
            map.put("1",deliveryno);
            map.put("2", "0");
            ResultMsg msg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List s = new ArrayList();
            s.add(map);
            msg.setData(s);
            gasMsg.setMessage(new JsonMapper().toJson(msg));
            System.out.println(Main.sign + ",卸油完成 send:" + gasMsg.toString());
            Main.CC.writeAndFlush(gasMsg);
        } catch (Exception e2) {
        }
    }

    public void addbill(AcceptanceDeliveryBills  bill){
        for (int i=0;i<tabbedPane.getTabCount();i++){
             if(tabbedPane.getTitleAt(i).toString().equals(bill.getDeliveryno()))
             {
                 return;
             }
        }
        Jhys2 jhys2=new Jhys2(bill,this);
        tabbedPane.addTab(bill.getDeliveryno(), jhys2);
    }

    public void yhys2close(String deliveryno){
        if (tabbedPane.getTabCount()>1) {
            Component com=componentHashMap.get(deliveryno);
            tabbedPane.remove(com);
        }else{
            frame.dispose();
        }
    }

    @Override
    public void update(GasMsg gasMsg) {
        if (gasMsg.getPid().equals("A15_10002")||gasMsg.getPid().equals("A15_10004")||gasMsg.getPid().equals("A15_10005")) {
            LOG.info("收到："+gasMsg.toString());
            Iterator iter = Main.jhys2Map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Jhys2 val = (Jhys2) entry.getValue();
               /* if ((val.getCanlist() != null && val.getCanlist().size() > 0)||gasMsg.getPid().equals("A15_10005")) {
                    val.update(gasMsg);
                }*/
                if (val!=null){
                    val.update(gasMsg);
                }
            }
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            int i=0;
            while (true) {
                    i++;
                    try {
                        if (Main.CC == null) {
                            System.out.println("cc is null");
                        } else {
                            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10002.toString());
                            Main.CC.writeAndFlush(gasMsg);
                            if (i == 4) {
                                i = 0;
                                gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                                Main.CC.writeAndFlush(gasMsg);
                            }
                            if (i == 0) {
                                gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                                Main.CC.writeAndFlush(gasMsg);
                            }
                            LOG.info("gasMsg:"+gasMsg.toString());
                            gasMsg=null;
                        }
                    } catch (Exception e) {
                        System.out.println("获取罐枪实时状态线程异常：" + e.getMessage());
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


