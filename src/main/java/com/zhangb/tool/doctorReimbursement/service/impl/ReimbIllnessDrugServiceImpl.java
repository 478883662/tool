package com.zhangb.tool.doctorReimbursement.service.impl;

import cn.hutool.db.Db;
import com.zhangb.tool.common.dao.BaseDao;
import com.zhangb.tool.doctorReimbursement.bo.ReimbDrugBo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbDealRecord;
import com.zhangb.tool.doctorReimbursement.entity.ReimbIllnessDrug;
import com.zhangb.tool.doctorReimbursement.service.IReimbIllnessDrugService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
@Service
public class ReimbIllnessDrugServiceImpl implements IReimbIllnessDrugService {

    @Override
    public Integer saveIllnessDrug(ReimbIllnessDrug reimbIllnessDrug) throws SQLException, IllegalAccessException {
        ReimbIllnessDrug where = new ReimbIllnessDrug();
        where.setIllnessNo(reimbIllnessDrug.getIllnessNo());
        where.setDrugNo(reimbIllnessDrug.getDrugNo());
        Integer count = BaseDao.count(where);
        if (count>0){
            BaseDao.update(reimbIllnessDrug,where);
        }else{
            BaseDao.insert(reimbIllnessDrug);
        }
        return null;
    }

    @Override
    public List<ReimbDrugBo> getIllnessDrugList(String illnessNo) throws SQLException {
        List<ReimbDrugBo> reimbDrugBoList = Db.use().query("select t3.drug_no,t3.drug_name,t3.price,t2.drug_num,t3.one_type,t3.drug_seq\n" +
                        " from tool_illness_t t1,tool_illness_drug_t t2,tool_drug_t t3\n" +
                        "where t1.illness_no = t2.illness_no and t2.drug_no = t3.drug_no\n" +
                        "and t1.illness_no=?" ,
                ReimbDrugBo.class,illnessNo);
        return reimbDrugBoList;
    }
}
