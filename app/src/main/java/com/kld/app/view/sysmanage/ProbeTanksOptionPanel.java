package com.kld.app.view.sysmanage;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kld.app.service.SysProbeService;
import com.kld.app.service.SysProbeTanksService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.main.Constant;
import com.kld.gsm.ATG.domain.SysManagePaTRelation;
import com.kld.gsm.ATG.domain.SysManageProbePar;

public class ProbeTanksOptionPanel extends JPanel{
	JPanel probeTanksOptionPanel;
	JLabel sbxh2Lab;
	JComboBox tbbhJComboBox;
	JTextField tbdkTextField;
	JLabel ygbh2Lab;
	JLabel yplx2Lab;
	JLabel ypb2mLab;
	JButton saveBut;  //保存
	JButton closeBut; //关闭
	
	public ProbeTanksOptionPanel(){
			probeTanksOptionPanel = new JPanel();
			JLabel sbxhLab = new JLabel("设备型号");
			sbxhLab.setSize(Constant.width,Constant.height);
			sbxhLab.setLocation(Constant.lie1, 10);
			sbxh2Lab = new JLabel();
			sbxh2Lab.setSize(Constant.width,Constant.height);
			sbxh2Lab.setLocation(Constant.lie2, 10);
			probeTanksOptionPanel.add(sbxhLab);
			probeTanksOptionPanel.add(sbxh2Lab);

	        JLabel tbbhLab = new JLabel("探棒编号");
	        tbbhLab.setSize(Constant.width,Constant.height);
	        tbbhLab.setLocation(Constant.lie1, 50);
	        tbbhJComboBox = new JComboBox();
	        tbbhJComboBox.setSize(Constant.width,Constant.height);
	        tbbhJComboBox.setLocation(Constant.lie2, 50);
	        tbbhJComboBox.addItem("1");
	        tbbhJComboBox.addItem("2");
	        probeTanksOptionPanel.add(tbbhLab);
	        probeTanksOptionPanel.add(tbbhJComboBox);

	        JLabel tbdkLab = new JLabel("探棒端口（范围：1-256）");
	        tbdkLab.setSize(Constant.width,Constant.height);
	        tbdkLab.setLocation(Constant.lie1, 90);
	        tbdkTextField = new JTextField();
	        tbdkTextField.setSize(Constant.width,Constant.height);
	        tbdkTextField.setLocation(Constant.lie2, 90);
	        probeTanksOptionPanel.add(tbdkLab);
	        probeTanksOptionPanel.add(tbdkTextField);


	        JLabel ygbhLabel = new JLabel("油罐编号");
	        ygbhLabel.setSize(Constant.width,Constant.height);
	        ygbhLabel.setLocation(Constant.lie1, 130);
	        ygbh2Lab = new JLabel();
	        ygbh2Lab.setSize(Constant.width,Constant.height);
	        ygbh2Lab.setLocation(Constant.lie2, 130);
	        probeTanksOptionPanel.add(ygbhLabel);
	        probeTanksOptionPanel.add(ygbh2Lab);

	        JLabel yplxLab = new JLabel("油品类型（汽油，柴油）");
	        yplxLab.setSize(Constant.width,Constant.height);
	        yplxLab.setLocation(Constant.lie1, 170);
	        yplx2Lab = new JLabel();
	        yplx2Lab.setSize(Constant.width,Constant.height);
	        yplx2Lab.setLocation(Constant.lie2, 170);
	        probeTanksOptionPanel.add(yplxLab);
	        probeTanksOptionPanel.add(yplx2Lab);


	        JLabel ypbmLab = new JLabel("油品编码");
	        ypbmLab.setSize(Constant.width,Constant.height);
	        ypbmLab.setLocation(Constant.lie1, 210);
	        ypb2mLab = new JLabel();
	        ypb2mLab.setSize(Constant.width,Constant.height);
	        ypb2mLab.setLocation(Constant.lie2, 210);
	        probeTanksOptionPanel.add(ypbmLab);
	        probeTanksOptionPanel.add(ypb2mLab);
	        
	        probeTanksOptionPanel.setLayout(null);
	        probeTanksOptionPanel.setSize(800,500) ;
	        probeTanksOptionPanel.setVisible(true);
	        saveBut = new JButton("保存");
	        saveBut.setSize(Constant.widthBut,Constant.heightBut);
	        saveBut.setLocation(Constant.lie1, 250);
	        probeTanksOptionPanel.add(saveBut);
	        saveBut.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					SysProbeTanksService probeService =(SysProbeTanksService) (Context.getInstance().getBean("probeTanksService"));
					SysManagePaTRelation smpr = new SysManagePaTRelation();
					//sbxh2Lab.getText()设备型号
					smpr.setProbemodel((String)tbbhJComboBox.getSelectedItem());
					//探棒端口缺字段
					smpr.setOilcan(Integer.parseInt((String)ygbh2Lab.getText()));
					smpr.setOilno(yplx2Lab.getText());
					//油品编码
					probeService.insert(smpr);
				}
			});
	        
	        
	        
	        closeBut= new JButton("关闭");
	        closeBut.setSize(Constant.widthBut,Constant.heightBut);
	        closeBut.setLocation(Constant.lie2, 250);
	        probeTanksOptionPanel.add(closeBut);
	}
	
	
	public void getInfo(){
		SysManagePaTRelation smpr = new SysManagePaTRelation();
		//字段补齐后添加
		
		
		
		
	}
	
	public void setPanel(JFrame frame) throws ParseException {
		ProbeTanksOptionPanel panel=new ProbeTanksOptionPanel();
		SysManagePaTRelation smp=new ProbeTanksPanel().getInfo();
		panel.sbxh2Lab.setText(smp.getDreviceModel());
		panel.ygbh2Lab.setText(smp.getOilcan().toString());
		panel.yplx2Lab.setText(smp.getStrOilType());
		panel.ypb2mLab.setText(smp.getOilno());

    	frame.setBounds(0, 280, 800, 500);
    	frame.setLayout(new BorderLayout(0,0));
    	frame.add(panel.probeTanksOptionPanel);
    	frame.setVisible(true);

	}
}
