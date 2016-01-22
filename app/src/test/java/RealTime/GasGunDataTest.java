package RealTime;

import com.kld.gsm.MacLog.CardTypeEnum;
import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-11-26 12:45
 * @Description: 模拟实时罐枪及泵码信息数据
 */
public class GasGunDataTest {
    @Test
    public void getData() {
        GasMsg gasMsg = createData();
        System.out.println(gasMsg);
        ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
        System.out.println(resultMsg.toString());
        System.out.println(resultMsg.getData().size());
        List<MacLogInfo> list = resultMsg.getData();
        for(int i=0;i<list.size();i++){

            Map map = (Map) list.get(i);
            System.out.println(map.get("GunNum"));
        }
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
//        MacLogInfo macLogInfo2 = new MacLogInfo();
//        macLogInfo2.GunNum = '9';
//        macLogInfo2.GasName = "名称";
//        macLogInfo2.CardNum = "加油卡数";
//        macLogInfo2.PumpReadout = 1.1;
//        macLogInfo2.Oiler = "oiler";
//        macLogInfo2.CardType = CardTypeEnum.IC卡;
//        macLogInfo2.TotalCount = 1;
//        macLogInfo2.amount =(BigDecimal.valueOf(376));
//        macLogInfo2.qty = BigDecimal.valueOf(946);
//        macLogInfo2.Price = BigDecimal.valueOf(100);
//        macLogInfo2.GunStatus = GunStatusEnum.卡插入;
//        macLogInfo2.FuelQuatity = BigDecimal.valueOf(375);

        List list = new ArrayList();
        list.add(macLogInfo);
       // list.add(macLogInfo2);
        GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS("127.0.0.1", list, Constants.PID_Code.A15_10002.toString());
        return gasMsg;
    }
}
