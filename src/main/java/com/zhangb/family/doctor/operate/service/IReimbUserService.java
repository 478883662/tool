package com.zhangb.family.doctor.operate.service;


import com.github.pagehelper.Page;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.doctor.operate.bo.ReimbUserBo;
import com.zhangb.family.doctor.basedata.remote.dto.ReimbUserDTO;
import com.zhangb.family.doctor.basedata.remote.dto.ValueDTO;
import com.zhangb.family.doctor.basedata.entity.ReimbUserInfoPO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/9/24.
 */
public interface IReimbUserService {

    /**
     * 查询所有的报销用户
     * @return
     */
    List<ReimbUserInfoPO> getAllUserInfo(ReimbUserInfoPO reimbUserInfoPO) throws Exception;

    /**
     * 查询所有人员
     * @return
     */
    Page<ReimbUserBo> getUserListByPage(ReimbUserDTO reimbUserDTO) throws SQLException;

    /**
     * 批量保存到数据库
     * @param userInfoList
     */
    void addBatchUser(List<ReimbUserInfoPO> userInfoList) throws SQLException, BizException;

    /**
     * 逻辑删除用户
     * @param reimbUserDTO
     * @return
     */
    Boolean updateUserEnableFlag(ReimbUserDTO reimbUserDTO) throws SQLException, BizException;

    /**
     * 查询所有的用户
     * @return
     */
    List<ValueDTO> getNameList();

    /**
     * 查询所有户主
     * @return
     */
    List<ValueDTO> getMasterNameList();
}
