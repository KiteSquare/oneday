package com.oneday.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * 由出生日期获得年龄
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }
}
