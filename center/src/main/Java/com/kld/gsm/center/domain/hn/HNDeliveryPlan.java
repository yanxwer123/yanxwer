package com.kld.gsm.center.domain.hn;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by xhz on 2015/11/21.
 * 湖南销售省级监控平台配送单
 */
public class HNDeliveryPlan {
    @JsonProperty("PSD_ID")
    private String PSD_ID;
    @JsonProperty("OILS_ID")
    private String OILS_ID;
    @JsonProperty("DEPOT_CODE")
    private String DEPOT_CODE;
    @JsonProperty("XQL")
    private Double XQL;
    @JsonProperty("XQUNIT")
    private String XQUNIT;
    @JsonProperty("CP_NO")
    private String CP_NO;
    @JsonProperty("PS_DATE")
    private String PS_DATE;
    @JsonProperty("LSGC")
    private String LSGC;
    @JsonProperty("KCDD")
    private String KCDD;

    private String nodeno;

    public String getPSD_ID(){ return PSD_ID; }
    public void setPSD_ID(String PSD_ID){this.PSD_ID=PSD_ID;}

    public String getOILS_ID(){return OILS_ID;}
    public void setOILS_ID(String OILS_ID){this.OILS_ID=OILS_ID;}

    public String getDEPOT_CODE(){return DEPOT_CODE;}
    public void setDEPOT_CODE(String DEPOT_CODE){this.DEPOT_CODE=DEPOT_CODE;}

    public Double getXQL(){return XQL;}
    public  void setXQL(Double XQL){this.XQL=XQL;}

    public String getXQUNIT(){return XQUNIT;}
    public  void setXQUNIT(String XQL){this.XQUNIT=XQUNIT;}

    public String getPS_DATE(){return PS_DATE;}
    public  void setPS_DATE(String PS_DATE){this.PS_DATE=PS_DATE;}

    public String getLSGC(){return LSGC;}
    public  void setLSGC(String LSGC){this.LSGC=LSGC;}

    public String getKCDD(){return KCDD;}
    public  void setKCDD(String XQL){this.KCDD=KCDD;}

    public String getCP_NO(){return CP_NO;}
    public  void setCP_NO(String CP_NO){this.CP_NO=CP_NO;}

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno;
    }
}
