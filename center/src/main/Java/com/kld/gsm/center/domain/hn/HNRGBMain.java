package com.kld.gsm.center.domain.hn;


import java.util.List;

/*
Created BY niyang
Created Date 2015/11/22
Description 湖南接口容积表包装实体类
*/
public class HNRGBMain {
    private HNRGB rgb;

    private List<HNRGBInfo> hnrgbInfos;

    public HNRGB getRgb() {
        return rgb;
    }

    public void setRgb(HNRGB rgb) {
        this.rgb = rgb;
    }

    public List<HNRGBInfo> getHnrgbInfos() {
        return hnrgbInfos;
    }

    public void setHnrgbInfos(List<HNRGBInfo> hnrgbInfos) {
        this.hnrgbInfos = hnrgbInfos;
    }
}
