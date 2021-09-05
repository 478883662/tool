package com.zhangb.family.doctor.operate.dao;

import com.zhangb.family.doctor.operate.bo.ReimbUserBo;
import com.zhangb.family.doctor.basedata.remote.dto.ReimbUserDTO;
import com.zhangb.family.doctor.basedata.remote.dto.ValueDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by z9104 on 2021/5/5.
 */
@Mapper
public interface ReimbUserDao {

    /**
     * 查询所有的报销人员
     * @param reimbUserDTO
     * @return
     */
    List<ReimbUserBo> getUserBoList(ReimbUserDTO reimbUserDTO);

    /**
     * 查询所有病人
     * @return
     */
    List<ValueDTO> getNameList();

    List<ValueDTO> getMasterNameList();
}
