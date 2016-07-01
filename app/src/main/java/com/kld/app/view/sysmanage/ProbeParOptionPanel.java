package com.kld.app.view.sysmanage;

import com.kld.app.service.IquidService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.service.SysProbeService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.IntegerDocument;
import com.kld.app.util.ProbeDoubleDocument;
import com.kld.app.util.SuperDoubleDocument;
import com.kld.app.util.SuperStringDocument;
import com.kld.app.view.acceptance.ComboboxItem;
import com.kld.app.view.main.Constant;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.ATG.domain.SysManageProbePar;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.Socket.Constants;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.*;
import java.util.List;


/**
 * 
 * @author Aven
 * 探棒校正参数设置-添加&修改弹出panel
 */
public class ProbeParOptionPanel extends JPanel implements Watcher {
	Logger logger = Logger.getLogger(ProbeParOptionPanel.class);
	JPanel probeParOptionPanel;
	JTextField sbxhTextField; // 设备型号
	JTextField tbbhTextField; // 探棒编号
	JComboBox yplxComboBox; // 油品类型
	JTextField yw0djzTextField; // 油位0点校正(mm)
	JTextField sw0djzTextField; // 水位0点校正(mm)
	JTextField tbpyTextField; // 探棒偏移(mm)
	JTextField qxpyTextField; // 倾斜偏移(mm)
	JTextField wd1sczTextField; // 温度1实测值(℃)
	JTextField wd1tbclzTextField; // 温度1探棒测量值(℃)
	JTextField wd2sczTextField; // 温度2实测值(℃)
	JTextField wd2tbclzTextField; // 温度2探棒测量值(℃)
	JTextField wd3sczTextField; // 温度3实测值(℃)
	JTextField wd3tbclzTextField; // 温度3探棒测量值(℃)
	JTextField wd4sczTextField; // 温度4实测值(℃)
	JTextField wd4tbclzTextField; // 温度4探棒测量值(℃)
	JTextField wd5sczTextField; // 温度5实测值(℃)
	JTextField wd5tbclzTextField; // 温度5探棒测量值(℃)
	JTextField csmdTextField; // 初始密度（kg/m³）
	JTextField csgdcTextField; // 初始高度差（mm）
	JTextField mdxzxsTextField; // 密度的修正系数
	JComboBox ygbhComboBox; //油罐
	JTextField ygbhTextField; // 油罐
	JTextField tbdkTextField; // 探棒端口
	JButton saveBut;  //保存
	JButton updateBut;  //保存
	JButton closeBut; //关闭
	ProbeParPanel panel;
	JPanel cpanel;
 	Point point = Main.getFrameLocation();
	public ProbeParOptionPanel(){
		Double min=-999.99;
		Double max=999.99;
		// 注册观察者开始
		Watched watch = ConcreteWatched.getInstance();
		watch.addWetcher("A", this);
		// 注册观察者结束
		probeParOptionPanel=new JPanel();

		JLabel sbxhLabel = new JLabel("设备型号");
		sbxhLabel.setSize(Constant.width, Constant.height);
		sbxhLabel.setLocation(Constant.lie1, 10);
		sbxhTextField = new JTextField();
		sbxhTextField.setSize(Constant.width, Constant.height);
		sbxhTextField.setLocation(Constant.lie2, 10);
		sbxhTextField.setText("");                                       //查询Liquid
		sbxhTextField.setEditable(false);
		probeParOptionPanel.add(sbxhLabel);
		probeParOptionPanel.add(sbxhTextField);

		JLabel tbbhLabel = new JLabel("探棒编号");
		tbbhLabel.setSize(Constant.width, Constant.height);
		tbbhLabel.setLocation(Constant.lie1, 50);
		tbbhTextField = new JTextField();

		tbbhTextField.setDocument(new SuperStringDocument(32));
		tbbhTextField.setSize(Constant.width, Constant.height);
		tbbhTextField.setLocation(Constant.lie2, 50);
		probeParOptionPanel.add(tbbhLabel);
		probeParOptionPanel.add(tbbhTextField);

		JLabel yplxLabel = new JLabel("油品类型");
		yplxLabel.setSize(Constant.width, Constant.height);
		yplxLabel.setLocation(Constant.lie3, 50);
		yplxComboBox = new JComboBox();
		yplxComboBox.setSize(Constant.width, Constant.height);
		yplxComboBox.setLocation(Constant.lie4, 50);
		probeParOptionPanel.add(yplxLabel);
		probeParOptionPanel.add(yplxComboBox);

		JLabel tbdkLabel = new JLabel("探棒端口(1-256)");
		tbdkLabel.setSize(Constant.width, Constant.height);
		tbdkLabel.setLocation(Constant.lie1, 90);
		tbdkTextField = new JTextField();
		tbdkTextField.setSize(Constant.width, Constant.height);
		tbdkTextField.setLocation(Constant.lie2, 90);
		tbdkTextField.setDocument(new IntegerDocument(1, 256));
		probeParOptionPanel.add(tbdkLabel);
		probeParOptionPanel.add(tbdkTextField);

		JLabel yw0djzLabel = new JLabel("油位0点校正(mm)");
		yw0djzLabel.setSize(Constant.width, Constant.height);
		yw0djzLabel.setLocation(Constant.lie1, 130);
		yw0djzTextField = new JTextField();
		SuperDoubleDocument yw0=new SuperDoubleDocument(3,2);
		yw0.set_min(min);
		yw0.set_max(max);
		yw0.isfs=true;
		yw0djzTextField.setDocument(yw0);
		yw0djzTextField.setSize(Constant.width, Constant.height);
		yw0djzTextField.setLocation(Constant.lie2, 130);
		probeParOptionPanel.add(yw0djzLabel);
		probeParOptionPanel.add(yw0djzTextField);

		JLabel sw0djzLabel = new JLabel("水位0点校正(mm)");
		sw0djzLabel.setSize(Constant.width, Constant.height);
		sw0djzLabel.setLocation(Constant.lie1, 170);
		sw0djzTextField = new JTextField();
		SuperDoubleDocument sw0=new SuperDoubleDocument(3,2);
		sw0.set_min(min);
		sw0.set_max(max);
		sw0.isfs=true;
		sw0djzTextField.setDocument(sw0);
		sw0djzTextField.setSize(Constant.width, Constant.height);
		sw0djzTextField.setLocation(Constant.lie2, 170);
		probeParOptionPanel.add(sw0djzLabel);
		probeParOptionPanel.add(sw0djzTextField);



		JLabel wd1sczLabel = new JLabel("温度1实测值(℃)");
		wd1sczLabel.setSize(Constant.width, Constant.height);
		wd1sczLabel.setLocation(Constant.lie1, 210);
		wd1sczTextField = new JTextField();
		SuperDoubleDocument wd1=new SuperDoubleDocument(3,2);
		wd1.set_min(min);
		wd1.set_max(max);
		wd1.isfs=true;
		wd1sczTextField.setDocument(wd1);
		wd1sczTextField.setSize(Constant.width, Constant.height);
		wd1sczTextField.setLocation(Constant.lie2, 210);
		probeParOptionPanel.add(wd1sczLabel);
		probeParOptionPanel.add(wd1sczTextField);

		JLabel wd1tbclzLabel = new JLabel("温度1探棒测量值(℃)");
		wd1tbclzLabel.setSize(Constant.width, Constant.height);
		wd1tbclzLabel.setLocation(Constant.lie1, 250);
		wd1tbclzTextField = new JTextField();
		SuperDoubleDocument wd1tb=new SuperDoubleDocument(3,2);
		wd1tb.set_min(min);
		wd1tb.set_max(max);
		wd1tb.isfs=true;
		wd1tbclzTextField.setDocument(wd1tb);
		wd1tbclzTextField.setSize(Constant.width, Constant.height);
		wd1tbclzTextField.setLocation(Constant.lie2, 250);
		probeParOptionPanel.add(wd1tbclzLabel);
		probeParOptionPanel.add(wd1tbclzTextField);

		JLabel wd2sczLabel = new JLabel("温度2实测值(℃)");
		wd2sczLabel.setSize(Constant.width, Constant.height);
		wd2sczLabel.setLocation(Constant.lie1, 290);
		wd2sczTextField = new JTextField();
		SuperDoubleDocument wd2=new SuperDoubleDocument(3,2);
		wd2.set_min(min);
		wd2.set_max(max);
		wd2.isfs=true;
		wd2sczTextField.setDocument(wd2);
		wd2sczTextField.setSize(Constant.width, Constant.height);
		wd2sczTextField.setLocation(Constant.lie2, 290);
		probeParOptionPanel.add(wd2sczLabel);
		probeParOptionPanel.add(wd2sczTextField);

		JLabel wd2tbclzLabel = new JLabel("温度2探棒测量值(℃)");
		wd2tbclzLabel.setSize(Constant.width, Constant.height);
		wd2tbclzLabel.setLocation(Constant.lie1, 330);
		wd2tbclzTextField = new JTextField();
		SuperDoubleDocument wd2tb=new SuperDoubleDocument(3,2);
		wd2tb.set_min(min);
		wd2tb.set_max(max);
		wd2tb.isfs=true;
		wd2tbclzTextField.setDocument(wd2tb);
		wd2tbclzTextField.setSize(Constant.width, Constant.height);
		wd2tbclzTextField.setLocation(Constant.lie2, 330);
		probeParOptionPanel.add(wd2tbclzLabel);
		probeParOptionPanel.add(wd2tbclzTextField);

		JLabel wd3sczLabel = new JLabel("温度3实测值(℃)");
		wd3sczLabel.setSize(Constant.width, Constant.height);
		wd3sczLabel.setLocation(Constant.lie1, 370);
		wd3sczTextField = new JTextField();
		SuperDoubleDocument wd3=new SuperDoubleDocument(3,2);
		wd3.set_min(min);
		wd3.set_max(max);
		wd3.isfs=true;
		wd3sczTextField.setDocument(wd3);
		wd3sczTextField.setSize(Constant.width, Constant.height);
		wd3sczTextField.setLocation(Constant.lie2, 370);
		probeParOptionPanel.add(wd3sczLabel);
		probeParOptionPanel.add(wd3sczTextField);

		JLabel wd3tbclzLabel = new JLabel("温度3探棒测量值(℃)");
		wd3tbclzLabel.setSize(Constant.width, Constant.height);
		wd3tbclzLabel.setLocation(Constant.lie1, 410);
		wd3tbclzTextField = new JTextField();
		SuperDoubleDocument wd3tb=new SuperDoubleDocument(3,2);
		wd3tb.set_min(min);
		wd3tb.set_max(max);
		wd3tb.isfs=true;
		wd3tbclzTextField.setDocument(wd3tb);
		wd3tbclzTextField.setSize(Constant.width, Constant.height);
		wd3tbclzTextField.setLocation(Constant.lie2, 410);
		probeParOptionPanel.add(wd3tbclzLabel);
		probeParOptionPanel.add(wd3tbclzTextField);

		JLabel ygbhLab = new JLabel("油罐编号");
		ygbhLab.setSize(Constant.width, Constant.height);
		ygbhLab.setLocation(Constant.lie3, 10);
		ygbhComboBox = new JComboBox();
		ygbhComboBox.setSize(Constant.width, Constant.height);
		ygbhComboBox.setLocation(Constant.lie4, 10);
		ygbhTextField=new JTextField();
		ygbhTextField.setSize(Constant.width, Constant.height);
		ygbhTextField.setLocation(Constant.lie4, 10);
		ygbhTextField.setVisible(false);
		probeParOptionPanel.add(ygbhLab);
		probeParOptionPanel.add(ygbhComboBox);
		probeParOptionPanel.add(ygbhTextField);


		JLabel wd4sczLabel = new JLabel("温度4实测值(℃)");
		wd4sczLabel.setSize(Constant.width, Constant.height);
		wd4sczLabel.setLocation(Constant.lie3, 90);
		wd4sczTextField = new JTextField();
		SuperDoubleDocument wd4=new SuperDoubleDocument(3,2);
		wd4.set_min(min);
		wd4.set_max(max);
		wd4.isfs=true;
		wd4sczTextField.setDocument(wd4);
		wd4sczTextField.setSize(Constant.width, Constant.height);
		wd4sczTextField.setLocation(Constant.lie4, 90);
		probeParOptionPanel.add(wd4sczLabel);
		probeParOptionPanel.add(wd4sczTextField);

		JLabel wd4tbclzLabel = new JLabel("温度4探棒测量值(℃)");
		wd4tbclzLabel.setSize(Constant.width, Constant.height);
		wd4tbclzLabel.setLocation(Constant.lie3, 130);
		wd4tbclzTextField = new JTextField();
		SuperDoubleDocument wd4tb=new SuperDoubleDocument(3,2);
		wd4tb.set_min(min);
		wd4tb.set_max(max);
		wd4tb.isfs=true;
		wd4tbclzTextField.setDocument(wd4tb);
		wd4tbclzTextField.setSize(Constant.width, Constant.height);
		wd4tbclzTextField.setLocation(Constant.lie4, 130);
		probeParOptionPanel.add(wd4tbclzLabel);
		probeParOptionPanel.add(wd4tbclzTextField);

		JLabel wd5sczLabel = new JLabel("温度5实测值(℃)");
		wd5sczLabel.setSize(Constant.width, Constant.height);
		wd5sczLabel.setLocation(Constant.lie3, 170);
		wd5sczTextField = new JTextField();
		SuperDoubleDocument wd5=new SuperDoubleDocument(3,2);
		wd5.set_min(min);
		wd5.set_max(max);
		wd5.isfs=true;
		wd5sczTextField.setDocument(wd5);
		wd5sczTextField.setSize(Constant.width, Constant.height);
		wd5sczTextField.setLocation(Constant.lie4, 170);
		probeParOptionPanel.add(wd5sczLabel);
		probeParOptionPanel.add(wd5sczTextField);

		JLabel wd5tbclzLabel = new JLabel("温度5探棒测量值(℃)");
		wd5tbclzLabel.setSize(Constant.width, Constant.height);
		wd5tbclzLabel.setLocation(Constant.lie3, 210);
		wd5tbclzTextField = new JTextField();
		SuperDoubleDocument wd5tb=new SuperDoubleDocument(3,2);
		wd5tb.set_min(min);
		wd5tb.set_max(max);
		wd5tb.isfs=true;
		wd5tbclzTextField.setDocument(wd5tb);
		wd5tbclzTextField.setSize(Constant.width, Constant.height);
		wd5tbclzTextField.setLocation(Constant.lie4, 210);
		probeParOptionPanel.add(wd5tbclzLabel);
		probeParOptionPanel.add(wd5tbclzTextField);

		JLabel tbpyLabel = new JLabel("探棒偏移(mm)");
		tbpyLabel.setSize(Constant.width, Constant.height);
		tbpyLabel.setLocation(Constant.lie3, 250);
		tbpyTextField = new JTextField();
		SuperDoubleDocument tbpy=new SuperDoubleDocument(3,2);
		tbpy.set_min(min);
		tbpy.set_max(max);
		tbpy.isfs=true;
		tbpyTextField.setDocument(tbpy);
		tbpyTextField.setSize(Constant.width, Constant.height);
		tbpyTextField.setLocation(Constant.lie4, 250);
		probeParOptionPanel.add(tbpyLabel);
		probeParOptionPanel.add(tbpyTextField);

		JLabel qxpyLabel = new JLabel("倾斜偏移(mm)");
		qxpyLabel.setSize(Constant.width, Constant.height);
		qxpyLabel.setLocation(Constant.lie3, 290);
		qxpyTextField = new JTextField();
		SuperDoubleDocument qxpy=new SuperDoubleDocument(3,2);
		qxpy.set_min(min);
		qxpy.set_max(max);
		qxpy.isfs=true;
		qxpyTextField.setDocument(qxpy);
		qxpyTextField.setSize(Constant.width, Constant.height);
		qxpyTextField.setLocation(Constant.lie4, 290);
		probeParOptionPanel.add(qxpyLabel);
		probeParOptionPanel.add(qxpyTextField);

		JLabel csmdLabel = new JLabel("初始密度(kg/m³)");
		csmdLabel.setSize(Constant.width, Constant.height);
		csmdLabel.setLocation(Constant.lie3, 330);
		csmdTextField = new JTextField();
		SuperDoubleDocument csmd=new SuperDoubleDocument(3,2);
		csmd.set_min(0d);
		csmd.set_max(max);
		csmd.isfs=false;
		csmdTextField.setDocument(csmd);
		csmdTextField.setSize(Constant.width, Constant.height);
		csmdTextField.setLocation(Constant.lie4, 330);
		probeParOptionPanel.add(csmdLabel);
		probeParOptionPanel.add(csmdTextField);

		JLabel csgdcLabel = new JLabel("初始高度差(mm)");
		csgdcLabel.setSize(Constant.width, Constant.height);
		csgdcLabel.setLocation(Constant.lie3, 370);
		csgdcTextField = new JTextField();
		SuperDoubleDocument csgd=new SuperDoubleDocument(3,2);
		csgd.set_min(min);
		csgd.set_max(max);
		csgd.isfs=true;
		csgdcTextField.setDocument(csgd);
		csgdcTextField.setSize(Constant.width, Constant.height);
		csgdcTextField.setLocation(Constant.lie4, 370);
		probeParOptionPanel.add(csgdcLabel);
		probeParOptionPanel.add(csgdcTextField);

		JLabel mdxzxsLabel = new JLabel("密度的修正系数");
		mdxzxsLabel.setSize(Constant.width, Constant.height);
		mdxzxsLabel.setLocation(Constant.lie3, 410);
		mdxzxsTextField = new JTextField();
		SuperDoubleDocument mdxz=new SuperDoubleDocument(3,2);
		mdxz.set_min(min);
		mdxz.set_max(max);
		mdxz.isfs=true;
		mdxzxsTextField.setDocument(mdxz);
		mdxzxsTextField.setSize(Constant.width, Constant.height);
		mdxzxsTextField.setLocation(Constant.lie4, 410);
		probeParOptionPanel.add(mdxzxsLabel);
		probeParOptionPanel.add(mdxzxsTextField);

		saveBut = new JButton("保存");
		saveBut.setSize(Constant.widthBut,Constant.heightBut);
		saveBut.setLocation(Constant.lie2 + 120, 450);
		probeParOptionPanel.add(saveBut);
		saveBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				SysProbeService probeOptionService = (SysProbeService) (Context.getInstance().getBean("probeService"));
				try {

					//此处判断探棒编号是否存在
						SysManageProbePar smpp=probeOptionService.selectModelByProbemodel(tbbhTextField.getText());
						int countOilCan=probeOptionService.ExisOilCan(Integer.parseInt(ygbhComboBox.getSelectedItem().toString()));
						if(countOilCan==0) {
							boolean bHaveNull=false;
							String strIsNull="";
							if(tbbhTextField.getText().trim().length()==0&&!bHaveNull) {
								bHaveNull=true;
								strIsNull=strIsNull+"探棒编号不允许为空";
								tbbhTextField.requestFocusInWindow();
							}
							if(tbdkTextField.getText().trim().length()==0&&!bHaveNull) {
								bHaveNull=true;
								strIsNull=strIsNull+"探棒端口不允许为空";
								tbdkTextField.requestFocusInWindow();
							}
							if(bHaveNull)
							{
								JOptionPane.showMessageDialog(cpanel, strIsNull, "提示信息", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							int countProbePort=probeOptionService.ExisProbePort(Integer.parseInt(tbdkTextField.getText()));
							if(countProbePort==0&&!bHaveNull) {
								//region 此处加上判断是否为空的语句
								if(sbxhTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"设备型号不允许为空";
									sbxhTextField.requestFocusInWindow();
								}
								HashMap<String,JTextField> jts=new HashMap<String, JTextField>();
								jts.put("油位0点校正",yw0djzTextField);
								jts.put("水位0点校正",sw0djzTextField);
								jts.put("探棒偏移",tbpyTextField);
								jts.put("倾斜偏移",qxpyTextField);
								jts.put("温度1实测值",wd1sczTextField);
								jts.put("温度1探棒测量值",wd1tbclzTextField);
								jts.put("温度2实测值",wd2sczTextField);
								jts.put("温度2探棒测量值",wd2tbclzTextField);
								jts.put("温度3实测值",wd3sczTextField);
								jts.put("温度3探棒测量值",wd3tbclzTextField);
								jts.put("温度4实测值",wd4sczTextField);
								jts.put("温度4探棒测量值",wd4tbclzTextField);
								jts.put("温度5实测值",wd5sczTextField);
								jts.put("温度5探棒测量值",wd5tbclzTextField);
								jts.put("初始密度",csmdTextField);
								jts.put("初始高度差",csgdcTextField);
								jts.put("密度的修正系数",mdxzxsTextField);
								if (!textVaild(jts).equals("")){
									bHaveNull=true;
									strIsNull=textVaild(jts);
								}
								//region 废弃的代码
								/*if(yw0djzTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"油位0点校正不允许为空";
									yw0djzTextField.requestFocusInWindow();
								}
								if(!istransDouble(yw0djzTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"油位0点校正数据格式错误";
									yw0djzTextField.requestFocusInWindow();
								}

								if(sw0djzTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"水位0点校正不允许为空";
									sw0djzTextField.requestFocusInWindow();
								}
								if(!istransDouble(sw0djzTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"水位0点校正数据格式错误";
									sw0djzTextField.requestFocusInWindow();
								}

								if(tbpyTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"探棒偏移不允许为空";
									tbpyTextField.requestFocusInWindow();
								}
								if(!istransDouble(tbpyTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"探棒偏移数据格式错误";
									tbpyTextField.requestFocusInWindow();
								}


								if(qxpyTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"倾斜偏移不允许为空";
									qxpyTextField.requestFocusInWindow();
								}
								if(!istransDouble(qxpyTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"倾斜偏移数据格式错误";
									qxpyTextField.requestFocusInWindow();
								}


								if(wd1sczTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度1实测值不允许为空";
									wd1sczTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd1sczTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度1实测值数据格式错误";
									wd1sczTextField.requestFocusInWindow();
								}

								if(wd1tbclzTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度1探棒测量值不允许为空";
									wd1tbclzTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd1tbclzTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度1探棒测量值数据格式错误";
									wd1tbclzTextField.requestFocusInWindow();
								}


								if(wd2sczTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度2实测值不允许为空";
									wd2sczTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd2sczTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度2实测值数据格式错误";
									wd2sczTextField.requestFocusInWindow();
								}

								if(wd2tbclzTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度2探棒测量值不允许为空";
									wd2tbclzTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd2tbclzTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度2探棒测量值数据格式错误";
									wd2tbclzTextField.requestFocusInWindow();
								}


								if(wd3sczTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度3实测值不允许为空";
									wd3sczTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd3sczTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度3实测值数据格式错误";
									wd3sczTextField.requestFocusInWindow();
								}

								if(wd3tbclzTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度3探棒测量值不允许为空";
									wd3tbclzTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd3tbclzTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度3探棒测量值数据格式错误";
									wd3tbclzTextField.requestFocusInWindow();
								}


								if(wd4sczTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度4实测值不允许为空";
									wd4sczTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd4sczTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度4实测值数据格式错误";
									wd4sczTextField.requestFocusInWindow();
								}

								if(wd4tbclzTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度4探棒测量值不允许为空";
									wd4tbclzTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd4tbclzTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度4探棒测量值数据格式错误";
									wd4tbclzTextField.requestFocusInWindow();
								}


								if(wd5sczTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度5实测值不允许为空";
									wd5sczTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd5sczTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度5实测值数据格式错误";
									wd5sczTextField.requestFocusInWindow();
								}


								if(wd5tbclzTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度5探棒测量值不允许为空";
									wd5tbclzTextField.requestFocusInWindow();
								}
								if(!istransDouble(wd5tbclzTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"温度5实测值数据格式错误";
									wd5tbclzTextField.requestFocusInWindow();
								}


								if(csmdTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"初始密度不允许为空";
									csmdTextField.requestFocusInWindow();
								}
								if(!istransDouble(csmdTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"初始密度数据格式错误";
									csmdTextField.requestFocusInWindow();
								}

								if(csgdcTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+ "初始高度差不允许为空";
									csgdcTextField.requestFocusInWindow();
								}
								if(!istransDouble(csgdcTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"初始高度差数据格式错误";
									csgdcTextField.requestFocusInWindow();
								}

								if(mdxzxsTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"密度的修正系数不允许为空";
									mdxzxsTextField.requestFocusInWindow();
								}
								if(!istransDouble(mdxzxsTextField.getText().trim())&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"密度的修正系数数据格式错误";
									mdxzxsTextField.requestFocusInWindow();
								}*/
								//endregion

								if(bHaveNull)
								{
									JOptionPane.showMessageDialog(cpanel, strIsNull, "提示信息", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								//endregion
								//region 设置数据
								smpp = new SysManageProbePar();
								smpp.setDevicemodel(sbxhTextField.getText());
								smpp.setProbemodel(tbbhTextField.getText());
								//ComboboxItem item=(ComboboxItem) yplxComboBox.getSelectedItem();
								String Oilno = GetOilTypeCode();
								smpp.setOilno(Oilno);
								smpp.setOilzero(Double.parseDouble(yw0djzTextField.getText()));
								smpp.setWaterzero(Double.parseDouble(sw0djzTextField.getText()));
								smpp.setProbeskew(Double.parseDouble(tbpyTextField.getText()));
								smpp.setInclineskew(Double.parseDouble(qxpyTextField.getText()));
								smpp.setCreatetime(new Date()); //当前日期
								smpp.setLastadjusttime(new Date());
								smpp.setRealtemp1(Double.parseDouble(wd1sczTextField.getText()));
								smpp.setProrealtemp1(Double.parseDouble(wd1tbclzTextField.getText()));
								smpp.setRealtemp2(Double.parseDouble(wd2sczTextField.getText()));
								smpp.setProrealtemp2(Double.parseDouble(wd2tbclzTextField.getText()));
								smpp.setRealtemp3(Double.parseDouble(wd3sczTextField.getText()));
								smpp.setProrealtemp3(Double.parseDouble(wd3tbclzTextField.getText()));
								smpp.setRealtemp4(Double.parseDouble(wd4sczTextField.getText()));
								smpp.setProrealtemp4(Double.parseDouble(wd4tbclzTextField.getText()));
								smpp.setRealtemp5(Double.parseDouble(wd5sczTextField.getText()));
								smpp.setProrealtemp5(Double.parseDouble(wd5tbclzTextField.getText()));
								smpp.setInitdesnsity(Double.parseDouble(csmdTextField.getText()));
								smpp.setInithighdiff(Double.parseDouble(csgdcTextField.getText()));
								smpp.setCorrectionfactor(Double.parseDouble(mdxzxsTextField.getText()));
								smpp.setOilcan(Integer.parseInt(ygbhComboBox.getSelectedItem().toString()));
								smpp.setProbeport(Integer.parseInt(tbdkTextField.getText()));
								smpp.setTranstatus("0");
								//endregion
							}
							else {
								JOptionPane.showMessageDialog(cpanel, "端口已被设置！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
						else {
							JOptionPane.showMessageDialog(cpanel, "油罐已被设置！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
					    	return;
						}
						/*	System.out.println("close:"+cc.close().isSuccess());
							System.out.println("isOpen:"+cc.isOpen());*/

							ArrayList<SysManageProbePar> list = new ArrayList<SysManageProbePar>();
							list.add(smpp);
							logger.info("smpp:" + smpp);
							try{
								probeOptionService.insert(smpp);
								GasMsg gasMsg7 = ResultUtils.getInstance().sendSUCCESS(Main.sign,
										list, Constants.PID_Code.A15_10007.toString());
								Main.CC.writeAndFlush(gasMsg7);
							}
							catch (Exception exc){
								JOptionPane.showMessageDialog(cpanel, "链接失败！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
							}

/*
						JOptionPane.showMessageDialog(cpanel, "添加成功！", "", JOptionPane.INFORMATION_MESSAGE);
						panel.setPanel(cpanel);

						Currentframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						Currentframe.dispose();*/

				}
				catch (Exception ex)
				{
					System.out.println("此处打印异常信息"+ex);
					JOptionPane.showMessageDialog(cpanel, "添加失败！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
				}



			}
		});

		updateBut = new JButton("保存");
		updateBut.setSize(Constant.widthBut,Constant.heightBut);
		updateBut.setLocation(Constant.lie2+120,450);
		probeParOptionPanel.add(updateBut);
		updateBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				SysProbeService probeOptionService =(SysProbeService) (Context.getInstance().getBean("probeService"));
				try {

					//此处判断探棒编号是否存在


							boolean bHaveNull=false;
							String strIsNull="";
							if(tbbhTextField.getText().trim().length()==0&&!bHaveNull) {
								bHaveNull=true;
								strIsNull=strIsNull+"探棒编号不允许为空";
								tbbhTextField.requestFocusInWindow();
							}
							if(tbdkTextField.getText().trim().length()==0&&!bHaveNull) {
								bHaveNull=true;
								strIsNull=strIsNull+"探棒端口不允许为空";
								tbdkTextField.requestFocusInWindow();
							}
							if(bHaveNull)
							{
								JOptionPane.showMessageDialog(cpanel, strIsNull, "提示信息", JOptionPane.INFORMATION_MESSAGE);
								return;
							}

								//region 此处加上判断是否为空的语句
								if(sbxhTextField.getText().trim().length()==0&&!bHaveNull) {
									bHaveNull=true;
									strIsNull=strIsNull+"设备型号不允许为空";
									sbxhTextField.requestFocusInWindow();
								}
								HashMap<String,JTextField> jts=new HashMap<String, JTextField>();
								jts.put("油位0点校正",yw0djzTextField);
								jts.put("水位0点校正",sw0djzTextField);
								jts.put("探棒偏移",tbpyTextField);
								jts.put("倾斜偏移",qxpyTextField);
								jts.put("温度1实测值",wd1sczTextField);
								jts.put("温度1探棒测量值",wd1tbclzTextField);
								jts.put("温度2实测值",wd2sczTextField);
								jts.put("温度2探棒测量值",wd2tbclzTextField);
								jts.put("温度3实测值",wd3sczTextField);
								jts.put("温度3探棒测量值",wd3tbclzTextField);
								jts.put("温度4实测值",wd4sczTextField);
								jts.put("温度4探棒测量值",wd4tbclzTextField);
								jts.put("温度5实测值",wd5sczTextField);
								jts.put("温度5探棒测量值",wd5tbclzTextField);
								jts.put("初始密度",csmdTextField);
								jts.put("初始高度差",csgdcTextField);
								jts.put("密度的修正系数",mdxzxsTextField);
								if (!textVaild(jts).equals("")){
									bHaveNull=true;
									strIsNull=textVaild(jts);
								}

								if(bHaveNull)
								{
									JOptionPane.showMessageDialog(cpanel, strIsNull, "提示信息", JOptionPane.INFORMATION_MESSAGE);
									return;
								}



				/*	int countProbePort=probeOptionService.ExisProbePort(Integer.parseInt(tbdkTextField.getText()));
					if(countProbePort==0) {*/
						SysManageProbePar smpp = new SysManageProbePar();
						smpp.setDevicemodel(sbxhTextField.getText());
						smpp.setProbemodel(tbbhTextField.getText());
						//ComboboxItem item=(ComboboxItem)yplxComboBox.getSelectedItem();
						String Oilno = GetOilTypeCode();
						smpp.setOilno(Oilno);
						smpp.setOilzero(Double.parseDouble(yw0djzTextField.getText()));
						smpp.setWaterzero(Double.parseDouble(sw0djzTextField.getText()));
						smpp.setProbeskew(Double.parseDouble(tbpyTextField.getText()));
						smpp.setInclineskew(Double.parseDouble(qxpyTextField.getText()));
						smpp.setCreatetime(new Date()); //当前日期
						smpp.setLastadjusttime(new Date());
						smpp.setRealtemp1(Double.parseDouble(wd1sczTextField.getText()));
						smpp.setProrealtemp1(Double.parseDouble(wd1tbclzTextField.getText()));
						smpp.setRealtemp2(Double.parseDouble(wd2sczTextField.getText()));
						smpp.setProrealtemp2(Double.parseDouble(wd2tbclzTextField.getText()));
						smpp.setRealtemp3(Double.parseDouble(wd3sczTextField.getText()));
						smpp.setProrealtemp3(Double.parseDouble(wd3tbclzTextField.getText()));
						smpp.setRealtemp4(Double.parseDouble(wd4sczTextField.getText()));
						smpp.setProrealtemp4(Double.parseDouble(wd4tbclzTextField.getText()));
						smpp.setRealtemp5(Double.parseDouble(wd5sczTextField.getText()));
						smpp.setProrealtemp5(Double.parseDouble(wd5tbclzTextField.getText()));
						smpp.setInitdesnsity(Double.parseDouble(csmdTextField.getText()));
						smpp.setInithighdiff(Double.parseDouble(csgdcTextField.getText()));
						smpp.setCorrectionfactor(Double.parseDouble(mdxzxsTextField.getText()));

						smpp.setOilcan(Integer.parseInt(ygbhTextField.getText()));
						smpp.setProbeport(Integer.parseInt(tbdkTextField.getText()));

							logger.info("smpp:"+smpp);
							ArrayList<SysManageProbePar> list = new ArrayList<SysManageProbePar>();
							list.add(smpp);
							try {
								probeOptionService.update(smpp);
								GasMsg gasMsg7 = ResultUtils.getInstance().sendSUCCESS(Main.sign,
										list, Constants.PID_Code.A15_10007.toString());
								Main.CC.writeAndFlush(gasMsg7);
							}
							catch (Exception exc){
								JOptionPane.showMessageDialog(cpanel, "链接失败！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
							}

				/*	}
					else {
						JOptionPane.showMessageDialog(cpanel, "端口已被设置！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
						return;
					}*/

					//JOptionPane.showMessageDialog(cpanel, " 修改成功！", "", JOptionPane.INFORMATION_MESSAGE);

				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(cpanel, "修改失败！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

//		closeBut = new JButton("关闭");
//		closeBut.setSize(Constant.widthBut,Constant.heightBut);
//		closeBut.setLocation(Constant.lie3,410);
//		probeParOptionPanel.add(closeBut);

		probeParOptionPanel.setLayout(null);
		probeParOptionPanel.setSize(800,550) ;
		probeParOptionPanel.setVisible(true);
	}

	private String textVaild(HashMap<String,JTextField> textFields){
		boolean bHaveNull=false;
		String strIsNull="";
		for (Map.Entry<String,JTextField> item:textFields.entrySet()){
			JTextField txtf=item.getValue();
			if(txtf.getText().trim().length()==0&&!bHaveNull) {
				bHaveNull=true;
				strIsNull=strIsNull+item.getKey()+"不允许为空";
				txtf.requestFocusInWindow();
				break;
			}

			if(!istransDouble(txtf.getText().trim())&&!bHaveNull) {
				bHaveNull=true;
				strIsNull=strIsNull+item.getKey()+"数据格式错误";
				txtf.requestFocusInWindow();
				break;
			}
		}

		return strIsNull;
	}


	private boolean istransDouble(String str){
		boolean result=false;
		try	{
			Double.parseDouble(str);
			result=true;
		}catch (Exception e){
			result=false;
		}
		return result;
	}

     private  JDialog Currentframe;
	public void setPanel(ProbeParPanel probeParPanel,JDialog frame,JPanel centerPanel){
		panel=probeParPanel;
		cpanel=centerPanel;
		Currentframe=frame;
		Currentframe.setModal(true);
        IquidService iquidService =(IquidService) (Context.getInstance().getBean("iquidService"));
        //设备型号
        String SBXH= iquidService.selectSBXH();
		SysManageCanInfoService tankInfoService=(SysManageCanInfoService) (Context.getInstance().getBean("SysManageTankInfoService"));
		List<SysManageCanInfo> TankInfoLst=tankInfoService.selectAll();
		for (SysManageCanInfo item:TankInfoLst){
			ygbhComboBox.addItem(item.getOilcan());
		}
		//初始化油品类型
		InitOilType();
		sbxhTextField.setText(SBXH);
		updateBut.setVisible(false);
		frame.setBounds(290, 150, 800, 550);
		frame.setLocation(point.x, point.y);
		frame.setLayout(new BorderLayout(0, 0));
		frame.add(probeParOptionPanel);
    	frame.setVisible(true);
	}

	public void setPanel2(ProbeParPanel probeParPanel,JDialog frame,JPanel centerPanel){
		//ProbeParOptionPanel panel=new ProbeParOptionPanel();
        try {
			panel = probeParPanel;
			cpanel = centerPanel;
			Currentframe=frame;
			Currentframe.setModal(true);
			SysManageProbePar smp = probeParPanel.getInfo();
			if (smp != null) {
				sbxhTextField.setText(smp.getDevicemodel());
				//System.out.println(smp.getDevicemodel());
				//System.out.println(sbxhTextField.getText());
				tbbhTextField.setText(smp.getProbemodel());
				//System.out.println(smp.getProbemodel());
				//System.out.println(tbbhTextField.getText());

				InitOilType();
				yplxComboBox.setSelectedItem(smp.getOilno());

				yw0djzTextField.setText(smp.getOilzero().toString());
				sw0djzTextField.setText(smp.getWaterzero().toString());
				tbpyTextField.setText(smp.getProbeskew().toString());
				qxpyTextField.setText(smp.getInclineskew().toString());
				wd1sczTextField.setText(smp.getRealtemp1().toString());
				wd1tbclzTextField.setText(smp.getProrealtemp1().toString());
				wd2sczTextField.setText(smp.getRealtemp2().toString());
				wd2tbclzTextField.setText(smp.getProrealtemp2().toString());
				wd3sczTextField.setText(smp.getRealtemp3().toString());
				wd3tbclzTextField.setText(smp.getProrealtemp3().toString());
				wd4sczTextField.setText(smp.getRealtemp4().toString());
				wd4tbclzTextField.setText(smp.getProrealtemp4().toString());
				wd5sczTextField.setText(smp.getRealtemp5().toString());
				wd5tbclzTextField.setText(smp.getProrealtemp5().toString());
				csmdTextField.setText(smp.getInitdesnsity().toString());
				csgdcTextField.setText(smp.getInithighdiff().toString());
				mdxzxsTextField.setText(smp.getCorrectionfactor().toString());
				tbdkTextField.setText(smp.getProbeport().toString());
				/*SysManageCanInfoService tankInfoService = (SysManageCanInfoService) (Context.getInstance().getBean("SysManageTankInfoService"));
				List<SysManageCanInfo> TankInfoLst = tankInfoService.selectAll();
				for (SysManageCanInfo item : TankInfoLst) {
					ygbhComboBox.addItem(item.getOilcan());
				}
				ygbhComboBox.setSelectedItem(smp.getOilcan());
				ygbhComboBox.setEditable(false);
				ygbhComboBox.setEditable(false);*/
				ygbhComboBox.setVisible(false);
				ygbhTextField.setText(smp.getOilcan().toString());
				ygbhTextField.setEnabled(false);
				ygbhTextField.setVisible(true);
				saveBut.setVisible(false);
				tbbhTextField.setEditable(false);
				frame.setBounds(250, 150, 800, 550);
				frame.setLayout(new BorderLayout(0, 0));
				frame.setLocation(point.x, point.y);
				frame.add(probeParOptionPanel);
				frame.setVisible(true);
			}
			else
				JOptionPane.showMessageDialog(centerPanel, "选择一条探棒设置信息", "提示信息", JOptionPane.INFORMATION_MESSAGE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private  void InitOilType(){
		SysManageDic sysManageDic=Context.getInstance().getBean(SysManageDic.class);
		List<SysManageDict> sysManageDicts=sysManageDic.getByParentCode("yplx");
		List<ComboboxItem> OilTypeLst=new ArrayList<ComboboxItem>();
		for (SysManageDict dic:sysManageDicts){
			ComboboxItem item=new ComboboxItem(dic.getValue(),dic.getName());
			yplxComboBox.addItem(item);
		}

		//region oldcode
		/*OilTypeLst.add("汽油");
		OilTypeLst.add("高标号汽油");
		OilTypeLst.add("低标号汽油");
		OilTypeLst.add("乙醇汽油(E10-E20)");
		OilTypeLst.add("乙醇汽油(E21-E40)");
		OilTypeLst.add("乙醇汽油(E41-E60)");
		OilTypeLst.add("乙醇汽油(E61-E80)");
		OilTypeLst.add("乙醇汽油(E81-E100)");
		OilTypeLst.add("甲醇汽油");
		OilTypeLst.add("柴油");
		OilTypeLst.add("生物柴油");
		OilTypeLst.add("煤油");
		OilTypeLst.add("液化石油气");
		for(String item:OilTypeLst){

		}*/
		//endregion
	}

	private  String GetOilTypeCode(){
		return  ((ComboboxItem) yplxComboBox.getSelectedItem()).getKey();
		//region oldcode
		/*if(yplxComboBox.getSelectedItem().equals("汽油"))
			return  "1000";
		else  if(yplxComboBox.getSelectedItem().equals("高标号汽油"))
			return  "1901";
		else  if(yplxComboBox.getSelectedItem().equals("低标号汽油"))
			return  "1902";
		else  if(yplxComboBox.getSelectedItem().equals("乙醇汽油(E10-E20)"))
			return  "1911";
		else  if(yplxComboBox.getSelectedItem().equals("乙醇汽油(E21-E40)"))
			return  "1912";
		else  if(yplxComboBox.getSelectedItem().equals("乙醇汽油(E41-E60)"))
			return  "1913";
		else  if(yplxComboBox.getSelectedItem().equals("乙醇汽油(E61-E80)"))
			return  "1914";
		else  if(yplxComboBox.getSelectedItem().equals("乙醇汽油(E81-E100)"))
			return  "1915";
		else  if(yplxComboBox.getSelectedItem().equals("甲醇汽油"))
			return  "1921";
		else  if(yplxComboBox.getSelectedItem().equals("柴油"))
			return  "2000";
		else  if(yplxComboBox.getSelectedItem().equals("生物柴油"))
			return  "2901";
		else  if(yplxComboBox.getSelectedItem().equals("煤油"))
			return  "3000";
		else  if(yplxComboBox.getSelectedItem().equals("液化石油气"))
			return  "4000";
		else
			return "";*/
		//endregion
	}

	
	public static void main(String args[]){
/*		JFrame frame = new JFrame() ;
		frame.setLayout(null);
		ProbeParOptionPanel panel=new ProbeParOptionPanel();
		frame.add(panel.probeParOptionPanel) ;
		frame.setSize(800,600) ;
		frame.setLocation(300,200) ;
		frame.setVisible(true) ;
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent a){
				System.exit(1) ;
			}
		}) ;*/
	}

	@Override
	public void update(GasMsg gasMsg) {
		ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
		/*//System.out.println("PID_Code"+gasMsg.getPid());*/
		if(gasMsg.getPid().equals(Constants.PID_Code.A15_10007.toString())) {
			String	ret = data.getResult();
			if ("0".equals(ret)) {//成功
				JOptionPane.showMessageDialog(cpanel, "液位仪连接成功，探棒设置成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);
				panel.setPanel(cpanel);
			} else {
				JOptionPane.showMessageDialog(cpanel, "设置失败！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			}
			Currentframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Currentframe.dispose();
		}
	}
}
