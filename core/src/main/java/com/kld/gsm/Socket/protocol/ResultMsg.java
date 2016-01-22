package com.kld.gsm.Socket.protocol;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/20 14:26
 * @Description:  GasMessage   message  SubMessage date 自定义对象
 */
public class ResultMsg {
    private String id;//用户标识
    private String result;//返回结果
    private String time;//时间
    private List data;//业务数据

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List  getData() {
        return data;
    }

    public void setData(List  data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SubMessage{" +
                "id='" + id + '\'' +
                ", result='" + result + '\'' +
                ", time='" + time + '\'' +
                ", data=" + data +
                '}';
    }
}

