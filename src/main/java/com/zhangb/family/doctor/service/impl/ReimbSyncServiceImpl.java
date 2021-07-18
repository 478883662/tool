package com.zhangb.family.doctor.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.family.doctor.common.constants.ReimbRemoteStrategyKeyConstants;
import com.zhangb.family.doctor.entity.ReimbUserInfo;
import com.zhangb.family.doctor.entity.ReimbYlCard;
import com.zhangb.family.doctor.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
@Service
public class ReimbSyncServiceImpl implements IReimbSyncService {

    @Autowired
    private IReimbUserService userService  ;
    @Autowired
    private IReimbIllnessService illnessService;
    @Autowired
    private IReimbRecordService recordService;
    @Autowired
    private ReimbYlCardService reimbYlCardService;
    @Autowired
    private ICxnhRemoteService remoteService;

    @Override
    public String syncAll() throws Exception {
        List<ReimbYlCard> ylCardList = reimbYlCardService.getAllYlCard();
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
    public List<ReimbUserInfo> syncUserByYlCard(String ylCard) throws Exception {
        //远程调用长信农合获取用户信息
        List<ReimbUserInfo> resultList = remoteService.execute(ReimbRemoteStrategyKeyConstants.REIMB_GET_USER_BY_YLCARD_STRATEGY,ylCard);
        if (CollectionUtil.isNotEmpty(resultList)){
            userService.addBatchUser(resultList);
        }
        return resultList;
    }

    @Override
    public void syncRecordInfo(String ylCard, String name) throws Exception {
        //查询报销记录是否同步过
        boolean isNew = false;
        ReimbUserInfo where = new ReimbUserInfo();
        where.setYlCard(ylCard);
        if (!StrUtil.hasBlank(name)){
            where.setName(name);
        }
        List<ReimbUserInfo> userList = userService.getAllUserInfo(where);
        //取出所有用户
        for (ReimbUserInfo reimbUserInfo:userList){
            //2、同步报销记录
            recordService.syncRecord(reimbUserInfo.getSelfNo());
            isNew = true;
        }
        //如果报销记录有更新就更新下病例信息，看是否有新增的病例
        if(isNew){
            //同步病例信息(病例住院天数与间隔天数需要人工维护)
            illnessService.syncIllnessFromRecord();
        }
    }
}
