package com.zhangb.family.doctor.basedata.service.impl;

import cn.hutool.db.Db;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;
import com.zhangb.family.doctor.basedata.entity.ReimbIllnessDrugPO;
import com.zhangb.family.doctor.basedata.entity.ReimbIllnessPO;
import com.zhangb.family.doctor.basedata.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.basedata.service.IReimbAddBaseDataService;
import com.zhangb.family.doctor.basedata.service.IReimbSyncService;
import com.zhangb.family.doctor.basedata.service.ReimbDealRecordService;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.common.constants.RemoteConstants;
import com.zhangb.family.doctor.operate.bo.ReimbDrugBo;
import com.zhangb.family.doctor.operate.service.IReimbSyncOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReimbSyncServiceImpl implements IReimbSyncService {

    @Autowired
    private ICxnhRemoteService remoteService;
    @Autowired
    private IReimbSyncOperateService syncService;
    @Autowired
    private ReimbDealRecordService reimbDealRecordService;
    @Autowired
    private IReimbAddBaseDataService reimbBaseDataService;

    @Override
    public void syncIllness() throws Exception {
        //查询所有的病例信息
        String sql = "select distinct illness_no,illness_name from tool_deal_record_t";
        List<ReimbIllnessPO> reimbIllnessPOList = Db.use().query(sql, ReimbIllnessPO.class);
        //同步到病例表
        for (ReimbIllnessPO reimbIllnessPO : reimbIllnessPOList) {
            reimbBaseDataService.saveIllness(reimbIllnessPO);
        }
    }

    @Override
    public synchronized void syncRemoteRecord(String selfNo) throws Exception {
        //同步农合报销记录
        List<ReimbDealRecordPO> resultList
                = remoteService.execute(ReimbRemoteStrategyKeyConstants.REIMB_GET_RECORD_STRATEGY, selfNo);
        //全量同步前先删除之前所有记录
        reimbBaseDataService.deleteAllDealRecord(resultList);
        //保存报销记录到本地记录表
        reimbBaseDataService.saveDealRecord(resultList);
    }

    @Override
    public void syncRemoteRecord(String selfNo, String bizId) throws Exception {
        //同步农合报销记录
        List<ReimbDealRecordPO> resultList
                = remoteService.execute(ReimbRemoteStrategyKeyConstants.REIMB_GET_RECORD_STRATEGY, bizId,bizId,selfNo);
        //保存报销记录到本地记录表
        reimbBaseDataService.saveDealRecord(resultList);
    }

    @Override
    public String syncRemoteIllnessDrug(String illNessNo) throws BizException {
        //查询本地库里同类病里最新的一次报销的记录
        ReimbDealRecordPO reimbDealRecordPO = reimbDealRecordService.getNewDealRecord(illNessNo);
        String bizId = reimbDealRecordPO.getBizId();
        List<ReimbDrugBo> resultList = remoteService.execute(ReimbRemoteStrategyKeyConstants.REIMB_GET_ILLNESS_DRUG_STRATEGY, RemoteConstants.YL_LOCATION_NO, bizId);
        //刷新最新的病例用药关系
        List<ReimbIllnessDrugPO> reimbIllnessDrugPOList = new ArrayList<>();
        for (ReimbDrugBo reimbDrugBo : resultList) {
            ReimbIllnessDrugPO reimbIllnessDrugPO = new ReimbIllnessDrugPO();
            reimbIllnessDrugPO.setIllnessNo(illNessNo);
            reimbIllnessDrugPO.setDrugNo(reimbDrugBo.getDrugNo());
            reimbIllnessDrugPO.setDrugNum(reimbDrugBo.getDrugNum());
            reimbIllnessDrugPOList.add(reimbIllnessDrugPO);
        }
        try {
            reimbBaseDataService.saveIllnessDrug(reimbIllnessDrugPOList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
