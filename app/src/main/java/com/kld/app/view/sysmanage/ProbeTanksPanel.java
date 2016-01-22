package com.kld.app.view.sysmanage;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.*;

import com.kld.app.service.SysProbeTanksService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.domain.SysManagePaTRelation;
import com.kld.gsm.ATG.domain.SysManageProbePar;

public class ProbeTanksPanel extends JPanel{
	JPanel probeTanksPanel;
	JTable probeTanksTable;
	public ProbeTanksPanel(){
		probeTanksPanel = new JPanel();
	//	JTable probeTankstable=new JTable();
    	final String[] tableHeads = {
    			"设备型号",
    			"探棒编号",
                "油罐编码",
                "油品类型（汽油，柴油）",
                "油品编码",
                "上次校正时间"
        };
    	
    	SysProbeTanksService probeService =(SysProbeTanksService) (Context.getInstance().getBean("probeTanksService"));
    	List<SysManagePaTRelation> list=probeService.selectAll();
     
		probeTanksTable = new JTable(this.getList(list),tableHeads) ;
        for (int i = 0; i < tableHeads.length; i++) {
        	probeTanksTable.getColumnModel().getColumn(i).setPreferredWidth(133);
        }
        probeTanksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scr =new JScrollPane(probeTanksTable);
        scr.setSize(800, 390);
		probeTanksPanel.setLayout(null);
		probeTanksPanel.add(scr);
		probeTanksPanel.setSize(800,470) ;
		probeTanksPanel.setVisible(true);
	}
	
	public void update(){
		
	}
	
	public Object[][] getList(List<SysManagePaTRelation> listInfo) {
		Object obj[][]=new Object[listInfo.size()][6];
		for (int i = 0; i < listInfo.size(); i++) {
			obj[i][0]=listInfo.get(i).getDreviceModel();                                                  //listInfo.get(i).get
			obj[i][1]=listInfo.get(i).getProbemodel();
			obj[i][2]=listInfo.get(i).getOilcan();
			obj[i][3]=listInfo.get(i).getStrOilType();
			obj[i][4]=listInfo.get(i).getOilno();
			obj[i][5]=listInfo.get(i).getLastadjusttime();
		}
	return obj;
	}

	public SysManagePaTRelation getInfo() throws ParseException {
		SysManagePaTRelation smp = new SysManagePaTRelation();
		int row=probeTanksTable.getSelectedRowCount();
		smp.setDreviceModel((String) probeTanksTable.getValueAt(row, 0));
		smp.setProbemodel((String) probeTanksTable.getValueAt(row, 1));
		//System.out.println((String) probeTanksTable.getValueAt(row, 0));
		smp.setOilcan(Integer.parseInt(probeTanksTable.getValueAt(row, 2).toString()));
		smp.setStrOilType((String)probeTanksTable.getValueAt(row, 3));
		smp.setOilno(probeTanksTable.getValueAt(row, 4).toString());
		SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		String datestr=probeTanksTable.getValueAt(row, 5).toString();
		Date date = sdf.parse(datestr);
		smp.setLastadjusttime(date);
		return smp;
	}
	
    public void setPanel(JPanel centerPanel){
    	ProbeTanksPanel panel=new ProbeTanksPanel();
		centerPanel.setBounds(0, 180, 800, 390);
		centerPanel.setLayout(new BorderLayout(0,0));
		centerPanel.add(panel.probeTanksPanel);
		centerPanel.setVisible(true);
	}
	 public static void main(String args[]){
		 	
		 JDialog dialog = new JDialog() ;
		 dialog.setLayout(null);
		 ProbeTanksPanel panel=new ProbeTanksPanel();
		 dialog.add(panel.probeTanksPanel) ;
		 dialog.setSize(800,600) ;
		 dialog.setLocation(300,200) ;
		 dialog.setVisible(true) ;
		 dialog.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent a){
					System.exit(1) ;
				}
			}) ;
	 }
}
