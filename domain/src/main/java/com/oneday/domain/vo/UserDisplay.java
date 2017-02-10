package com.oneday.domain.vo;

/**
 * 用户展示信息
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/8 16:08
 */
public class UserDisplay {
    /**
     * 昵称
     */
    protected String name;
    /**
     * 个人签名
     */
    protected String signature;
    /**
     * 手机号码
     */
    protected String phone;

    /**
     * 性别，0 男， 1 女
     */
    protected Integer sex;

    /**
     * 年龄
     */
    protected Integer age;

    /**
     *
     * 身高 cm
     */
    protected Integer height;

    /**
     * 学历
     */
    protected String education;

    /**
     * 行业
     */
    protected String industry;

    /**
     * 收入
     */
    protected String income;

    /**
     * 头像图片地址
     */
    protected String head;

    /**
     * 个人介绍
     */
    protected String detail;

    /**
     * 距离
     */
    protected Double distance;

    /**
     * 婚姻状态
     */
    protected String marriage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }
}
