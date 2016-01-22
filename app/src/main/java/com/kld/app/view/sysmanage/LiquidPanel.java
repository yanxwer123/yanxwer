package com.kld.app.view.sysmanage;

import com.kld.app.service.IquidService;
import com.kld.app.service.SysManageDictService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.IntegerDocument;
import com.kld.app.view.main.Constant;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.ATG.domain.SysManageIquidInstrument;
import com.kld.gsm.Socket.Constants;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class LiquidPanel extends JPanel implements Watcher{
	Logger logger = Logger.getLogger("LiquidPanel");
	public JPanel liquidPanel;
	JPanel centerPanels;
    private JButton iquidSaveButton;
    private GridBagLayout layoutmain;
    private GridBagConstraints smain;
    JComboBox csJComboBox;//厂商
    JComboBox sbxhJComboBox;//设备型号
    JComboBox cjlxJComboBox;//采集类型
    JComboBox txmsJComboBox;//通讯模式
    JTextField ckdzTextField;//串口地址
    JComboBox btlJComboBox1;//波特率1
    JComboBox btlJComboBox2;//波特率2
    JComboBox tzwJComboBox;//停止位
    JComboBox jywJComboBox;//检验位
    JComboBox sjwJComboBox;//数据位
    JTextField scjzsjTextField;//上次校正时间
    JTextField IPdzJTextField;//IP地址
    JTextField IPdkJTextField;//IP端口
	JTextField TranStatusField;//串口地址
    JButton liquidAddBut;  //保存
    JLabel csJLabel =    new JLabel("         生产厂商：");
	JLabel ipLabel1 =    new JLabel("         网络地址：");
	JLabel sbxhLabel =   new JLabel("         设备型号：");
	JLabel cjlxLabel =   new JLabel("         采集类型：");
	JLabel txmsLabel =   new JLabel("         通讯模式：");
	JLabel remakLabel =  new JLabel();
	JLabel ckdzLabel =   new JLabel("         串口地址：");
	JLabel btlLabel1 =   new JLabel("         波  特  率：");
	JLabel tzwLabel =    new JLabel("         停  止  位：");
	JLabel jywLabel =    new JLabel("         检  验  位：");
	JLabel sjwLabel =    new JLabel("         数  据  位：");
	JLabel scjzsjLabel = new JLabel("         上次设置：");
	JLabel portLabel2 =  new JLabel("         网络端口：");
	String ret;//液位仪接口返回的是否成功状态
	JLabel TranStatusLabel = new JLabel("         是否成功：");
	
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    public void  initLiquidPanel() {

		// 注册观察者开始
		Watched watch = ConcreteWatched.getInstance();
		watch.addWetcher("A", this);
 		// 注册观察者结束
    	IPdzJTextField = new JTextField();
    	IPdkJTextField= new JTextField();
    	liquidPanel= new JPanel();
		liquidPanel.setLayout(null);
    	csJComboBox = new JComboBox();
    	sbxhJComboBox = new JComboBox();
    	cjlxJComboBox = new JComboBox();
    	txmsJComboBox = new JComboBox();
    	btlJComboBox1 = new JComboBox();
    	btlJComboBox2 = new JComboBox();
    	tzwJComboBox = new JComboBox();
    	jywJComboBox = new JComboBox();
    	sjwJComboBox = new JComboBox();
		TranStatusField=new JTextField();
    	
    	final SysManageDictService dictService =(SysManageDictService) (Context.getInstance().getBean(SysManageDictService.class));
		List<SysManageDict> list=dictService.selectAll();

		csJComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					//厂商名称
					String csName = (String) csJComboBox.getSelectedItem();
					int DictID = dictService.selectDictidByName(csName);
					sbxhJComboBox.removeAllItems();
					List<String> sbNames = dictService.selectSBByDictID(DictID);
					for (String item : sbNames) {
						sbxhJComboBox.addItem(item);
					}
				}
			}
		});

		for (int i = 0; i < list.size(); i++)
			switch (list.get(i).getParentid()) {
				case 2:
					csJComboBox.addItem(list.get(i).getName());
					break;
				case 8:
					sbxhJComboBox.addItem(list.get(i).getName());
					break;
				case 12:
					cjlxJComboBox.addItem(list.get(i).getName());
					break;
				case 13:
					txmsJComboBox.addItem(list.get(i).getName());
					break;
				case 14:
					btlJComboBox1.addItem(list.get(i).getName());
					btlJComboBox2.addItem(list.get(i).getName());
					break;
				case 15:
					tzwJComboBox.addItem(list.get(i).getName());
					break;
				case 16:
					jywJComboBox.addItem(list.get(i).getName());
					break;
				case 17:
					sjwJComboBox.addItem(list.get(i).getName());

					break;
				default:
					break;
			}


		csJLabel.setSize(Constant.width, Constant.height);
		csJLabel.setLocation(Constant.lie1, 50);
		csJComboBox.setBounds(Constant.lie2, 50, Constant.width, Constant.height);//.setSize(Constant.width, Constant.height);
        //csJComboBox.setLocation(Constant.lie2, 50);
        liquidPanel.add(csJLabel);
        liquidPanel.add(csJComboBox);
		liquidPanel.updateUI();
		sbxhLabel.setSize(Constant.width, Constant.height);
		sbxhLabel.setLocation(Constant.lie1, 90);
		sbxhJComboBox.setSize(Constant.width, Constant.height);
		sbxhJComboBox.setLocation(Constant.lie2, 90);
        liquidPanel.add(sbxhLabel);
		liquidPanel.add(sbxhJComboBox);

		cjlxLabel.setSize(Constant.width, Constant.height);
		cjlxLabel.setLocation(Constant.lie1, 130);
		cjlxJComboBox.setSize(Constant.width, Constant.height);
		cjlxJComboBox.setLocation(Constant.lie2, 130);
        liquidPanel.add(cjlxLabel);
		liquidPanel.add(cjlxJComboBox);

		txmsLabel.setSize(Constant.width, Constant.height);
		txmsLabel.setLocation(Constant.lie1, 170);
		txmsJComboBox.setSize(Constant.width, Constant.height);
		txmsJComboBox.setLocation(Constant.lie2, 170);
        liquidPanel.add(txmsLabel);
		liquidPanel.add(txmsJComboBox);

		ckdzLabel.setSize(Constant.width, Constant.height);
        ckdzLabel.setLocation(Constant.lie1, 210);
        ckdzTextField = new JTextField();
		ckdzTextField.setSize(Constant.width, Constant.height);
		ckdzTextField.setLocation(Constant.lie2, 210);
        liquidPanel.add(ckdzLabel);
		liquidPanel.add(ckdzTextField);

		btlLabel1.setSize(Constant.width, Constant.height);
		btlLabel1.setLocation(Constant.lie3, 50);
		btlJComboBox1.setSize(Constant.width, Constant.height);
		btlJComboBox1.setLocation(Constant.lie4, 50);
        liquidPanel.add(btlLabel1);
		liquidPanel.add(btlJComboBox1);


		tzwLabel.setSize(Constant.width, Constant.height);
		tzwLabel.setLocation(Constant.lie3, 90);
		tzwJComboBox.setSize(Constant.width, Constant.height);
		tzwJComboBox.setLocation(Constant.lie4, 90);
        liquidPanel.add(tzwLabel);
		liquidPanel.add(tzwJComboBox);


		jywLabel.setSize(Constant.width, Constant.height);
		jywLabel.setLocation(Constant.lie3, 130);

		jywJComboBox.setSize(Constant.width, Constant.height);
		jywJComboBox.setLocation(Constant.lie4, 130);
        liquidPanel.add(jywLabel);
		liquidPanel.add(jywJComboBox);


		sjwLabel.setSize(Constant.width, Constant.height);
		sjwLabel.setLocation(Constant.lie3, 170);//原130
		sjwJComboBox.setSize(Constant.width, Constant.height);
		sjwJComboBox.setLocation(Constant.lie4, 170);
        liquidPanel.add(sjwLabel);
		liquidPanel.add(sjwJComboBox);

		TranStatusLabel.setSize(Constant.width, Constant.height);
		TranStatusLabel.setLocation(Constant.lie3, 10);//原170
		TranStatusField.setSize(Constant.width, Constant.height);
		TranStatusField.setLocation(Constant.lie4, 10);//原170
		TranStatusField.setEditable(false);
		liquidPanel.add(TranStatusLabel);
		liquidPanel.add(TranStatusField);


		scjzsjLabel.setSize(Constant.width, Constant.height);
        scjzsjLabel.setLocation(Constant.lie1, 10);
        scjzsjTextField = new JTextField();
		scjzsjTextField.setText(sdf.format(new Date()));
		scjzsjTextField.setSize(Constant.width, Constant.height);
        scjzsjTextField.setLocation(Constant.lie2, 10);
        scjzsjTextField.setEditable(false);
        liquidPanel.add(scjzsjLabel);
		liquidPanel.add(scjzsjTextField);


		ipLabel1.setSize(Constant.width, Constant.height);
		ipLabel1.setLocation(Constant.lie1, 210);
		IPdzJTextField.setSize(Constant.width, Constant.height);
        IPdzJTextField.setLocation(Constant.lie2, 210);
        liquidPanel.add(ipLabel1);
		liquidPanel.add(IPdzJTextField);
        ipLabel1.setVisible(false);
		IPdzJTextField.setVisible(false);

		portLabel2.setSize(Constant.width, Constant.height);
		portLabel2.setLocation(Constant.lie3, 210);
		IntegerDocument integerDocument=new IntegerDocument(1,65535);
		IPdkJTextField.setSize(Constant.width, Constant.height);
        IPdkJTextField.setLocation(Constant.lie4, 210);
		IPdkJTextField.setDocument(integerDocument);
        liquidPanel.add(portLabel2);
		liquidPanel.add(IPdkJTextField);
		remakLabel.setSize(Constant.width, Constant.height);
		remakLabel.setLocation(Constant.lie2, 235);
		liquidPanel.add(remakLabel);
		IPdkJTextField.setVisible(false);
		portLabel2.setVisible(false);
		if("网口".equals(txmsJComboBox.getSelectedItem())){
			remakLabel.setText("例如:192.168.0.0");
		}
		else{
			remakLabel.setText("例如:/dev/ttyS数字");
		}

        txmsJComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("网口".equals(txmsJComboBox.getSelectedItem())) {
					ipLabel1.setVisible(true);
					IPdzJTextField.setVisible(true);
					portLabel2.setVisible(true);
					IPdkJTextField.setVisible(true);
					ckdzLabel.setVisible(false);
					ckdzTextField.setVisible(false);
//					ckdzTextField.setText("");
					btlLabel1.setVisible(false);
					btlJComboBox1.setVisible(false);
//					btlJComboBox1.setSelectedItem("");
					btlJComboBox2.setVisible(false);
//					btlJComboBox2.setSelectedItem("");g
					tzwLabel.setVisible(false);
					tzwJComboBox.setVisible(false);
//					tzwJComboBox.setSelectedItem("");
					jywLabel.setVisible(false);
					jywJComboBox.setVisible(false);
//					jywJComboBox.setSelectedItem("");
					sjwLabel.setVisible(false);
					sjwJComboBox.setVisible(false);
//					sjwJComboBox.setSelectedItem("");

					remakLabel.setText("例如：192.168.0.0");

				} else if ("串口".equals(txmsJComboBox.getSelectedItem())) {
					ipLabel1.setVisible(false);
					IPdzJTextField.setVisible(false);
//					IPdzJTextField.setText("");
					portLabel2.setVisible(false);
					IPdkJTextField.setVisible(false);
					ckdzLabel.setVisible(true);
					ckdzTextField.setVisible(true);
					btlLabel1.setVisible(true);
					btlJComboBox1.setVisible(true);
					btlJComboBox2.setVisible(true);
					tzwLabel.setVisible(true);
					tzwJComboBox.setVisible(true);
					jywLabel.setVisible(true);
					jywJComboBox.setVisible(true);
					sjwLabel.setVisible(true);
					sjwJComboBox.setVisible(true);
					remakLabel.setText("例如：/dev/ttyS数字");
				}
			}
		});
        
        
        //String lasttime = DateHelper.getDateFmt((new initData().getLastAdjustTime()), "yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd")+"";
        
        
        liquidAddBut = new JButton("保存");
		liquidAddBut.setSize(Constant.widthBut, Constant.heightBut);
		liquidAddBut.setLocation(350, 260);
		liquidPanel.add(liquidAddBut);
		liquidPanel.updateUI();

        liquidAddBut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SysManageIquidInstrument record = new SysManageIquidInstrument();
				record.setFactory((String) csJComboBox.getSelectedItem());
				record.setMactype((String) sbxhJComboBox.getSelectedItem());
				record.setWorktype((String) cjlxJComboBox.getSelectedItem());
				record.setCommtype((String) txmsJComboBox.getSelectedItem());
				if("串口".equals(txmsJComboBox.getSelectedItem())) {
					record.setSerialport(ckdzTextField.getText());
					record.setIpaddress("127.0.0.1");
					record.setIpport("80");
				}
				record.setBaudrate1((String) btlJComboBox1.getSelectedItem());
				record.setBaudrate2((String) btlJComboBox2.getSelectedItem());
				record.setStopsite((String) tzwJComboBox.getSelectedItem());
				record.setChecksite((String) jywJComboBox.getSelectedItem());
				record.setDatasite((String) sjwJComboBox.getSelectedItem());
				record.setTranstatus("0");
				record.setCreatetime(new Date());
				if("网口".equals(txmsJComboBox.getSelectedItem())) {
					try {
						String[] IPs = IPdzJTextField.getText().split("\\.");
						//System.out.println(IPdzJTextField.getText());
						if (IPs.length == 4) {
							for (String num : IPs) {
								Integer.parseInt(num);
							}
							record.setIpaddress(IPdzJTextField.getText());
						} else {
							JOptionPane.showMessageDialog(liquidPanel, "IP地址格式不正确！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(liquidPanel, "IP地址格式不正确！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					try {
						Double.parseDouble(IPdkJTextField.getText());//端口号
						record.setIpport(IPdkJTextField.getText());

						record.setSerialport("0");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(liquidPanel, "IP端口请输入数字！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				record.setLastadjusttime(new Date());
				IquidService iquidService = (IquidService) (Context.getInstance().getBean("iquidService"));
				int r = iquidService.insert(record);

				if (r>0) {//成功
					JOptionPane.showMessageDialog(null, "设置成功，请退出并重启管控系统！", "提示信息", JOptionPane.PLAIN_MESSAGE);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							setValueToControl();
						}
					});
				} else {
					JOptionPane.showMessageDialog(null, "设置失败！", "提示信息", JOptionPane.PLAIN_MESSAGE);
				}

 					/*record.setId(0);
					ArrayList<SysManageIquidInstrument> list = new ArrayList<SysManageIquidInstrument>();
					logger.info("record:" + record);
					list.add(record);
					GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign,
							list, Constants.PID_Code.A15_10006.toString());
					System.out.println("GasMsg=:======================\n"+gasMsg);
					Main.CC.writeAndFlush(gasMsg);*/

			}
		});

		liquidPanel.setSize(800, 500) ;
		liquidPanel.updateUI();
		//初始化数据
		setValueToControl();
    }
	private void setValueToControl() {
		IquidService iquidService = (IquidService) (Context.getInstance().getBean("iquidService"));
		SysManageIquidInstrument iquidInstrument =iquidService.selectLast();
		if(iquidInstrument!=null) {
			csJComboBox.setSelectedItem(iquidInstrument.getFactory());//厂商
			sbxhJComboBox.setSelectedItem(iquidInstrument.getMactype());//设备型号
			cjlxJComboBox.setSelectedItem(iquidInstrument.getWorktype());//采集类型
			txmsJComboBox.setSelectedItem(iquidInstrument.getCommtype());//通讯模式
			ckdzTextField.setText(iquidInstrument.getSerialport());//串口地址
			btlJComboBox1.setSelectedItem(iquidInstrument.getBaudrate1());//  波特率1
			btlJComboBox2.setSelectedItem(iquidInstrument.getBaudrate2());//波特率2
			tzwJComboBox.setSelectedItem(iquidInstrument.getStopsite());//停止位
			jywJComboBox.setSelectedItem(iquidInstrument.getChecksite());//检验位
			sjwJComboBox.setSelectedItem(iquidInstrument.getDatasite());//数据位
			SimpleDateFormat Datefo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String LastTime = Datefo.format(iquidInstrument.getLastadjusttime());
			scjzsjTextField.setText(LastTime);//上次校正时间
			IPdzJTextField.setText(iquidInstrument.getIpaddress());//IP地址
			IPdkJTextField.setText(iquidInstrument.getIpport());//IP端口
			if(!StringUtils.isEmpty(iquidInstrument.getTranstatus())) {
				if(iquidInstrument.getTranstatus().equals("0")){
					TranStatusField.setText("是");//传输状态
				}
			}
		    else
			{
				TranStatusField.setText("否");
			}

		}else {
			csJComboBox.setSelectedItem("");//厂商
			sbxhJComboBox.setSelectedItem("");//设备型号
			cjlxJComboBox.setSelectedItem("");//采集类型
			txmsJComboBox.setSelectedItem("");//通讯模式
			ckdzTextField.setText("/dev/ttyS");//串口地址
			btlJComboBox1.setSelectedItem("9600");//  波特率1
			btlJComboBox2.setSelectedItem("1222");//波特率2
			tzwJComboBox.setSelectedItem("");//停止位
			jywJComboBox.setSelectedItem("");//检验位
			sjwJComboBox.setSelectedItem("8");//数据位
			scjzsjTextField.setText("");//上次校正时间
			IPdzJTextField.setText("");//IP地址
			IPdkJTextField.setText("");//IP端口

		}
	}

    public void setPanel(JPanel centerPanel){
    /*	LiquidPanel panel=new LiquidPanel();*/
		centerPanels=centerPanel;
		centerPanel.removeAll();
		centerPanel.setLayout(null);
		centerPanel.setLayout(new BorderLayout(0,0));
		initLiquidPanel();
		centerPanel.add(liquidPanel);
		centerPanel.updateUI();
	}
	@Override
	public void update(GasMsg gasMsg) {
		ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
		if(gasMsg.getPid().equals(Constants.PID_Code.A15_10006.toString())) {
			ret = data.getResult();
			if ("0".equals(ret)) {//成功
				JOptionPane.showMessageDialog(null, "设置成功！", "提示信息", JOptionPane.PLAIN_MESSAGE);

				//因为update(GasMsg gasMsg)方法和控件不是在一个进程里
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						setValueToControl();
					}
				});

			} else {
				JOptionPane.showMessageDialog(null, "设置失败！", "提示信息", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
}
