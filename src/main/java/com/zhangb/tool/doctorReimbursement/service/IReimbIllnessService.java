package com.zhangb.tool.doctorReimbursement.service;

import com.zhangb.tool.doctorReimbursement.bo.ReimbDrugBo;
import com.zhangb.tool.doctorReimbursement.bo.ReimbIllnessDrugBo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbIllness;
import com.zhangb.tool.doctorReimbursement.entity.ReimbUserInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/9/26.
 */
public interface IReimbIllnessService {
    /**
     * 同步所有的病例(只增量同步)
     */
    void syncIllnessFromRecord() throws SQLException, IllegalAccessException;

    /**
     * 获取病例及用药信息
     * @param ylLocationNo 医疗机构编码
     * @param bizId 业务id
     * @return
     */
    List<ReimbDrugBo> getDrugByIllness(String ylLocationNo, String bizId) throws Exception;

    /**
     * 从本地库里获取病例信息
     * @param illNessNo
     * @return
     */
    ReimbIllness getIllness(String illNessNo) throws SQLException, IllegalAccessException;

    /**
     * 随机获取一种病
     * @param user
     * @param currIllnessNo
     * @return
     */
    ReimbIllness getRandomIllness(ReimbUserInfo user,String currIllnessNo) throws SQLException, IllegalAccessException;
}
