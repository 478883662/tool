package com.zhangb.family.common.service.impl;

import com.zhangb.family.common.dao.FamilyFileDao;
import com.zhangb.family.common.dto.FamilyFileDto;
import com.zhangb.family.common.entity.FamilyFile;
import com.zhangb.family.common.service.FamilyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyFileServiceImpl implements FamilyFileService {

    @Autowired
    private FamilyFileDao familyFileDao;

    @Override
    public Integer addFile(FamilyFile familyFile) {
        return familyFileDao.addFile(familyFile);
    }

    @Override
    public Integer updateFile(FamilyFile familyFile) {
        return familyFileDao.updateFile(familyFile);
    }

    @Override
    public FamilyFile getFamilyFile(FamilyFileDto familyFileDto) {
        return familyFileDao.getFamilyFile(familyFileDto);
    }

    @Override
    public Integer getFamilyFileCount(FamilyFileDto familyFileDto) {
        return familyFileDao.getFamilyFileCount(familyFileDto);
    }
}
