package com.zhangb.family.doctor.basedata.service.impl;

import com.zhangb.family.doctor.basedata.dao.ReimbDealRecordDao;
import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;
import com.zhangb.family.doctor.basedata.service.ReimbDealRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReimbDealRecordServiceImpl implements ReimbDealRecordService {

    @Autowired
    private ReimbDealRecordDao reimbDealRecordDao;
    @Override
    public ReimbDealRecordPO getNewDealRecord(String illNessNo) {
        return reimbDealRecordDao.getNewDealRecord(illNessNo);
    }
}
