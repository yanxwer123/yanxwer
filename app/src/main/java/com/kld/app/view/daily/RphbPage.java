package com.kld.app.view.daily;

import com.kld.app.service.AlarmDailyLostService;
import com.kld.app.service.DailyDailyBalanceService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.app.view.acceptance.MyTable;
import com.kld.gsm.ATG.domain.DailyDailyBalance;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * 日平衡
 * @author YANGRL
 *
 */
public class RphbPage {
	Logger log = Logger.getLogger(this.getClass());
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private JScrollPane scrollPane = new JScrollPane();
	List<DailyDailyBalance> list = new ArrayList();
	JTable probePartable;
	private JTable table;

	final String[] tableHeads= {
			"品种",
			"期初库存(L)",
			"出货单号",
			"进货数量(L)",
			"期间付出(L)",
			"期末库存(L)",
			"实测库存(L)",
			"损耗量(L)",
			"损耗率(%)"
	};
	//table数据
	Object[][] data = new Object[0][tableHeads.length];



	public void setPanel(JPanel centerPanel){

		centerPanel.setLayout(null);
		scrollPane.setBounds(0, 50, 800, 340);
		centerPanel.add(scrollPane);

		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBounds(0, 0, 800, 300);
        centerPanel.add(panel1);
//		panel1.setBackground(new Color(Integer.decode(Constant.CENTER_BG_COCLER)));

		JLabel startLab = new JLabel("日期：");
//		startLab.setForeground(Color.WHITE);
		startLab.setFont(Constant.BOTTOM_FONT);
		startLab.setBounds(15, 10, 40, 30);
//		startLab.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel1.add(startLab);

		//日期选择器
		final JXDatePicker beginDate=new JXDatePicker();
		beginDate.getEditor().setEditable(false);
		beginDate.setBounds(56, 10, 150, 30);
		beginDate.setFormats(dateFormat);
		beginDate.setDate(new Date());
		panel1.add(beginDate);

		JLabel a = new JLabel("至");
		a.setBounds(220, 10, 20, 30);
//		a.setForeground(Color.WHITE);
		panel1.add(a);

		final JXDatePicker endDate=new JXDatePicker();
		endDate.getEditor().setEditable(false);
		endDate.setPreferredSize(new Dimension(200, 30));
		endDate.setBounds(246, 10, 150, 30);
		endDate.setFormats(dateFormat);
		endDate.setDate(new Date());
		panel1.add(endDate);
		panel1.updateUI();


		// 日期选择器
		JButton cxbutton = new JButton("查询");
		cxbutton.setBounds(410, 10, 60, 30);
		panel1.add(cxbutton);
		selectDailyDailyBalance(beginDate.getDate(), endDate.getDate());

		cxbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//时间校验
				if (null == beginDate.getDate() || "".equals(beginDate.getDate())) {
					JOptionPane.showMessageDialog(null, "请输入开始时间！", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (null == endDate.getDate() || "".equals(endDate.getDate())) {
					JOptionPane.showMessageDialog(null, "请输入结束时间！", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (endDate.getDate().compareTo(beginDate.getDate()) < 0) {
					JOptionPane.showMessageDialog(null, "开始时间不能晚于结束时间,请重新选择", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				selectDailyDailyBalance(beginDate.getDate(), endDate.getDate());


			}
		});
		getTable(tableHeads, data);
		/*probePartable = getTable(tableHeads,data);
		scrollPane.setViewportView(probePartable);*/
	}

			private void selectDailyDailyBalance(Date beginDate, Date endDate) {
				DailyDailyBalanceService dailyDailyBalanceService = (DailyDailyBalanceService) (Context
						.getInstance().getBean("dailyDailyBalanceQueryService"));
				AlarmDailyLostService alarmDailyLostService =
						(AlarmDailyLostService) (Context.getInstance().getBean("alarmDailyLostService"));
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(endDate);
				calendar.add(calendar.DATE, 1);//
				List<DailyDailyBalance> dailyDailyBalancesList = dailyDailyBalanceService.selectByDate(beginDate, calendar.getTime());
				int j=0;
				/*for(DailyDailyBalance d: dailyDailyBalancesList){
					j++;
					d.toString();
					////System.out.println("查询的数据是，第"+j+"个是："+d.toString());
				}*/
				list = listToMap(dailyDailyBalancesList);
				DecimalFormat decimalFormat = new DecimalFormat("######0.00");
				data = new Object[list.size()][tableHeads.length];
				for (int i = 0; i < list.size(); i++) {
					DailyDailyBalance info = list.get(i);
					DecimalFormat df=new DecimalFormat("0");
					data[i][0] = alarmDailyLostService.selectOilNo(info.getOilno());//油品类型
					data[i][1] = df.format(info.getDarlyankstock());//本日罐存（账存）
					//data[i][2] = info.getDeliveryno();//出库单号
					if (info.getDeliveryno().equals(",")){
						data[i][2] = "";
					}else{
						data[i][2] = info.getDeliveryno();
					}
					data[i][3] = df.format(info.getReceivel());//进货数量
					data[i][4] = df.format(info.getTodayout());//本日付出
					String todaystock=decimalFormat.format(info.getTodaystock());
					data[i][5] = Double.parseDouble(todaystock);//期末库存
					data[i][6] = df.format(info.getRealstock());//实测库存
					String loss=decimalFormat.format(info.getLoss());
					data[i][7] =Double.parseDouble(loss) ;//损耗量
					log.error("info.getLosssent():"+info.getLosssent());
					String losssent=decimalFormat.format(info.getLosssent())+"%";
					log.error("losssent:"+losssent);
					data[i][8] = losssent;//损耗率

				}
				getTable(tableHeads, data);
				/*probePartable = getTable(tableHeads, data);
				scrollPane.setViewportView(probePartable);
				scrollPane.updateUI();*/
			}

			private List<DailyDailyBalance> listToMap(List<DailyDailyBalance> dailyDailyBalancesList) {
				////System.out.println("开始转换");
				Map<String, List<DailyDailyBalance>> map = new HashMap<String, List<DailyDailyBalance>>();
				for (DailyDailyBalance model : dailyDailyBalancesList) {
					String oilNo = model.getOilno();
					List<DailyDailyBalance> tmpList = map.get(oilNo);
					if (tmpList == null) {
						tmpList = new ArrayList<DailyDailyBalance>();
						map.put(oilNo, tmpList);
					}
					tmpList.add(model);
				}
				List<DailyDailyBalance> list = new ArrayList<DailyDailyBalance>();
				// 遍历map
				////System.out.println("开始转换赋值");
				for (Map.Entry<String, List<DailyDailyBalance>> entry : map.entrySet()) {
					DailyDailyBalance minModel = null;
					for (DailyDailyBalance o : entry.getValue()) {
						if (minModel == null) {
							minModel = o;
						} else {
							////System.out.println("打印此处的Accountdate"+minModel.getAccountdate()+"HHHH"+o.getAccountdate());
							if (minModel.getAccountdate().after(o.getAccountdate())) {
								minModel.setDarlyankstock(o.getDarlyankstock());
							} else {
								minModel.setRealstock(o.getRealstock());
							}
							BigDecimal recSum = BigDecimal.valueOf(Double.parseDouble(minModel.getReceivel().toString()) + Double.parseDouble(o.getReceivel().toString()));
							minModel.setReceivel(recSum);
							BigDecimal todSum = BigDecimal.valueOf(Double.parseDouble(minModel.getTodayout().toString()) + Double.parseDouble(o.getReceivel().toString()));
							minModel.setTodayout(todSum);
							if(o.getDeliveryno()==null||o.getDeliveryno().toString().equals("")){
								//nothing
							}else {
								if (!minModel.getDeliveryno().toString().equals("")){
									minModel.setDeliveryno(minModel.getDeliveryno().toString() + "," + o.getDeliveryno().toString());
								}
								else{
									minModel.setDeliveryno(o.getDeliveryno().toString());
								}
							}

						}
					}
					////System.out.println("继续赋值……………………");
					DailyDailyBalance model = new DailyDailyBalance();
					model.setOilno(minModel.getOilno());//品种
					model.setDarlyankstock(minModel.getDarlyankstock());//期初灌存
					model.setDeliveryno(minModel.getDeliveryno());//出库单号
					model.setReceivel(minModel.getReceivel());//进货数量
					model.setTodayout(minModel.getTodayout());//期间付出
					BigDecimal todStockSum = BigDecimal.valueOf(Double.parseDouble(minModel.getDarlyankstock().toString()) + Double.parseDouble(minModel.getReceivel().toString()) - Double.parseDouble(minModel.getTodayout().toString()));
					model.setTodaystock(todStockSum);//期末库存  期初罐存+进货数量－付出数量
					model.setRealstock(minModel.getRealstock());//实测库存 查询里最后一天的实际库存
					BigDecimal lossSum = BigDecimal.valueOf(Double.parseDouble(minModel.getDarlyankstock().toString()) + Double.parseDouble(minModel.getReceivel().toString()) - Double.parseDouble(minModel.getTodayout().toString()) - Double.parseDouble(minModel.getRealstock().toString()));
					model.setLoss(lossSum);//耗损量  期末罐存－实测库存
					BigDecimal lsSum=BigDecimal.valueOf(0);
					if(Double.parseDouble(minModel.getTodayout().toString())>1) {
						lsSum = BigDecimal.valueOf(100 * Double.parseDouble(lossSum.toString()) / Double.parseDouble(minModel.getTodayout().toString()));
					}
					model.setLosssent(lsSum);
					//model((minModel.getTodaystock() + minModel.getReceivel() - minModel.getTodayout() - minModel.getRealstock()) / minModel.getTodayout());//损耗量/期间付出
					list.add(model);
					////System.out.println("赋值完成……………………………………");
				}
				return list;
			}


			private void getTable(String[] titles, Object[][] data) {

//      JTable probePartable = new JTable(data,tableHeads) ;
				/*AlarmTableModel model = new AlarmTableModel(tableHeads, data);
				probePartable = new JTable(model);
				probePartable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				probePartable.getTableHeader().setReorderingAllowed(false);//不拖动

				Enumeration<TableColumn> columns = probePartable.getColumnModel().getColumns();
				TableColumn firstColumn = columns.nextElement();
				firstColumn.setPreferredWidth(150);

				// 设置table表头居中
				DefaultTableCellHeaderRenderer thr = new DefaultTableCellHeaderRenderer();
				thr.setHorizontalAlignment(JLabel.CENTER);
				probePartable.getTableHeader().setDefaultRenderer(thr);
				// 设置table内容居中
				DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
				tcr.setHorizontalAlignment(JLabel.CENTER);
				probePartable.setDefaultRenderer(Object.class, tcr);

				probePartable.setRowHeight(20);
				probePartable.setBorder(new LineBorder(new Color(0, 0, 0)));
				while (columns.hasMoreElements()){
					TableColumn column = columns.nextElement();
					column.setPreferredWidth(150);
				}
				//probePartable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				probePartable.setRowSelectionAllowed(true);
				probePartable.setColumnSelectionAllowed(false);

				return probePartable;*/
				DefaultTableModel model = new DefaultTableModel(data,titles);
				table=new MyTable();
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				DefaultTableCellRenderer render = new DefaultTableCellRenderer();
				render.setHorizontalAlignment(SwingConstants.CENTER);
				//table.setBorder(new LineBorder(new Color(0, 0, 0)));
				table.setRowHeight(20);
				table.setDefaultRenderer(Object.class, render);

				table.setCellSelectionEnabled(false);
				FitTableColumns(table);
				//table.setDefaultRenderer(Object.class, r);

				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setRowSelectionAllowed(true);
				table.getColumnModel().getColumns();
				table.setModel(model);
				for (int i = 0; i < titles.length; i++) {
					table.getColumnModel().getColumn(i).setPreferredWidth(197);
				}
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
			if(width<90){
				width=90;
			}
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
						table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				if(Math.max(width, preferedWidth)>=90){
					width = Math.max(width, preferedWidth);
				}
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}
}
