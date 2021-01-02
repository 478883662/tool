package com.zhangb.tool.doctorReimbursement.common.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MyWebMvcConfigurer  implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/temp/ylCardPic/**").addResourceLocations("file:D:/temp/ylCardPic/");
    }
}
