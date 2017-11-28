package com.oneday.common.util;

import ch.hsr.geohash.GeoHash;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * geo hash 工具类
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/7/3 16:28
 */
public class GeocodeUtil {
    /**
     * 获取包括当前经纬度点的临近的9个geocode
     * @param lat
     * @param lon
     * @param number
     * @return
     */
    public static List<String> adjacentGeocodes(Double lat, Double lon, int number) {
        if (lat == null || lon == null) {
            return null;
        }
        if (number > 12) {
            number=12;
        }
        List<String> res = new ArrayList<>();
        GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lon, number);
        // 当前
        res.add(geoHash.toBase32());
        // N, NE, E, SE, S, SW, W, NW
        GeoHash[] adjacent = geoHash.getAdjacent();
        for (GeoHash hash : adjacent) {
            res.add(hash.toBase32());
        }
        return res;
    }
    public static void main(String[] s) {
        // 移动设备经纬度
        double lon1 = 116.3125333347639, lat1 = 39.98355521792821;
        // 商户经纬度
        double lon2 = 116.312535, lat2 = 39.984733;

        SpatialContext geo = SpatialContext.GEO;
        double distance = geo.calcDistance(geo.makePoint(lon1, lat1), geo.makePoint(lon2, lat2))
                * DistanceUtils.DEG_TO_KM;
        if (distance>1) {
            System.out.println((int) distance+"km");// KM
        } else {
            System.out.println((int)(distance*1000)+"m");// KM
        }

    }
}
