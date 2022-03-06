package com.zhangb.family.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TryCalculate4DTO {

    private String infno;
    private String msgid;
    private String mdtrtarea_admvs;
    private String insuplc_admdvs;
    private String recer_sys_code;
    private String dev_no;
    private String dev_safe_info;
    private String cainfo;
    private String signtype;
    private String infver;
    private String opter_type;
    private String opter;
    private String opter_name;
    private String inf_time;
    private String fixmedins_code;
    private String fixmedins_name;
    private String sign_no;
    private TryCalculate2InputDTO input;

}
