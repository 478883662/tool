package com.zhangb.tool.doctorReimbursement.service.impl;

import com.zhangb.tool.common.dao.BaseDao;
import com.zhangb.tool.doctorReimbursement.entity.ReimbDealResult;
import com.zhangb.tool.doctorReimbursement.service.IReimbDealResultService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by z9104 on 2020/10/8.
 */
@Service
public class ReimbDealResultServiceImpl implements IReimbDealResultService {
    @Override
    public synchronized Integer saveDealResult(ReimbDealResult reimbDealResult) throws SQLException, IllegalAccessException {
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
