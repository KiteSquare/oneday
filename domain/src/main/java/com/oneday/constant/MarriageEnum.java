package com.oneday.constant;

/**
 * 婚姻状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/9 11:35
 */
public enum  MarriageEnum {
    UNMARRIED (1, "未婚"),
    MARRIED (2, "已婚"),
    DIVOCE (3, "离异");
    private Integer index;
    private String desc;

    private MarriageEnum(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public static String getStrByIndex(Integer index) {
        if (index == null) {
            return null;
        }
        for (MarriageEnum marriageEnum: MarriageEnum.values()) {
            if (marriageEnum.getIndex().equals(index)) {
                return marriageEnum.getDesc();
            }
        }
        return null;
    }
}
