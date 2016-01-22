package com.kld.gsm.coord.servcie;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/10 15:40
 * @Description:交易明细 及油量
 */
public interface ITransacService {
    void getInformation(int MacNo, int GunNo, int TTC, String TakeDate);
}
