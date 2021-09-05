package com.zhangb.family.doctor.operate.service.impl;

import cn.hutool.core.date.DateUtil;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.basedata.entity.ReimbDealResultPO;
import com.zhangb.family.doctor.operate.service.IReimbDealResultService;
import org.springframework.stereotype.Service;

/**
 * Created by z9104 on 2020/10/8.
 */
@Service
public class ReimbDealResultServiceImpl implements IReimbDealResultService {
    @Override
    public synchronized Integer saveDealResult(ReimbDealResultPO reimbDealResultPO) throws Exception {
        reimbDealResultPO.setDealResult("【"+DateUtil.today()+"】"+ reimbDealResultPO.getDealResult());
        ReimbDealResultPO where  = new ReimbDealResultPO();
        where.setYlCard(reimbDealResultPO.getYlCard());
        where.setName(reimbDealResultPO.getName());
        Integer count = BaseDao.count(where);
        if (count>0){
            return  BaseDao.update(reimbDealResultPO,where);
        }
        return BaseDao.insert(reimbDealResultPO);
    }
}
