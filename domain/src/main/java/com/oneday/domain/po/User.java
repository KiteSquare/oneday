package com.oneday.domain.po;

/**
 * 用户域
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/6 16:58
 */


import com.oneday.constant.SexEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * CREATE TABLE `user` (
 `id` int(20) NOT NULL COMMENT 'id',
 `name` varchar(35) DEFAULT NULL COMMENT '名称',
 `phone` varchar(15) NOT NULL COMMENT '电话',
 `idcard` varchar(18) DEFAULT NULL COMMENT '身份证号',
 `signature` varchar(100) DEFAULT NULL COMMENT '签名',
 `city` varchar(35) DEFAULT NULL,
 `lon` decimal(20,17) DEFAULT NULL COMMENT '经度，longitude',
 `lat` decimal(20,17) DEFAULT NULL COMMENT '维度，latitude',
 `head` varchar(256) DEFAULT NULL COMMENT '头像图片',
 `detail` varchar(8192) DEFAULT NULL COMMENT '个人简介详情',
 `create` datetime DEFAULT NULL COMMENT '创建日期',
 `update` datetime DEFAULT NULL COMMENT '更新日期',
 `yn` tinyint(1) DEFAULT '0' COMMENT '0 有效 1 无效',
 PRIMARY KEY (`id`),
 UNIQUE KEY `index_phone` (`phone`) USING BTREE,
 KEY `index_idcard` (`idcard`) USING BTREE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Long id;
    /**
     * 昵称
     */
    protected String name;
    /**
     * 密码
     */
    protected String password;
    /**
     * 手机号码
     */
    protected String phone;
    /**
     * 身份证号码
     */
    protected String idcard;

    /**
     * 性别，0 男， 1 女
     */
    protected Integer sex;
    /**
     * 出生日期
     */
    protected Date birth;

    /**
     *
     * 身高 cm
     */
    protected Integer height;

    /**
     * 婚姻状况
     * 参考 MarriageEnum
     */
    protected Integer marriage;

    /**
     * 个人签名
     */
    protected String signature;
    /**
     * 初始城市
     */
    protected String city;
    /**
     * 初始纬度
     */
    protected Double lon;
    /**
     * 初始经度
     */
    protected Double lat;

    /**
     * 行业
     */
    protected String industry;

    /**
     * 学历 0 小学以下，1 小学， 2 初中， 3 高中，4 中专，5 大专，6 本科，7 硕士，8 博士
     * 参见EducationEnum
     */
    protected Integer education;

    /**
     * 收入范围  参见IncomeEnum
     */
    protected Integer income;

    /**
     * 头像图片地址
     */
    protected String head;
    /**
     * 个人介绍
     */
    protected String detail;

    /**
     * 当前状态
     */
    protected Integer status;
    /**
     * 数量，当为男性时候表示发送(send)的数量，当为女性的时候表示接收(receive)的数量
     */
    protected Integer count;
    /**
     * 创建时间
     */
    protected Date create;
    /**
     * 更新时间
     */
    protected Date update;

    protected Integer yn = 0;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isMale() {
        return SexEnum.MAN.getSex().equals(sex);
    }

    public boolean isFemale() {
        return SexEnum.FEMALE.getSex().equals(sex);
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Integer getMarriage() {
        return marriage;
    }

    public void setMarriage(Integer marriage) {
        this.marriage = marriage;
    }
}
