package com.zhangb.family.doctor.dao;

import com.zhangb.family.doctor.dto.YlCardDTO;
import com.zhangb.family.doctor.entity.ReimbYlCard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by z9104 on 2021/5/5.
 */
@Mapper
public interface ReimbYlCardDao {

    /**
     * 查询所有的医疗账户
     * @param ylCardDTO
     * @return
     */
    List<ReimbYlCard> getAllYlCard(YlCardDTO ylCardDTO);
}
