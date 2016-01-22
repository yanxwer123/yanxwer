package com.kld.gsm.center.domain.hn;

import java.util.List;


/*
Created BY niyang
Created Date 2015/12/11
*/
public class HNRemote {
    private String nodeno;

    private List<HNGunInfo> pdinfos;

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno;
    }

    public List<HNGunInfo> getPdinfos() {
        return pdinfos;
    }

    public void setPdinfos(List<HNGunInfo> pdinfos) {
        this.pdinfos = pdinfos;
    }
}
