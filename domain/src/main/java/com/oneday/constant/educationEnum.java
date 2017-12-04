package com.oneday.constant;

/**
 * 学历
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2017/2/8 16:12
 */
public  enum  EducationEnum {
    OTHER(0,"小学以下"),
    PRIMARYSCHOOL(1,"小学"),
    MIDDLESCHOOL(2, "初中"),
    HIGHSCHOOL(3, "高中"),
    SPECIALSECONDARYSCHOOL(4, "中专"),
    COLLEGE(5, "大专"),
    BACHELOR(6, "本科"),
    MASTER(7,"硕士"),
    DOCTOR(8, "博士");

    private Integer index;
    private String desc;

    private EducationEnum(Integer index, String desc) {
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
        for (EducationEnum educationEnum: EducationEnum.values()) {
            if (educationEnum.getIndex().equals(index)) {
                return educationEnum.getDesc();
            }
        }
        return null;
    }
}
