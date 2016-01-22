package com.kld.app.view.alarm;

import com.kld.app.service.AlarmMeasureLeakService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.service.IquidService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.util.IntegerDocument;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.AlarmMeasureLeak;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageIquidInstrument;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import com.kld.gsm.util.V20Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StartAlarmFrame extends JDialog implements Watcher {

    private JDialog frame;
    private JComboBox ypBox;
    private JTextField timeTextField;
    public static int iTime;
    private Integer canno;
    private String oiltype;

    private IAcceptanceOdRegisterService odRegisterService;
    private IquidService iquidService;
    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    /**
     * Create the application.
     */
    public StartAlarmFrame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JDialog();
        frame.setResizable(false);
        frame.setModal(true);
        frame.setTitle("启动检测");
        frame.setBounds(480, 300, 209, 219);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
//        frame.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("Button.light"));
        panel.setBounds(0, 0, 403, 190);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel ypLabel = new JLabel("选择油罐：");
        ypLabel.setBounds(20, 53, 90, 15);
        panel.add(ypLabel);

        ypBox = new JComboBox();
        SysManageCanInfoService tankInfoService = (SysManageCanInfoService) (Context.getInstance().getBean("SysManageTankInfoService"));
        List<SysManageCanInfo> TankInfoLst = tankInfoService.selectAll();
        for (SysManageCanInfo item : TankInfoLst) {
            ypBox.addItem(item.getOilcan());
        }
        ypBox.setBounds(120, 50, 60, 21);
        panel.add(ypBox);

        JLabel timeLabel = new JLabel("持续时间(1-24)：");
        timeLabel.setBounds(20, 90, 110, 15);
        panel.add(timeLabel);
        timeTextField=new JTextField();
        timeTextField.setDocument(new IntegerDocument(1, 24));
        timeTextField.setBounds(120, 90, 60, 21);
        panel.add(timeTextField);
        panel.updateUI();

       // Watcher watcher = new StartAlarmFrame();
        Main.watch.addWetcher("A", this);

        JButton xyBtn = new JButton("确定");
        xyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (iquidService==null){
                    iquidService=Context.getInstance().getBean(IquidService.class);
                }
                SysManageIquidInstrument iquidInstrument=iquidService.selectLast();
                Main.setStatus("启动静态液位异常检测...");
                //do someting


                if(iquidInstrument.getWorktype().equals("控制台采集")){
                    //region 控制台采集
                      // Channel cc = Main.reLink();
                      if (Main.CC == null) {
                          //System.out.println("Link Netty Server FAll");
                      } else {
                          //从页面传过来的oilcan
                          Integer oilcanno = Integer.parseInt(ypBox.getSelectedItem().toString());
                          try {
                              iTime = Integer.parseInt(timeTextField.getText().toString());
                          }catch (Exception e1){
                              timeTextField.setText("");
                              JOptionPane.showMessageDialog(null, "时间格式不正确", "信息提示", JOptionPane.ERROR_MESSAGE);
                              return;
                          }
                          if(iTime<1||iTime>24){
                              timeTextField.setText("");
                              JOptionPane.showMessageDialog(null, "检测时间应大于1小时，不超过24小时", "信息提示", JOptionPane.ERROR_MESSAGE);
                              return;
                          }
                          AlarmMeasureLeakService alarmMeasureLeakService = (AlarmMeasureLeakService) (Context.getInstance().getBean("alarmMeasureLeakService"));
                          List<AlarmMeasureLeak> alarmMeasureLeakList = alarmMeasureLeakService.selectByOilcan(oilcanno);
                          for (AlarmMeasureLeak o : alarmMeasureLeakList) {
                              if (null == o.getEnddate() || "".equals(o.getEnddate())) {
                                  JOptionPane.showMessageDialog(null, "已经启动，未停止，请不要反复点击启动", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                                  return;
                              }
                          }
                          Integer oilno = oilcanno;
                          HashMap map = new HashMap();
                          map.put("oilno", oilno);
                          List list = new ArrayList();
                          list.add(map);
                          GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, list, Constants.PID_Code.A15_10012.toString());
                         // System.out.println("send 12" + gasMsg);
                          Main.CC.writeAndFlush(gasMsg);
                          //frame.dispose();
                      }
                    //endregion
                }
                else {
                    //region 探棒直连
                    //region 控制台采集
                    // Channel cc = Main.reLink();
                    if (Main.CC == null) {
                        //System.out.println("Link Netty Server FAll");
                    } else {
                        //从页面传过来的oilcan
                        Integer oilcanno = Integer.parseInt(ypBox.getSelectedItem().toString());
                        try {
                            iTime = Integer.parseInt(timeTextField.getText().toString());
                        }catch (Exception e1){
                            timeTextField.setText("");
                            JOptionPane.showMessageDialog(null, "时间格式不正确", "信息提示", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if(iTime<1||iTime>24){
                            timeTextField.setText("");
                            JOptionPane.showMessageDialog(null, "检测时间应大于1小时，不超过24小时", "信息提示", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        AlarmMeasureLeakService alarmMeasureLeakService = (AlarmMeasureLeakService) (Context.getInstance().getBean("alarmMeasureLeakService"));
                        List<AlarmMeasureLeak> alarmMeasureLeakList = alarmMeasureLeakService.selectByOilcan(oilcanno);
                        for (AlarmMeasureLeak o : alarmMeasureLeakList) {
                            if (null == o.getEnddate() || "".equals(o.getEnddate())) {
                                JOptionPane.showMessageDialog(null, "已经启动，未停止，请不要反复点击启动", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                        }
                        canno=oilcanno;

                        GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                        Main.CC.writeAndFlush(gasMsg);
                        //frame.dispose();
                    }
                    //endregion
                }
            }

        });
        xyBtn.setBounds(55, 130, 93, 23);
        panel.add(xyBtn);

        frame.addWindowListener(new WindowAdapter() {
                                    public void windowClosing(WindowEvent e) {
                                        frame.dispose();
                                    }
                                }
        );
    }

    @Override
    public void update(GasMsg gasMsg) {
        //region控制台启动
        if ("A15_10012".equals(gasMsg.getPid())) {
            Main.setStatus("静态液位异常检测顺利启动。");
            ////System.out.println("进入返回————————————");
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            //失败了
            if(data.getResult().equals("1"))
            {
                JOptionPane.showMessageDialog(null, "启动失败", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //获得罐号和启动时间
            Integer oilcanno = Integer.parseInt(data.getData().get(0).toString());

            List list = data.getData();
            String startdate = (String) list.get(1);
            Map hm=new HashMap();
            hm.put(1,startdate);
            hm.put(2,list.get(2));
            hm.put(3,list.get(3));
            Start(hm, oilcanno, null);
            frame.setVisible(false);
            frame.dispose();
        }
        //endregion

        //region 探棒直连启动
        if ("A15_10004".equals(gasMsg.getPid())){
            //实时库存

            /*fOilCubage;//净油体积   标准体积/油水总高
            fOilStandCubage;//标准体积，单位：升
            fEmptyCubage;//空体积  ，单位：升
            fTotalHeight;//油水总高，单位：毫米
            fWaterHeight;//水高 ，单位：毫米
            fOilTemp;//平均温度，单位：摄氏度
            fOilTemp1;//5点温度
            fOilTemp2;//
            fOilTemp3;//fEmptyCubage
            fOilTemp4;//
            fOilTemp5;//
            fWaterBulk;//水体积，单位：升*/



            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<Map<String, ?>> candata = resultMsg.getData();
            DecimalFormat df=new DecimalFormat("######.00");
            for (int m = 0; m < candata.size(); m++) {
                if (canno==Integer.parseInt(candata.get(m).get("uOilCanNo").toString())) {
                   //得到该罐的罐存
                    Map map=candata.get(m);
                    atg_stock_data_out_t stock=(atg_stock_data_out_t)mapToObject(atg_stock_data_out_t.class, map);
                    Double dstj = Double.parseDouble(df.format(stock.fWaterBulk));
                    Double dpjwd = Double.parseDouble(df.format(stock.fOilTemp));
                    Double djytj = Double.parseDouble(df.format(stock.fOilCubage));

                    SysManageCanInfoService sysManageCanInfoService=Context.getInstance().getBean(SysManageCanInfoService.class);
                    SysManageCanInfo canInfo=sysManageCanInfoService.selectbycanno(canno);
                    if (canInfo!=null){
                        if (odRegisterService==null){
                            odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
                        }
                        oiltype=odRegisterService.selectOilType(canInfo.getOilno()).getOiltype().toString();
                    }
                    Map hm=new HashMap();
                    SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date = new Date();
                    String datestr= sd.format(date);
                    hm.put(1,datestr);
                    hm.put(2,getV20L(oiltype,dpjwd,djytj));
                    hm.put(3, dstj);
                    Start(hm, canno, stock);
                    frame.setVisible(false);
                    frame.dispose();
                    break;
                }


            }
        }
        //endregion
    }

    public  Object mapToObject(Class clazz, Map<String,Object> map){
        if(null == map){
            return null;
        }
        Field[] fields = clazz.getDeclaredFields(); //取到类下所有的属性，也就是变量名
        Field field;
        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        for(int i=0; i<fields.length; i++){
            field = fields[i];
            String fieldName = field.getName();
            //把属性的第一个字母处理成大写
            String stringLetter=fieldName.substring(0, 1).toUpperCase();
            //取得set方法名，比如setBbzt
            String setterName="set"+stringLetter+fieldName.substring(1);
            //真正取得set方法。
            Method setMethod = null;
            Class fieldClass = field.getType();
            try {
                if(isHaveSuchMethod(clazz, setterName)){
                    if(fieldClass == String.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, String.valueOf(map.get(fieldName)));//为其赋值
                    }else if(fieldClass == Integer.class || fieldClass == int.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Integer.parseInt(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Boolean.class || fieldClass == boolean.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Boolean.getBoolean(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Short.class || fieldClass == short.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Short.parseShort(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Long.class || fieldClass == long.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Long.parseLong(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Double.class || fieldClass == double.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Double.parseDouble(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Float.class || fieldClass == float.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Float.parseFloat(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == BigInteger.class ){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, BigInteger.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));//为其赋值
                    }else if(fieldClass == BigDecimal.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, BigDecimal.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));//为其赋值
                    }else if(fieldClass == Date.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        if(map.get(fieldName).getClass() == java.sql.Date.class){
                            setMethod.invoke(o, new Date(((java.sql.Date)map.get(fieldName)).getTime()));//为其赋值
                        }else if(map.get(fieldName).getClass() == java.sql.Time.class){
                            setMethod.invoke(o, new Date(((java.sql.Time)map.get(fieldName)).getTime()));//为其赋值
                        }else if(map.get(fieldName).getClass() == java.sql.Timestamp.class){
                            setMethod.invoke(o, new Date(((java.sql.Timestamp)map.get(fieldName)).getTime()));//为其赋值
                        }
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }   catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return o;
    }
    public  boolean isHaveSuchMethod(Class<?> clazz, String methodName){
        Method[] methodArray = clazz.getMethods();
        boolean result = false;
        if(null != methodArray){
            for(int i=0; i<methodArray.length; i++){
                if(methodArray[i].getName().equals(methodName)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
    private double getV20L(String oilType,double vt, double V) {
        V20Utils v20Utils=new V20Utils();
        if (V==0.0){return 0.0;}
        if (oilType.equals("03")){
            //柴油
            return  v20Utils.getDieV20(vt, V);
        }else {
            //汽油
            return  v20Utils.getGasV20(vt,V);
        }
    }
    private void Start(Map data, Integer oilcanno,atg_stock_data_out_t stock) {
        AlarmMeasureLeakService alarmMeasureLeakService = (AlarmMeasureLeakService) (Context.getInstance().getBean("alarmMeasureLeakService"));
        //根据罐号查询表
        List<AlarmMeasureLeak> alarmMeasureLeakList = alarmMeasureLeakService.selecthasStartByOilcan(oilcanno);
        //查询不到启动信息
        if (alarmMeasureLeakList.size() == 0) {
            //1、插入数据
           //List list = data.getData();
            String startdate = (String) data.get(1);
            //System.out.println("取得的会见为" + startdate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            try {
                AlarmMeasureLeak alarm = new AlarmMeasureLeak();
                alarm.setOilcanno(oilcanno);
                alarm.setTime(iTime);
                alarm.setStartdate(sdf.parse(startdate));
                alarm.setStartoill((Double) data.get(2));
                alarm.setStartwaterl((Double) data.get(3));
                if (stock!=null){
                    alarm.setStartoiltemp1(stock.fOilTemp1);
                    alarm.setStartoiltemp2(stock.fOilTemp2);
                    alarm.setStartoiltemp3(stock.fOilTemp3);
                    alarm.setStartoiltemp4(stock.fOilTemp4);
                    alarm.setStartoiltemp5(stock.fOilTemp5);
                    alarm.setStartoilheight(stock.fTotalHeight);
                    alarm.setStartwaterheight(stock.fWaterHeight);
                }
                alarmMeasureLeakService.insertSelective(alarm);
                JOptionPane.showMessageDialog(null, "启动成功", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                Main.oilExcep.reload();
            } catch (ParseException e1) {
                //System.out.println("准换时间");
            }

        } else if (alarmMeasureLeakList.size() > 0) {//已经启动过，再次启动时判断。
            Integer status = 0;//用于区分是否需要启动插入
            //System.out.println("打印此处，跟踪程序status：" + status);
            for (AlarmMeasureLeak o : alarmMeasureLeakList) {
                if (null == o.getEnddate() || "".equals(o.getEnddate())) {//根据结束时间判断是否启动过。
                    //System.out.println("进到这里，状态位设置为1");//未结束，置为1。
                    status = 1;
                }
            }
            //如果启动过，但是已结束。则重新插入
            if (status == 0) {
                //System.out.println("进到这里，状态位为0");
                //List list = data.getData();
                String startdate = (String) data.get(1);
                //System.out.println("取得的会见为" + startdate);

                //2、插入数据
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                try {
                    AlarmMeasureLeak alarm = new AlarmMeasureLeak();
                    alarm.setOilcanno(oilcanno);
                    alarm.setTime(iTime);
                    //System.out.println("time222222:~~~~~~~" + time + "~~~~~~~");
                    alarm.setStartdate(sdf.parse(startdate));
                    //System.out.println("转换之后的时间为" + startdate);
                    alarmMeasureLeakService.insertSelective(alarm);
                    JOptionPane.showMessageDialog(null, "启动成功", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    Main.oilExcep.reload();
                } catch (ParseException e1) {
                    //System.out.println("换时间");
                }
            }
        }
    }
}

