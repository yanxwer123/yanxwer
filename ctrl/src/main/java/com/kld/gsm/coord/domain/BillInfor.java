package com.kld.gsm.coord.domain;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/12/8 19:21
 * @Description:单据信息表
 */
public class BillInfor extends AbsValueBean{
    private  String typeno;
    private  String billname;
    private  String maxvouchno;

    public String getTypeno() {
        return typeno;
    }

    public void setTypeno(String typeno) {
        this.typeno = typeno;
    }

    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getMaxvouchno() {
        return maxvouchno;
    }

    public void setMaxvouchno(String maxvouchno) {
        this.maxvouchno = maxvouchno;
    }
}
