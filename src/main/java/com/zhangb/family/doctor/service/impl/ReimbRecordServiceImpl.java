package com.zhangb.family.doctor.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.common.constants.RemoteConstants;
import com.zhangb.family.doctor.entity.ReimbDealRecord;
import com.zhangb.family.doctor.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.service.IReimbRecordService;
import com.zhangb.family.doctor.service.IReimbSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by z9104 on 2020/9/26.
 */
@Service
public class ReimbRecordServiceImpl implements IReimbRecordService {

    @Autowired
    private ICxnhRemoteService remoteService;
    @Autowired
    private IReimbSyncService syncService;
    @Override
    public synchronized String syncRecord(String selfNo) throws Exception {
        String resultStr = remoteService.executeRemote(ReimbRemoteStrategyKeyConstants.REIMB_GET_RECORD_STRATEGY,selfNo);
        List<ReimbDealRecord> resultList = parseResult(resultStr);
        ReimbDealRecord where = new ReimbDealRecord();
        where.setSelfNo(selfNo);
        BaseDao.delete(where);
        //先删除此人的所有记录，重新全量同步到db
        for(ReimbDealRecord reimbDealRecord:resultList){
            BaseDao.insert(reimbDealRecord);
        }
        if (resultList.size()>1){
            //取最近报销的记录
            return resultList.stream().max(new Comparator<ReimbDealRecord>() {
                @Override
                public int compare(ReimbDealRecord o1, ReimbDealRecord o2) {
                    return o1.getReimbDate().compareTo(o2.getReimbDate());
                }
            }).map(ReimbDealRecord::getBizId).get();
        }else{
            return null;
        }

    }

    @Override
    public boolean isReimbToday(String ylCard, String selfNo) throws Exception {
        ReimbDealRecord where = new ReimbDealRecord();
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
    public ReimbDealRecord getLastRecord(String selfNo) throws SQLException {
        int year = DateUtil.thisYear();
        List<ReimbDealRecord> recordList = Db.use().query("select * from tool_deal_record_t where self_no=? and REIMB_YEAR =? AND REIMB_TYPE='1101'  and money >0 " +
                " order by REIMB_DATE desc limit 0,1",
                ReimbDealRecord.class,selfNo,year);
        if (!CollectionUtil.isEmpty(recordList)){
            return recordList.get(0);
        }
        return null;
    }

    @Override
    public ReimbDealRecord getLastRecord(String selfNo, String ylLocationNo) throws SQLException {
        int year = DateUtil.thisYear();
        List<ReimbDealRecord> recordList = Db.use().query("select * from tool_deal_record_t where self_no=? and YL_LOCATION_NO=? and money >0 and REIMB_YEAR=? " +
                        " order by REIMB_DATE desc limit 0,1",
                ReimbDealRecord.class,selfNo,ylLocationNo,year);
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

    /**
     * 解析报销记录结果
     * @param resultStr
     * @return
     */
    private List<ReimbDealRecord> parseResult(String resultStr) {
        List<ReimbDealRecord> resultList = new ArrayList<>();
        if (StrUtil.hasBlank(resultStr)){
            return resultList;
        }
        String[] rowList = StrUtil.split(resultStr,"\r\n");
        for(String row:rowList){
            String[] result = StrUtil.split(row,"\t");
            ReimbDealRecord reimbDealRecord = new ReimbDealRecord();
            reimbDealRecord.setReimbNo(result[6]);
            reimbDealRecord.setIllNessNo(result[23]);
            reimbDealRecord.setIllNessName(result[24]);
            reimbDealRecord.setInDate(DateUtil.parseDate(result[16]));
            reimbDealRecord.setOutDate(DateUtil.parseDate(result[17]));
            reimbDealRecord.setMoney(new BigDecimal(result[14]));
            reimbDealRecord.setReimbDate(DateUtil.parseDate(result[15]));
            reimbDealRecord.setReimbType(result[5]);
            reimbDealRecord.setYlLocation(result[19]);
            reimbDealRecord.setYlLocationNo(result[18]);
            reimbDealRecord.setYlCard(result[7]);
            reimbDealRecord.setSelfNo(result[8]);
            reimbDealRecord.setName(result[9]);
            reimbDealRecord.setReimbYear(result[0]);
            reimbDealRecord.setBizId(result[3]);
            resultList.add(reimbDealRecord);
        }
        return resultList;
    }
}
