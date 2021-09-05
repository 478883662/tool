package com.zhangb.family.doctor.operate.service;


import com.zhangb.family.doctor.operate.bo.*;
import com.zhangb.family.doctor.common.enums.DoctorReimbResultEnum;
import com.zhangb.family.doctor.basedata.entity.ReimbPrintInfoPO;
import com.zhangb.family.doctor.basedata.entity.ReimbUserInfoPO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/10/1.
 */
public interface IReimbService {

    /**
     * 获取业务id
     * @return
     * @throws Exception
     */
    String getBizId() throws Exception;

    //1 业务id     2用户个人编码  3用户姓名 4医疗账户  5病例编码    6入院时间  7出院时间
    String bindBizId(String bizId, String selfNo,
                     String name, String ylCard, String illnessNo,
                     String inDate, String outDate) throws Exception;


    /**
     * 保存选择的用药
     * 业务id  1 药品编号  2药品名称  3药品细分类  4药品序号  5入院时间 6单价  7数量  8金额
     * @param bizId
     * @param drugNo
     * @param drugName
     * @param oneType
     * @param drugSet
     * @param inDate
     * @param price
     * @param num
     * @param money
     * @return
     * @throws Exception
     */
    String saveSelectDrug(String bizId, String drugNo, String drugName, String oneType,
                          String drugSet, String inDate, String price,
                          String num, String money) throws Exception;

    /**
     * 获取接下来要报销的病例
     * @param user
     * @return
     */
    ReimbIllnessBo getNextIllness(ReimbUserInfoPO user) throws Exception;

    /**
     * 报销
     * @param reimbIllnessBo
     * @return
     */
    DoctorReimbResultEnum reimb(ReimbIllnessBo reimbIllnessBo) throws Exception;

    /**
     * 获取打印信息
     * @param bizId 入参
     * @return
     */
    ReimbPrintBo getPrintInfo(String bizId) throws Exception;

    void savePrintInfo(ReimbPrintInfoPO reimbPrintInfoPO) throws Exception;

    List<ReimbUnPrintRecordBo> getAllUnPrintInfo(String state) throws SQLException;

    /**
     * 根据bizId获取打印对象
     * @param bizId
     * @return
     */
    ReimbUnPrintRecordBo getUnPrintInfo(String bizId, String state) throws SQLException;

    /**
     * 报销单人
     * @param ylCard
     * @param name
     * @return
     */
    List<DoctorReimbResultBo>  reimbForYlCardAndName(String ylCard, String name);

    /**
     * 查询指定日期的报销情况
     * @param today
     * @return
     */
    DoctorReimbDetailInfo getTodayReimbInfo(String today);
}
