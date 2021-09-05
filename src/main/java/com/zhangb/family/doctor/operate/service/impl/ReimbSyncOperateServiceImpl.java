package com.zhangb.family.doctor.operate.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.family.doctor.basedata.entity.ReimbDealRecordPO;
import com.zhangb.family.doctor.basedata.entity.ReimbUserInfoPO;
import com.zhangb.family.doctor.basedata.entity.ReimbYlCardPO;
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
    public String syncAll() throws Exception {
        List<ReimbYlCardPO> ylCardList = reimbYlCardService.getAllYlCard();
        ylCardList.forEach(e->{
            syncAllByYlCard(e.getYlCard());
        });
        return "同步完成";
    }

    @Override
    public String syncAllByYlCard(String ylCard){
        try {
            syncUserByYlCard(ylCard);
            syncRecordInfo(ylCard,null);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "同步完成";
    }

    @Override
    public List<ReimbUserInfoPO> syncUserByYlCard(String ylCard) throws Exception {
        //远程调用长信农合获取用户信息
        List<ReimbUserInfoPO> resultList = remoteService.execute(ReimbRemoteStrategyKeyConstants.REIMB_GET_USER_BY_YLCARD_STRATEGY,ylCard);
        if (CollectionUtil.isNotEmpty(resultList)){
            userService.addBatchUser(resultList);
        }
        return resultList;
    }

    @Override
    public void syncRecordInfo(String ylCard, String name) throws Exception {
        //查询报销记录是否同步过
        boolean isNew = false;
        ReimbUserInfoPO where = new ReimbUserInfoPO();
        where.setYlCard(ylCard);
        if (!StrUtil.hasBlank(name)){
            where.setName(name);
        }
        List<ReimbUserInfoPO> userList = userService.getAllUserInfo(where);
        //取出所有用户
        for (ReimbUserInfoPO reimbUserInfoPO :userList){
            //2、同步报销记录
            ReimbDealRecordPO reimbDealRecordPO = reimbSyncService.syncRemoteRecord(reimbUserInfoPO.getSelfNo());
            //3，同步病例及其用药
            reimbSyncService.syncRemoteIllnessDrug(reimbDealRecordPO.getIllNessNo());
            isNew = true;
        }
        //如果报销记录有更新就更新下病例信息，看是否有新增的病例
        if(isNew){
            //同步病例信息(病例住院天数与间隔天数需要人工维护)
            reimbSyncService.syncIllness();
        }
    }
}
