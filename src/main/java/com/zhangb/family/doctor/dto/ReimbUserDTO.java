package com.zhangb.family.doctor.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * Created by z9104 on 2020/10/4.
 */
@Data
public class ReimbUserDTO {

    private PageInfo pageInfo;

    private String ylCard = "";
    private String name = "";
    private String today;
    private Integer year;
    /**是否有效*/
    private String enableFlag;

}
