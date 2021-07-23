package com.zhangb.family.common.service;

import com.zhangb.family.common.dto.FamilyFileDto;
import com.zhangb.family.common.entity.FamilyFile;

public interface FamilyFileService {

    /**
     * 添加文件到数据库
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
     * 根据外键查询对应的文件对象
     * @param familyFileDto
     * @return
     */
    FamilyFile getFamilyFile(FamilyFileDto familyFileDto);

    /**
     * 查询文件数量
     * @param familyFileDto
     * @return
     */
    Integer getFamilyFileCount(FamilyFileDto familyFileDto);
}
