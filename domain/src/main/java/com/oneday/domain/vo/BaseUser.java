package com.oneday.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/13 17:30
 */
public class BaseUser implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Long id;
    protected String name;
    protected String phone;
    protected String head;
    protected Integer sex;
    protected Date create;
    protected Date expired;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
