package com.zhangb.family.doctor.operate.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;
import com.zhangb.family.doctor.basedata.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.common.constants.RemoteConstants;
import com.zhangb.family.doctor.operate.service.IReimbRecordService;
import com.zhangb.family.doctor.operate.service.IReimbSyncOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/9/26.
 */
@Service
public class ReimbRecordServiceImpl implements IReimbRecordService {

    @Autowired
    private ICxnhRemoteService remoteService;
    @Autowired
    private IReimbSyncOperateService syncService;

    @Override
    public boolean isReimbToday(String ylCard, String selfNo) throws Exception {
        ReimbDealRecordPO where = new ReimbDealRecordPO();
        where.setYlCard(ylCard);
        where.setSelfNo(selfNo);
        where.setReimbYear(DateUtil.today());
        int count = BaseDao.count(where);
        if(count>0){
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal getUserReimbTotal(String selfNo) throws SQLException {
        int year = DateUtil.thisYear();
        Number number = Db.use().queryNumber("select sum(MONEY) from tool_deal_record_t where self_no = ? and REIMB_YEAR = ? AND REIMB_TYPE='1101'",selfNo,year);
        return number == null? BigDecimal.ZERO:new BigDecimal(number.toString());
    }

    @Override
    public BigDecimal getYlReimbTotal(String ylLocationNo,int year) throws SQLException {
        Number number = Db.use().queryNumber("select sum(MONEY) from tool_deal_record_t where YL_LOCATION_NO = ? and REIMB_YEAR = ? AND REIMB_TYPE='1101'",ylLocationNo,year);
        return number == null? BigDecimal.ZERO:new BigDecimal(number.doubleValue());
    }

    @Override
    public ReimbDealRecordPO getLastRecord(String selfNo) throws SQLException {
        int year = DateUtil.thisYear();
        List<ReimbDealRecordPO> recordList = Db.use().query("select * from tool_deal_record_t where self_no=? and REIMB_YEAR =? AND REIMB_TYPE='1101'  and money >0 " +
                " order by REIMB_DATE desc limit 0,1",
                ReimbDealRecordPO.class,selfNo,year);
        if (!CollectionUtil.isEmpty(recordList)){
            return recordList.get(0);
        }
        return null;
    }

    @Override
    public ReimbDealRecordPO getLastRecord(String selfNo, String ylLocationNo) throws SQLException {
        int year = DateUtil.thisYear();
        List<ReimbDealRecordPO> recordList = Db.use().query("select * from tool_deal_record_t where self_no=? and YL_LOCATION_NO=? and money >0 and REIMB_YEAR=? " +
                        " order by REIMB_DATE desc limit 0,1",
                ReimbDealRecordPO.class,selfNo,ylLocationNo,year);
        if (!CollectionUtil.isEmpty(recordList)){
            return recordList.get(0);
        }
        return null;
    }

    @Override
    public int getIllnessCount(String selfNo, String illnessNo) throws SQLException {
        int year = DateUtil.thisYear();
        Number number = Db.use().queryNumber("select count(1) from tool_deal_record_t " +
                        "where self_no = ? and ILLNESS_NO=? and REIMB_YEAR = ? and YL_LOCATION_NO=?",
                selfNo,illnessNo,year, RemoteConstants.YL_LOCATION_NO);
        return number.intValue();
    }


}
