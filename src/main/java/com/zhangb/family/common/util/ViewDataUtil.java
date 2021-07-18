package com.zhangb.family.common.util;

import com.zhangb.family.common.module.ViewData;

/**
 * Created by z9104 on 2021/5/16.
 */
public class ViewDataUtil {

    public static ViewData success(){
        return new ViewData(ViewData.RespEnum.SUCCESS);
    }

    public static ViewData success(Object obj){
        ViewData viewData = new ViewData(ViewData.RespEnum.SUCCESS);
        viewData.setObj(obj);
        return viewData;
    }

    public static ViewData bizError(String msg){
        ViewData viewData = new ViewData(ViewData.RespEnum.BIZ_ERROR);
        viewData.setMsg(msg);
        return viewData;
    }

}
