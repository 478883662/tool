package com.zhangb.family.common.dao;

import com.zhangb.family.common.dto.FamilyFileDto;
import com.zhangb.family.common.entity.FamilyFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FamilyFileDao {

    /**
     * 添加文件
     * @param familyFile
     * @return
     */
    Integer addFile(FamilyFile familyFile);
    /**
     * 更新文件
     * @param familyFile
     * @return
     */
    Integer updateFile(FamilyFile familyFile);

    /**
     * 查询文件
     * @param familyFileDto
     * @return
     */
    FamilyFile getFamilyFile(FamilyFileDto familyFileDto );

    /**
     * 查询文件数量
     * @param familyFileDto
     * @return
     */
    Integer getFamilyFileCount(FamilyFileDto familyFileDto);
}
