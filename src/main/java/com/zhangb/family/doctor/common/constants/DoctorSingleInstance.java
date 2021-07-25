package com.zhangb.family.doctor.common.constants;

import java.util.HashSet;
import java.util.Set;

public enum DoctorSingleInstance {
    INSTANCE;

    private Set<String> chufangFileSet = new HashSet<>();

    /**
     * 判断今日是否同步过
     * @param filePath
     * @return
     */
    public synchronized boolean isNewChufang(String filePath){
        if (chufangFileSet.add(filePath)){
            //存在说明同步过
            return true;
        }
        return false;
    }
}
