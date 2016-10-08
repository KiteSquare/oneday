package com.oneday.constant;

/**
 * 接受者状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0  2016/9/7 15:03
 */
public enum ReceiverEnum {
    NOTHING(0,"无状态"),
    SINGLE(1,"单身"),
    CHOOSE(2,"待选择"),
    HOLD(3,"暂定"),
    SUCCESS(4,"确定");

    private Integer status;
    private String desc;

    private ReceiverEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
