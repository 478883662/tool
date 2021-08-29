package com.zhangb.family.doctor.service;

import com.github.pagehelper.Page;
import com.zhangb.family.doctor.dto.YlCardDTO;
import com.zhangb.family.doctor.dto.ValueDTO;
import com.zhangb.family.doctor.entity.ReimbYlCard;

import java.util.List;

/**
 * Created by z9104 on 2021/5/5.
 */
public interface IReimbYlCardService {


    /**
     * 查询所有的医疗账户
     * @return
     */
    Page<ReimbYlCard> getYlCardByPage(YlCardDTO ylCardDTO);

    /**
     * 查询所有的医疗账户
     * @return
     */
    List<ReimbYlCard> getAllYlCard() throws Exception;

    /**
     * 新增一个医疗账户信息
     * @param reimbYlCard
     */
    void addYlCard(ReimbYlCard reimbYlCard) throws Exception;

    /**
     * 查询所有的医疗账号集合
     * @return
     */
    List<ValueDTO> getYlCardNoList();

    Boolean deleteYlCard(String ylCard);
}
