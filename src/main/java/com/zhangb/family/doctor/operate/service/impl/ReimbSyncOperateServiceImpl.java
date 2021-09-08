package com.zhangb.family.doctor.operate.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zhangb.family.doctor.basedata.entity.ReimbUserInfoPO;
import com.zhangb.family.doctor.basedata.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.basedata.service.IReimbAddBaseDataService;
import com.zhangb.family.doctor.basedata.service.IReimbSyncService;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.operate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
@Service
public class ReimbSyncOperateServiceImpl implements IReimbSyncOperateService {

    @Autowired
    private IReimbUserService userService  ;
    @Autowired
    private IReimbIllnessService illnessService;
    @Autowired
    private IReimbRecordService recordService;
    @Autowired
    private IReimbYlCardService reimbYlCardService;
    @Autowired
    private ICxnhRemoteService remoteService;
    @Autowired
    private IReimbAddBaseDataService reimbBaseDataService;
    @Autowired
    private IReimbSyncService reimbSyncService;


    @Override
    public List<ReimbUserInfoPO> syncUserByYlCard(String ylCard) throws Exception {
        //远程调用长信农合获取用户信息
        List<ReimbUserInfoPO> resultList = remoteService.execute(ReimbRemoteStrategyKeyConstants.REIMB_GET_USER_BY_YLCARD_STRATEGY,ylCard);
        if (CollectionUtil.isNotEmpty(resultList)){
            userService.addBatchUser(resultList);
        }
        return resultList;
    }

}
