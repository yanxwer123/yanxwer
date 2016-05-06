package com.kld.app.view.daily;

import com.kld.app.service.AlarmDailyLostService;
import com.kld.app.service.DailyStationShiftInfoService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.acceptance.MyTableModel;
import com.kld.gsm.ATG.domain.DailyOpotCount;
import com.kld.gsm.ATG.domain.DailyPumpDigitShift;
import com.kld.gsm.ATG.domain.DailyStationShiftInfo;
import com.kld.gsm.ATG.domain.DailyTankShift;
import com.kld.gsm.MacLog.PayTypeEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class YzbbMxPage extends JFrame{
	private DailyStationShiftInfoService dailyStationShiftInfoService =
			(DailyStationShiftInfoService) (Context.getInstance().getBean("dailyStationShiftInfoService"));
	//汞码信息
	private static final String[] GM_TITLES = new String[]{"枪号","接班泵码","交班泵码","走字数"};
	//付油信息
	private static final String[] FY_TITLES = new String[]{"油品","合计","升数","油票","记账","银行卡","其他1","其他2","IC卡","IC卡积分","已售未提","已提未售","代存代付","油票代管","自用油","非机走"};
	//油罐信息	
	private static final String[] YG_TITLES = new String[]{"油罐编号","油品","接班油量(L)","交班油高(mm)","交班升数(L)","交班水高(mm)","交班水量(L)","付油量(L)","卸油量(L)","损耗量(L)","损耗率(%)"};
   //,"卸油量","库存","损耗"

    private DailyStationShiftInfo dailyStationShiftInfo;
	
    private JScrollPane scrollPane;
    private JTable table;//泵码信息
    private Object[][] billArray = {{"枪号","接班时泵码","当前泵码","走字数"}};//去掉加油量
    
    private JScrollPane scrollPane1;
    private JTable table1;//付油信息
    private Object[][] billArray1 = {{"油品","合计","升数","油票","记账","银行卡","其他1","其他2","IC卡","IC卡积分","已售未提","已提未售","代存代付","油票代管","自用油","非机走"}};
   
    private JScrollPane scrollPane2;
    private JTable table2;//油罐信息
    private Object[][] billArray2 = {{"油罐编号","油品","接班油量(L)","交班油高(mm)","交班升数(L)","交班水高(mm)","交班水量(L)","付油量(L)","卸油量(L)","损耗量(L)","损耗率(%)"}};//
	
    
    private JDialog frame;
    public JDialog getFrame() {
        return frame;
    }
    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    /**
     * Create the application.
     */
    public YzbbMxPage(DailyStationShiftInfo bc) {
    	this.dailyStationShiftInfo = bc;
    	//System.out.println("班次号："+bc.getShift());
        initialize();
    }
    private void initialize(){
        frame = new JDialog();
        frame.setTitle("查看班报");
        frame.setResizable(false);
        frame.setBounds(320, 70, 630, 580);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
//        frame.setSize(600,700);
        //禁用close功能tf.setUndecorated(true);
        //不显示标题栏,最大化,最小化,退出按钮
//        frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG );
        frame.getContentPane().setLayout(null);

		//        JScrollPane scrollPane = new JScrollPane();
//        frame.getContentPane().add(scrollPane);
//		scrollPane.setBounds(0, 0, 1100, 660);
//		scrollPane.setVisible(true);
		SimpleDateFormat Datefo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		JPanel panel = new JPanel();
	    panel.setLayout(null);
	    panel.setBounds(0, 0, 700, 580);
	    panel.setPreferredSize(new Dimension(200, 700));
	    
	    
	    JLabel bcLabel = new JLabel("班次:"+dailyStationShiftInfo.getShift());
	    bcLabel.setFont(Constant.BOTTOM_FONT); 
	    bcLabel.setBounds(30, 10, 150, 12);
	    bcLabel.setForeground(Color.BLACK);
	    panel.add(bcLabel);
	    
	    JLabel jbrLabel = new JLabel("接班人:"+dailyStationShiftInfo.getSuccessor());
	    jbrLabel.setFont(Constant.BOTTOM_FONT); 
	    jbrLabel.setBounds(160, 10, 150, 12);
	    jbrLabel.setForeground(Color.BLACK);
	    panel.add(jbrLabel);

		//System.out.println("----------123:" + dailyStationShiftInfo.getSucceedtime());

		String succeedtime="";
		if (dailyStationShiftInfo.getShifttime()!=null)
		{
			succeedtime=Datefo.format(dailyStationShiftInfo.getShifttime());
		}

	    JLabel jbsjLabel = new JLabel("接班时间:"+succeedtime);
	    jbsjLabel.setFont(Constant.BOTTOM_FONT); 
	    jbsjLabel.setBounds(270, 10, 220, 12);
	    jbsjLabel.setForeground(Color.BLACK);
	    panel.add(jbsjLabel);
	    
	    JLabel jiaobanrenLabel = new JLabel("交班人:"+dailyStationShiftInfo.getShiftoperator());
	    jiaobanrenLabel.setFont(Constant.BOTTOM_FONT); 
	    jiaobanrenLabel.setBounds(500, 10, 150, 12);
	    jiaobanrenLabel.setForeground(Color.BLACK);
	    panel.add(jiaobanrenLabel);
	    
	    JLabel gmLabel = new JLabel("泵码信息");
	    gmLabel.setFont(Constant.BOTTOM_FONT); 
	    gmLabel.setBounds(30, 35, 84, 12);
	    gmLabel.setForeground(Color.BLACK);
	    panel.add(gmLabel);

		//System.out.println("-------aa");
	   // if(getGMxx()!=nul
	    	billArray = getGMxx();
	    //}
        table = getTable(GM_TITLES, billArray);
		table.setBounds(30, 65, 570, 130);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBounds(30, 65, 575, 130);
        panel.add(scrollPane);

		//从195开始的
		JLabel fyLabel = new JLabel("付油信息");
        fyLabel.setFont(Constant.BOTTOM_FONT); 
        fyLabel.setBounds(30, 205, 84, 12);
        fyLabel.setForeground(Color.BLACK);
	    panel.add(fyLabel);
	    
	    if(getFYxx()!=null){
	    	billArray1 = getFYxx();
	    }
        table1 = getTable(FY_TITLES, billArray1);
        scrollPane1 = new JScrollPane();
        scrollPane1.setViewportView(table1);
        scrollPane1.setBounds(30, 235, 575, 130);
        panel.add(scrollPane1);
        
        
        JLabel ygLabel = new JLabel("油罐信息");
        ygLabel.setFont(Constant.BOTTOM_FONT); 
        ygLabel.setBounds(30, 375, 84, 12);
        ygLabel.setForeground(Color.BLACK);
	    panel.add(ygLabel);
        
	    if(getYGxx()!=null){
	    	billArray2 = getYGxx();
	    }
	    table2 = getTable(YG_TITLES, billArray2);
        scrollPane2 = new JScrollPane();
        scrollPane2.setViewportView(table2);
        scrollPane2.setBounds(30, 410,575, 130);

        panel.add(scrollPane2);
        
        frame.add(panel);
    }
    
	private Object[][] getGMxx() {
		List<DailyPumpDigitShift> list = dailyStationShiftInfoService.queryGMxx(dailyStationShiftInfo.getShift().toString());
		Object[][] ob = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			DailyPumpDigitShift shift = list.get(i);
			//"枪号","接班时泵码","当前泵码","加油量","走字数"
			ob[i][0] = shift.getOilgun();
			ob[i][1] = shift.getTopump();
			ob[i][2] = shift.getHopump();
			//ob[i][3] = "加油量";//需要通过班次统计
			ob[i][3] = shift.getPumpnum();// 当前泵码-接班泵码
		}
		return ob;
	}
	DecimalFormat df = new DecimalFormat("######0.00");

	private class result
	{
	/*	 现金 00
		邮票 01
		记账 02
		银行卡 03
		其他1 04
		其他2 05
		IC卡 06
		IC卡积分 16
		已售未提 22
		已提未售 32
		代存代付 42
		邮票代管 52
		自用油 62
		非机走 07*/
		private String oilno;//油品
        private Double total;//合计
		private Double liter;//升数
		private Double yp;//邮票
		private Double jz;//记账
		private Double yhk;//银行卡
		private Double other1;//其他1
		private Double other2;//其他2
		private Double ic;//ic卡
		private Double icjf;//ic卡积分
		private Double yswt;//已售未提
		private Double ytws;//已提未售
		private Double dcdf;//代存代付
		private Double ypdg;//邮票代管
		private Double zyy;//自用油
		private Double fjz;//飞机走


		public String getOilno() {
			return oilno;
		}

		public Double getTotal() {

			return Double.valueOf(df.format(total));
		}

		public Double getLiter() {
			return  Double.valueOf(df.format(liter));
		}

		public void setLiter(Double liter) {
			this.liter = liter;
		}

		public Double getYp() {
			return   Double.valueOf(df.format(yp));
		}

		public Double getJz() {
			return Double.valueOf(df.format(jz));
		}

		public Double getYhk() {
			return Double.valueOf(df.format(yhk));
		}

		public Double getOther1() {
			return Double.valueOf(df.format(other1));
		}

		public Double getOther2() {
			return Double.valueOf(df.format(other2));
		}

		public Double getIc() {
			return Double.valueOf(df.format(ic));
		}

		public Double getIcjf() {
			return Double.valueOf(df.format(icjf));
		}

		public Double getYswt() {
			return Double.valueOf(df.format(yswt));
		}

		public Double getYtws() {
			return Double.valueOf(df.format(ytws));
		}

		public Double getDcdf() {
			return Double.valueOf(df.format(dcdf));
		}

		public Double getYpdg() {
			return Double.valueOf(df.format(ypdg));
		}

		public Double getZyy() {
			return Double.valueOf(df.format(zyy));
		}

		public Double getFjz() {
			return Double.valueOf(df.format(fjz));
		}

		public void setOilno(String oilno) {
			this.oilno = oilno;
		}

		public void setTotal(Double total) {
			this.total = total;
		}

		public void setYp(Double yp) {
			this.yp = yp;
		}

		public void setJz(Double jz) {
			this.jz = jz;
		}

		public void setYhk(Double yhk) {
			this.yhk = yhk;
		}

		public void setOther1(Double other1) {
			this.other1 = other1;
		}

		public void setOther2(Double other2) {
			this.other2 = other2;
		}

		public void setIc(Double ic) {
			this.ic = ic;
		}

		public void setIcjf(Double icjf) {
			this.icjf = icjf;
		}

		public void setYswt(Double yswt) {
			this.yswt = yswt;
		}

		public void setYtws(Double ytws) {
			this.ytws = ytws;
		}

		public void setDcdf(Double dcdf) {
			this.dcdf = dcdf;
		}

		public void setYpdg(Double ypdg) {
			this.ypdg = ypdg;
		}

		public void setZyy(Double zyy) {
			this.zyy = zyy;
		}

		public void setFjz(Double fjz) {
			this.fjz = fjz;
		}


	}

	AlarmDailyLostService alarmDailyLostService =
			(AlarmDailyLostService) (Context.getInstance().getBean("alarmDailyLostService"));
	private Object[][] getFYxx() {
		List<DailyOpotCount> OilNolist =dailyStationShiftInfoService.selectFyxx(dailyStationShiftInfo.getShift().toString());
		//dailyStationShiftInfoService.queryFyxxOilNo(dailyStationShiftInfo.getShift().toString());
		 //


	     List<result> list=getDataInfo(OilNolist);
		//System.out.println(dailyStationShiftInfo.getShift().toString());
		//dailyStationShiftInfoService.queryFyxxOilNo(dailyStationShiftInfo.getShift().toString());
		//Object[][] ob = new Object[OilNolist.size()][12];
		//for (int i = 0; i < OilNolist.size(); i++) {
		Object[][] ob = new Object[list.size()][16];
		for (int i = 0; i < list.size(); i++) {
			//"油品","合计","现金","IC卡","POS机","应收款付油","预收款付油",
			//"自用油","站间转仓","回灌","现金总金额","IC卡总金额"
			//String OilNo = ((Map)OilNolist.get(i)).get("OilNo").toString();
			//ob[i][0] = OilNo;
			//List list = dailyStationShiftInfoService.queryFyxx(dailyStationShiftInfo.getShift().toString(),OilNo);
			//SELECT count(Liter) as Liter,TraCode,count(Amount) as Amount
			//List list=new ArrayList(12);
				/*油品 合计	现金 00
		邮票 01
		记账 02
		银行卡 03
		其他1 04
		其他2 05
		IC卡 06
		IC卡积分 16
		已售未提 22
		已提未售 32
		代存代付 42
		邮票代管 52
		自用油 62
		非机走 07*/

			for (int j = 0; j < list.size(); j++) {
				result info = list.get(i);
				ob[i][0]=alarmDailyLostService.selectOilNo(info.getOilno());
				ob[i][1]=info.getTotal();
				ob[i][2]=info.getLiter();
				ob[i][3]=info.getYp();
				ob[i][4]=info.getJz();
				ob[i][5]=info.getYhk();
				ob[i][6]=info.getOther1();
				ob[i][7]=info.getOther2();
				ob[i][8]=info.getIc();
				ob[i][9]=info.getIcjf();
				ob[i][10]=info.getYswt();
				ob[i][11]=info.getYtws();
				ob[i][12]=info.getDcdf();
				ob[i][13]=info.getYpdg();
				ob[i][14]=info.getZyy();
				ob[i][15]=info.getFjz();
				/*if(map.get("Type").toString().equals("00")){//现金
					ob[i][2] = map.get("Liter").toString();
					ob[i][10] = map.get("Amount").toString();
				}else if(map.get("TraCode").toString().equals("20")){//ic卡
					ob[i][3] = map.get("Liter").toString();
					ob[i][11] = map.get("Amount").toString();
				}else if(map.get("TraCode").toString().equals("30")){//pos机
					ob[i][4] = map.get("Liter").toString();
				}else if(map.get("TraCode").toString().equals("40")){//应收款付油
					ob[i][5] = map.get("Liter").toString();
				}else if(map.get("TraCode").toString().equals("50")){//预收款付油
					ob[i][6] = map.get("Liter").toString();
				}else if(map.get("TraCode").toString().equals("60")){//自用油
					ob[i][7] = map.get("Liter").toString();
				}else if(map.get("TraCode").toString().equals("70")){//回灌
					ob[i][8] = map.get("Liter").toString();
				}else if(map.get("TraCode").toString().equals("80")){//站间转仓
					ob[i][9] = map.get("Liter").toString();
				}*/
			}
			/*ob[i][1] =new Integer(ob[i][2].toString())+new Integer(ob[i][3].toString())+
					new Integer(ob[i][4].toString())+new Integer(ob[i][5].toString())+
					new Integer(ob[i][6].toString())+new Integer(ob[i][7].toString())+
					new Integer(ob[i][8].toString())+new Integer(ob[i][9].toString());*/
		}
		return ob;
	}

	result minModel = null;
	private void AddModel(DailyOpotCount o)
	{
		minModel = new result();
		minModel.setOilno(o.getOilno());
		////System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~" + o.getType());
		////System.out.println("!!!!!!!!!!!!!!!!!!!!"+GunStatusEnum.下班.value());
		if (o.getType().equals(PayTypeEnum.升数.value()))//升数
		{
			minModel.setLiter(o.getLiter());
		}
		else
		{
			minModel.setLiter(0.00);
		}

		if (o.getType().equals(PayTypeEnum.邮票.value()))//邮票
		{
			minModel.setYp(o.getAmount());
		}
		else
		{
			minModel.setYp(0.00);
		}
		if (o.getType().equals(PayTypeEnum.记账.value()))//记账
		{
			minModel.setJz(o.getAmount());
		}
		else
		{
			minModel.setJz(0.00);
		}
		if (o.getType().equals(PayTypeEnum.银行卡.value()))//银行卡
		{
			minModel.setYhk(o.getAmount());
		}
		else
		{
			minModel.setYhk(0.00);
		}
		if (o.getType().equals(PayTypeEnum.其他1.value()))//其他1
		{
			minModel.setOther1(o.getAmount());
		}
		else
		{
			minModel.setOther1(0.00);
		}
		if (o.getType().equals(PayTypeEnum.其他2.value()))//其他2
		{
			minModel.setOther2(o.getAmount());
		}
		else
		{
			minModel.setOther2(0.00);
		}
		if (o.getType().equals(PayTypeEnum.IC卡积分.value()))//IC卡积分
		{
			minModel.setIcjf(o.getAmount());
		}
		else
		{
			minModel.setIcjf(0.00);
		}

		if (o.getType().equals(PayTypeEnum.IC卡.value()))//IC卡
		{
			minModel.setIc(o.getAmount());
		}
		else
		{
			minModel.setIc(0.00);
		}

		if (o.getType().equals(PayTypeEnum.已售未提.value()))//已售未提
		{
			minModel.setYswt(o.getAmount());
		}
		else
		{
			minModel.setYswt(0.00);
		}

		if (o.getType().equals(PayTypeEnum.已提未售.value()))//已提未售
		{
			minModel.setYtws(o.getAmount());
		}
		else
		{
			minModel.setYtws(0.00);
		}

		if (o.getType().equals(PayTypeEnum.代存代付.value()))//已提未售
		{
			minModel.setDcdf(o.getAmount());
		}
		else
		{
			minModel.setDcdf(0.00);
		}

		if (o.getType().equals(PayTypeEnum.邮票代管.value()))//已提未售
		{
			minModel.setYpdg(o.getAmount());
		}
		else
		{
			minModel.setYpdg(0.00);
		}

		if (o.getType().equals(PayTypeEnum.自用油.value()))//自用油
		{
			minModel.setZyy(o.getAmount());
		}
		else
		{
			minModel.setZyy(0.00);
		}

		if (o.getType().equals(PayTypeEnum.非机走.value()))	//非机走
		{
			minModel.setFjz(o.getAmount());
		}
		else
		{
			minModel.setFjz(0.00);
		}
	}

	private List<result> getDataInfo(List<DailyOpotCount> oilNolist) {
			Map<String, List<DailyOpotCount>> map =new HashMap<String, List<DailyOpotCount>>();
			for (DailyOpotCount a : oilNolist) {
				String oilNo = a.getOilno();
				List<DailyOpotCount> tmpList = map.get(oilNo);
				if (tmpList == null){
					tmpList = new ArrayList<DailyOpotCount>();
					map.put(oilNo, tmpList);
				}
				tmpList.add(a);
			}
			List< result> list = new ArrayList<result>();
			// 遍历map
			for (Map.Entry<String, List<DailyOpotCount>> entry : map.entrySet()) {
				//result minModel = null;
				for (DailyOpotCount o : entry.getValue()) {
					if (minModel==null) {
						AddModel(o);
					}
					else
					{
						if (minModel.getOilno().equals(o.getOilno()))
						{
							if (o.getType().equals(PayTypeEnum.升数.value()))
							{
								minModel.setLiter(o.getLiter());
							}
							if (o.getType().equals(PayTypeEnum.邮票.value()))
							{
								minModel.setYp(minModel.getYp() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.记账.value()))
							{
								minModel.setJz(minModel.getJz() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.银行卡.value()))
							{
								minModel.setYhk(minModel.getYhk() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.其他1.value()))
							{
								minModel.setOther1(minModel.getOther1() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.其他2.value()))
							{
								minModel.setOther2(minModel.getOther2() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.IC卡积分.value()))
							{
								minModel.setIcjf(minModel.getIcjf() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.IC卡.value()))//IC卡
							{
								minModel.setIc(minModel.getIc() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.已售未提.value()))//已售未提
							{
								minModel.setYswt(minModel.getYswt() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.已提未售.value()))//已提未售
							{
								minModel.setYtws(minModel.getYtws() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.代存代付.value()))//代存代付
							{
								minModel.setDcdf(minModel.getDcdf() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.邮票代管.value()))
							{
								minModel.setYpdg(minModel.getYpdg() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.自用油.value()))//自用油
							{
								minModel.setZyy(minModel.getZyy() + o.getAmount());
							}
							if (o.getType().equals(PayTypeEnum.非机走.value()))
							{
								minModel.setFjz(minModel.getFjz() + o.getAmount());
							}
						}
						else
						{
							AddModel(o);
						}

					/*minModel.setReceivel(minModel.getReceivel() + o.getReceivel());
					minModel.setTodayout(minModel.getTodayout() + o.getReceivel());
					minModel.setDeliveryno(minModel.getDeliveryno().toString() + "," + o.getDeliveryno().toString());*/
					}
				}
				//todo
				result model = new result();
				model.setOilno(minModel.getOilno());//品种
				model.setLiter(minModel.getLiter());//升数
				model.setYp(minModel.getYp());//邮票
				model.setJz(minModel.getJz());
				model.setYhk(minModel.getYhk());
				model.setOther1(minModel.getOther1());
				model.setOther2(minModel.getOther2());
				model.setIcjf(minModel.getIcjf());
				model.setYtws(minModel.getYtws());
				model.setYswt(minModel.getYswt());
				model.setZyy(minModel.getZyy());//自用油
				model.setIc(minModel.getIc());
				model.setDcdf(minModel.getDcdf());
				model.setYpdg(minModel.getYpdg());
				model.setFjz(minModel.getFjz());
				model.setTotal(minModel.getLiter()+minModel.getZyy()+minModel.getIc());
				list.add(model);

				minModel=null;
			}
			return list;

	}


	private Object[][] getYGxx() {
		List<com.kld.gsm.ATG.domain.DailyTankShift> list = dailyStationShiftInfoService.queryYGxx(dailyStationShiftInfo.getShift().toString());
		Object[][] ob = new Object[list.size()][11];
		for (int i = 0; i < list.size(); i++) {
			DailyTankShift shift = list.get(i);
			//"油罐编号","油品","接班油量（L）","交班油高（mm）","交班升数",
			//"交班水高（mm）","交班水量（L）","付油量","卸油量","损耗量","损耗率"
			DecimalFormat df1=new DecimalFormat("######.00");
			ob[i][0] = shift.getOilcan();
			ob[i][1] = shift.getOilname();
			ob[i][2] = df1.format(shift.getTooill());
			ob[i][3] = shift.getHooilhigh();
			ob[i][4] = df1.format(shift.getHooill());
			ob[i][5] = shift.getHeightwater();
			ob[i][6] = df1.format(shift.getWaterl());
			ob[i][7] = df1.format(shift.getSalel());
			//ob[i][7] = shift.getInoill();
			ob[i][8] = df1.format(shift.getInoill());
			//损耗量
			ob[i][9] = df1.format(shift.getTooill() + shift.getInoill() - shift.getSalel() - shift.getHooill());
			//计算损耗率,如果付油量为0,则设置损耗率为100
			Double dShl=100.00;
			if(shift.getSalel()>0)
			{
				dShl=(shift.getTooill() + shift.getInoill() - shift.getSalel() - shift.getHooill())/shift.getSalel()*100;
			}

			ob[i][10] = df.format(dShl)+"%";
		}
		return ob;
	}
	private JTable getTable(String[] titles,Object[][] data){

		MyTableModel model = new MyTableModel(titles, data);
        JTable table=new MyTable(model);  
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
		//table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(20);
        table.setDefaultRenderer(Object.class, render);
		if(data.equals(billArray1)||data.equals(billArray2)){
			//System.out.println("打印下&&&&&"+data.equals(billArray1));
			FitTableColumns(table);
		}
		if(data.equals(billArray)){
			Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
			TableColumn firstColumn = columns.nextElement();
			firstColumn.setWidth(90);
		while (columns.hasMoreElements()){
			TableColumn column = columns.nextElement();
			column.setPreferredWidth(160);
//	        	column.setCellRenderer(render);
		}}
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        table.setRowSelectionAllowed(true);  
//        table.setColumnSelectionAllowed(true); 
//        table.setBounds(30, 60, 1000, 230);
		return table;
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
