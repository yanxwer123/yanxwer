package com.kld.app.view.sysmanage;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.kld.app.service.SysCubageOptionService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.main.Constant;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATG.domain.SysManageCubageInfoKey;

public class CubageOptionPanel extends JPanel{
	JPanel cubageOptionPanel;
	JTable cubageOptiontable;
	public CubageOptionPanel(SysManageCubageInfoKey key){
		cubageOptionPanel = new JPanel();
		cubageOptiontable = new JTable();
		JButton CloseBut = new JButton("关闭");
		final String[] tableHeads = {
    			"高度",
    			"升数"
        };
		
		
		
		
		SysCubageOptionService sysCubageOptionService =(SysCubageOptionService) (Context.getInstance().getBean("sysCubageOptionService"));
		List<SysManageCubageInfo> list=sysCubageOptionService.selectByKey(key);
		cubageOptiontable = new JTable(this.getList(list),tableHeads) ;
		
		 JLabel yglab = new JLabel("油罐：");
		 yglab.setSize(Constant.width,Constant.height);
		 yglab.setLocation(Constant.lie1, 10);
		 cubageOptionPanel.add(yglab);
		 for (int i = 0; i < tableHeads.length; i++) {
			 cubageOptiontable.getColumnModel().getColumn(i).setPreferredWidth(99);
	        }
		 
		 	cubageOptiontable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        JScrollPane scr =new JScrollPane(cubageOptiontable);
	        scr.setSize(200, 200);
	        scr.setLocation(50,50) ;
	        cubageOptionPanel.add(scr);
		 
	        CloseBut.setSize(Constant.widthBut,Constant.heightBut);
	        CloseBut.setLocation(130, 265);
	        cubageOptionPanel.add(CloseBut);
		 
		 
		 cubageOptionPanel.setLayout(null);
		 cubageOptionPanel.setSize(800,500) ;
		 cubageOptionPanel.setVisible(true);
	}
	
	 public Object[][] getList(List<SysManageCubageInfo> listInfo) {
			Object obj[][]=new Object[listInfo.size()][2];
			for (int i = 0; i < listInfo.size(); i++) {
				obj[i][0]=listInfo.get(i).getHeight();
				obj[i][1]=listInfo.get(i).getLiter();
			}
		return obj;
		}
	 public void setPanel(JFrame frame,SysManageCubageInfoKey key){
		 	CubageOptionPanel panel=new CubageOptionPanel(key);
	    	frame.setBounds(0, 280, 800, 500);
	    	frame.setLayout(new BorderLayout(0,0));
	    	frame.add(panel.cubageOptionPanel);
	    	frame.setVisible(true);
		}
	 
//	 
//	public static void main(String args[]){
//		JFrame frame = new JFrame() ;
//		frame.setLayout(null);
//		CubageOptionPanel panel=new CubageOptionPanel('2');
//		frame.add(panel.cubageOptionPanel) ;
//		frame.setSize(800,600) ;
//		frame.setLocation(300,200) ;
//		frame.setVisible(true) ;
//		frame.addWindowListener(new WindowAdapter(){
//			public void windowClosing(WindowEvent a){
//				System.exit(1) ;
//			}
//		}) ;
//	}
}
