package com.zhangb.family.doctor.service;


import com.github.pagehelper.Page;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.doctor.bo.ReimbUserBo;
import com.zhangb.family.doctor.dto.ReimbUserDTO;
import com.zhangb.family.doctor.entity.ReimbUserInfo;

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
    List<ReimbUserInfo> getAllUserInfo(ReimbUserInfo reimbUserInfo) throws Exception;

    /**
     * 查询所有人员
     * @return
     */
    Page<ReimbUserBo> getUserListByPage(ReimbUserDTO reimbUserDTO) throws SQLException;

    /**
     * 批量保存到数据库
     * @param userInfoList
     */
    void addBatchUser(List<ReimbUserInfo> userInfoList) throws SQLException, BizException;

    /**
     * 逻辑删除用户
     * @param reimbUserDTO
     * @return
     */
    Boolean deleteUser(ReimbUserDTO reimbUserDTO) throws SQLException, BizException;
}
