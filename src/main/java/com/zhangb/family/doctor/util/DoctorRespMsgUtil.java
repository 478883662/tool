package com.zhangb.family.doctor.util;

import com.zhangb.family.common.constants.GlobalConstants;
import com.zhangb.family.doctor.bo.DoctorReimbResultBo;

import java.util.List;

public class DoctorRespMsgUtil {

    public static String getHtmlStr(List<DoctorReimbResultBo> resultList){
        StringBuilder stringBuilder = new StringBuilder();
        for (DoctorReimbResultBo doctorReimbResultBo : resultList){
            stringBuilder.append("<span>")
                    .append(doctorReimbResultBo.getName())
                    .append(doctorReimbResultBo.getErrMsg())
                    .append("<span/>")
                    .append(GlobalConstants.NEXT_LINE);
        }
        return stringBuilder.toString();
    }
}
