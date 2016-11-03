package com.oneday.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/11/3 18:11
 */
public class DateUtil {
    public static Date strToDate(String str, String format) {
        if (str == null ) {
            return null;
        }
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(str);
        } catch (ParseException e) {
           throw new RuntimeException(e);
        }
    }
}
