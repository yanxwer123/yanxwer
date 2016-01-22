package com.kld.app.view.sysmanage;

import com.kld.app.service.AlmService;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.view.main.Constant;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.dao.SysManageIquidInstrumentDao;
import com.kld.gsm.ATG.domain.SysManageAlarmParameter;
import com.kld.gsm.ATG.domain.SysManageIquidInstrument;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

public class AlmOptionPanel extends JPanel implements Watcher{
	Logger logger = Logger.getLogger(AlmOptionPanel.class);
	 int selectedRowID;

	public int getSelectedRowID() {
		return selectedRowID;
	}

	public void setSelectedRowID(int selectedRowID) {
		this.selectedRowID = selectedRowID;
	}

	JPanel almOptionPanel;
	JLabel xgbhLab; // 油罐编号
	JTextField dywyjTextField; // 低液位预警
	JTextField dywbjTextField; // 低液位报警
	JTextField gywyjTextField; // 高液位预警
	JTextField gywbjTextField; // 高液位报警
	JTextField gswyjTextField; // 高水位预警
	JTextField gswbjTextField; // 高水位报警
	JTextField dybjTextField; // 盗油报警
	JTextField lybjTextField; // 漏油报警
	JTextField stbjTextField; // 渗漏报警
	JTextField gwbjTextField; // 高温报警
	JTextField dwbjTextField;// 低温报警
	JButton saveBut;  // 保存
	JButton closeBut; //关闭
	AlmService almService =(AlmService) (Context.getInstance().getBean("almService"));
	public AlmOptionPanel(){
		almOptionPanel=new JPanel();
		//注册观察者开始
		Watched watch =Main.watch;
		watch.addWetcher("A", this);

		JLabel ygbhLabel = new JLabel("油罐编号");
		ygbhLabel.setSize(Constant.width, Constant.height);
		ygbhLabel.setLocation(Constant.lie1, 10);
		xgbhLab = new JLabel();
		xgbhLab.setSize(Constant.width, Constant.height);
		xgbhLab.setLocation(Constant.lie2, 10);
		almOptionPanel.add(ygbhLabel);
		almOptionPanel.add(xgbhLab);
		
		JLabel dywyjLabel = new JLabel("低液位预警(mm)");
		dywyjLabel.setSize(Constant.width, Constant.height);
		dywyjLabel.setLocation(Constant.lie1, 50);
		dywyjTextField = new JTextField();
		dywyjTextField.setSize(Constant.width, Constant.height);
		dywyjTextField.setLocation(Constant.lie2, 50);
		almOptionPanel.add(dywyjLabel);
		almOptionPanel.add(dywyjTextField);
		
		JLabel dywbjLabel = new JLabel("低液位报警(mm)");
		dywbjLabel.setSize(Constant.width, Constant.height);
		dywbjLabel.setLocation(Constant.lie1, 90);
		dywbjTextField = new JTextField();
		dywbjTextField.setSize(Constant.width, Constant.height);
		dywbjTextField.setLocation(Constant.lie2, 90);
		almOptionPanel.add(dywbjLabel);
		almOptionPanel.add(dywbjTextField);
		
		JLabel gywyjLabel = new JLabel("高液位预警(mm)");
		gywyjLabel.setSize(Constant.width, Constant.height);
		gywyjLabel.setLocation(Constant.lie1, 130);
		gywyjTextField = new JTextField();
		gywyjTextField.setSize(Constant.width, Constant.height);
		gywyjTextField.setLocation(Constant.lie2, 130);
		almOptionPanel.add(gywyjLabel);
		almOptionPanel.add(gywyjTextField);
		
		JLabel gywbjLabel = new JLabel("高液位报警(mm)");
		gywbjLabel.setSize(Constant.width, Constant.height);
		gywbjLabel.setLocation(Constant.lie1, 170);
		gywbjTextField = new JTextField();
		gywbjTextField.setSize(Constant.width, Constant.height);
		gywbjTextField.setLocation(Constant.lie2, 170);
		almOptionPanel.add(gywbjLabel);
		almOptionPanel.add(gywbjTextField);

		JLabel gswyjLabel = new JLabel("高水位预警(mm)");
		gswyjLabel.setSize(Constant.width, Constant.height);
		gswyjLabel.setLocation(Constant.lie1, 210);
		gswyjTextField = new JTextField();
		gswyjTextField.setSize(Constant.width, Constant.height);
		gswyjTextField.setLocation(Constant.lie2, 210);
		almOptionPanel.add(gswyjLabel);
		almOptionPanel.add(gswyjTextField);
		
		JLabel gswbjLabel = new JLabel("高水位报警(mm)");
		gswbjLabel.setSize(Constant.width, Constant.height);
		gswbjLabel.setLocation(Constant.lie3, 10);
		gswbjTextField = new JTextField();
		gswbjTextField.setSize(Constant.width, Constant.height);
		gswbjTextField.setLocation(Constant.lie4, 10);
		almOptionPanel.add(gswbjLabel);
		almOptionPanel.add(gswbjTextField);
		//盗油报警,单位：默认300L/H
		JLabel dybjLabel = new JLabel("盗油报警(L/H)");
		dybjLabel.setSize(Constant.width, Constant.height);
		dybjLabel.setLocation(Constant.lie3, 50);
		dybjTextField = new JTextField();
		dybjTextField.setSize(Constant.width, Constant.height);
		dybjTextField.setLocation(Constant.lie4, 50);
		almOptionPanel.add(dybjLabel);
		almOptionPanel.add(dybjTextField);
		//漏油报警,单位：默认60L/H
		JLabel lybjLabel = new JLabel("漏油报警(L/H)");
		lybjLabel.setSize(Constant.width, Constant.height);
		lybjLabel.setLocation(Constant.lie3, 90);
		lybjTextField = new JTextField();
		lybjTextField.setSize(Constant.width, Constant.height);
		lybjTextField.setLocation(Constant.lie4, 90);
		almOptionPanel.add(lybjLabel);
		almOptionPanel.add(lybjTextField);
		//渗漏报警,单位：升/小时，默认0.8L/H
		JLabel slbjLabel = new JLabel("渗漏报警(L/H)");
		slbjLabel.setSize(Constant.width, Constant.height);
		slbjLabel.setLocation(Constant.lie3, 130);
		stbjTextField = new JTextField();
		stbjTextField.setSize(Constant.width, Constant.height);
		stbjTextField.setLocation(Constant.lie4, 130);
		almOptionPanel.add(slbjLabel);
		almOptionPanel.add(stbjTextField);
		//高温报警，单位：摄氏度。温度>=55
		JLabel gwbjLabel = new JLabel("高温报警(℃)");
		gwbjLabel.setSize(Constant.width, Constant.height);
		gwbjLabel.setLocation(Constant.lie3, 170);
		gwbjTextField = new JTextField();
		gwbjTextField.setSize(Constant.width, Constant.height);
		gwbjTextField.setLocation(Constant.lie4, 170);
		almOptionPanel.add(gwbjLabel);
		almOptionPanel.add(gwbjTextField);
		//低温报警，单位：摄氏度。温度<=-10
		JLabel dwbjLabel = new JLabel("低温报警(℃)");
		dwbjLabel.setSize(Constant.width, Constant.height);
		dwbjLabel.setLocation(Constant.lie3, 210);
		dwbjTextField = new JTextField();
		dwbjTextField.setSize(Constant.width, Constant.height);
		dwbjTextField.setLocation(Constant.lie4, 210);
		almOptionPanel.add(dwbjLabel);
		almOptionPanel.add(dwbjTextField);
		
		saveBut = new JButton("下发液位仪");
		saveBut.setSize(Constant.widthBut, Constant.heightBut);
		saveBut.setLocation(300, 250);
		almOptionPanel.add(saveBut);
		
		saveBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//region 获取探棒工作模式
				logger.info("get iq");
				SysManageIquidInstrumentDao sysManageIquidInstrumentDao = Context.getInstance().getBean(SysManageIquidInstrumentDao.class);
				SysManageIquidInstrument sysManageIquidInstrument = sysManageIquidInstrumentDao.selectLast();

				logger.info("探棒工作模式："+sysManageIquidInstrument.getWorktype());
				if (sysManageIquidInstrument!=null&&sysManageIquidInstrument.getWorktype()!=null&&sysManageIquidInstrument.getWorktype().trim().equals("探棒直联")){
					logger.info("探棒直连模式不下发");
					JOptionPane.showMessageDialog(null, "设置成功。", "信息", JOptionPane.INFORMATION_MESSAGE);
					Currentframe.dispose();
					return;
				}
				//endregion
 				SysManageAlarmParameter record = new SysManageAlarmParameter();

				record.setOilcan(Integer.parseInt(xgbhLab.getText()));
				record.setLowprealarm(Double.parseDouble(dywyjTextField.getText()));
				record.setLowalarm(Double.parseDouble(dywbjTextField.getText()));
				record.setHighprealarm(Double.parseDouble(gywyjTextField.getText()));
				record.setHighalarm(Double.parseDouble(gywbjTextField.getText()));
				record.setWateralarm(Double.parseDouble(gswyjTextField.getText()));
				record.setWaterhightalarm(Double.parseDouble(gswbjTextField.getText()));
				if(stbjTextField.getText()!=null&&!"".equals(stbjTextField.getText())) {
					record.setLeakageoilalarm(Double.parseDouble(stbjTextField.getText()));
				}else{
					record.setLeakageoilalarm(0.0);
				}
				if(dybjTextField.getText()!=null&&!"".equals(dybjTextField.getText())) {
					record.setStealoilalarm(Double.parseDouble(dybjTextField.getText()));
				}else{
					record.setStealoilalarm(0.0);
				}
				if(lybjTextField.getText()!=null&&!"".equals(lybjTextField.getText())) {
					record.setLeakoilalarm(Double.parseDouble(lybjTextField.getText()));
				}else{
					record.setLeakoilalarm(0.0);
				}
				record.setHightempalarm(Double.parseDouble(gwbjTextField.getText()));
				record.setLowtempalarm(Double.parseDouble(dwbjTextField.getText()));
				record.setOptime(new Date());
				//todo:下发到液位仪
				//	almService.updateByPrimaryKey(record);
				ArrayList<SysManageAlarmParameter> list = new ArrayList<SysManageAlarmParameter>();
				list.add(record);
				//System.out.println("SysManageAlarmParameter:" + record);
				//System.out.println("SysManageAlarmParameter list size" + list.size());
				logger.info("SysManageAlarmParameter list size"+list.size());
				GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, list, Constants.PID_Code.A15_10010.toString());
				//System.out.println("send:" + gasMsg.toString());
				Main.CC.writeAndFlush(gasMsg);
				logger.info("SysManageAlarmParameter work is done~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}
		});

		dywyjTextField.setEditable(false);
		dywbjTextField.setEditable(false);
		gywyjTextField.setEditable(false);
		gywbjTextField.setEditable(false);
		gswyjTextField.setEditable(false);
		gswbjTextField.setEditable(false);
		dybjTextField.setEditable(false);
		lybjTextField.setEditable(false);
		stbjTextField.setEditable(false);
		gwbjTextField.setEditable(false);
		dwbjTextField.setEditable(false);
		almOptionPanel.setLayout(null);
		almOptionPanel.setSize(400,550) ;
		almOptionPanel.setVisible(true);
	}
	private JDialog Currentframe;
	public void setPanel(JDialog frame){
		//frame=new JDialog();
		frame.setModal(true);
		Currentframe = frame;
		int id=getSelectedRowID();
		if(id==-1)
		{
			JOptionPane.showMessageDialog(this, "请选择行数据。", "信息", JOptionPane.ERROR_MESSAGE);
			return;
		}

		SysManageAlarmParameter smap=almService.selectByPrimaryKey(id);
		//System.out.println("get samp from db:" + smap.getOilcan().toString());
		////System.out.println("smap.getLowprealarm().toString()"+smap.getLowprealarm().toString());
		xgbhLab.setText(smap.getOilcan().toString());
		dywyjTextField.setText(smap.getLowprealarm()==null?"": smap.getLowprealarm().toString());
		dywbjTextField.setText(smap.getLowalarm() == null ? "" : smap.getLowalarm().toString());
		gywyjTextField.setText(smap.getHighprealarm() == null ? "" : smap.getHighprealarm().toString());
		gywbjTextField.setText(smap.getHighalarm() == null ? "" : smap.getHighalarm().toString());
		gswyjTextField.setText(smap.getWateralarm() == null ? "" : smap.getWateralarm().toString());
		gswbjTextField.setText(smap.getWateralarm() == null ? "" : smap.getWaterhightalarm().toString());
		dybjTextField.setText(smap.getStealoilalarm() == null ? "" : smap.getStealoilalarm().toString());
		lybjTextField.setText(smap.getLeakoilalarm() == null ? "" : smap.getLeakoilalarm().toString());
		stbjTextField.setText(smap.getLeakageoilalarm() == null ? "" : smap.getLeakageoilalarm().toString());
		gwbjTextField.setText(smap.getHightempalarm() == null ? "" : smap.getHightempalarm().toString());
		dwbjTextField.setText(smap.getLowtempalarm() == null ? "" : smap.getLowtempalarm().toString());
		frame.setSize(780, 350);
    	frame.setLayout(new BorderLayout(0, 0));
    	frame.add(this.almOptionPanel);
		Main.setCenter(frame);
    	frame.setVisible(true);

	}

	@Override
	public void update(GasMsg gasMsg) {
		if("A15_10010".equals(gasMsg.getPid())){
			ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
			if ("1".equals(data.getResult())) {
				JOptionPane.showMessageDialog(null, "操作失败！", "警告", JOptionPane.INFORMATION_MESSAGE);
			}else if ("0".equals(data.getResult())){
				JOptionPane.showMessageDialog(null, "操作成功！", "提醒", JOptionPane.INFORMATION_MESSAGE);
			}
			Currentframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Currentframe.dispose();
		}

	}
}
