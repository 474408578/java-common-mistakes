package com.xschen.commonmistakes._08_equals.intandstringequal;

/**
 * @author xschen
 * @date 2021/9/23 9:53
 */
public enum StatusEnum {

    CREATED(1000, "已创建"),
    PAID(1000, "已支付"),
    DELIVERED(1000, "已送达"),
    FINISHED(1000, "已完成");

    private final Integer status;
    private final String desc;

    StatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
