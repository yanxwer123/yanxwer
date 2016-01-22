package com.kld.app.view.daily;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.kld.app.service.DailyPowerRecordService;
import com.kld.gsm.ATG.domain.DailyPowerRecord;
import com.kld.gsm.ATG.domain.SysManageProbePar;
import org.jdesktop.swingx.JXDatePicker;

import com.kld.app.service.DailyOilDailyRecordService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.acceptance.MyTableModel;
import com.kld.gsm.ATG.domain.DailyOilDailyRecord;
/**
 * 交易记录
 * @author YANGRL
 *
 */
public class YwykgjlPage {
	private static final String[] CKD_TITLES = new String[]{"操作日期","操作时间","操作类型：0开机；1关机","油罐编号","油水总高(mm)","水高(mm)","平均温度(℃)","5点温度1","5点温度2","5点温度3","5点温度4","5点温度5","净油体积(L)","标准体积(L)","空体积(L)","水体积(L)"};
	private Object[][] billArray = new Object[0][CKD_TITLES.length];
	private JScrollPane scrollPane;
	private JTable table;
	JScrollPane scr;
	List<DailyPowerRecord> list;
	//中部需要滚动条
	public void setPanel(JPanel centerPanel){
		centerPanel.setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 40, 800, 330);
		centerPanel.add(scrollPane);

		JLabel rqlab = new JLabel("日期：");
		rqlab.setForeground(Color.WHITE);
		centerPanel.add(rqlab);
		rqlab.setBounds(10, 5, 40, 25);
		
		//日期选择器
		final JXDatePicker beginDate=new JXDatePicker();
		beginDate.setDate(new Date());
		beginDate.setPreferredSize(new Dimension(200, 100));
		centerPanel.add(beginDate);
		beginDate.setBounds(50, 5, 150, 25);
		
		JLabel zhilab = new JLabel("至");
		zhilab.setForeground(Color.WHITE);
		centerPanel.add(zhilab);
		zhilab.setBounds(210, 5, 20, 25);
		
		final JXDatePicker endDate=new JXDatePicker();
		endDate.setDate(new Date());
		endDate.setPreferredSize(new Dimension(200, 100));
		centerPanel.add(endDate);
		endDate.setBounds(230, 5, 150, 25);

		centerPanel.updateUI();
		JButton cxbutton = new JButton("查询");
		centerPanel.add(cxbutton);
		cxbutton.setBounds(405, 5, 80, 25);
		cxbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DailyPowerRecordService dailyPowerRecordService = (DailyPowerRecordService) (Context.getInstance().getBean("dailyPowerRecordService"));
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				list = dailyPowerRecordService.selectByDate(sd.format(beginDate.getDate()), sd.format(endDate.getDate()));
				billArray = new Object[list.size()][CKD_TITLES.length];
				for (int i = 0; i < list.size(); i++) {
					DailyPowerRecord info = list.get(i);
					SimpleDateFormat Datefo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String czDate = Datefo.format(info.getDate());
					String[] dateTime=czDate.toString().split(" ");
					billArray[i][0] = dateTime[0];
					billArray[i][1] = dateTime[1];
					billArray[i][2] = info.getOperatetype();
					billArray[i][3] = info.getOilcanno();
					billArray[i][4] = info.getTotalheight();
					billArray[i][5] = info.getWaterheight();
					billArray[i][6] = info.getOiltemp();
					billArray[i][7] = info.getOiltemp1();
					billArray[i][8] = info.getOiltemp2();
					billArray[i][9] = info.getOiltemp3();
					billArray[i][10] = info.getOiltemp4();
					billArray[i][11] = info.getOiltemp5();
					billArray[i][9] = info.getOilcubage();
					billArray[i][10] = info.getOilstandcubage();
					billArray[i][11] = info.getEmptycubage();
					billArray[i][12] = info.getWaterbulk();
				}
				table = getTable(CKD_TITLES, billArray);
				for (int i = 0; i < CKD_TITLES.length; i++) {
					table.getColumnModel().getColumn(i).setPreferredWidth(114);
				}
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				scr =new JScrollPane(table);
				scr.setSize( 800, 350);
				scrollPane.setLayout(null);
				scrollPane.add(scr);
				scrollPane.setSize(800, 350);
				scrollPane.setVisible(true);
			}
		});

		table = new JTable(new Object[][]{},CKD_TITLES) ;
		for (int i = 0; i < CKD_TITLES.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(114);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scr =new JScrollPane(table);
		scr.setSize( 800, 350);
		scrollPane.setLayout(null);
		scrollPane.add(scr);
		scrollPane.setSize( 800, 350);
		scrollPane.setVisible(true);
	}

	public Object[][] getList(List<DailyPowerRecord> listInfo) {
		Object obj[][]=new Object[listInfo.size()][13];
		for (int i = 0; i < listInfo.size(); i++) {
		}
		return obj;
	}

	private JTable getTable(String[] titles,Object[][] data){
		MyTableModel model = new MyTableModel(titles, data);  
        JTable table=new MyTable(model); 
        //选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        //table添加行监听
//        SelectionListener listener = new SelectionListener(table);
//        table.getSelectionModel().addListSelectionListener(listener);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        TableColumn firstColumn = columns.nextElement();
        firstColumn.setWidth(60);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        //table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setRowHeight(20);
        table.setDefaultRenderer(Object.class, render);
        while (columns.hasMoreElements()){
        	TableColumn column = columns.nextElement();
        	column.setPreferredWidth(150);
//        	column.setCellRenderer(render);
        }
//        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  
        table.setRowSelectionAllowed(true);  
//        table.setColumnSelectionAllowed(true); 
        table.setBounds(30, 30, 800, 300);
		return table;
	}
}
