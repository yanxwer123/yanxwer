package com.kld.app.view.sysmanage;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.swing.*;
import javax.swing.table.*;

import com.kld.app.service.SysProbeService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.acceptance.MyTableModel;
import com.kld.app.view.main.Constant;
import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.ATG.domain.SysManageProbePar;

/**
 * 
 * @author Aven
 *	探棒校正参数设置
 */
public class ProbeParPanel extends JPanel {

	private JPanel cpanel;
	JPanel probeParPanel;
    public 	JTable probePartable;
	JScrollPane scr;
    public ProbeParPanel() {
    	probeParPanel = new JPanel();

        // table的数据
        final Object[][] data = {
                {
                    "1",
                    "",
                    "",
                    "",
                    "", 
                    "6",
                    "",
                    "",
                    "",
                    "",
                    "11",
                    "",
                    "",
                    "",
                    "",
                    "16",
                    "",
                    "",
                    "",
                    "",
                    "","", ""
                }
        };
		//GetAllData();
    }

	public  void  GetAllData(){
		final String[] tableHeads = {
				"设备型号",
				"探棒编号",
				"油品类型",
				"油罐编码",
				"探棒端口号",
				"油位0点校正(mm)",
				"水位0点校正(mm)",
				"温度1实测值(℃)",
				"温度1探棒测量值(℃)",
				"温度2实测值(℃)",
				"温度2探棒测量值(℃)",
				"温度3实测值(℃)",
				"温度3探棒测量值(℃)",
				"温度4实测值(℃)",
				"温度4探棒测量值(℃)",
				"温度5实测值(℃)",
				"温度5探棒测量值(℃)",
				"探棒偏移(mm)",
				"倾斜偏移(mm)",
				"初始密度(kg/m3)",
				"初始高度差(mm)",
				"密度的修正系数",
				"上次校正时间"

		};
		SysProbeService probeService =(SysProbeService) (Context.getInstance().getBean("probeService"));
		List<SysManageProbePar> list=probeService.selectAll();
		MyTableModel model = new MyTableModel(tableHeads, this.getList(list));
				for(int i=0;i<model.getRowCount();i++){
				 String  OilName=GetOilTypeName(list.get(i));
					model.setValueAt(OilName,i,2);
				}
		probePartable = new MyTable(model);
		probePartable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		probePartable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		probeParPanel.setLayout(null);
		probeParPanel.removeAll();
		probeParPanel.repaint();
		/*if(probePartable==null)
		   probePartable = new JTable(this.getList(list),tableHeads) ;*/
		/*for (int i = 0; i < tableHeads.length; i++) {
			probePartable.getColumnModel().getColumn(i).setPreferredWidth(114);
		}*/
		scr =new JScrollPane(probePartable);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		//table.setBorder(new LineBorder(new Color(0, 0, 0)));
		probePartable.setRowHeight(20);
		probePartable.setDefaultRenderer(Object.class, render);
		FitTableColumns(probePartable);
		scr.setSize(800, 390);
		probeParPanel.add(scr);
		probeParPanel.setSize(800, 470) ;
		probeParPanel.setVisible(true);
	}

	private void FitTableColumns(JTable table) {
		JTableHeader header = table.getTableHeader();
		int rowCount = table.getRowCount();

		Enumeration columns = table.getColumnModel().getColumns();
		while(columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) table.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(table, column.getIdentifier()
							, false, false, -1, col).getPreferredSize().getWidth();
			if(width<80){
				width=80;
			}
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
						table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				if(Math.max(width, preferedWidth)>=80){
					width = Math.max(width, preferedWidth);
				}
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}

	private  String GetOilTypeName(SysManageProbePar list){
		if(list.getOilno().equals("1000"))
			return "汽油";
		else  if(list.getOilno().equals("1901"))
			return  "高标号汽油";
		else  if(list.getOilno().equals("1902"))
			return  "低标号汽油";
		else  if(list.getOilno().equals("1911"))
			return "乙醇汽油(E10-E20)";
		else  if(list.getOilno().equals("1912"))
			return  "乙醇汽油(E21-E40)";
		else  if(list.getOilno().equals("1913"))
			return "乙醇汽油(E41-E60)";
		else  if(list.getOilno().equals("1914"))
			return "乙醇汽油(E61-E80)";
		else  if(list.getOilno().equals("1915"))
			return "乙醇汽油(E81-E100)";
		else  if(list.getOilno().equals("1921"))
			return  "甲醇汽油";
		else  if(list.getOilno().equals("2000"))
			return "柴油";
		else  if(list.getOilno().equals("2901"))
			return "生物柴油";
		else  if(list.getOilno().equals("3000"))
			return "煤油";
		else  if(list.getOilno().equals("4000"))
			return "液化石油气";
		else
			return "";
	}
    
    public void setPanel(JPanel centerPanel){
		cpanel=centerPanel;
		cpanel.setLayout(null);
		cpanel.removeAll();
		cpanel.repaint();
		cpanel.setBounds(0, 180, 800, 390);
		cpanel.setLayout(new BorderLayout(0, 0));
		cpanel.add(probeParPanel);
		cpanel.setVisible(true);
		GetAllData();
	}
    
    public SysManageProbePar getInfo() throws ParseException {
    	int row=probePartable.getSelectedRow();
		if(row!=-1) {
			SysManageProbePar smp = new SysManageProbePar();
			smp.setDevicemodel(probePartable.getValueAt(row, 0).toString());
			smp.setProbemodel((String) probePartable.getValueAt(row, 1));
			//System.out.println((String) probePartable.getValueAt(row, 0));
			smp.setOilno((String) probePartable.getValueAt(row, 2));
			smp.setOilcan(Integer.parseInt(probePartable.getValueAt(row, 3).toString()));
			smp.setProbeport(Integer.parseInt(probePartable.getValueAt(row, 4).toString()));
			smp.setOilzero(Double.parseDouble(probePartable.getValueAt(row, 5).toString()));
			smp.setWaterzero(Double.parseDouble(probePartable.getValueAt(row, 6).toString()));
			smp.setRealtemp1(Double.parseDouble(probePartable.getValueAt(row, 7).toString()));
			smp.setProrealtemp1(Double.parseDouble(probePartable.getValueAt(row, 8).toString()));
			smp.setRealtemp2(Double.parseDouble(probePartable.getValueAt(row, 9).toString()));
			smp.setProrealtemp2(Double.parseDouble(probePartable.getValueAt(row, 10).toString()));
			smp.setRealtemp3(Double.parseDouble(probePartable.getValueAt(row, 11).toString()));
			smp.setProrealtemp3(Double.parseDouble(probePartable.getValueAt(row, 12).toString()));
			smp.setRealtemp4(Double.parseDouble(probePartable.getValueAt(row, 13).toString()));
			smp.setProrealtemp4(Double.parseDouble(probePartable.getValueAt(row, 14).toString()));
			smp.setRealtemp5(Double.parseDouble(probePartable.getValueAt(row, 15).toString()));
			smp.setProrealtemp5(Double.parseDouble(probePartable.getValueAt(row, 16).toString()));
			smp.setProbeskew(Double.parseDouble(probePartable.getValueAt(row, 17).toString()));
			smp.setInclineskew(Double.parseDouble(probePartable.getValueAt(row, 18).toString()));
			smp.setInitdesnsity(Double.parseDouble(probePartable.getValueAt(row, 19).toString()));
			smp.setInithighdiff(Double.parseDouble(probePartable.getValueAt(row, 20).toString()));
			smp.setCorrectionfactor(Double.parseDouble(probePartable.getValueAt(row, 21).toString()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datestr = probePartable.getValueAt(row, 22).toString();
			Date date = sdf.parse(datestr);
			smp.setLastadjusttime(date);

			return smp;
		}
		return  null;
    }
    
    public void deleteInfo(){
		try {
			int row = probePartable.getSelectedRow();
			if(row!=-1) {
				SysProbeService probeService = (SysProbeService) (Context.getInstance().getBean("probeService"));
				probeService.deleteByPrimaryKey((String) probePartable.getValueAt(row, 1));
				JOptionPane.showMessageDialog(cpanel, "删除成功！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
				setPanel(cpanel);
				probePartable.updateUI();
			}
			else
				JOptionPane.showMessageDialog(cpanel, "选择一条探棒设置信息", "提示信息", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(cpanel, "删除失败！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
		}

    }


	public Object[][] getList(List<SysManageProbePar> listInfo) {
		Object obj[][]=new Object[listInfo.size()][23];
		for (int i = 0; i < listInfo.size(); i++) {
			obj[i][0]=listInfo.get(i).getDevicemodel();                                                  //listInfo.get(i).get
			obj[i][1]=listInfo.get(i).getProbemodel();
			obj[i][2]=listInfo.get(i).getOilno();
			obj[i][3]=listInfo.get(i).getOilcan();
			obj[i][4]=listInfo.get(i).getProbeport();
			obj[i][5]=listInfo.get(i).getOilzero();
			obj[i][6]=listInfo.get(i).getWaterzero();
			obj[i][7]=listInfo.get(i).getRealtemp1();
			obj[i][8]=listInfo.get(i).getProrealtemp1();
			obj[i][9]=listInfo.get(i).getRealtemp2();
			obj[i][10]=listInfo.get(i).getProrealtemp2();
			obj[i][11]=listInfo.get(i).getRealtemp3();
			obj[i][12]=listInfo.get(i).getProrealtemp3();
			obj[i][13]=listInfo.get(i).getRealtemp4();
			obj[i][14]=listInfo.get(i).getProrealtemp4();
			obj[i][15]=listInfo.get(i).getRealtemp5();
			obj[i][16]=listInfo.get(i).getProrealtemp5();
			obj[i][17]=listInfo.get(i).getProbeskew();
			obj[i][18]=listInfo.get(i).getInclineskew();
			obj[i][19]=listInfo.get(i).getInitdesnsity();
			obj[i][20]=listInfo.get(i).getInithighdiff();
			obj[i][21]=listInfo.get(i).getCorrectionfactor();
			SimpleDateFormat Datefo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String czDate = Datefo.format(listInfo.get(i).getLastadjusttime());
			obj[i][22]=czDate;

		}
	return obj;
	}
    
    public static void main(String args[]){
		JFrame frame = new JFrame() ;
			frame.setLayout(null);
			ProbeParPanel panel=new ProbeParPanel();
			frame.add(panel.probeParPanel) ;
			frame.setSize(800,600) ;
			frame.setLocation(300,200) ;
			frame.setVisible(true) ;
			frame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent a){
					System.exit(1) ;
				}
			}) ;
	}
}
	
