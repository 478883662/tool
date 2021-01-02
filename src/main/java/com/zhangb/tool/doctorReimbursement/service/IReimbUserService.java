package com.zhangb.tool.doctorReimbursement.service;

import com.zhangb.tool.doctorReimbursement.bo.ReimbUserBo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbUserInfo;
import com.zhangb.tool.doctorReimbursement.entity.ReimbYlCard;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/9/24.
 */
public interface IReimbUserService {

    /**
     * 通过用户姓名同步用户信息
     * @param name
     * @return
     */
    String syncUserByName(String name) throws Exception;

    /**
     * 通过医疗账号同步用户信息
     * @param ylCard
     * @return
     * @throws Exception
     */
    String syncUserByYlCard(String ylCard) throws Exception;

    /**
     * 查询所有的报销用户
     * @return
     */
    List<ReimbUserInfo> getAllUserInfo(ReimbUserInfo reimbUserInfo) throws SQLException, IllegalAccessException;

    /**
     * 查询所有的医疗账户
     * @return
     */
    List<ReimbYlCard> getAllYlCard() throws SQLException, IllegalAccessException;

    /**
     * 手工添加医疗账户信息
     * @param reimbYlCard
     */
    void addYlCard(ReimbYlCard reimbYlCard) throws SQLException, IllegalAccessException;

    /**
     * 查询所有人员
     * @return
     */
    List<ReimbUserBo> getUserBoList() throws SQLException;

}
