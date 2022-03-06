package com.zhangb.family.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TryCalculate2DataDTO {

    private String psn_no;
    private String insutype;
    private String begntime;
    private String mdtrt_cert_type;
    private String mdtrt_cert_no;
    private String ipt_otp_no;
    private String atddr_no;
    private String dr_name;
    private String dept_code;
    private String dept_name;
    private String caty;

}
