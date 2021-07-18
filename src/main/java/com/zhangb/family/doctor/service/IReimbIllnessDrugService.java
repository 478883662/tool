package com.zhangb.family.doctor.service;


import com.zhangb.family.doctor.bo.ReimbDrugBo;
import com.zhangb.family.doctor.entity.ReimbIllnessDrug;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbIllnessDrugService {
    /**
     * 保存病例与药品的关系
     * @param reimbIllnessDrug
     * @return
     */
    Integer saveIllnessDrug(ReimbIllnessDrug reimbIllnessDrug) throws Exception;

    /**
     * 获取病例所有的用药情况
     * @param illnessNo
     * @return
     */
    List<ReimbDrugBo> getIllnessDrugList(String illnessNo) throws SQLException;
}
