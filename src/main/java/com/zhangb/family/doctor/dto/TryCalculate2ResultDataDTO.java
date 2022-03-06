package com.zhangb.family.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TryCalculate2ResultDataDTO {

    private String psn_no;
    private String mdtrt_id;
    private String exp_content;
    private String ipt_otp_no;
}
