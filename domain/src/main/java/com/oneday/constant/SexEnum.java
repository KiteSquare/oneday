package com.oneday.constant;

/**
 * 追求者状态
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 14:52
 */
public enum SexEnum {
    MAN(0,"男"),
    FEMALE(1,"女");

    private Integer sex;
    private String desc;

    private SexEnum(Integer sex, String desc) {
        this.sex = sex;
        this.desc = desc;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }
    public static String getSexDesc(Integer sex) {
        if (sex == null) return null;
        if ( 0 == sex)  {
            return SexEnum.MAN.getDesc();
        } else if (1 == sex) {
            return SexEnum.FEMALE.getDesc();
        }
        return null;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static boolean isAvailable(Integer sex) {
        return  sex == 0 || sex == 1;
    }
    public static boolean isMale(Integer sex) {
        return MAN.getSex().equals(sex);
    }

    public static boolean isFemale(Integer sex) {
        return FEMALE.getSex().equals(sex);
    }
}
