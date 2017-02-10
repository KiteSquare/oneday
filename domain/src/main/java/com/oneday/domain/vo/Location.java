package com.oneday.domain.vo;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/1/19 15:43
 */
public class Location {
    /**
     * 初始纬度
     */
    protected Double lon;
    /**
     * 初始经度
     */
    protected Double lat;

    public Location(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
