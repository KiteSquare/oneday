package com.oneday.domain.po;

/**
 * 追求者-接受者关系
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0 2016/9/7 15:35
 */
public class HunterReceiver {
    protected Long id;
    protected Long hunter;
    protected Long receiver;
    protected Integer status;
    protected Long create;
    protected Long update;
    protected Integer yn = 0;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHunter() {
        return hunter;
    }

    public void setHunter(Long hunter) {
        this.hunter = hunter;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreate() {
        return create;
    }

    public void setCreate(Long create) {
        this.create = create;
    }

    public Long getUpdate() {
        return update;
    }

    public void setUpdate(Long update) {
        this.update = update;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
