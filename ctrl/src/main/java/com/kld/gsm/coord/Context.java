package com.kld.gsm.coord;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luyan on 15/11/14.
 */
public class Context {

    private static ApplicationContext ctx=null;
    public static ApplicationContext getInstance()
    {
        if(ctx==null)
        {
            ctx=new ClassPathXmlApplicationContext("./spring/spring-ctrl.xml");

        }
        return ctx;
    }
}
