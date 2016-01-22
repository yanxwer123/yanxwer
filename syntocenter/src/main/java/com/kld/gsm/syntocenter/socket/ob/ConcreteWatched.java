package com.kld.gsm.syntocenter.socket.ob;

 import com.kld.gsm.Socket.protocol.GasMsg;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/14 22:41
 * @Description:
 */
public class ConcreteWatched implements Watched {
    //存放观察者
    // private List<Watcher> list = new ArrayList<Watcher>();
    private static Map<String, Watcher> map = new LinkedHashMap<String, Watcher>();
    private static ConcreteWatched concreteWatched = null;

    private ConcreteWatched() {
    }

    public static ConcreteWatched getInstance() {
        if (concreteWatched == null) {
            concreteWatched = new ConcreteWatched();
        }
        return concreteWatched;
    }


    @Override
    public void addWetcher(String key, Watcher wetcher) {
        map.put(key, wetcher);

    }

    @Override
    public void removeWetcher(String key) {
        map.remove(key);
    }

    @Override
    public void notifyWechers(GasMsg gasMsg, String key) {
        if (map.get(key).equals("") || map.size() < 1) {
            System.out.println("没有观察者");
        }else {
            //具体的业务逻辑，根据消息通知观察者
            map.get(key).update(gasMsg);
           // System.out.println("根据key" + key + "查询出来的" + map.get(key));
        }
     }
}
