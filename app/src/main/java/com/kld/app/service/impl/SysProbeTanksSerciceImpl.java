package com.kld.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.app.service.SysProbeTanksService;
import com.kld.gsm.ATG.dao.SysManagePaTRelationDao;
import com.kld.gsm.ATG.domain.SysManagePaTRelation;


@Service("probeTanksService")
public class SysProbeTanksSerciceImpl implements SysProbeTanksService {
	
	@Resource
	private SysManagePaTRelationDao sysManagePaTRelationDao;
	
	@Override
	public List<SysManagePaTRelation> selectAll() {
		return sysManagePaTRelationDao.selectAll();
	}

	@Override
	public int insert(SysManagePaTRelation record) {
		// TODO Auto-generated method stub
		return sysManagePaTRelationDao.insert(record);
	}
	
	
//	public Object[][] getList(List<SysManagePaTRelation> list) {
//		Object obj[][]=new Object[list.size()][12];
//		for (int i = 0; i < listRece.size(); i++) {
//			obj[i][0]=listRece.get(i).getSjbh();
//		//	obj[i][1]=listRece.get(i).getSkdw();
//			obj[i][1]=listRece.get(i).getKhxm();
//			obj[i][2]=listRece.get(i).getHtlsh();
//		//	obj[i][3]=listRece.get(i).getSky();
//			obj[i][3]=listRece.get(i).getKprq();
//		//	obj[i][5]=listRece.get(i).getFkr();
//		//	obj[i][6]=listRece.get(i).getHrh();
//		//	obj[i][7]=listRece.get(i).getYwbm();
//		//	obj[i][8]=listRece.get(i).getBz();
//		//	obj[i][9]=listRece.get(i).getMbbh();
//			obj[i][4]=listRece.get(i).getHjje();
//			
//			//////System.out.println(s[i][8]);
//		}
//	return obj;
//	}
}
