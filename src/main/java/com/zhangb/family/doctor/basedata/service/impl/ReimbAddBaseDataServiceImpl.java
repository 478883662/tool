package com.zhangb.family.doctor.basedata.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;
import com.zhangb.family.doctor.basedata.entity.ReimbDrugPO;
import com.zhangb.family.doctor.basedata.entity.ReimbIllnessDrugPO;
import com.zhangb.family.doctor.basedata.entity.ReimbIllnessPO;
import com.zhangb.family.doctor.basedata.service.IReimbAddBaseDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
@Service
public class ReimbAddBaseDataServiceImpl implements IReimbAddBaseDataService {

    @Override
    public void saveDrug(ReimbDrugPO reimbDrugPO) throws Exception {
        ReimbDrugPO where = new ReimbDrugPO();
        where.setDrugNo(reimbDrugPO.getDrugNo());
        Integer count = BaseDao.count(where);
        if (count > 0) {
            BaseDao.update(reimbDrugPO, where);
        } else {
            BaseDao.insert(reimbDrugPO);
        }
    }


    @Override
    public void saveIllnessDrug(List<ReimbIllnessDrugPO> reimbIllnessDrugPOList) throws Exception {
        reimbIllnessDrugPOList.get(0).getIllnessNo();
        ReimbIllnessDrugPO where = new ReimbIllnessDrugPO();
        where.setIllnessNo(reimbIllnessDrugPOList.get(0).getIllnessNo());
        //先删除原来的用药关系
        BaseDao.delete(where);
        //重新添加新的用药
        for (ReimbIllnessDrugPO reimbIllnessDrugPO:reimbIllnessDrugPOList){
            BaseDao.insert(reimbIllnessDrugPO);
        }

    }


    @Override
    public void saveIllness(ReimbIllnessPO reimbIllnessPO) throws Exception {
        ReimbIllnessPO where = new ReimbIllnessPO();
        where.setIllnessNo(reimbIllnessPO.getIllnessNo());
        int count = BaseDao.count(where);
        if (count == 0) {
            //不存在就插入
            BaseDao.insert(reimbIllnessPO);
        } else {
            //存在就更新，只更新有值的部分
            BaseDao.updateForValue(reimbIllnessPO, where);
        }
    }

    @Override
    public void deleteAllDealRecord(List<ReimbDealRecordPO> resultList) throws Exception {
        if (CollectionUtil.isEmpty(resultList)){
            return;
        }
        ReimbDealRecordPO where = new ReimbDealRecordPO();
        where.setSelfNo(resultList.get(0).getSelfNo());
        BaseDao.delete(where);
    }

    @Override
    public void saveDealRecord(List<ReimbDealRecordPO> resultList) throws Exception{
        if (CollectionUtil.isEmpty(resultList)){
            return;
        }
        //先删除此人的所有记录，重新全量同步到db
        for(ReimbDealRecordPO reimbDealRecordPO :resultList){
            BaseDao.insert(reimbDealRecordPO);
        }
    }
}
