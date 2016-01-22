package com.kld.gsm.coord.server.handler;

import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.ITransacService;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/2 18:34
 * @Description: 交易明细 及油量 gengxin
 */
public class TransacHandler {

    //  @Autowired
    // private static ITransacService transacService;

    /**
     * @param MacNo    油机编号
     * @param GunNo    油枪编号
     * @param TTC      交易序号
     * @param TakeDate 交易时间
     */
    public static void getInformation(int MacNo, int GunNo, int TTC, String TakeDate) {
        ITransacService transacService = Context.getInstance().getBean(ITransacService.class);
        //System.out.println("go getInformation:[" + transacService + "]");
        transacService.getInformation(MacNo, GunNo, TTC, TakeDate);
    }
}
