package com.zhangb.family.doctor.operate.dao;

import com.zhangb.family.doctor.basedata.remote.dto.YlCardDTO;
import com.zhangb.family.doctor.basedata.remote.dto.ValueDTO;
import com.zhangb.family.doctor.basedata.entity.ReimbYlCardPO;
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
    List<ReimbYlCardPO> getAllYlCard(YlCardDTO ylCardDTO);

    /**
     * 查询所有医疗账号
     * @return
     */
    List<ValueDTO> getYlCardNoList();
}
