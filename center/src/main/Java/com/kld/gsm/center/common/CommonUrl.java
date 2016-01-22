package com.kld.gsm.center.common;



/**
 * Created by miaozy on 15/5/4.
 */
public class CommonUrl {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getTarget(String resource){
        return  url + resource;
    }
}
