package com.zhangb.tool.doctorReimbursement.common.enums;

import lombok.Getter;

/**
 * Created by z9104 on 2020/10/1.
 */
public enum ReimbSyncTypeEnum {

    YL_CARD_SYNC("YL_CARD_SYNC")
    ,REIMB_RECORD("REIMB_RECORD")
    ,SYNC_ALL("SYNC_ALL")
    ;

    ReimbSyncTypeEnum(String type) {
        this.type = type;
    }

    @Getter
    private String type;

}
