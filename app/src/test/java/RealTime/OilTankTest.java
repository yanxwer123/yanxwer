package RealTime;

import com.kld.app.service.MonitorTimeInventoryService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import org.junit.Test;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-11-26 17:03
 * @Description: 实时油罐信息
 */
public class OilTankTest {
    @Test
    public void getData() {
//                  MonitorTimeInventoryService monitorTimeInventoryService =
//                (MonitorTimeInventoryService) (Context.getInstance().getBean("monitorTimeInventoryService"));
        MonitorTimeInventoryService monitorTimeInventoryService = Context.getInstance().getBean(MonitorTimeInventoryService.class);

        List<SysManageCanInfo> tankInfolist = monitorTimeInventoryService.selectAll();
        List<SysManageOilGunInfo> oilGunList = monitorTimeInventoryService.selectAllOilGun();
        int size = tankInfolist.size();
        System.out.println(size);
        for (SysManageCanInfo sysManageTankInfo : tankInfolist) {
            System.out.println(sysManageTankInfo);

        }
        int oilGunSize = oilGunList.size();

        System.out.println(oilGunSize);
        for (SysManageOilGunInfo sysManageOilGunInfo : oilGunList) {
            System.out.println(sysManageOilGunInfo);
        }

    }
}
