package com.zhangb.family.doctor.operate.service;

import com.github.pagehelper.Page;
import com.zhangb.family.doctor.basedata.remote.dto.YlCardDTO;
import com.zhangb.family.doctor.basedata.remote.dto.ValueDTO;
import com.zhangb.family.doctor.basedata.entity.ReimbYlCardPO;

import java.util.List;

/**
 * Created by z9104 on 2021/5/5.
 */
public interface IReimbYlCardService {


    /**
     * 查询所有的医疗账户
     * @return
     */
    Page<ReimbYlCardPO> getYlCardByPage(YlCardDTO ylCardDTO);

    /**
     * 查询所有的医疗账户
     * @return
     */
    List<ReimbYlCardPO> getAllYlCard() throws Exception;

    /**
     * 新增一个医疗账户信息
     * @param reimbYlCardPO
     */
    void addYlCard(ReimbYlCardPO reimbYlCardPO) throws Exception;

    /**
     * 查询所有的医疗账号集合
     * @return
     */
    List<ValueDTO> getYlCardNoList();

    Boolean deleteYlCard(String ylCard);
}
