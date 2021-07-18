package com.zhangb.family.doctor.service.impl;

import cn.hutool.core.date.DateUtil;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.entity.ReimbDealResult;
import com.zhangb.family.doctor.service.IReimbDealResultService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by z9104 on 2020/10/8.
 */
@Service
public class ReimbDealResultServiceImpl implements IReimbDealResultService {
    @Override
    public synchronized Integer saveDealResult(ReimbDealResult reimbDealResult) throws Exception {
        reimbDealResult.setDealResult("【"+DateUtil.today()+"】"+reimbDealResult.getDealResult());
        ReimbDealResult where  = new ReimbDealResult();
        where.setYlCard(reimbDealResult.getYlCard());
        where.setName(reimbDealResult.getName());
        Integer count = BaseDao.count(where);
        if (count>0){
            return  BaseDao.update(reimbDealResult,where);
        }
        return BaseDao.insert(reimbDealResult);
    }
}
