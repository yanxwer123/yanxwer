package com.kld.gsm.center.domain.hn;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
Created BY niyang
Created Date 2015/11/21
Description 湖南接口 市公司信息实体类
*/
public class HNCity {
    @JsonProperty("CODE")
    private String code;

    @JsonProperty("NAME")
    private  String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
