package com.oneday.domain.vo;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/13 18:28
 */
public class BaseRequest {
    private String accessToken;
    protected Double lat;
    protected Double lon;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
