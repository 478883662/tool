package com.zhangb.family.doctor.operate.service;


import com.zhangb.family.doctor.basedata.entity.ReimbIllnessPO;
import com.zhangb.family.doctor.basedata.entity.ReimbUserInfoPO;

/**
 * Created by z9104 on 2020/9/26.
 */
public interface IReimbIllnessService {



    /**
     * 从本地库里获取病例信息
     * @param illNessNo
     * @return
     */
    ReimbIllnessPO getIllness(String illNessNo) throws Exception;

    /**
     * 随机获取一种病
     * @param user
     * @param currIllnessNo
     * @return
     */
    ReimbIllnessPO getRandomIllness(ReimbUserInfoPO user, String currIllnessNo) throws Exception;
}
