package com.zhangb.family.doctor.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangb.family.common.dao.BaseDao;
import com.zhangb.family.doctor.dao.ReimbYlCardDao;
import com.zhangb.family.doctor.dto.YlCardDTO;
import com.zhangb.family.doctor.dto.ValueDTO;
import com.zhangb.family.doctor.entity.ReimbYlCard;
import com.zhangb.family.doctor.remote.service.ICxnhRemoteService;
import com.zhangb.family.doctor.service.IReimbYlCardService;
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
    public Page<ReimbYlCard> getYlCardByPage(YlCardDTO ylCardDTO) {
        //设置分页参数到threadLocal中
        PageHelper.startPage(ylCardDTO.getPageInfo());
        Page<ReimbYlCard> resultList = (Page<ReimbYlCard>)reimbYlCardDao.getAllYlCard(ylCardDTO);
        return resultList;
    }

    @Override
    public List<ReimbYlCard> getAllYlCard() throws Exception {
        return BaseDao.select(new ReimbYlCard(),ReimbYlCard.class);
    }

    @Override
    public void addYlCard(ReimbYlCard reimbYlCard) throws Exception {
        ReimbYlCard where = new ReimbYlCard();
        where.setYlCard(reimbYlCard.getYlCard());
        int count = BaseDao.count(where);
        reimbYlCard.setCreatedDate(new Date());
        if(count>0){
            BaseDao.updateForValue(reimbYlCard,where);
        }else{
            BaseDao.insert(reimbYlCard);
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
