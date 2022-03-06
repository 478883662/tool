package com.zhangb.family.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TryCalculate2ResultDTO {
    private String infcode;
    private String warn_msg;
    private String cainfo;
    private String err_msg;
    private String refmsg_time;
    private String signtype;
    private String respond_time;
    private String inf_refmsgid;
    private TryCalculate2OutputDTO output;
}
