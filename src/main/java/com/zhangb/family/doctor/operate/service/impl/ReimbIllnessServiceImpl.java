package com.zhangb.family.doctor.operate.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.basedata.entity.ReimbIllnessPO;
import com.zhangb.family.doctor.basedata.entity.ReimbUserInfoPO;
import com.zhangb.family.doctor.basedata.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.common.constants.ReimbConstants;
import com.zhangb.family.doctor.operate.service.IReimbIllnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by z9104 on 2020/9/26.
 */
@Service
public class ReimbIllnessServiceImpl implements IReimbIllnessService {

    @Autowired
    private ICxnhRemoteService remoteService;


    @Override
    public ReimbIllnessPO getIllness(String illNessNo) throws Exception {
        ReimbIllnessPO where  = new ReimbIllnessPO();
        where.setIllnessNo(illNessNo);
        List<ReimbIllnessPO> resultList = BaseDao.select(where, ReimbIllnessPO.class);
        if (CollectionUtil.isEmpty(resultList)){
            return null;
        }
        ReimbIllnessPO reimbIllnessPO = resultList.get(0);
        if (StrUtil.isBlank(reimbIllnessPO.getRestDay())){
            reimbIllnessPO.setRestDay(ReimbConstants.DEFALT_ILLNESS_REST_DAY);
        }
        return reimbIllnessPO;
    }

    @Override
    public ReimbIllnessPO getRandomIllness(ReimbUserInfoPO user, String currIllnessNo) throws Exception {
        //获取用户接下来要报销的病例
        int age = Integer.valueOf(user.getAge());
        List<String> illnessNoList = CollectionUtil.newArrayList();
        if (age>40){
            illnessNoList.add("20129");
            illnessNoList.add("30221");
            illnessNoList.add("3449");
        }else{
            illnessNoList.add("20129");
            illnessNoList.add("30221");
        }
        int index = NumberUtil.generateRandomNumber(0,100,1)[0];
        String illnessNo =illnessNoList.get(index%illnessNoList.size());
        return getIllness(illnessNo);
    }

}
