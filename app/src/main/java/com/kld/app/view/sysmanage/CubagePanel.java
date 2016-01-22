package com.kld.app.view.sysmanage;

import com.kld.app.service.SysCubageService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.main.Constant;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageCubage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CubagePanel extends JPanel{
	JPanel cubagePanel;
	JComboBox ygbhJComboBox = new JComboBox();;
	public JTable probePartable =  new MyTable() ;
	public DefaultTableModel model;
	public JButton cubageQueryBut = new JButton("查询");
	public Date date;
	JScrollPane scr;
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SysManageCanInfoService tankInfoService=(SysManageCanInfoService) (Context.getInstance().getBean("SysManageTankInfoService"));

	public CubagePanel() {
		//region 油罐编号 label
    	cubagePanel = new JPanel();
    	JLabel ygbhJLabel = new JLabel("油罐编号：");
		ygbhJLabel.setFont(com.kld.app.util.Constant.BOTTOM_FONT);
		ygbhJLabel.setBounds(10, 10, 80, 30);
		cubagePanel.add(ygbhJLabel);
		ygbhJComboBox.setEditable(false);
		ygbhJComboBox.setBounds(80, 10, 170, 30);
		cubagePanel.add(ygbhJComboBox);
		cubagePanel.updateUI();
		//endregion
		setYgbhComboBox();

		//查询
//		JButton cubageQueryBut = new JButton("查询");
		cubageQueryBut.setSize(Constant.widthBut, Constant.heightBut);
		cubageQueryBut.setLocation(300, 10);
    	cubagePanel.add(cubageQueryBut);
		cubagePanel.updateUI();

    	final String[] tableHeads = {
    			"罐号",
    			"版本号",
    			"操作人",
				"设置状态",
				"更新时间"
        };
    	Object[][] data = {
                {
                    "",
                    "",
					"",
					"",
					""
                }
        };
       // JTable probePartable = new JTable(this.getList(list),tableHeads) ;
		model =new DefaultTableModel(data,tableHeads);
		probePartable.setModel(model);
    	cubageQueryBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int key = 0;
				if (!"全部".equals(ygbhJComboBox.getSelectedItem().toString())) {
					key = Integer.parseInt(ygbhJComboBox.getSelectedItem().toString());
				}
				SysCubageService sysCubageService2 = (SysCubageService) (Context.getInstance().getBean("sysCubageService"));
				List<SysManageCubage> listByKey = sysCubageService2.selectByKeyAll(key);
				model = new DefaultTableModel(this.getList(listByKey), tableHeads);
				probePartable.setModel(model);
				for (int i = 0; i < tableHeads.length; i++) {
					probePartable.getColumnModel().getColumn(i).setPreferredWidth(160);
				}
				//System.out.println("probePartable.updateUI()");
				probePartable.updateUI();
			}

			public Object[][] getList(List<SysManageCubage> listInfo) {
				Object obj[][] = new Object[listInfo.size()][5];
				for (int i = 0; i < listInfo.size(); i++) {
					obj[i][0] = listInfo.get(i).getOilcan();
					obj[i][1] = listInfo.get(i).getVersion();
//					obj[i][2] = (1==listInfo.get(i).getSetstate())?"已使用":"未使用";
					obj[i][2] = listInfo.get(i).getSetman();
					obj[i][3] = (1 == listInfo.get(i).getInused()) ? "已生效" : "未生效";
					if (listInfo.get(i).getSettime() != null && !"".equals(listInfo.get(i).getSettime())) {
						obj[i][4] = sd.format(listInfo.get(i).getSettime());
					}
				}
				return obj;
			}
		});
		for (int i = 0; i < tableHeads.length; i++) {
        	probePartable.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        probePartable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scr =new JScrollPane(probePartable);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		//table.setBorder(new LineBorder(new Color(0, 0, 0)));
		probePartable.setRowHeight(20);
		probePartable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		probePartable.setDefaultRenderer(Object.class, render);
        scr.setSize(800, 330);
		scr.setLocation(0, 50) ;
        cubagePanel.setLayout(null);
        cubagePanel.add(scr);
        cubagePanel.setSize(800, 470) ;
        cubagePanel.setVisible(true);
		cubageQueryBut.doClick(0);
    }


	private void setYgbhComboBox()
	{

		ygbhJComboBox.removeAllItems();
		List<SysManageCanInfo> CanInfoList=tankInfoService.selectAll();
		ygbhJComboBox.addItem("全部");
		for (SysManageCanInfo item:CanInfoList){
			ygbhJComboBox.addItem(item.getOilcan());
		}
	}
    public void setPanel(JPanel centerPanel) {
		centerPanel.setBounds(0, 180, 800, 390);
		centerPanel.setLayout(null);
		centerPanel.add(cubagePanel);
		centerPanel.setVisible(true);
		setYgbhComboBox();
		centerPanel.updateUI();
		cubagePanel.updateUI();

	}
}
