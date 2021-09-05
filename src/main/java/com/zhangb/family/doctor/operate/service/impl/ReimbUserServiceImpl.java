package com.zhangb.family.doctor.operate.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangb.family.common.constants.GlobalConstants;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.doctor.operate.bo.ReimbUserBo;
import com.zhangb.family.doctor.operate.dao.ReimbUserDao;
import com.zhangb.family.doctor.basedata.remote.dto.ReimbUserDTO;
import com.zhangb.family.doctor.basedata.remote.dto.ValueDTO;
import com.zhangb.family.doctor.basedata.entity.ReimbUserInfoPO;
import com.zhangb.family.doctor.operate.service.IReimbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/9/24.
 */
@Service("reimbUserServiceImpl")
public class ReimbUserServiceImpl implements IReimbUserService {

    @Autowired
    private ReimbUserDao reimbUserDao;

    @Override
    public List<ReimbUserInfoPO> getAllUserInfo(ReimbUserInfoPO reimbUserInfoPO) throws Exception {
        return BaseDao.select(reimbUserInfoPO, ReimbUserInfoPO.class);
    }


    @Override
    public Page<ReimbUserBo> getUserListByPage(ReimbUserDTO reimbUserDTO) throws SQLException {
        //设置分页参数到threadLocal中
        PageHelper.startPage(reimbUserDTO.getPageInfo());
        reimbUserDTO.setToday(DateUtil.today());
        reimbUserDTO.setYear(DateUtil.thisYear());
        Page<ReimbUserBo> resultList = (Page<ReimbUserBo>)reimbUserDao.getUserBoList(reimbUserDTO);
        return resultList;
    }

    @Override
    public void addBatchUser(List<ReimbUserInfoPO> userInfoList) throws SQLException, BizException {
        for(ReimbUserInfoPO reimbUserInfoPO :userInfoList){
//            先查询数量
            ReimbUserInfoPO where = new ReimbUserInfoPO();
            where.setIdCard(reimbUserInfoPO.getIdCard());
            int count = BaseDao.count(where);
            //数据库里存在就只更新值
            if(count>0){
                BaseDao.updateForValue(reimbUserInfoPO,where);
            }else{//不存在就插入一条新记录
                reimbUserInfoPO.setEnableFlag(GlobalConstants.ENABLE_FLAG_T);
                BaseDao.insert(reimbUserInfoPO);
            }
        }
    }

    @Override
    public Boolean updateUserEnableFlag(ReimbUserDTO reimbUserDTO) throws SQLException, BizException {
        ReimbUserInfoPO where = new ReimbUserInfoPO();
        where.setName(reimbUserDTO.getName());
        where.setYlCard(reimbUserDTO.getYlCard());

        ReimbUserInfoPO reimbUserInfoPO = new ReimbUserInfoPO();
        reimbUserInfoPO.setEnableFlag(reimbUserDTO.getEnableFlag());
        BaseDao.updateForValue(reimbUserInfoPO,where);
        return null;
    }

    @Override
    public List<ValueDTO> getNameList() {
        return reimbUserDao.getNameList();
    }

    @Override
    public List<ValueDTO> getMasterNameList() {
        return reimbUserDao.getMasterNameList();
    }


}
