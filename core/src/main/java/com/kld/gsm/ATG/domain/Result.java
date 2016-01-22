package com.kld.gsm.ATG.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/*
Created BY ny
Created Date 2015/11/18
*/
public class Result {
    private boolean result;

    private String errcode;

    private String msg;

    private List rows;

    private int total;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public List getRows() {
        return rows;
    }

    @JsonIgnore
    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



}
