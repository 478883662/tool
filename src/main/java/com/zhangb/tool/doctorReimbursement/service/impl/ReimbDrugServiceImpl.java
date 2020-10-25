package com.zhangb.tool.doctorReimbursement.service.impl;

import com.zhangb.tool.common.dao.BaseDao;
import com.zhangb.tool.doctorReimbursement.entity.ReimbDrug;
import com.zhangb.tool.doctorReimbursement.entity.ReimbIllnessDrug;
import com.zhangb.tool.doctorReimbursement.service.IReimbDrugService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by z9104 on 2020/10/1.
 */
@Service
public class ReimbDrugServiceImpl implements IReimbDrugService {

    @Override
    public Integer saveDrug(ReimbDrug reimbDrug) throws SQLException, IllegalAccessException {
        ReimbDrug where = new ReimbDrug();
        where.setDrugNo(reimbDrug.getDrugNo());
        Integer count = BaseDao.count(where);
        if (count>0){
            BaseDao.update(reimbDrug,where);
        }else{
            BaseDao.insert(reimbDrug);
        }
        return null;
    }
}
