package com.zhangb.family.doctor.operate.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.operate.dao.ReimbYlCardDao;
import com.zhangb.family.doctor.basedata.remote.dto.YlCardDTO;
import com.zhangb.family.doctor.basedata.remote.dto.ValueDTO;
import com.zhangb.family.doctor.basedata.entity.ReimbYlCardPO;
import com.zhangb.family.doctor.basedata.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.operate.service.IReimbYlCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 医疗账户业务类
 */
@Service
public class ReimbYlCardServiceImpl  implements IReimbYlCardService {

    @Autowired
    private ReimbYlCardDao reimbYlCardDao;
    @Autowired
    private ICxnhRemoteService remoteService;

    @Override
    public Page<ReimbYlCardPO> getYlCardByPage(YlCardDTO ylCardDTO) {
        //设置分页参数到threadLocal中
        PageHelper.startPage(ylCardDTO.getPageInfo());
        Page<ReimbYlCardPO> resultList = (Page<ReimbYlCardPO>)reimbYlCardDao.getAllYlCard(ylCardDTO);
        return resultList;
    }

    @Override
    public List<ReimbYlCardPO> getAllYlCard() throws Exception {
        return BaseDao.select(new ReimbYlCardPO(), ReimbYlCardPO.class);
    }

    @Override
    public void addYlCard(ReimbYlCardPO reimbYlCardPO) throws Exception {
        ReimbYlCardPO where = new ReimbYlCardPO();
        where.setYlCard(reimbYlCardPO.getYlCard());
        int count = BaseDao.count(where);
        reimbYlCardPO.setCreatedDate(new Date());
        if(count>0){
            BaseDao.updateForValue(reimbYlCardPO,where);
        }else{
            BaseDao.insert(reimbYlCardPO);
        }
    }

    @Override
    public List<ValueDTO> getYlCardNoList() {
        return reimbYlCardDao.getYlCardNoList();
    }

    @Override
    public Boolean deleteYlCard(String ylCard) {
        


        return true;
    }

}
