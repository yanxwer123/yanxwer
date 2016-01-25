package com.kld.app.view.acceptance;
/**
 * Created by ken on 2016/1/22.
 */

import com.kld.app.service.*;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.util.V20Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * 使用了原始的分页方式去渲染JTextArea，提供了打印预览机制。
 * <p>
 * 事实上，我们还可以通过其他方式打印JTextArea：
 * </p><ol>
 * <li>{@code Component.print(Graphics g);} or
 * {@code Component.printAll(Graphics g);}</li>
 * <li>{@code Component.paint(Graphics g);} or
 * {@code Component.paintAll(Graphics g);} whose rending may be slightly
 * different to the previous method (for example, <code>JFrame</code>)</li>
 * <li>{@code JTable.print();} or {@code JTextComponent.print();} provide
 * especially powerful and convenient printing mechanism</li>
 * </ol>
 *
 * @author Gaowen
 */
public class PrintUIComponent extends JDialog {
    public static final double MM_TO_PAPER_UNITS = 1 / 25.4 * 72;
    public static double widthA4 = 210 * MM_TO_PAPER_UNITS;
    public static double heightA4 = 297 * MM_TO_PAPER_UNITS;
    public static double leftMargin = 4.0 * MM_TO_PAPER_UNITS;
    public static double topMargin = 4.0 * MM_TO_PAPER_UNITS;
    private AcceptanceOdRegister odRegister;
    private List<AcceptanceOdRegisterInfo> odRegisterInfos;
    private IAcceptanceOdRegisterInfoService registerInfoService;
    private IAcceptanceDeliveryService deliveryService;
    private IAcceptanceOdRegisterService registerService;
    private AlarmDailyLostService alarmDailyLostService;
    private SysmanageService sysmanageService;
    private AlarmOilInContrastService alarmOilInContrastService;
    private SysManageCanInfoService sysManageCanInfoService;
    //private IMonitorTimeInventoryService monitorTimeInventoryService;
    private List<SysManageOilGunInfo> oilGunList;
    private MonitorTimeInventoryService monitorTimeInventoryService;
    String stationName;
    String billno;//出库单号
    private AcceptanceDeliveryBills deliveryBill; //出库单
    private String Oilname; //油品名称
    //实收体积(Vt)
    String realRecieve;
    //实收体积(V20)
    String realRecieveV20;
    //进油期间付油体积
    String duringSales;
    //实收损益量(Vt)
    String dischargeLoss;
    //实收损益量(V20)
    String dischargeLossV20;
    DecimalFormat decimalFormat = new DecimalFormat("######0.00");

    //实收损益率(‰)
    String dischargeRate;
    //实收损益率20(‰)
    String dischargeRateV20;
    //超耗索赔量(V20)
    String indemnityloss;
    //回罐铅封号
    String backBankNo;
    //到站时间 28 17
    String instationtime;
    //原发体积(V20)
    String planLV20;
    private AlarmOilInContrast alarmOilInContrast;

    JButton button;
    MyPanel panel;
    GridBagConstraints gridBagConstraints;

    public void init(String billno) {
        this.setTitle("验收单打印");
        this.setResizable(false);
        this.setModal(true);
        this.billno = billno;
        getOdreg(billno);
        odRegisterInfos = getOdRegisterInfos(billno);
        panel = new MyPanel();
        button = new JButton("验收单打印");
        if (sysmanageService == null) {
            sysmanageService = Context.getInstance().getBean(SysmanageService.class);
        }
        SysManageDepartment sysManageDepartment = sysmanageService.getdeptinfo();
        if (sysManageDepartment != null) {
            stationName = sysManageDepartment.getNodetag();
        } else {

        }
        if (odRegister != null) {
            realRecieve = odRegister.getRealgetl() == null ? "" : odRegister.getRealgetl().toString();
            realRecieveV20 = odRegister.getRealGetLV20() == null ? "" : odRegister.getRealGetLV20().toString();
            duringSales = odRegister.getDuringsales() == null ? "" : odRegister.getDuringsales().toString();
            dischargeLoss = odRegister.getDischargeloss() == null ? "" : odRegister.getDischargeloss().toString();
            dischargeLossV20 = odRegister.getDischargeLossV20() == null ? "" : odRegister.getDischargeLossV20().toString();

            dischargeRate = odRegister.getDischargerate() == null ? "" : decimalFormat.format(odRegister.getDischargerate() * 1000) + "";
            dischargeRateV20 = odRegister.getDischargeRateV20() == null ? "" : decimalFormat.format(odRegister.getDischargeRateV20() * 1000) + "";
            indemnityloss = odRegister.getIndemnityloss() == null ? "0" : odRegister.getIndemnityloss().toString();
            backBankNo = odRegister.getBackbankno() == null ? "" : odRegister.getBackbankno().toString();
            instationtime = odRegister.getBegintime() == null ? "" : odRegister.getBegintime().toLocaleString();

        }


        getContentPane().setLayout(new GridBagLayout());
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(button, gridBagConstraints);
        setSize(700, 550);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PrinterJob printJob = PrinterJob.getPrinterJob();
                PageFormat pf = printJob.defaultPage();
                pf.setOrientation(PageFormat.PORTRAIT);
                Paper paper = pf.getPaper();
                paper.setSize(widthA4, heightA4);
                paper.setImageableArea(leftMargin, topMargin, widthA4 - 2 * leftMargin, heightA4 - 2 * topMargin);
                pf.setPaper(paper);
                printJob.setCopies(1);
                Book book = new Book();
                book.append(panel, pf, 1);
                printJob.setPageable(book);
                if (printJob.printDialog()) {
                    try {

                        printJob.print();
                    } catch (Exception PrinterExeption) {
                        PrinterExeption.printStackTrace();
                    }
                }
            }
        });
        setVisible(true);
    }

    /*
       * 初始化出库单，加载进货验收主数据
       * */
    private void getOdreg(String billno) {
        if (deliveryService == null) {
            deliveryService = Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        }
        deliveryBill = deliveryService.selectByPrimaryKey(billno);
        if (deliveryBill == null) {
            deliveryBill = new AcceptanceDeliveryBills();
            //出库单表没有查询到 去nobill表查询
            AcceptanceNoBills noBills = deliveryService.getNobillBykey(billno);
            deliveryBill.setDeliveryno(noBills.getDeliveryno());
            deliveryBill.setPlanl(noBills.getPlanl());
            deliveryBill.setPlanton(noBills.getPlanton());
            deliveryBill.setOilno(noBills.getOilno());
            deliveryBill.setFromdepotname(noBills.getFromoildepot());
            deliveryBill.setTonodeno(noBills.getTonodeno());

        }
        //  model.setValueAt(deliveryBill.getOilno(), 0, 1);
        //region 取油品名称
        if (alarmDailyLostService == null) {
            alarmDailyLostService = Context.getInstance().getBean(AlarmDailyLostService.class);
        }
        Oilname = alarmDailyLostService.selectOilNo(deliveryBill.getOilno());

        if (registerService == null) {
            registerService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        odRegister = registerService.selectByPrimaryKey(billno);
        String OIL_TYPE_1 = registerService.selectOilType(deliveryBill.getOilno()).getOiltype().toString();
        planLV20 = getV20L(OIL_TYPE_1, deliveryBill.getDeliverytemp() == null ? 0 : deliveryBill.getDeliverytemp(), deliveryBill.getPlanl() == null ? 0 : deliveryBill.getPlanl()) + "";
    }

    private double getV20L(String oilType, double vt, double V) {
        V20Utils v20Utils = new V20Utils();
        if (V == 0.0) {
            return 0.0;
        }
        if (oilType.equals("03")) {
            //柴油
            return v20Utils.getDieV20(vt, V);
        } else {
            //汽油
            return v20Utils.getGasV20(vt, V);
        }
    }

    /*
  *
  * 加载进货验收明细数据
  * */
    private List<AcceptanceOdRegisterInfo> getOdRegisterInfos(String billno) {
        if (registerInfoService == null) {
            registerInfoService = Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        }
        return registerInfoService.selectByDeliveryNoDate(billno, null, null);
    }

    public static void main(String[] args) {
        PrintUIComponent myFrame = new PrintUIComponent();
        myFrame.init("");
    }

    class MyPanel extends JPanel implements Printable {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            paingMyGrafik(g2);
            paintlabels(g2);

        }

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            Graphics2D g2 = (Graphics2D) graphics;
//affine transformation for grip of the panel`s content to the size A4
            double W = pageFormat.getImageableWidth();
            double H = pageFormat.getImageableHeight();
            double minX = pageFormat.getImageableX();
            double minY = pageFormat.getImageableY();
            g2.transform(new AffineTransform((W) / this.getWidth(), 0, 0, (H) / this.getHeight(), minX, minY));
            paingMyGrafik(g2);//画线
            paintlabels(g2);
            return 0;
        }

        private void paintlabels(Graphics2D g2) {
            paintLabel(g2, "地罐交接校对单", this.getWidth() / 2, this.getHeight() - 20);
            paintLabel(g2, stationName == null ? "站名:" : "站名:" + stationName, 85, this.getHeight() - 40);

            paintLabel(g2, billno == null ? "出库单号:" : "出库单号:" + billno, 280, this.getHeight() - 40);
            if (deliveryBill != null) {
                paintLabel(g2, Oilname == null ? "油品：" : "油品：" + Oilname, 420, this.getHeight() - 40);
                paintLabel(g2, "发出库:", 70, this.getHeight() - 62);
                paintLabel(g2, "发油日期时间", 200, this.getHeight() - 62);
                paintLabel(g2, "原发重量(Kg)", 330, this.getHeight() - 62);
                paintLabel(g2, "原发体积(Vt)", 480, this.getHeight() - 62);
                paintLabel(g2, "原发体积(V20)", 610, this.getHeight() - 62);
                paintLabel(g2, deliveryBill.getFromdepotname(), 70, this.getHeight() - 82);
                paintLabel(g2, deliveryBill.getDeliverytime() == null ? "" : deliveryBill.getDeliverytime().toLocaleString(), 200, this.getHeight() - 82);
                paintLabel(g2, deliveryBill.getPlanton() == null ? "" : deliveryBill.getPlanton().toString(), 330, this.getHeight() - 82);
                paintLabel(g2, deliveryBill.getPlanl() == null ? "" : decimalFormat.format(deliveryBill.getPlanl()).toString(), 480, this.getHeight() - 82);
                paintLabel(g2, planLV20, 610, this.getHeight() - 82);


                paintLabel(g2, "承运车号:", 70, this.getHeight() - 102);
                paintLabel(g2, "到站日期时间", 200, this.getHeight() - 102);
                paintLabel(g2, "原发密度(kg/m³)", 330, this.getHeight() - 102);
                paintLabel(g2, "原发温度(℃)", 480, this.getHeight() - 102);
                paintLabel(g2, "使用温度", 610, this.getHeight() - 102);
                paintLabel(g2, deliveryBill.getCarno() == null ? "" : deliveryBill.getCarno().toString(), 70, this.getHeight() - 122);
                paintLabel(g2, instationtime, 200, this.getHeight() - 122);
                paintLabel(g2, deliveryBill.getDensity() == null ? "" : deliveryBill.getDensity().toString(), 330, this.getHeight() - 122);

                paintLabel(g2, deliveryBill.getDeliverytemp() == null ? "" : deliveryBill.getDeliverytemp().toString(), 480, this.getHeight() - 122);
                paintLabel(g2, "油库实发油温", 610, this.getHeight() - 122);
            }

            paintLabel(g2, "交易明细", 40, this.getHeight() - 142);

            paintLabel(g2, "罐号", 28, this.getHeight() - 162);
            paintLabel(g2, "数据类别", 65, this.getHeight() - 162);
            paintLabel(g2, "油水总高(mm)", 130, this.getHeight() - 162);
            paintLabel(g2, "水高(mm)", 200, this.getHeight() - 162);
            paintLabel(g2, "油温(℃)", 250, this.getHeight() - 162);
            paintLabel(g2, "存油量(Vt)", 300, this.getHeight() - 162);
            paintLabel(g2, "存油量(V20)", 370, this.getHeight() - 162);
            paintLabel(g2, "操作时间", 460, this.getHeight() - 162);
            paintLabel(g2, "验收情况", 560, this.getHeight() - 162);
            paintLabel(g2, "是否手录", 640, this.getHeight() - 162);


            if (odRegisterInfos.size() > 0) {
                if (odRegisterInfos.get(0) != null) {
                    AcceptanceOdRegisterInfo acceptanceOdRegisterInfo = odRegisterInfos.get(0);
                    System.out.println("0:" + acceptanceOdRegisterInfo);
                    paintLabel(g2, acceptanceOdRegisterInfo.getOilcan() == null ? "" : acceptanceOdRegisterInfo.getOilcan().toString(), 25, this.getHeight() - 182);
                    paintLabel(g2, "卸前", 65, this.getHeight() - 182);
                    paintLabel(g2, acceptanceOdRegisterInfo.getBeginoilheight() == null ? "" : acceptanceOdRegisterInfo.getBeginoilheight().toString(), 130, this.getHeight() - 182);
                    paintLabel(g2, acceptanceOdRegisterInfo.getBeginwaterheight() == null ? "" : acceptanceOdRegisterInfo.getBeginwaterheight().toString(), 200, this.getHeight() - 182);
                    paintLabel(g2, acceptanceOdRegisterInfo.getBegintemperature() == null ? "" : acceptanceOdRegisterInfo.getBegintemperature().toString(), 250, this.getHeight() - 182);
                    paintLabel(g2, acceptanceOdRegisterInfo.getBeginoill() == null ? "" : acceptanceOdRegisterInfo.getBeginoill().toString(), 300, this.getHeight() - 182);
                    paintLabel(g2, acceptanceOdRegisterInfo.getBeginv20l() == null ? "" : acceptanceOdRegisterInfo.getBeginv20l().toString(), 370, this.getHeight() - 182);
                    paintLabel(g2, acceptanceOdRegisterInfo.getBegintime() == null ? "" : acceptanceOdRegisterInfo.getBegintime().toLocaleString(), 461, this.getHeight() - 182);
                    paintLabel(g2, "", 550, this.getHeight() - 182);
                    paintLabel(g2, "", 610, this.getHeight() - 182);

                    paintLabel(g2, acceptanceOdRegisterInfo.getOilcan() == null ? "" : acceptanceOdRegisterInfo.getOilcan().toString(), 25, this.getHeight() - 202);
                    paintLabel(g2, "卸后", 65, this.getHeight() - 202);
                    paintLabel(g2, acceptanceOdRegisterInfo.getEndoilheight() == null ? "" : acceptanceOdRegisterInfo.getEndoilheight().toString(), 130, this.getHeight() - 202);
                    paintLabel(g2, acceptanceOdRegisterInfo.getEndwaterheight() == null ? "" : acceptanceOdRegisterInfo.getEndwaterheight().toString(), 200, this.getHeight() - 202);
                    paintLabel(g2, acceptanceOdRegisterInfo.getEndtemperature() == null ? "" : acceptanceOdRegisterInfo.getEndtemperature().toString(), 250, this.getHeight() - 202);
                    paintLabel(g2, acceptanceOdRegisterInfo.getEndoill() == null ? "" : acceptanceOdRegisterInfo.getEndoill().toString(), 300, this.getHeight() - 202);
                    paintLabel(g2, acceptanceOdRegisterInfo.getEndv20l() == null ? "" : acceptanceOdRegisterInfo.getEndv20l().toString(), 370, this.getHeight() - 202);
                    paintLabel(g2, acceptanceOdRegisterInfo.getEndtime() == null ? "" : acceptanceOdRegisterInfo.getEndtime().toLocaleString(), 461, this.getHeight() - 202);
                    paintLabel(g2, "", 550, this.getHeight() - 202);
                    paintLabel(g2, "", 610, this.getHeight() - 202);
                }
                if (odRegisterInfos.size() > 1) {
                    if (odRegisterInfos.get(1) != null) {
                        AcceptanceOdRegisterInfo acceptanceOdRegisterInfo = odRegisterInfos.get(1);
                        System.out.println("1:" + acceptanceOdRegisterInfo);

                        paintLabel(g2, acceptanceOdRegisterInfo.getOilcan() == null ? "" : acceptanceOdRegisterInfo.getOilcan().toString(), 25, this.getHeight() - 222);
                        paintLabel(g2, "卸前", 65, this.getHeight() - 222);
                        paintLabel(g2, acceptanceOdRegisterInfo.getBeginoilheight() == null ? "" : acceptanceOdRegisterInfo.getBeginoilheight().toString(), 130, this.getHeight() - 222);
                        paintLabel(g2, acceptanceOdRegisterInfo.getBeginwaterheight() == null ? "" : acceptanceOdRegisterInfo.getBeginwaterheight().toString(), 200, this.getHeight() - 222);
                        paintLabel(g2, acceptanceOdRegisterInfo.getBegintemperature() == null ? "" : acceptanceOdRegisterInfo.getBegintemperature().toString(), 250, this.getHeight() - 222);
                        paintLabel(g2, acceptanceOdRegisterInfo.getBeginoill() == null ? "" : acceptanceOdRegisterInfo.getBeginoill().toString(), 300, this.getHeight() - 222);
                        paintLabel(g2, acceptanceOdRegisterInfo.getBeginv20l() == null ? "" : acceptanceOdRegisterInfo.getBeginv20l().toString(), 370, this.getHeight() - 222);
                        paintLabel(g2, acceptanceOdRegisterInfo.getBegintime() == null ? "" : acceptanceOdRegisterInfo.getBegintime().toLocaleString(), 461, this.getHeight() - 222);
                        paintLabel(g2, "", 550, this.getHeight() - 222);
                        paintLabel(g2, "", 610, this.getHeight() - 222);


                        paintLabel(g2, acceptanceOdRegisterInfo.getOilcan() == null ? "" : acceptanceOdRegisterInfo.getOilcan().toString(), 25, this.getHeight() - 242);
                        paintLabel(g2, "卸后", 65, this.getHeight() - 242);
                        paintLabel(g2, acceptanceOdRegisterInfo.getEndoilheight() == null ? "" : acceptanceOdRegisterInfo.getEndoilheight().toString(), 130, this.getHeight() - 242);
                        paintLabel(g2, acceptanceOdRegisterInfo.getEndwaterheight() == null ? "" : acceptanceOdRegisterInfo.getEndwaterheight().toString(), 200, this.getHeight() - 242);
                        paintLabel(g2, acceptanceOdRegisterInfo.getEndtemperature() == null ? "" : acceptanceOdRegisterInfo.getEndtemperature().toString(), 250, this.getHeight() - 242);
                        paintLabel(g2, acceptanceOdRegisterInfo.getEndoill() == null ? "" : acceptanceOdRegisterInfo.getEndoill().toString(), 300, this.getHeight() - 242);
                        paintLabel(g2, acceptanceOdRegisterInfo.getEndv20l() == null ? "" : acceptanceOdRegisterInfo.getEndv20l().toString(), 370, this.getHeight() - 242);
                        paintLabel(g2, acceptanceOdRegisterInfo.getEndtime() == null ? "" : acceptanceOdRegisterInfo.getEndtime().toLocaleString(), 461, this.getHeight() - 242);
                        paintLabel(g2, "", 550, this.getHeight() - 242);
                        paintLabel(g2, "", 610, this.getHeight() - 242);
                    }
                }

            }


            paintLabel(g2, "收油损耗情况", 50, this.getHeight() - 262);

            paintLabel(g2, "实收体积(Vt)", 45, this.getHeight() - 282);
            paintLabel(g2, "实收体积(V20)", 115, this.getHeight() - 282);
            paintLabel(g2, "期间付油体积", 185, this.getHeight() - 282);
            paintLabel(g2, "实收损溢量(Vt)", 260, this.getHeight() - 282);
            paintLabel(g2, "实收损溢量(V20)", 345, this.getHeight() - 282);
            paintLabel(g2, "实收损溢率(‰)", 430, this.getHeight() - 282);
            paintLabel(g2, "实收损溢率V20(‰)", 520, this.getHeight() - 282);
            paintLabel(g2, "超耗索赔量(V20)", 620, this.getHeight() - 282);

            paintLabel(g2, realRecieve, 45, this.getHeight() - 302);
            paintLabel(g2, realRecieveV20, 115, this.getHeight() - 302);
            paintLabel(g2, duringSales, 185, this.getHeight() - 302);
            paintLabel(g2, dischargeLoss, 260, this.getHeight() - 302);
            paintLabel(g2, dischargeLossV20, 345, this.getHeight() - 302);
            paintLabel(g2, dischargeRate, 430, this.getHeight() - 302);
            paintLabel(g2, dischargeRateV20, 520, this.getHeight() - 302);
            paintLabel(g2, indemnityloss, 620, this.getHeight() - 302);


            paintLabel(g2, "卸油员签字:", 45, this.getHeight() - 330);
            paintLabel(g2, "驾驶员签字:", 260, this.getHeight() - 330);
            paintLabel(g2, "时间日期:", 520, this.getHeight() - 330);
            paintLabel(g2, new Date().toLocaleString(), 625, this.getHeight() - 330);

            paintLabel(g2, "出库铅封号:", 45, this.getHeight() - 360);
            paintLabel(g2, "", 130, this.getHeight() - 360);

            paintLabel(g2, "回空铅封号:", 345, this.getHeight() - 360);
            paintLabel(g2, backBankNo, 500, this.getHeight() - 360);
            paintLabel(g2, "备注:如遇系统特殊情况请在该栏目填写", 120, this.getHeight() - 393);

            paintLabel(g2, "正损溢表示损耗,负损溢表示盈余。", 110, this.getHeight() - 433);
            paintLabel(g2, "加油站卸油时油罐对应加油机停止对外销售,卸油完成后在液位仪提取油罐数据生成报表后方可对外销售", 260, this.getHeight() - 445);

            // paintLabel(g2, "qweqweqwe", this.getWidth() / 2, this.getHeight() - 20);//画文字

        }

        private void paintLabel(Graphics2D graphics, String label, int x, int y) {
//because mirror reflection change also text
            Color oldColor = graphics.getColor();
            graphics.setColor(Color.black);

            Font oldFont = graphics.getFont();
            Font labelFont = new Font("none", Font.PLAIN, 10);
            graphics.setFont(labelFont);
            Point2D ptSrc = new Point2D.Double(x - graphics.getFontMetrics().stringWidth(label) / 2, y);
            Point2D ptDst = new Point2D.Double();
            toUsualyCoordinate().transform(ptSrc, ptDst);
            graphics.drawString(label, (int) ptDst.getX(), (int) ptDst.getY());
            graphics.setFont(oldFont);
            graphics.setColor(oldColor);
        }

        private void paingMyGrafik(Graphics2D g2) {
//here paint i my grafik
            AffineTransform old = g2.getTransform();
            g2.transform(toUsualyCoordinate());
            Rectangle2D rect = new Rectangle2D.Double();
            rect.setRect(13, this.getHeight() - 415, this.getWidth() - 23, this.getHeight() - 100);
            g2.draw(rect);
            g2.setColor(Color.black);
//            g2.drawLine(13, this.getHeight() - 26, this.getWidth() - 10, this.getHeight() - 26);
            g2.drawLine(13, this.getHeight() - 46, this.getWidth() - 10, this.getHeight() - 46);
            g2.drawLine(13, this.getHeight() - 66, this.getWidth() - 10, this.getHeight() - 66);
            g2.drawLine(13, this.getHeight() - 86, this.getWidth() - 10, this.getHeight() - 86);
            g2.drawLine(13, this.getHeight() - 106, this.getWidth() - 10, this.getHeight() - 106);
            g2.drawLine(13, this.getHeight() - 126, this.getWidth() - 10, this.getHeight() - 126);

            g2.drawLine(this.getWidth() / 5, this.getHeight() - 126, this.getWidth() / 5, this.getHeight() - 46);
            g2.drawLine(this.getWidth() / 5 * 2, this.getHeight() - 126, this.getWidth() / 5 * 2, this.getHeight() - 46);
            g2.drawLine(this.getWidth() / 5 * 3, this.getHeight() - 126, this.getWidth() / 5 * 3, this.getHeight() - 46);
            g2.drawLine(this.getWidth() / 5 * 4, this.getHeight() - 126, this.getWidth() / 5 * 4, this.getHeight() - 46);
            //交易明细
            g2.drawLine(13, this.getHeight() - 146, this.getWidth() - 10, this.getHeight() - 146);
            g2.drawLine(13, this.getHeight() - 166, this.getWidth() - 10, this.getHeight() - 166);
            g2.drawLine(13, this.getHeight() - 186, this.getWidth() - 10, this.getHeight() - 186);
            g2.drawLine(13, this.getHeight() - 206, this.getWidth() - 10, this.getHeight() - 206);
            g2.drawLine(13, this.getHeight() - 226, this.getWidth() - 10, this.getHeight() - 226);
            g2.drawLine(13, this.getHeight() - 246, this.getWidth() - 10, this.getHeight() - 246);

            g2.drawLine(40, this.getHeight() - 246, 40, this.getHeight() - 146);
            g2.drawLine(90, this.getHeight() - 246, 90, this.getHeight() - 146);
            g2.drawLine(170, this.getHeight() - 246, 170, this.getHeight() - 146);
            g2.drawLine(230, this.getHeight() - 246, 230, this.getHeight() - 146);
            g2.drawLine(270, this.getHeight() - 246, 270, this.getHeight() - 146);
            g2.drawLine(330, this.getHeight() - 246, 330, this.getHeight() - 146);
            g2.drawLine(405, this.getHeight() - 246, 405, this.getHeight() - 146);
            g2.drawLine(520, this.getHeight() - 246, 520, this.getHeight() - 146);
            g2.drawLine(600, this.getHeight() - 246, 600, this.getHeight() - 146);

            //收油损耗情况
            g2.drawLine(13, this.getHeight() - 266, this.getWidth() - 10, this.getHeight() - 266);
            g2.drawLine(13, this.getHeight() - 286, this.getWidth() - 10, this.getHeight() - 286);
            g2.drawLine(13, this.getHeight() - 306, this.getWidth() - 10, this.getHeight() - 306);
            g2.drawLine(13, this.getHeight() - 346, this.getWidth() - 10, this.getHeight() - 346);
            g2.drawLine(13, this.getHeight() - 366, this.getWidth() - 10, this.getHeight() - 366);
            g2.drawLine(13, this.getHeight() - 415, this.getWidth() - 10, this.getHeight() - 415);

            g2.drawLine(80, this.getHeight() - 366, 80, this.getHeight() - 266);
            g2.drawLine(220, this.getHeight() - 415, 220, this.getHeight() - 366);

            g2.drawLine(150, this.getHeight() - 306, 150, this.getHeight() - 266);
            g2.drawLine(220, this.getHeight() - 346, 220, this.getHeight() - 266);
            g2.drawLine(300, this.getHeight() - 366, 300, this.getHeight() - 266);

            g2.drawLine(390, this.getHeight() - 306, 390, this.getHeight() - 266);
            g2.drawLine(390, this.getHeight() - 366, 390, this.getHeight() - 346);

            g2.drawLine(470, this.getHeight() - 346, 470, this.getHeight() - 266);
            g2.drawLine(570, this.getHeight() - 346, 570, this.getHeight() - 266);


            g2.setTransform(old);

        }

        private AffineTransform toUsualyCoordinate() {
            AffineTransform at = new AffineTransform();
//mirror reflection relative to ox
            at.concatenate(new AffineTransform(1, 0, 0, -1, 0, 0));
//parallel shift (x,y) -> (x, y-h)
            at.concatenate(AffineTransform.getTranslateInstance(0, -this.getHeight()));
            return at;
        }

        private int min(int a, int b) {
            return (a < b) ? a : b;
        }
    }
}