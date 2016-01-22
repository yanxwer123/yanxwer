package com.kld.app.service.impl;

import com.kld.app.service.DailyTradeAccountService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.dao.DailyTankShiftDao;
import com.kld.gsm.ATG.dao.DailyTradeAccountDao;
import com.kld.gsm.ATG.dao.SysManageOilGunInfoDao;
import com.kld.gsm.ATG.domain.DailyTradeAccount;
import com.kld.gsm.ATG.domain.DailyTradeAccountKey;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by 1 on 2015/10/26.
 */
@Service("dailyTradeAccountService")
@SuppressWarnings("all")
public class DailyTradeAccountServiceImpl implements DailyTradeAccountService {
    @Resource
    private DailyTradeAccountDao dailyTradeAccountDao;

    @Resource
    private SysManageOilGunInfoDao sysmanageOilGunInfoDao;

	@Override
	public int deleteByPrimaryKey(DailyTradeAccountKey key) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(DailyTradeAccount record) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.insert(record);
	}

	@Override
	public int insertSelective(DailyTradeAccount record) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.insertSelective(record);
	}

	@Override
	public DailyTradeAccount selectByPrimaryKey(DailyTradeAccountKey key) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(DailyTradeAccount record) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DailyTradeAccount record) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.updateByPrimaryKey(record);
	}

	/*@Override
	public int updateByKey(List list) {
		// TODO Auto-generated method stub
		dailyTradeAccountDao.updateByKey(String Vo);
		return 0;
	}*/

	@Override
	public List<DailyTradeAccount> findNotRecieved() {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.findNotRecieved();
	}

	@Override
	public int updateIsRecieved(DailyTradeAccount record) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.updateIsRecieved(record);
	}
    
	public static void main(String[] args) {
		DailyTradeAccountService iquidInstrumentService =
				(DailyTradeAccountService) (Context.getInstance().getBean("dailyTradeAccountService"));
		DailyTradeAccount account = new DailyTradeAccount();
		account.setShift("20141114");
		account.setMacno(20141114);
		account.setTtc(1234);
		account.setTakedate(new Date());
		account.setOilgun("12");
		iquidInstrumentService.insert(account);
		try {
			List<DailyTradeAccount> xx = iquidInstrumentService.findNotRecieved();
//			////System.out.println(xx.get(0).getShift());
			////System.out.println(xx.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<DailyTradeAccount> query(HashMap map) {
		// TODO Auto-generated method stub
		return dailyTradeAccountDao.query(map);
	}

	@Override
	public List<SysManageOilGunInfo> selectAllOilGun() {
		// TODO Auto-generated method stub
		return sysmanageOilGunInfoDao.selectAllOilGun();
	}
	
	    @SuppressWarnings({"unchecked" })
    @Override
    public Map selectDuringSales(Integer oilCan, Date begin, Date end) {
        Map params = new HashMap();
        params.put("oilCan", oilCan);
        params.put("begin", begin);
        params.put("end", end);
        return dailyTradeAccountDao.selectDuringSales(params);
    }

	@Override
	public Map GetSaleOilSumByCanNoAndDate(String canno, Date st, Date et) {
		Map<String,Object> hm=new HashMap<String, Object>();
		hm.put("canno",canno);
		hm.put("st",st);
		hm.put("et",et);
		return dailyTradeAccountDao.GetSaleOilLiterByCanNo(hm);

	}

	@Override
	public ArrayList<String> findLikeShift(HashMap shiftmap) {
		ArrayList<String> list =dailyTradeAccountDao.findLikeShift(shiftmap);
		return list;
	}
}
