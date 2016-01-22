package com.kld.gsm.ATG.domain;

import java.text.DecimalFormat;
import java.util.Date;

public class SysManageTimeSaleOut extends SysManageTimeSaleOutKey {
    private Double sales;

    private Date createtime;

    private String transtatus;

    public String getTranStatus(){return transtatus;}

    public void setTranStatus(String transtatus){this.transtatus=transtatus;}

    public Double getSales() {
        return sales;
    }

    public void setSales(Double sales) {
        DecimalFormat df=new DecimalFormat("######.00");
        if (sales!=null){
            sales=Double.parseDouble(df.format(sales));
        }else{
            sales=0.0;
        }
        this.sales = sales;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}