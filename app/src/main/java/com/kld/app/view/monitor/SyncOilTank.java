package com.kld.app.view.monitor;

import com.kld.app.socket.ob.Watcher;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.SysManageOilType;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-01 15:34
 * @Description: 更新实时的油罐信息
 */
public class SyncOilTank implements Watcher {
    private static Map<String, JTable> oilCaninfo;
    private static List<SysManageOilType> types;

    public SyncOilTank() {
    }

    public SyncOilTank(Map<String, JTable> oilCaninfo) {
        this.oilCaninfo = oilCaninfo;
        this.types = types;

        Main.watch.addWetcher("A", new SyncOilTank());

        GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
        Main.CC.writeAndFlush(gasMsg);


//        int size = oilCaninfo.size();
//        //System.out.println("map :" + size);
//        for (int i = 1; i <= size; i++) {
//            TableModel tableModel = this.oilCaninfo.get("" + i).getModel();
//            tableModel.setValueAt("油罐编号" + i, 0, 1);
//            tableModel.setValueAt("油品" + i, 1, 1);
//            tableModel.setValueAt("油品" + i, 2, 1);
//            tableModel.setValueAt("油品" + i, 3, 1);
//            tableModel.setValueAt("油品" + i, 4, 1);
//            tableModel.setValueAt("油品" + i, 5, 1);
//            tableModel.setValueAt("油品" + i, 6, 1);
//            tableModel.setValueAt("油品" + i, 7, 1);
//            tableModel.setValueAt("油品" + i, 8, 1);

//        }


        //System.out.println("start updating oil tank  .....");
        //System.out.println("Element : " + this.oilCaninfo.toString());
    }

    @Override
    public void update(GasMsg gasMsg) {
        //System.out.println("");
        ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);

        if (resultMsg.getResult().equals("0")) {
            //System.out.println("查询成功");
            //拿到List
            List<atg_stock_data_out_t> list = resultMsg.getData();
            int size = list.size();
            //System.out.println("Ctrl return atg_stock_data_out_t_List.size" + size);
            for (int i = 0; i < size; i++) {
                String str = list.get(i).uOilCanNo + "";

                if (str != null && !str.equals("")) {
                    JTable jTable = oilCaninfo.get(str);
                    if (jTable != null && jTable.equals("")) {
                        TableModel tableModel = jTable.getModel();
                        //格式化
                        DecimalFormat df=new DecimalFormat("##########0.00");
                        tableModel.setValueAt(df.format(list.get(i).fOilCubage), 2, 1);
                        tableModel.setValueAt(df.format(list.get(i).fOilStandCubage), 3, 1);
                        tableModel.setValueAt(df.format(list.get(i).fEmptyCubage), 4, 1);
                        tableModel.setValueAt(df.format(list.get(i).fTotalHeight), 5, 1);
                        tableModel.setValueAt(df.format(list.get(i).fWaterHeight), 6, 1);
                        tableModel.setValueAt(df.format(list.get(i).fWaterBulk), 7, 1);
                        tableModel.setValueAt(df.format(list.get(i).fOilTemp), 8, 1);
                    }
                }
            }
        }else if (resultMsg.getResult().equals("1")) {
            //System.out.println("查询失败");
        }
    }
}