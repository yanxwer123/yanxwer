package com.kld.gsm.syntocenter.server;


import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.Socket.Constants;

import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.syntocenter.domain.CanInfo;
import com.kld.gsm.syntocenter.domain.GunInfo;
import com.kld.gsm.syntocenter.socket.ob.Watcher;
import com.kld.gsm.syntocenter.util.action;
import com.kld.gsm.syntocenter.util.httpClient;
import com.kld.gsm.syntocenter.util.param;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
Created BY niyang
Created Date 2015/12/13
*/
public class CanAndGunStatus implements Watcher {
    private static final Logger LOG = Logger.getLogger("CanAndGunStatus");
    public CanAndGunStatus() {
        //region注册观察者开始

        //endregion
    }

    public void reg() {
        ApplicationMain.watch.addWetcher("A", this);
        LOG.info("Reg Observer");
    }

    public void send() {
        LOG.info("send");
        ApplicationMain.count+=2;
        try {
            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(ApplicationMain.sign, new ArrayList(), Constants.PID_Code.A15_10002.toString());
            ApplicationMain.CC.writeAndFlush(gasMsg);
            gasMsg = ResultUtils.getInstance().sendSUCCESS(ApplicationMain.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
            ApplicationMain.CC.writeAndFlush(gasMsg);
        }catch (Exception e){
            LOG.error("send error:"+e.getMessage(),e);
        }

    }

    @Override
    public void update(GasMsg gasMsg) {
        //region getnodeno
        String nodeno = ApplicationMain.NodeNo;
        //endregion

        if (gasMsg.getPid().equals("A15_10002")) {
            //油枪状态
            LOG.info("5秒同步枪状态 into");
            ApplicationMain.count--;
            List<GunInfo> gunInfos = new ArrayList<GunInfo>();
            try {
                ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
                List<MacLogInfo> macLogInfos = resultMsg.getData();

                for (int i = 0; i < macLogInfos.size(); i++) {
                    // region获取罐号
                    GunInfo gunInfo = new GunInfo();
                    Map<String, ?> map = (Map) (macLogInfos.get(i));
                    //枪map
                    gunInfo.setOilGun(Integer.parseInt(map.get("GunNum").toString()));
                    if (map.get("GunStatus") != null) {
                        gunInfo.setOilGunStatus(GunStatusEnum.valueOf(map.get("GunStatus").toString()).value());
                    }
                    gunInfo.setGetTime(new Date());
                    gunInfo.setPumpUp(map.get("PumpReadout") == null ? null : map.get("PumpReadout").toString());
                    gunInfo.setNodeno(nodeno);
                    gunInfos.add(gunInfo);
                }
            }catch (Exception e){
                LOG.error("5秒同步枪状态:"+e.getMessage());
                return;
            }
            try {
                //获取action地址
                //action ac = new action();
                String path = ApplicationMain.gunaddr;
                //ac.getUri("resource.services.TI.addQSSZT");

                Map<String, String> hm = new param().getparam();
                //发送站级数据
                httpClient client = new httpClient();
                client.request(path, gunInfos, hm);
                gunInfos.clear();
                client=null;
            } catch (Exception e) {
                gunInfos.clear();
                LOG.error("5秒同步枪状态："+e.getMessage());
            }
            LOG.info("GunRealStatus end");
        }

        if (gasMsg.getPid().equals("A15_10004")) {
            //实时库存
            LOG.info("5秒同步罐状态 into");
            List<CanInfo> canInfos = new ArrayList<CanInfo>();
            try {
                ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
                List<Map<String, ?>> candata = resultMsg.getData();
                for (int m = 0; m < candata.size(); m++) {
                    CanInfo canInfo = new CanInfo();
                    Map<String, ?> map = candata.get(m);
                    canInfo.setNodeno(nodeno);
                    canInfo.setOilcan(Integer.parseInt(map.get("uOilCanNo").toString()));
                    canInfo.setOill(Double.parseDouble(map.get("fOilCubage").toString()));
                    canInfo.setStandardl(Double.parseDouble(map.get("fOilStandCubage").toString()));
                    canInfo.setStoretime(new Date());
                    canInfo.setTemperature(Double.parseDouble(map.get("fOilTemp").toString()));
                    canInfo.setTemp1(Double.parseDouble(map.get("fOilTemp1").toString()));
                    canInfo.setTemp2(Double.parseDouble(map.get("fOilTemp2").toString()));
                    canInfo.setTemp3(Double.parseDouble(map.get("fOilTemp3").toString()));
                    canInfo.setTemp4(Double.parseDouble(map.get("fOilTemp4").toString()));
                    canInfo.setTemp5(Double.parseDouble(map.get("fOilTemp5").toString()));
                    canInfo.setDensitiesstandard(Double.parseDouble(map.get("fStandDensity").toString()));
                    canInfo.setDensities(Double.parseDouble(map.get("fApparentDensity").toString()));
                    canInfo.setHeighttotal(Double.parseDouble(map.get("fTotalHeight").toString()));
                    canInfo.setHeightwater(Double.parseDouble(map.get("fWaterHeight").toString()));
                    canInfo.setWaterl(Double.parseDouble(map.get("fWaterBulk").toString()));
                    if (!ApplicationMain.oilcanmap.isEmpty() && ApplicationMain.oilcanmap.get(map.get("uOilCanNo").toString()) != null) {
                        canInfo.setOilno(ApplicationMain.oilcanmap.get(map.get("uOilCanNo").toString()));
                    }
                    canInfo.setVolumeempty(Double.parseDouble(map.get("fEmptyCubage").toString()));
                    if (!ApplicationMain.canversion.isEmpty() && ApplicationMain.canversion.get(map.get("uOilCanNo").toString()) != null) {
                        canInfo.setVersion(ApplicationMain.canversion.get(map.get("uOilCanNo").toString()));
                    }
                    canInfos.add(canInfo);
                }
            }catch (Exception e){
                LOG.error("5秒同步罐状态异常"+e.getMessage());
            }
            try {
                //获取action地址
                // action ac = new action();
                String path =ApplicationMain.canaddr;
                //System.out.println("TankandGunRealStatus:"+path);
                Map<String, String> hm = new param().getparam();
                //发送站级数据
                httpClient client = new httpClient();
                client.request(path, canInfos, hm);
                canInfos.clear();
                client=null;
            } catch (Exception e) {
                LOG.error("5秒同步罐状态：" + e.getMessage());
                canInfos.clear();
            }
            LOG.info("TankRealStatus end");
        }
    }
}
