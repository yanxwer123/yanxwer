package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.HNGunInfoMapper;
import com.kld.gsm.center.dao.HNRemoteToHnMapper;
import com.kld.gsm.center.domain.hn.*;
import com.kld.gsm.center.domain.hn.HNGunInfo;
import com.kld.gsm.center.service.HNGunInfoService;
import com.kld.gsm.center.service.HNRemoteToHnService;
import com.kld.gsm.center.util.JsonMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by xhz on 2015/11/26.
 */
@Service
public class HNRemoteToHnInfo implements HNRemoteToHnService {
    private final static Log logger = LogFactory.getLog(HNRemoteToHnInfo.class);

    @Resource
    private HNRemoteToHnMapper hnRemoteToHnMapper;
    public List<HNRemote> GetRemotetoHnInfo(String nodeno) {
        List<HNRemote> remotes=new ArrayList<HNRemote>();
        String[] array=nodeno.split(",");
        for (int i=0;i<array.length;i++)
        {
            HNRemote hnRemote=new HNRemote();
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("nodeno", array[i]);
            List<HNGunInfo> info=hnRemoteToHnMapper.getremotetohninfo(map);
            hnRemote.setNodeno(array[i]);
            hnRemote.setPdinfos(info);
            remotes.add(hnRemote);
        }
        try {
            String js = new JsonMapper().toJson(remotes);
            logger.info(js);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return remotes;
    }
}
