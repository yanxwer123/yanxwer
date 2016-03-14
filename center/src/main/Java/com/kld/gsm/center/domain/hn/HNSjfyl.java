package com.kld.gsm.center.domain.hn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2016/2/23 14:45
 * @Description:湖南返回实际发油量实体
 */
public class HNSjfyl {
    @JsonProperty("ckdId")
    private String CKD_ID;//出库单号
    @JsonProperty("sjfyl")
    private String SJ_FYL;//实际发油量
    @JsonProperty("wd")
    private Double SJ_WD;//温度
    @JsonProperty("md")
    private Double SJ_MD;//密度

    public String getCKD_ID() {
        return CKD_ID;
    }

    public void setCKD_ID(String CKD_ID) {
        this.CKD_ID = CKD_ID;
    }

    public String getSJ_FYL() {
        return SJ_FYL;
    }

    public void setSJ_FYL(String SJ_FYL) {
        this.SJ_FYL = SJ_FYL;
    }

    public Double getSJ_MD() {
        return SJ_MD;
    }

    public void setSJ_MD(Double SJ_MD) {
        this.SJ_MD = SJ_MD;
    }

    public Double getSJ_WD() {
        return SJ_WD;
    }

    public void setSJ_WD(Double SJ_WD) {
        this.SJ_WD = SJ_WD;
    }
}
