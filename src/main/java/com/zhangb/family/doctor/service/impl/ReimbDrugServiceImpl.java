package com.zhangb.family.doctor.service.impl;

import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.entity.ReimbDrug;
import com.zhangb.family.doctor.service.IReimbDrugService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by z9104 on 2020/10/1.
 */
@Service
public class ReimbDrugServiceImpl implements IReimbDrugService {

    @Override
    public Integer saveDrug(ReimbDrug reimbDrug) throws Exception {
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
