package com.kld.app.view.main;

import com.kld.app.socket.client.TcpClient;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.gsm.ATG.domain.SysManageAlarmParameter;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.util.JsonMapper;
import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
  

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0 a
 * @CreationTime: 2015/10/14 22:45
 * @Description:
 */
public class App1 implements Watcher{
    public static void main(String[] args) throws Exception {

        Watched girl = ConcreteWatched.getInstance();

        Watcher watcher = new App1();
        girl.addWetcher("A", watcher);

        Channel cc = TcpClient.getInstance().getChannel("192.168.0.103", 8992);

        GasMsg gasMessage = new GasMsg();
        SysManageAlarmParameter sysManageAlarmParameter = new SysManageAlarmParameter();
        sysManageAlarmParameter.setOilcan(1);
        sysManageAlarmParameter.setLowprealarm(1.2);
        sysManageAlarmParameter.setLowalarm(1.0);
        sysManageAlarmParameter.setHighprealarm(2.1);
        sysManageAlarmParameter.setHighalarm(2.0);
        sysManageAlarmParameter.setWateralarm(0.1);
        sysManageAlarmParameter.setHightempalarm(30.1);
        sysManageAlarmParameter.setLowtempalarm(0.0);
        Date now = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//等价于now.toLocaleString()
        String data = myFmt.format(now);
        //sysManageAlarmParameter.setLastoptime(now);
        //sysManageAlarmParameter.setOptime(now);
        sysManageAlarmParameter.setTranstatus("测试");

        ResultMsg alarmParaMessage = new ResultMsg();
        alarmParaMessage.setId("1");
        alarmParaMessage.setTime(data);
        alarmParaMessage.setResult("0");
        List list = new ArrayList();
        list.add(sysManageAlarmParameter);
        alarmParaMessage.setData(list);
        String subJson = new JsonMapper().toJson(alarmParaMessage);

        gasMessage.setMessage(subJson);
        //System.out.println("abc:"+gasMessage);
        cc.writeAndFlush(gasMessage);
    }


    @Override
    public void update(GasMsg gasMessage) {
        //System.out.println("wwww"+gasMessage);
    }
}
