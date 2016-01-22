package com.kld.app.view.sysmanage;

import com.kld.app.service.ISetCapacityTableService;
import com.kld.app.service.SysCubageService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.Socket.Constants;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.gsm.Socket.protocol.CapacTabBMsg;
import com.kld.gsm.Socket.protocol.CapacTabMsg;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/28 13:45
 * @Description:
 */
public class SetCapacityTablePanel extends JPanel {
    Logger logger = Logger.getLogger(SetCapacityTablePanel.class);
    JLabel czyLabel1 = new JLabel("操作人：");
    JLabel czsjLabel1 = new JLabel("操作时间：");
    JLabel dqbbLabel1 = new JLabel("版本号：");
    JLabel ygbhLabel1 = new JLabel("油罐编号：");
    JLabel czyLabel2 = new JLabel("");
    JLabel czsjLabel2 = new JLabel("");
    JLabel dqbbLabel2 = new JLabel("");
    JLabel ygbhLabel2 = new JLabel("");
    JLabel konghangLabel = new JLabel(" ");
    JButton szButton = new JButton("设  置");
    CubagePanel cubagePanel;
    JFrame framesys;
    public SetCapacityTablePanel(CubagePanel cubagePanel,JFrame framesys) {
        this.cubagePanel=cubagePanel;
        this.framesys = framesys;
        int selectRow = cubagePanel.probePartable.getSelectedRow();
        int oilcanno = (Integer)cubagePanel.probePartable.getValueAt(selectRow,0);
        String version = (String)cubagePanel.probePartable.getValueAt(selectRow,1);
        String opername ="";// (String)cubagePanel.probePartable.getValueAt(selectRow,3);
        if(Main.USERMAP!=null&&Main.USERMAP.size()>0) {
            opername = Main.USERMAP.get("oprname").toString();
        }
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String now = sd.format(date);
        czyLabel2.setText(opername);
        czsjLabel2.setText(now);
        dqbbLabel2.setText(version);
        ygbhLabel2.setText(oilcanno + "");

        SysManageCubage sysManageCubage = new SysManageCubage();
        sysManageCubage.setVersion(version);
        sysManageCubage.setOilcan(oilcanno);
        sysManageCubage.setSetman(opername);
        sysManageCubage.setSettime(date);
        sysManageCubage.setInused(1);
        sysManageCubage.setSetstate(1);
        //注册观察者开始
        Watched watch = ConcreteWatched.getInstance();
        SzButtonListener sz = new SzButtonListener(cubagePanel,this,sysManageCubage,framesys);
        watch.addWetcher("A", sz);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        //把组件添加进jframe
        this.add(czyLabel1);
        this.add(czyLabel2);
        this.add(czsjLabel1);
        this.add(czsjLabel2);
        this.add(dqbbLabel1);
        this.add(dqbbLabel2);
        this.add(ygbhLabel1);
        this.add(ygbhLabel2);
        this.add(konghangLabel);
        this.add(szButton);
        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
        //是用来控制添加进的组件的显示位置
        s.fill = GridBagConstraints.BOTH;
        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        layout.setConstraints(czyLabel1, s);//设置组件
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(czyLabel2, s);
        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        layout.setConstraints(czsjLabel1, s);//设置组件
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(czsjLabel2, s);
        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        layout.setConstraints(dqbbLabel1, s);//设置组件
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(dqbbLabel2, s);
        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        layout.setConstraints(ygbhLabel1, s);//设置组件
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(ygbhLabel2, s);
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(konghangLabel, s);
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(szButton, s);
        szButton.addActionListener(sz);
    }
}
class SzButtonListener implements ActionListener, Watcher{
    Logger logger = Logger.getLogger(SzButtonListener.class);
    SysManageCubage sysManageCubage;
    CubagePanel cubagePanel;
    SetCapacityTablePanel setCapacityTablePanel;
    JFrame framesys;
    public SzButtonListener(CubagePanel cubagePanel, SetCapacityTablePanel setCapacityTablePanel,SysManageCubage sysManageCubage,JFrame framesys) {
        this.sysManageCubage = sysManageCubage;
        this.cubagePanel = cubagePanel;
        this.setCapacityTablePanel = setCapacityTablePanel;
        this.framesys = framesys;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SysCubageService sysCubageService =(SysCubageService) (Context.getInstance().getBean("sysCubageService"));
        ISetCapacityTableService setCapacityTableService = Context.getInstance().getBean(ISetCapacityTableService.class);
        setCapacityTableService.updateByPrimaryKeySelective(sysManageCubage);
        Channel cc = Main.CC;
        setCapacityTablePanel.szButton.disable();
        if (cc == null) {
            //System.out.println("Link Netty Server FAll");
            JOptionPane.showMessageDialog(null, "连接失败！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            setCapacityTablePanel.szButton.enable(true);
            return ;
        } else {
            SysManageCubageInfo sysManageCubageInfo = new SysManageCubageInfo();
            sysManageCubageInfo.setVersion(sysManageCubage.getVersion());
            sysManageCubageInfo.setOilcan(sysManageCubage.getOilcan());
//            查询容积明细表
            java.util.List<SysManageCubageInfo> sysManageCubageInfoList = sysCubageService.selectCubageInfo(sysManageCubageInfo);

            CapacTabMsg capacTabMsg = new CapacTabMsg();
            capacTabMsg.setOilNo(sysManageCubage.getOilcan());
            capacTabMsg.setOperation(1);//下发到液位仪
            capacTabMsg.setStrVersion(sysManageCubage.getVersion());
            ArrayList<CapacTabBMsg> sysManageCubageInfoArrayList = new ArrayList<CapacTabBMsg>();
            for(SysManageCubageInfo s : sysManageCubageInfoList) {
                CapacTabBMsg capacTabBMsg = new CapacTabBMsg();
                capacTabBMsg.setHeight(s.getHeight());
                capacTabBMsg.setLiter(s.getLiter());
                sysManageCubageInfoArrayList.add(capacTabBMsg);
            }
            capacTabMsg.setCapacTabBMsgs(sysManageCubageInfoArrayList);
            ArrayList<CapacTabMsg> list = new ArrayList<CapacTabMsg>();
            list.add(capacTabMsg);
            ////System.out.println("capacTabMsg:" + capacTabMsg);
            logger.info("capacTabMsg:" + capacTabMsg);
            ////System.out.println("list size" + list.size());
            logger.info("list size"+list.size());
            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, list, Constants.PID_Code.A15_10009.toString());

            //System.out.println("send:" + gasMsg.toString());
            cc.writeAndFlush(gasMsg);
        }
    }
    @Override
    public void update(GasMsg gasMsg) {
        if("A15_10009".equals(gasMsg.getPid())){
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            if ("1".equals(data.getResult())) {

                JOptionPane.showMessageDialog(null, "操作失败！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                setCapacityTablePanel.szButton.enable(true);
            }else if ("0".equals(data.getResult())){
                JOptionPane.showMessageDialog(null, "操作成功！", "信息提示", JOptionPane.INFORMATION_MESSAGE);

                cubagePanel.cubageQueryBut.doClick(0);
                setCapacityTablePanel.szButton.enable(true);
                framesys.dispose();
            }
        }
    }
}