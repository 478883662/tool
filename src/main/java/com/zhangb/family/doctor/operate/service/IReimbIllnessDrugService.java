package com.zhangb.family.doctor.operate.service;


import com.zhangb.family.doctor.operate.bo.ReimbDrugBo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbIllnessDrugService {

    /**
     * 获取病例所有的用药情况
     * @param illnessNo
     * @return
     */
    List<ReimbDrugBo> getIllnessDrugList(String illnessNo) throws SQLException;
}
