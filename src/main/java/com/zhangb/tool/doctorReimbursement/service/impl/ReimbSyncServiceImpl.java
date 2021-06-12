package com.zhangb.tool.doctorReimbursement.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.tool.common.dao.BaseDao;
import com.zhangb.tool.doctorReimbursement.common.enums.ReimbSyncTypeEnum;
import com.zhangb.tool.doctorReimbursement.controller.ReimbUserController;
import com.zhangb.tool.doctorReimbursement.entity.ReimbSyncInfo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbUserInfo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbYlCard;
import com.zhangb.tool.doctorReimbursement.service.IReimbIllnessService;
import com.zhangb.tool.doctorReimbursement.service.IReimbRecordService;
import com.zhangb.tool.doctorReimbursement.service.IReimbSyncService;
import com.zhangb.tool.doctorReimbursement.service.IReimbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
@Service
public class ReimbSyncServiceImpl implements IReimbSyncService {

    @Autowired
    private IReimbUserService userService  ;
    @Autowired
    private IReimbSyncService syncService;
    @Autowired
    private IReimbIllnessService illnessService;
    @Autowired
    private IReimbRecordService recordService;

    @Override
    public int getSyncCount(String ylCard, String today, String type) throws SQLException, IllegalAccessException {
        ReimbSyncInfo where  = new ReimbSyncInfo();
        where.setId(ylCard);
        where.setSyncDate(today);
        where.setSyncType(type);
        return BaseDao.count(where);
    }

    @Override
    public void insertSyncRecord(ReimbSyncInfo reimbSyncInfo) throws SQLException, IllegalAccessException {
        ReimbSyncInfo where = new ReimbSyncInfo();
        where.setId(reimbSyncInfo.getId());
        where.setSyncDate(reimbSyncInfo.getSyncDate());
        where.setSyncType(reimbSyncInfo.getSyncType());
        int count = BaseDao.count(where);
        if (count<1){
            BaseDao.insert(reimbSyncInfo);
        }
    }

    @Override
    public String syncUserInfo(String ylCard) throws Exception {
        //同步医疗账户下的人员信息
//        if(checkSync(ylCard, ReimbSyncTypeEnum.YL_CARD_SYNC.getType())){
//            System.out.println("医疗账号["+ylCard+"]今日已同步过了");
//            return "FAIL:医疗账号["+ylCard+"]今日已同步过了";
//        }
        //没有同步过，就同步更新下账户下所有人，看是否有新增的人
        String result = userService.syncUserByYlCard(ylCard);
        ReimbSyncInfo ylCardSyncInfo  = new ReimbSyncInfo();
        ylCardSyncInfo.setSyncType(ReimbSyncTypeEnum.YL_CARD_SYNC.getType());
        ylCardSyncInfo.setSyncDate(DateUtil.today());
        ylCardSyncInfo.setId(ylCard);
        ylCardSyncInfo.setCreateDate(new Date());
        syncService.insertSyncRecord(ylCardSyncInfo);
        return result;
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
            if(checkSync(reimbUserInfo.getSelfNo(),ReimbSyncTypeEnum.REIMB_RECORD.getType())){
                System.out.println("用户报销记录已同步过："+reimbUserInfo.getName());
                continue;
            }
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

    @Override
    public String syncAll() throws SQLException, IllegalAccessException {
        if(checkSync("ALL",ReimbSyncTypeEnum.SYNC_ALL.getType())){
            return "今日已全量同步过了!";
        }
        List<ReimbYlCard> ylCardList = userService.getAllYlCard();
        ReimbSyncInfo ylCardSyncInfo  = new ReimbSyncInfo();
        ylCardSyncInfo.setSyncType(ReimbSyncTypeEnum.SYNC_ALL.getType());
        ylCardSyncInfo.setSyncDate(DateUtil.today());
        ylCardSyncInfo.setId("ALL");
        ylCardSyncInfo.setCreateDate(new Date());
        insertSyncRecord(ylCardSyncInfo);
        synchronized (ReimbUserController.class){
            ylCardList.forEach(e->{
                //报销前先同步
                try {
                    syncService.syncUserInfo(e.getYlCard());
                    syncService.syncRecordInfo(e.getYlCard(),null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
        return "同步完成";
    }


    /**
     * 校验医疗账号是否同步过
     * @param id
     * @param type
     * @return
     */
    private boolean checkSync(String id, String type) throws SQLException, IllegalAccessException {
        String today = DateUtil.today();
        int count = getSyncCount(id,today,type);
        if (count>0){
            return true;
        }
        return false;
    }
}
