package com.zhangb.family.common.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by z9104 on 2021/7/4.
 */
@Data
public class MyImage {
    private MultipartFile image;
}
