package com.kld.app.springcontext;



import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kld.app.service.AcceptanceDeliveryBillService;
import com.kld.app.service.IIquidInstrumentService;
import com.kld.app.service.impl.IquidInstrumentServiceImpl;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Context {
    private static ApplicationContext ctx= null;  
    //静态工厂方法   
    public static ApplicationContext getInstance() {  
         if (ctx == null) {
             try {
                 String path = "spring/applicationContext.xml";
                 return  ctx = new ClassPathXmlApplicationContext(path);
             } catch (Exception e) {

                 ////System.out.println(e.getMessage());
               /*  try {
                     URL purl = this.getClass().getResource("/");
                     String p = new File(purl.getFile(), "../conf").getCanonicalPath();
                     p = p +"spring/applicationContext.xml";
                     return  ctx = new ClassPathXmlApplicationContext(p);
                 } catch (IOException ioe) {

                 }*/
             }
         }
        if(ctx==null)
        {
            ////System.out.println("spring context is null");
        }
        return ctx;  
    }  
    public static void main(String[] args) {
    	 IIquidInstrumentService iquidInstrumentService =
    	            (IquidInstrumentServiceImpl) (Context.getInstance().getBean("iquidInstrumentService"));
    	 ////System.out.println(iquidInstrumentService.getLastAdjustTime());
	}
}
