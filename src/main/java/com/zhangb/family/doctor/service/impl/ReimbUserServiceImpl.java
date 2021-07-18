package com.zhangb.family.doctor.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangb.family.common.constants.GlobalConstants;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.common.exception.BizException;
import com.zhangb.family.doctor.bo.ReimbUserBo;
import com.zhangb.family.doctor.dao.ReimbUserDao;
import com.zhangb.family.doctor.dto.ReimbUserDTO;
import com.zhangb.family.doctor.entity.ReimbUserInfo;
import com.zhangb.family.doctor.service.IReimbUserService;
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
    public List<ReimbUserInfo> getAllUserInfo(ReimbUserInfo reimbUserInfo) throws Exception {
        return BaseDao.select(reimbUserInfo,ReimbUserInfo.class);
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
    public void addBatchUser(List<ReimbUserInfo> userInfoList) throws SQLException, BizException {
        for(ReimbUserInfo reimbUserInfo:userInfoList){
//            先查询数量
            ReimbUserInfo where = new ReimbUserInfo();
            where.setIdCard(reimbUserInfo.getIdCard());
            int count = BaseDao.count(where);
            //数据库里存在就只更新值
            if(count>0){
                BaseDao.update(reimbUserInfo,where);
            }else{//不存在就插入一条新记录
                BaseDao.insert(reimbUserInfo);
            }
        }
    }

    @Override
    public Boolean deleteUser(ReimbUserDTO reimbUserDTO) throws SQLException, BizException {
        ReimbUserInfo where = new ReimbUserInfo();
        where.setName(reimbUserDTO.getName());
        where.setYlCard(reimbUserDTO.getYlCard());

        ReimbUserInfo reimbUserInfo = new ReimbUserInfo();
        reimbUserInfo.setEnableFlag(GlobalConstants.ENABLE_FLAG_F);
        BaseDao.updateForValue(reimbUserInfo,where);
        return null;
    }


}
