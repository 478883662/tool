package com.zhangb.family.doctor.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * Created by z9104 on 2021/5/16.
 */
@Data
public class YlCardDTO {
    private PageInfo pageInfo;

    private String ylCard;

    private String masterName;
}
