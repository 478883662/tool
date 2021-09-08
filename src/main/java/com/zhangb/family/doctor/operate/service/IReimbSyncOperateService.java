package com.zhangb.family.doctor.operate.service;

import com.zhangb.family.doctor.basedata.entity.ReimbUserInfoPO;

import java.util.List;

/**
 * 远端业务类，主要获取远端的信息封装到业务类中返回
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbSyncOperateService {


    /**
     * 同步报销用户信息，并返回远端最新的报销用户信息
     * @param ylCard
     * @return
     * @throws Exception
     */
    List<ReimbUserInfoPO> syncUserByYlCard(String ylCard) throws Exception;

}
