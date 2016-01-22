package com.kld.gsm.syntocenter.springContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
Created BY niyang
Created Date 2015/11/18
*/
public class springFactory {
    private static ApplicationContext ctx=null;
    public static ApplicationContext getInstance()
    {
        if(ctx==null)
        {
            ctx=new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        }
        return ctx;
    }
}
