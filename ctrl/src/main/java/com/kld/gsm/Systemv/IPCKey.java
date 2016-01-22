package com.kld.gsm.Systemv;

/**
 * Created by luyan on 15/10/30.
 */
public class IPCKey {

    static
    {
        System.loadLibrary("ftok");
    }
    public native static int getIntj(int ipckey);
}
