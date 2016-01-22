package com.kld.app.view.monitor;

import com.kld.app.service.MonitorTimeInventoryService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Common;
import com.kld.app.util.Constant;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import com.kld.gsm.MacLog.CardTypeEnum;
import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.Socket.Constants;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 实时罐枪监控
 * @author YANGRL
 *
 */
public class SsgqjkPage implements Watcher{
	//状态图表
	private JPanel panel1 = new JPanel();
	private JPanel centerPanel = null;
//	private DailyTradeAccountService dailyTradeAccountService =
//			(DailyTradeAccountService) (Context.getInstance().getBean("dailyTradeAccountService"));
//	private MonitorTimeInventoryService monitorTimeInventoryService =
//			(MonitorTimeInventoryService) (Context.getInstance().getBean("monitorTimeInventoryService"));
MonitorTimeInventoryService monitorTimeInventoryService = Context.getInstance().getBean(MonitorTimeInventoryService.class);

	//中部需要滚动条
	public void setPanel(JPanel centerPanel1){

		this.centerPanel = centerPanel1;
		centerPanel.setLayout(null);
		//注册观察者开始
        Main.watch.addWetcher("A", new SsgqjkPage());
		if (Main.CC == null) {
			System.out.println("Link Netty Server FAll");
		} else {
			GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(),Constants.PID_Code.A15_10002.toString());
			Main.CC.writeAndFlush(gasMsg);
		}
         //注册观察者结束
	}

	private JPanel getyqxx(Map map) {
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel Label = new JLabel(map.get("GunNum")+"#");
		Label.setFont(Constant.BOTTOM_FONT);
		Label.setBounds(6, 40, 80, 12);
		Label.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label);

		JLabel Label1 = new JLabel("油机号：21");
		Label1.setFont(Constant.BOTTOM_FONT);
		Label1.setBounds(45, 10, 100, 12);
		Label1.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label1);

		JLabel Label2 = new JLabel("泵码："+map.get("PumpReadout"));
		Label2.setFont(Constant.BOTTOM_FONT);
		Label2.setBounds(45, 28, 150, 12);
		Label2.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label2);

		JLabel Label3 = new JLabel(map.get("Qty").toString());
		Label3.setFont(Constant.BOTTOM_FONT);
		Label3.setBounds(5, 55, 80, 12);
		Label3.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label3);

		JLabel Label4 = new JLabel("升");
		Label4.setFont(Constant.BOTTOM_FONT);
		Label4.setBounds(80, 55, 80, 12);
		Label4.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label4);

		JLabel Label5 = new JLabel(map.get("Amount").toString());
		Label5.setFont(Constant.BOTTOM_FONT);
		Label5.setBounds(5, 70, 80, 12);
		Label5.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label5);

		JLabel Label6 = new JLabel("元");
		Label6.setFont(Constant.BOTTOM_FONT);
		Label6.setBounds(80,70, 80, 12);
		Label6.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label6);

		JLabel Label7 = new JLabel("单价"+map.get("Price"));
		Label7.setFont(Constant.BOTTOM_FONT);
		Label7.setBounds(5, 85, 80, 12);
		Label7.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label7);

		JLabel Label8 = new JLabel("元/升");
		Label8.setFont(Constant.BOTTOM_FONT);
		Label8.setBounds(80,85, 100, 12);
		Label8.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel.add(Label8);

		JButton hold1 = new JButton();
		hold1.setBorderPainted(false);
		hold1.setContentAreaFilled(false);
		hold1.setIcon(Common.createImageIcon(this.getClass(),"gas-gun.png"));
		hold1.setBounds(3, 2, 38, 38);
		panel.add(hold1);

		return panel;
	}
	public static GasMsg createData() {
		MacLogInfo macLogInfo = new MacLogInfo();
		macLogInfo.GunNum = '8';
		macLogInfo.GasName = "名称";
		macLogInfo.CardNum = "加油卡数";
		macLogInfo.PumpReadout = 1.1;
		macLogInfo.Oiler = "oiler";
		macLogInfo.CardType = CardTypeEnum.IC卡;
		macLogInfo.TotalCount = 1;
		macLogInfo.amount =(BigDecimal.valueOf(400));
		macLogInfo.qty = BigDecimal.valueOf(532);
		macLogInfo.Price = BigDecimal.valueOf(100);
		macLogInfo.GunStatus = GunStatusEnum.卡插入;
		macLogInfo.FuelQuatity = BigDecimal.valueOf(998);
		List list = new ArrayList();
		list.add(macLogInfo);
 		GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, list, Constants.PID_Code.A15_10002.toString());
		return gasMsg;
	}

	@Override
	public void update(GasMsg arg0) {
		// TODO Auto-generated method stub
//		new JsonMapper().fromJson(arg0, HashMap.class);

		ResultMsg data = new JsonMapper().fromJson(arg0.getMessage(), ResultMsg.class);
    	//System.out.println("data:"+((Map)(data.getData().get(0))).get("macno"));
    	List listData = data.getData();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 800, 390);
		centerPanel.add(scrollPane);
		scrollPane.setVisible(true);
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(Integer.decode(Constant.CENTER_BG_COCLER)));
		scrollPane.setViewportView(mainPanel);

		mainPanel.setLayout(null);
		panel1.setLayout(null);
		panel1.setPreferredSize(new Dimension(1200, 60));
		panel1.setBounds(0, 0, 1200, 60);
		panel1.setBackground(new Color(Integer.decode(Constant.CENTER_BG_COCLER)));

		//从基础信息表中获取油罐基础信息
		List<SysManageCanInfo> tankInfolist =monitorTimeInventoryService.selectAll();
		List<SysManageOilGunInfo> oilGunList = monitorTimeInventoryService.selectAllOilGun();
		int size =tankInfolist.size();
		if(size>0){
			mainPanel.setPreferredSize(new Dimension(1200, 50+size*230));//关键代码,设置JPanel的大小
			mainPanel.setBounds(0,0, 1200, size*500);

			for (int i = 0; i < size; i++) {
				MonitorTimeInventory info = monitorTimeInventoryService.selectByPrimaryKey(tankInfolist.get(i).getOilcan());
				Object[][] playerInfo = {
						{ "油罐编号", new Integer(100)},
						{ "油品", new Integer(20)},
						{ "净油体积（L）", new Integer(89)},
						{ "标准体积（L）", new Integer(89)},
						{ "空体积（L）", new Integer(89)},
						{ "油水总高（mm）", new Integer(89)},
						{ "水高（mm）", new Integer(89)},
						{ "水体积（L）", new Integer(89)},
						{ "平均温度（℃）", new Integer(89)}
						};
				String[] Names = { "", "" };
				JTable table = new JTable(playerInfo, Names);
				table.setFont(Constant.YGXX_FONT);
				table.setRowHeight(18);
				table.getColumnModel().getColumn(0).setPreferredWidth(120);
				table.getColumnModel().getColumn(1).setPreferredWidth(80);
				table.getTableHeader().setVisible(false);
//				table.setPreferredScrollableViewportSize(new Dimension(550, 60));// 设置此表视口的首选大小。
//				table.setPreferredSize(new Dimension(200, 180));
				table.setBounds(20, 40+i*230, 200, 180);
				mainPanel.add(table);

				//左侧比例
				JPanel blpanel1 = new JPanel();
				blpanel1.setBackground(Color.YELLOW);
				blpanel1.setBounds(10, 40+i*230, 10, 150);
				mainPanel.add(blpanel1);
				JPanel blpanel2 = new JPanel();
				blpanel2.setBackground(Color.BLUE);
				blpanel2.setBounds(10, 40+i*230+150, 10, 180-150);
				mainPanel.add(blpanel2);

				List<SysManageOilGunInfo> gunInfos = getOilGunByOilCan(info.getOilcan().toString(),oilGunList);
				List list = new ArrayList();
				for (Object object : listData) {
					Map map = (Map)object;
					for (SysManageOilGunInfo sysManageOilGunInfo : gunInfos) {
						if(map.get("GunNum").toString().equals(sysManageOilGunInfo.getOilgun())){
							list.add(object);
						}
					}
				}

				for (int  j = 0; j < list.size(); j++) {
					JPanel panel = getyqxx((Map)list.get(j));
					panel.setBounds(230+j%5*160, 40+j/5*110+i*230, 150, 100);
					mainPanel.add(panel);
				}
			}
		}

		JLabel hold1Label = new JLabel("空闲/上班/挂枪");
		hold1Label.setFont(Constant.BOTTOM_FONT);
		hold1Label.setBounds(20, 10, 84, 12);
		hold1Label.setForeground(new Color(Integer.decode(Constant.SSJK_COCLER)));
		panel1.add(hold1Label);

		JButton hold1 = new JButton();
		hold1.setBorderPainted(false);
		hold1.setContentAreaFilled(false);
		hold1.setIcon(Common.createImageIcon(this.getClass(),"7-.png"));
		hold1.setBounds(104, 5, 32, 32);
		panel1.add(hold1);

		JLabel hold1Label1 = new JLabel("提枪");
		hold1Label1.setFont(Constant.BOTTOM_FONT);
		hold1Label1.setBounds(150, 10, 24, 12);
		hold1Label1.setForeground(new Color(Integer.decode(Constant.SSJK_COCLER)));
		panel1.add(hold1Label1);

		JButton hold = new JButton();
		hold.setBorderPainted(false);
		hold.setContentAreaFilled(false);
		hold.setIcon(Common.createImageIcon(this.getClass(),"7.png"));
		hold.setBounds(180, 5, 32, 32);
		panel1.add(hold);
		mainPanel.add(panel1);
	}

	//判断油罐所属的有枪
	public List<SysManageOilGunInfo> getOilGunByOilCan(String oilcan,List<SysManageOilGunInfo> oilGunList){
		for (SysManageOilGunInfo sysManageOilGunInfo : oilGunList) {
			if(!sysManageOilGunInfo.getOilcan().equals(oilcan)){
				oilGunList.remove(sysManageOilGunInfo);
			}
		}
		return oilGunList;
	}
}
