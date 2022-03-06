package com.zhangb.family.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorV2ReqHeaderDTO {
    /**接口编号*/
    private String infno;
    /**唯一：fixmedins_code+时间戳*/
    private String msgid;
    private String mdtrtarea_admvs = "430621";
    private String insuplc_admdvs ="430621";
    private String recer_sys_code ="HIS";
    private String dev_no ="1";
    private String dev_safe_info="1";
    private String cainfo="";
    private String signtype="SM3";
    private String infver="V1.0";
    private String opter_type="1";
    private String opter="H43062100453-03";
    private String opter_name="刘庆团";
    /**调用接口时的系统时间*/
    private String inf_time;
    private String fixmedins_code="H43062100453";
    private String fixmedins_name="荣家湾镇城北居委会卫生室";
    private String sign_no="278251820";
    private Object input;

}
