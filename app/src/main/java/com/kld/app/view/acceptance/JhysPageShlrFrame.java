package com.kld.app.view.acceptance;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.userctrl.JTimePanel;
import com.kld.app.util.*;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import com.kld.gsm.util.V20Utils;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import com.kld.app.service.DailyTradeAccountService;
import com.kld.app.service.IAcceptanceOdRegisterInfoService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;


/**
 * 进货验收--无货单验收
 */
public class JhysPageShlrFrame extends JOptionPane implements Watcher {
    private static final Logger LOG = Logger.getLogger(JhysPageShlrFrame.class);// 录入方式手工录入
    public static final int ENTERTYPE_FSGLR = 0;
    // 录入方式手工录入
    public static final int ENTERTYPE_SGLR = 1;
    // 开始卸油
    public static final String TRANSSTATUS_BEGIN = "B";
    // 结束卸油
    public static final String TRANSSTATUS_END = "E";
    private static  String OIL_TYPE_1 = "01";
    private static final int text_height = 21;
    private static final int text_width = 126;
    private JDialog frame;
    private JTextField yszgField;
    private AcceptanceDeliveryBills bills;
    private JhysPage jhys;
    private JTextField jytjField;
    private JTextField bztjField;
    private JTextField sgField;
    private JTextField pjwdField;
    private JTextField hcYszgField;
    private JTextField hcJytjField;
    private JTextField hcBztjField;
    private JTextField hcsgField;
    private JTextField hcPjwdField;
    private JTimePanel beginTime;
    private JTimePanel endTime;
    private DateChooser dateChooser;
    private String deliveryNo, oilNo;
    private Integer canNo;
    private Double yfss;
    private DailyTradeAccountService dailyTradeAccountService;
    private IAcceptanceOdRegisterService odRegisterService;
    private IAcceptanceOdRegisterInfoService odRegisterInfoService;
    private AcceptanceOdRegisterInfo acceptanceOdRegisterInfo;
    private SysmanageService sysmanageService;
    private CkdcxPage ckdcxPage;
    private String qchcflag = "";

    public boolean iswhd;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    public CkdcxPage getCkdcxPage() {
        return ckdcxPage;
    }

    public void setCkdcxPage(CkdcxPage ckdcxPage) {
        this.ckdcxPage = ckdcxPage;
    }

    public AcceptanceOdRegisterInfo getAcceptanceOdRegisterInfo() {
        return acceptanceOdRegisterInfo;
    }

    public JhysPage getJhys() {
        return jhys;
    }

    public void setJhys(JhysPage jhys) {
        this.jhys = jhys;
    }

    /**
     * Create the application.
     *
     * @param oilNo
     * @param deliveryNo
     */

    public JhysPageShlrFrame(String deliveryNo, String oilNo, Integer canNo, Double yfss,AcceptanceDeliveryBills bill,AcceptanceOdRegisterInfo info) {

        this.deliveryNo = deliveryNo;
        this.oilNo = oilNo;
        this.canNo = canNo;
        this.yfss = yfss;
        this.bills=bill;
        this.acceptanceOdRegisterInfo=info;
        initialize();
        this.odRegisterService = (IAcceptanceOdRegisterService) Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        this.odRegisterInfoService = (IAcceptanceOdRegisterInfoService) Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        this.dailyTradeAccountService = (DailyTradeAccountService) Context.getInstance().getBean(DailyTradeAccountService.class);
        OIL_TYPE_1=odRegisterService.selectOilType(bill.getOilno()).getOiltype().toString();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Main.jhysNewPage.reLoad();
                frame.dispose();
                super.windowClosing(e);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });
        //注册观察者开始
        Watched watch = ConcreteWatched.getInstance();
        watch.addWetcher("A", this);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("手工录入");
        frame.setBounds(100, 100, 368, 500);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.getContentPane().setLayout(null);

        Main.setCenter(frame);
        JPanel qcsj = new JPanel();
        qcsj.setBackground(UIManager.getColor("Button.light"));
        qcsj.setBounds(10, 10, 342, 185);
        frame.getContentPane().add(qcsj);
        qcsj.setLayout(null);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 22, 342, 112);
        qcsj.add(separator);

        JLabel qcsjLabel = new JLabel("前尺数据");
        qcsjLabel.setBounds(10, 3, 54, 15);
        qcsj.add(qcsjLabel);

        JLabel sg = new JLabel("水    高(mm):");
        sg.setBounds(50, 33, 85, 15);
        qcsj.add(sg);

        JLabel yszg = new JLabel("油水总高(mm):");
        yszg.setBounds(50, 63, 85, 15);
        qcsj.add(yszg);

        JLabel pjwd = new JLabel("平均温度(℃):");
        //pjwd.setBounds(60, 113, 75, 15);
        pjwd.setBounds(50, 93, 85, 15);
        qcsj.add(pjwd);

        JLabel jytj = new JLabel(" 净油体积(L):");
        jytj.setBounds(50, 123, 85, 15);
        qcsj.add(jytj);

        JLabel bztj = new JLabel(" 标准体积(L):");
        //bztj.setBounds(60, 85, 75, 15);
        bztj.setBounds(50, 153, 85, 15);
        qcsj.add(bztj);

        SuperDoubleDocument superDoubleDocument=new SuperDoubleDocument(3,2);
        SuperDoubleDocument superDoubleDocument2=new SuperDoubleDocument(3,2);
        superDoubleDocument.isfs=true;
        superDoubleDocument.set_max(100d);
        superDoubleDocument.set_min(-100d);
        superDoubleDocument2.isfs=true;
        superDoubleDocument2.set_max(100d);
        superDoubleDocument2.set_min(-100d);
        final List<JTextField> txts=new ArrayList<JTextField>();
        sgField = new JTextField();
        sgField.setBounds(139, 30, text_width, text_height);
        sgField.setDocument(new SuperDoubleDocument(6, 2));
        qcsj.add(sgField);
        sgField.setColumns(10);
        sgField.setText(acceptanceOdRegisterInfo.getBeginwaterheight()==null?"":acceptanceOdRegisterInfo.getBeginwaterheight().toString());

        yszgField = new JTextField();
        yszgField.setBounds(139, 60, text_width, text_height);
        yszgField.setDocument(new SuperDoubleDocument(6, 2));
        qcsj.add(yszgField);
        yszgField.setColumns(10);
        txts.add(yszgField);
        yszgField.setText(acceptanceOdRegisterInfo.getBeginoilheight() == null ? "" : acceptanceOdRegisterInfo.getBeginoilheight().toString());

        pjwdField = new JTextField();
        pjwdField.setDocument(superDoubleDocument);
        pjwdField.setColumns(10);
        //pjwdField.setBounds(129, 113, text_width, text_height);
        pjwdField.setBounds(139, 90, text_width, text_height);
        qcsj.add(pjwdField);
        txts.add(pjwdField);
        pjwdField.setText(acceptanceOdRegisterInfo.getBegintemperature() == null ? "" : acceptanceOdRegisterInfo.getBegintemperature().toString());

        jytjField = new JTextField();
        jytjField.setColumns(10);
        //jytjField.setEnabled(false);
//        jytjField.setDocument(new SuperDoubleDocument(6, 2));
        jytjField.setBounds(139, 120, text_width, text_height);
        qcsj.add(jytjField);
        txts.add(jytjField);
        jytjField.setText(acceptanceOdRegisterInfo.getBeginoill() == null ? null : acceptanceOdRegisterInfo.getBeginoill().toString());

        bztjField = new JTextField();
        bztjField.setColumns(10);
        //bztjField.setDocument(new DoubleDocument());
        //bztjField.setBounds(129, 83, text_width, text_height);
        bztjField.setBounds(139, 150, text_width, text_height);
        bztjField.setText(acceptanceOdRegisterInfo.getBeginv20l() == null ? null : acceptanceOdRegisterInfo.getBeginv20l().toString());
        //bztjField.setEnabled(false);
        qcsj.add(bztjField);
        txts.add(bztjField);

        JPanel hcsj = new JPanel();
        hcsj.setBackground(UIManager.getColor("Button.light"));
        hcsj.setBounds(10, 200, 342, 185);
        frame.getContentPane().add(hcsj);
        hcsj.setLayout(null);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 23, 342, 121);
        hcsj.add(separator_1);

        JLabel hcsjLabel = new JLabel("后尺数据");
        hcsjLabel.setBounds(10, 5, 54, 15);
        hcsj.add(hcsjLabel);

        JLabel hcsg = new JLabel("水    高(mm):");
        hcsg.setBounds(50, 33, 85, 15);
        hcsj.add(hcsg);

        JLabel hcYszg = new JLabel("油水总高(mm):");
        hcYszg.setBounds(50, 63, 85, 15);
        hcsj.add(hcYszg);

        JLabel hcPjwd = new JLabel("平均温度(℃):");
        //hcPjwd.setBounds(60, 113, 75, 15);
        hcPjwd.setBounds(50, 93, 85, 15);
        hcsj.add(hcPjwd);

        JLabel hcJytj = new JLabel(" 净油体积(L):");
        hcJytj.setBounds(50, 123, 85, 15);
        hcsj.add(hcJytj);

        JLabel hcBztj = new JLabel(" 标准体积(L):");
        hcBztj.setBounds(50, 153, 85, 15);
        hcsj.add(hcBztj);

        hcsgField = new JTextField();
        hcsgField.setColumns(10);
        hcsgField.setDocument(new SuperDoubleDocument(6, 2));
        hcsgField.setBounds(139, 30, text_width, text_height);
        hcsj.add(hcsgField);

        hcYszgField = new JTextField();
        hcYszgField.setColumns(10);
        hcYszgField.setDocument(new SuperDoubleDocument(6, 2));
        hcYszgField.setBounds(139, 60, text_width, text_height);
        hcsj.add(hcYszgField);

        hcPjwdField = new JTextField();
        hcPjwdField.setColumns(10);

        hcPjwdField.setDocument(superDoubleDocument2);
        //hcPjwdField.setBounds(129, 110, text_width, text_height);
        hcPjwdField.setBounds(139, 90, text_width, text_height);
        hcsj.add(hcPjwdField);

        hcJytjField = new JTextField(111);
        hcJytjField.setColumns(10);
        hcJytjField.setDocument(new SuperDoubleDocument(6, 2));
        hcJytjField.setBounds(139, 120, text_width, text_height);
        hcsj.add(hcJytjField);

        hcBztjField = new JTextField();
        hcBztjField.setColumns(10);
        //hcBztjField.setDocument(new DoubleDocument());
        //hcBztjField.setEnabled(false);
        //hcBztjField.setBounds(129, 82, text_width, text_height);
        hcBztjField.setBounds(139, 150, text_width, text_height);
        hcsj.add(hcBztjField);

        JLabel kssj = new JLabel("开始时间:");
        kssj.setBounds(69, 391, 75, 15);
        frame.getContentPane().add(kssj);



        JLabel jssj = new JLabel("结束时间:");
        jssj.setBounds(69, 416, 75, 15);
        frame.getContentPane().add(jssj);

        beginTime = new JTimePanel();
        beginTime.setBounds(138, 381, 200 + 20, 30);
        //beginTime.setVisible(false);
        frame.getContentPane().add(beginTime);
        beginTime.setDate(acceptanceOdRegisterInfo.getBegintime());

      /*  JTimePanel betime=new JTimePanel();
        betime.setBounds(138, 318, 200+20, 30);
        frame.getContentPane().add(betime);
*/
        endTime = new JTimePanel();
        endTime.setBounds(138, 406, 200 + 20, 30);
        frame.getContentPane().add(endTime);



        //region 保存
        JButton button = new JButton("保存");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("".equals(yszgField.getText())||"".equals(jytjField.getText())||"".equals(pjwdField.getText())
                        ||"".equals(hcYszgField.getText())||"".equals(hcJytjField.getText())||"".equals(hcPjwdField.getText())
                        ||beginTime.getDate()==null||endTime.getDate()==null){
                    JOptionPane.showMessageDialog(null, "请将数据填写完整", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (Double.parseDouble(bztjField.getText())>Double.parseDouble(hcBztjField.getText())){
                    JOptionPane.showMessageDialog(null, "后尺升数小于前尺", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (beginTime.getDate().getTime()>=endTime.getDate().getTime()){
                    JOptionPane.showMessageDialog(null, "结束时间要晚于开始时间", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                acceptanceOdRegisterInfo = new AcceptanceOdRegisterInfo();
                acceptanceOdRegisterInfo.setManualNo(bills.getDeliveryno());
                acceptanceOdRegisterInfo.setDeliveryno(bills.getDeliveryno());
                // 保存卸油明细表信息
                acceptanceOdRegisterInfo.setOilcan(canNo);
                acceptanceOdRegisterInfo.setOilno(bills.getOilno());
                acceptanceOdRegisterInfo.setBeginoilheight(yszgField.getText().trim().isEmpty() ? null : Double.parseDouble(yszgField.getText().trim()));
                acceptanceOdRegisterInfo.setBeginoill(jytjField.getText().trim().isEmpty() ? null : Double.parseDouble(jytjField.getText().trim()));
                acceptanceOdRegisterInfo.setBeginv20l(Double.parseDouble(bztjField.getText().trim()));
                acceptanceOdRegisterInfo.setBegintemperature(pjwdField.getText().trim().isEmpty() ? null : Double.parseDouble(pjwdField.getText().trim()));
                acceptanceOdRegisterInfo.setEndoilheight(hcYszgField.getText().trim().isEmpty() ? null : Double.parseDouble(hcYszgField.getText().trim()));
                acceptanceOdRegisterInfo.setEndoill(hcJytjField.getText().trim().isEmpty() ? null : Double.parseDouble(hcJytjField.getText().trim()));
                acceptanceOdRegisterInfo.setEndv20l(Double.parseDouble(hcBztjField.getText().trim()));
                acceptanceOdRegisterInfo.setEndtemperature(hcPjwdField.getText().trim().isEmpty() ? null : Double.parseDouble(hcPjwdField.getText().trim()));
                acceptanceOdRegisterInfo.setBeginwaterheight(sgField.getText().trim().isEmpty() ? null : Double.parseDouble(sgField.getText().trim()));
                acceptanceOdRegisterInfo.setEndwaterheight(hcsgField.getText().trim().isEmpty()?null:Double.parseDouble(hcsgField.getText().trim()));
                double duringSales = 0;

                acceptanceOdRegisterInfo.setBegintime(beginTime.getDate());
                acceptanceOdRegisterInfo.setEndtime(endTime.getDate());
                //Map literMap = dailyTradeAccountService.selectDuringSales(canNo, beginTime.getDate(),endTime.getDate());
                Map literMap =dailyTradeAccountService.GetSaleOilSumByCanNoAndDate(canNo.toString(), beginTime.getDate(),endTime.getDate());
                if (literMap != null){
                    duringSales = Double.parseDouble(literMap.get("Liter") == null ? "0" : literMap.get("Liter").toString());
                }
                // duringSales=dailyTradeAccountService.GetSaleOilSumByCanNoAndDate(canNo.toString(), beginTime.getDate(),endTime.getDate());

                acceptanceOdRegisterInfo.setDuringsales(duringSales);
                double realGetL = acceptanceOdRegisterInfo.getEndv20l()
                        - acceptanceOdRegisterInfo.getBeginv20l() + duringSales;
                double realGetV20 = acceptanceOdRegisterInfo.getEndv20l() - acceptanceOdRegisterInfo.getBeginv20l() + duringSales;

                acceptanceOdRegisterInfo.setTranstatus(JhysPage.TRANSSTATUS_END);
                acceptanceOdRegisterInfo.setEntertype(JhysPage.ENTERTYPE_SGLR);
                acceptanceOdRegisterInfo.setDischargel(realGetL);
                acceptanceOdRegisterInfo.setCreatetime(new Date());
                acceptanceOdRegisterInfo.setIsdel(0);

                //region 取容积表版本号
                if (sysmanageService==null){
                    sysmanageService=Context.getInstance().getBean(SysmanageService.class);
                }
                List<SysManageCubage>sysManageCubages=sysmanageService.selectCubageInused();
                for(SysManageCubage item:sysManageCubages){
                    if (item.getOilcan()==acceptanceOdRegisterInfo.getOilcan()){
                        acceptanceOdRegisterInfo.setCanversion(item.getVersion());
                        break;
                    }
                }
                //endregion

                odRegisterInfoService.merge(acceptanceOdRegisterInfo);
                frame.setVisible(false);
                frame.dispose();
            }
        });
        //endregion

        button.setBounds(51, 440, 93, 23);
        frame.getContentPane().add(button);

        JButton button_1 = new JButton("取消");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setVisible(false);
                    frame.dispose();
                    //Main.jhysNewPage.reLoad();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        button_1.setBounds(198, 440, 93, 23);
        frame.getContentPane().add(button_1);
        //region 失去焦点时调用
        /**
         * 水高输入框失去焦点时候调用该方法
         */
        sgField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(isNotEmpty(sgField.getText())&&isNotEmpty(yszgField.getText())&&isNotEmpty(pjwdField.getText())){
                    qchcflag = "qc";
                    LOG.info("水高输入框失去焦点");
                    high2Liter(sgField.getText(), yszgField.getText(), pjwdField.getText());
                }
            }
        });
        /**
         * 油水总高输入框失去焦点时候调用该方法
         */
        yszgField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(isNotEmpty(sgField.getText())&&isNotEmpty(yszgField.getText())&&isNotEmpty(pjwdField.getText())){
                    qchcflag = "qc";
                    LOG.info("油水总高输入框失去焦点");
                    high2Liter(sgField.getText(), yszgField.getText(), pjwdField.getText());
                }
            }
        });
        /**
         * 平均温度输入框失去焦点时候调用该方法
         */
        pjwdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(isNotEmpty(sgField.getText())&&isNotEmpty(yszgField.getText())&&isNotEmpty(pjwdField.getText())){
                    qchcflag = "qc";
                    LOG.info("平均温度输入框失去焦点");
                    high2Liter(sgField.getText(), yszgField.getText(), pjwdField.getText());
                }
            }
        });

        /**
         * 后尺水高输入框失去焦点时候调用该方法
         */
        hcsgField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(isNotEmpty(hcsgField.getText())&&isNotEmpty(hcYszgField.getText())&&isNotEmpty(hcPjwdField.getText())){
                    qchcflag = "hc";
                    high2Liter(hcsgField.getText(),hcYszgField.getText(),hcPjwdField.getText());
                }
            }
        });
        /**
         * 后尺油水总高输入框失去焦点时候调用该方法
         */
        hcYszgField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(isNotEmpty(hcsgField.getText())&&isNotEmpty(hcYszgField.getText())&&isNotEmpty(hcPjwdField.getText())){
                    qchcflag = "hc";
                    high2Liter(hcsgField.getText(),hcYszgField.getText(),hcPjwdField.getText());
                }
            }
        });
        /**
         * 后尺平均温度输入框失去焦点时候调用该方法
         */
        hcPjwdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(isNotEmpty(hcsgField.getText())&&isNotEmpty(hcYszgField.getText())&&isNotEmpty(hcPjwdField.getText())){
                    qchcflag = "hc";
                    high2Liter(hcsgField.getText(),hcYszgField.getText(),hcPjwdField.getText());
                }
            }
        });

        //endregion
    }
    private double getV20L(String oilType,double vt, double v20) {
        DecimalFormat df = new DecimalFormat("######0.00");
        if (OIL_TYPE_1.equals(oilType)){
            return Double.parseDouble(df.format((vt - v20) * 0.0012 * vt));
        }else{
            return Double.parseDouble(df.format((vt - v20) * 0.0008 * vt));
        }
    }


    private void Setqc(){
        if (!jytjField.getText().trim().isEmpty()&&!pjwdField.getText().trim().isEmpty()){
            Double vt=Double.parseDouble(jytjField.getText().trim());
            Double pjwd=Double.parseDouble(pjwdField.getText().trim());
            V20Utils v20Utils=new V20Utils();

            if (OIL_TYPE_1.equals("03")){
                //柴油
                bztjField.setText(v20Utils.getDieV20(pjwd,vt).toString());
            }
            if (OIL_TYPE_1.equals("01")){
                //汽油
                bztjField.setText(v20Utils.getGasV20(pjwd,vt).toString());
            }

        }
    }

    private void Sethc(){
        if (!hcJytjField.getText().trim().isEmpty()&&!hcPjwdField.getText().trim().isEmpty()){
            Double vt=Double.parseDouble(hcJytjField.getText().trim());
            Double pjwd=Double.parseDouble(hcPjwdField.getText().trim());
            V20Utils v20Utils=new V20Utils();

            if (OIL_TYPE_1.equals("03")){
                //柴油
                hcBztjField.setText(v20Utils.getDieV20(pjwd,vt).toString());
            }
            if (OIL_TYPE_1.equals("01")){
                //汽油
                hcBztjField.setText(v20Utils.getGasV20(pjwd,vt).toString());
            }

        }
    }

    public void UpdateOdreg(String manualno){
        AcceptanceOdRegister od=odRegisterService.selectByPrimaryKey(manualno);
        //regionselect begin time
        AcceptanceOdRegisterInfo info=odRegisterInfoService.selectbegintime(manualno);
        if (info==null){
            od.setBegintime(null);
            od.setEndtime(null);
            return;
        }else{
            od.setBegintime(info.getBegintime());

        }

        //endregion

        //region select endtime
        AcceptanceOdRegisterInfo info1=odRegisterInfoService.selectendtime(manualno);
        if (info1==null){
            od.setBegintime(null);
            od.setEndtime(null);
            return;
        }else{
            od.setEndtime(info.getEndtime());

        }
        odRegisterService.updateByPrimaryKey(od);
        //endregion
    }
    /**
     * 根据手工录入的值进行高升转换
     */
    public void high2Liter(String sgField,String yszgField,String pjwdField){
        //调用高升转换接口
        LOG.info("开始调用高升转换接口");
        Channel cc = Main.CC;
        if (cc == null) {
            LOG.info("Channel是空");
            return ;
        }else{
            List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
            Map<String,Object> map = new HashMap<String, Object>();
            //TODO  WaterHeight
            LOG.info("传入参数oilcan：" + this.canNo + "WaterHeight："+sgField+"TotalHeight："
                    + yszgField + "OilTemp：" + pjwdField);
            map.put("oilcan",this.canNo);//oilcan
            //TODO
            map.put("WaterHeight",sgField);//WaterHeight
            map.put("TotalHeight",yszgField);//TotalHeight
            map.put("OilTemp", pjwdField);//OilTemp
            list.add(map);
            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, list, Constants.PID_Code.A15_10016.toString());
            LOG.info("gasMsg:"+gasMsg);
            cc.writeAndFlush(gasMsg);
            LOG.info("发送完成");
            list.clear();
        }
    }

    @Override
    public void update(GasMsg gasMsg) {
        LOG.info("开始接收gasMsg.getPid()="+gasMsg.getPid());
        if("A15_10016".equals(gasMsg.getPid())){
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            LOG.info("data.getResult()="+data.getResult());
            if ("0".equals(data.getResult())) {
                List<Map<String,Object>> list = data.getData();
                if(list!=null&&list.size()>0) {
                    LOG.info("ret.get(0).fOilCubage=" + list.get(0).get("fOilCubage")
                            + ",ret.get(0).fOilStandCubage"
                            + list.get(0).get("fOilStandCubage"));
                    String jytj = list.get(0).get("fOilCubage") + "";
                    double jytj_d = Double.parseDouble(jytj);
                    String bztj = list.get(0).get("fOilStandCubage") + "";
                    double bztj_d = Double.parseDouble(bztj);
                    DecimalFormat df = new DecimalFormat("#.0");
                    if("qc".equals(qchcflag)) {
                        jytjField.setText(df.format(jytj_d) + "");//净油体积
                        bztjField.setText(df.format(bztj_d) + "");//标准体积
                    }else if("hc".equals(qchcflag)){
                        hcJytjField.setText(df.format(jytj_d) + "");//后尺净油体积
                        hcBztjField.setText(df.format(bztj_d) + "");//后尺标准体积
                    }
                }
            }
        }
    }
    public boolean isNotEmpty(String s){
        if(s!=null&&!"".equals(s)){
            return true;
        }
        return false;
    }
}
