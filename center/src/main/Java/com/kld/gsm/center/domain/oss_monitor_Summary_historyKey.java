package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_monitor_Summary_historyKey {
    private String nodeno;

    private Date createdate;

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}