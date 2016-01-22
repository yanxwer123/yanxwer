package com.kld.gsm.coord.servcie.impl;


import com.kld.gsm.coord.dao.AtgoilinDao;
import com.kld.gsm.coord.domain.AtgOilIn;
import com.kld.gsm.coord.servcie.AtgoilinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chen on 2015/11/7.
 */
@SuppressWarnings("all")
@Service
public class AtgoilinServiceImpl implements AtgoilinService {
    @Autowired
    private AtgoilinDao atgoilinDao;
    @Override
    public int insertAtgoilin(AtgOilIn atgOilIn) {
        return atgoilinDao.insertAtgoilin(atgOilIn);
    }

    @Override
    public int updateAtgoilin(int Oilcan) {
        return atgoilinDao.updateAtgoilin(Oilcan);
    }
}
