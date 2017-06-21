package com.oneday.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户展示信息
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/8 16:08
 */
public class UserDisplay implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Long id;
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
     * 性别描述
     */
    protected String sexDisplay;

    /**
     * 年龄
     */
    protected Integer age;

    /**
     * 年龄
     */
    protected String ageDisplay;

    /**
     *
     * 身高 cm
     */
    protected Integer height;

    /**
     * 身高文字描述
     */
    protected String heightDisplay;

    /**
     * 学历
     */
    protected Integer education;
    /**
     * 学历文字描述
     */
    protected String educationDisplay;

    /**
     * 行业
     */
    protected String industry;

    /**
     * 收入
     */
    protected Integer income;

    /**
     * 收入文字描述
     */
    protected String incomeDisplay;

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
    protected Integer marriage;

    /**
     * 婚姻状态文字描述
     */
    protected String marriageDisplay;

    /**
     * 生日
     */
    protected String birth;

    /**
     * 城市编码
     */
    protected String city;
    /**
     * 省份代码
     */
    protected String provinceCode;
    /**
     * 省份名称
     */
    protected String province;
    /**
     * 城市代码
     */
    protected String cityCode;

    /**
     * 体重
     */
    protected Integer weight;
    /**
     * 体重文字描述
     */
    protected String weightDisplay;

    protected String requirement;

    protected List<String> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getSexDisplay() {
        return sexDisplay;
    }

    public void setSexDisplay(String sexDisplay) {
        this.sexDisplay = sexDisplay;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAgeDisplay() {
        return ageDisplay;
    }

    public void setAgeDisplay(String ageDisplay) {
        this.ageDisplay = ageDisplay;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getHeightDisplay() {
        return heightDisplay;
    }

    public void setHeightDisplay(String heightDisplay) {
        this.heightDisplay = heightDisplay;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getEducationDisplay() {
        return educationDisplay;
    }

    public void setEducationDisplay(String educationDisplay) {
        this.educationDisplay = educationDisplay;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getIncomeDisplay() {
        return incomeDisplay;
    }

    public void setIncomeDisplay(String incomeDisplay) {
        this.incomeDisplay = incomeDisplay;
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

    public Integer getMarriage() {
        return marriage;
    }

    public void setMarriage(Integer marriage) {
        this.marriage = marriage;
    }

    public String getMarriageDisplay() {
        return marriageDisplay;
    }

    public void setMarriageDisplay(String marriageDisplay) {
        this.marriageDisplay = marriageDisplay;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getWeightDisplay() {
        return weightDisplay;
    }

    public void setWeightDisplay(String weightDisplay) {
        this.weightDisplay = weightDisplay;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}
