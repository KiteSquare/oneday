package com.oneday.constant;

/**
 * 年收入水平枚举
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/8 16:37
 */
public enum IncomeEnum {
    ONE (1, "3万以下"),
    TWO (2, "3万-5万"),
    THREE (3, "5万-10万"),
    FOUR (4, "10万-20万"),
    FIVE (5, "20万-30万"),
    SIX (6, "30万-50万"),
    SEVEN (7, "50万-80万"),
    EIGHT (8, "80万-200万"),
    NINE (9, "200万以上");
    private Integer index;
    private String desc;

    private IncomeEnum(Integer index, String desc) {
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
        for (IncomeEnum incomeEnum: IncomeEnum.values()) {
            if (incomeEnum.getIndex().equals(index)) {
                return incomeEnum.getDesc();
            }
        }
        return null;
    }
}
