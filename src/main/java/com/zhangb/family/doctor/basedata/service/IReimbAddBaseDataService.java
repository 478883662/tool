package com.zhangb.family.doctor.basedata.service;


import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;
import com.zhangb.family.doctor.basedata.entity.ReimbDrugPO;
import com.zhangb.family.doctor.basedata.entity.ReimbIllnessDrugPO;
import com.zhangb.family.doctor.basedata.entity.ReimbIllnessPO;

import java.util.List;

/**
 * 药品service
 */
public interface IReimbAddBaseDataService {
    /**
     * 保存药品最新数据到数据库
     * @param reimbDrugPO
     * @return
     */
    void saveDrug(ReimbDrugPO reimbDrugPO) throws Exception;

    /**
     * 保存病例与药品的关系
     * @param reimbIllnessDrugPO
     * @return
     */
    void saveIllnessDrug( List<ReimbIllnessDrugPO> reimbIllnessDrugPOList) throws Exception;

    /**
     * 保存病例，查询处理记录表(只增量同步)
     */
    void saveIllness(ReimbIllnessPO reimbIllnessPO) throws Exception;


    /**
     * 保存农合报销记录
     * @param resultList
     */
    void saveDealRecord(List<ReimbDealRecordPO> resultList) throws Exception;
}
