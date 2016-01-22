package com.kld.app.view.monitor;


import com.kld.app.service.MonitorTimeInventoryService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.acceptance.MyTableModel;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import org.jdesktop.swingx.JXDatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 时点库存
 * 
 * @author YANGRL
 * 
 */
public class SdkcPage extends JPanel {
	private JComboBox ygbhcomboBox;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


	private String[] names = { "油罐编号", "油品", "库存时间", "标准体积(L)", "油水总高(mm)",
			"净油体积(L)", "水高(mm)", "水量(L)", "平均温度(℃)", "空体积(L)" };
	// table的数据
	private Object[][] data = {{ "", "", "", "", "",
			"", "", "", "", "" }};
	

	private JScrollPane scrollPane = new JScrollPane();
	private MonitorTimeInventoryService dailyStationShiftInfoService =
			(MonitorTimeInventoryService) (Context.getInstance().getBean("monitorTimeInventoryService"));
	
	private List<MonitorTimeInventory> dataList = new ArrayList<MonitorTimeInventory>();
	public void setPanel(final JPanel centerPanel) {
		centerPanel.setLayout(null);
        centerPanel.setBackground(Color.white);


		JLabel rqlab = new JLabel("日期：");
//		rqlab.setForeground(Color.WHITE);
		centerPanel.add(rqlab);
		rqlab.setBounds(10, 5, 40, 30);
		
		//日期选择器
		final JXDatePicker beginDate=new JXDatePicker();
		beginDate.getEditor().setEditable(false);
		beginDate.setPreferredSize(new Dimension(200, 100));
		beginDate.setFormats(dateFormat);
		beginDate.setBounds(50, 5, 150, 25);
		beginDate.setDate(new Date());
		centerPanel.add(beginDate);
//		final DateChooser mp = new DateChooser();
//		mp.setPreferredSize(new Dimension(200, 100));
//		centerPanel.add(mp);
//		mp.setBounds(41, 0, 200, 100);

		JLabel ygbhlab = new JLabel("油罐编号：");
//		ygbhlab.setForeground(Color.WHITE);
		centerPanel.add(ygbhlab);
		ygbhlab.setBounds(240, 5, 80, 30);

		ygbhcomboBox = new JComboBox();
		List<SysManageCanInfo> list =dailyStationShiftInfoService.selectAll();
		ygbhcomboBox.addItem("全部");
		for (SysManageCanInfo sysManageTankInfo : list) {

			ygbhcomboBox.addItem(sysManageTankInfo.getOilcan());
		}
		centerPanel.add(ygbhcomboBox);
		ygbhcomboBox.setBounds(320, 5, 150, 25);
		centerPanel.updateUI();

		JButton cxbutton = new JButton("查询");
		cxbutton.setBounds(485, 3, 60, 30);
		centerPanel.add(cxbutton);
        selectSdku(beginDate.getDate(), ygbhcomboBox.getSelectedItem());
        //页面初始化的时候加载查询



		/*JButton cxbutton = new JButton("查询");
		centerPanel.add(cxbutton);
		cxbutton.setBorderPainted(false);
		cxbutton.setContentAreaFilled(false);
		cxbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
		cxbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "search-.png"));
		cxbutton.setIcon(Common.createImageIcon(this.getClass(), "search.png"));
		cxbutton.setBounds(485, 3, 32, 32);*/

		//JTable maintable = getTable(names, data);
		scrollPane = new JScrollPane();
		//scrollPane.setViewportView(maintable);
		scrollPane.setBounds(4, 55, 790, 335);//
		centerPanel.add(scrollPane);
		getTable(names, data);


//		jLabel.setForeground(Color.WHITE);
		cxbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//时间校验
				if (null == beginDate.getDate() || "".equals(beginDate.getDate())) {
					JOptionPane.showMessageDialog(null, "请输入开始时间！", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				selectSdku(beginDate.getDate(), ygbhcomboBox.getSelectedItem());

			}
		});
		centerPanel.add(scrollPane);
		//getTable(names, data);
//		JTable maintable = getTable(names, data);
//		scrollPane = new JScrollPane();
//        scrollPane.setViewportView(maintable);
//        scrollPane.setBounds(10, 325, 780, 60);
////        maintable.setBounds(10, 325, 780, 60);
//		centerPanel.add(scrollPane);
//
//		JLabel jLabel = new JLabel("时点库存列表");
//		centerPanel.add(jLabel);
//		jLabel.setForeground(Color.WHITE);
//		jLabel.setBounds(10, 305, 100, 20);

	}

    private void selectSdku(Date beginDate, Object selectedItem) {

        HashMap map = new HashMap();
        map.put("begin", beginDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(beginDate);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        map.put("end", calendar.getTime());
        //System.out.println("打印出开始与结束事假" +beginDate.getDate());
        //System.out.println("打印出开始与结束事假"+ calendar.getTime());
        if ("全部".equals(ygbhcomboBox.getSelectedItem().toString())) {
            map.put("oilcan", null);
        } else {
            map.put("oilcan", ygbhcomboBox.getSelectedItem().toString());
        }
        List<MonitorTimeInventory> list = dailyStationShiftInfoService.querySdkc(map);
        //如果长度小于0，则返回
       /* if (list.size() < 1) {

            //scrollPane.updateUI();
            //return;
        }*/
        data = new Object[list.size()][10];
        for (int i = 0; i < list.size(); i++) {
            MonitorTimeInventory inventory = list.get(i);
            data[i][0] = inventory.getOilcan();
            data[i][1] = inventory.getOilno();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            data[i][2] = formatter.format(inventory.getStoretime());
			DecimalFormat decimalFormat = new DecimalFormat("######0.00");
			data[i][3] = decimalFormat.format(inventory.getStandardl());
            data[i][4] = inventory.getHeighttotal();
            data[i][5] = inventory.getOill();
            data[i][6] = inventory.getHeightwater();
            data[i][7] = inventory.getWaterl();
            data[i][8] = inventory.getTemperature();
            data[i][9] = inventory.getVolumeempty();
        }
       /* if (list.size() > 0) {
           *//* JTable maintable = getTable(names, data);
            scrollPane.setViewportView(maintable);
            scrollPane.setBounds(10, 55, 780, 330);
            scrollPane.updateUI();*//*

        }*/
		getTable(names, data);

    }

    // region生成显示图表的面板

	public JPanel createDemoLine(List<MonitorTimeInventory> list) {

		JFreeChart jfreechart = createChart(createDataset(list));

		if(jfreechart==null){
			return null;
		}

		return new ChartPanel(jfreechart);

	}

	// 生成图表主对象JFreeChart

	public static JFreeChart createChart(DefaultCategoryDataset linedataset) {
		if(linedataset.getColumnCount()<=0){
			return null;
		}
		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 0));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 0));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);

		// 定义图表对象

		JFreeChart chart = ChartFactory.createLineChart("一季度销售曲线", // 折线图名称

				"时间", // 横坐标名称

				"销售额(百万)", // 纵坐标名称

				linedataset, // 数据

				PlotOrientation.VERTICAL, // 水平显示图像

				false, // include legend

				true, // tooltips

				false // urls

				);

		CategoryPlot plot = chart.getCategoryPlot();

		plot.setRangeGridlinesVisible(true); // 是否显示格子线

		plot.setBackgroundAlpha(0.3f); // 设置背景透明度

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

		// chart.getTitle().setFont(new Font("宋体", Font.BOLD,0));//标题设置字体为0号则隐藏

		// rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,0)); //水平底部标题
		//
		// rangeAxis.setTickLabelFont(new Font("宋体",Font.BOLD,0)); //垂直标题

		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		rangeAxis.setAutoRangeIncludesZero(true);

		rangeAxis.setUpperMargin(0.20);

		rangeAxis.setLabelAngle(Math.PI / 2.0);

		// 有人说这个是水平方向设置的 方法。
		// ValueAxis numberaxis = plot.getRangeAxis();

		/*------设置X轴坐标上的文字-----------*/
		// rangeAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 12));

		/*------设置X轴的标题文字------------*/
		// rangeAxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));

		/*------设置Y轴坐标上的文字-----------*/
		// rangeAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 12));

		/*------设置Y轴的标题文字------------*/
		// rangeAxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));

		/*------这句代码解决了底部汉字乱码的问题-----------*/
		// chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

		return chart;

	}

	// 生成数据

	public DefaultCategoryDataset createDataset(List<MonitorTimeInventory> list) {


		DefaultCategoryDataset linedataset = new DefaultCategoryDataset();

		// 各曲线名称

		String series1 = "净油体积";

		// 横轴名称(列名称)
		String[] type = {"0","1:00","2:00","3:00","4:00","5:00","6:00","7:00","8:00"
				,"9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00"
				,"19:00","20:00","21:00","22:00","23:00"};
		int hour_show = 0;
		for (int i = 0; i < list.size(); i++) {
			MonitorTimeInventory monitorTimeInventory = list.get(i);
			int hour = monitorTimeInventory.getStoretime().getHours();
			if(hour == hour_show){
				linedataset.addValue(monitorTimeInventory.getOill(), series1, type[hour_show]);
				hour_show++;
				dataList.add(monitorTimeInventory);
			}
		}
		return linedataset;

	}
	public static void main(String[] args) {
		//System.out.println(new Date().getHours());
	}
	//endregion

	private void getTable(String[] titles,Object[][] data){
		DefaultTableModel model = new DefaultTableModel(data,titles);
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
		FitTableColumns(table);

//        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setRowSelectionAllowed(true);
//        table.setColumnSelectionAllowed(true);
//        table.setBounds(30, 30, 740, 260);
		//return table;
		table.updateUI();
		FitTableColumns(table);

		scrollPane.setViewportView(table);

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
}
