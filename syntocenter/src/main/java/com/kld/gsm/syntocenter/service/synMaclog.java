package com.kld.gsm.syntocenter.service;


import java.util.Map;

/*
Created BY niyang
Created Date 2015/12/8
*/
public interface synMaclog {

    boolean synMaclog(String remotepath,String localpath,Map ftp);

    boolean synTankandGunRTStatus();

    boolean synMacLogAuto();
}
