package com.oneday.common.util;

import java.security.MessageDigest;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2016/9/13 17:58
 */
public class MD5Util {
    /**
     * MD5加密 生成32位md5码
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     * @throws Exception
     */
    public static String md5Encode32(String inStr) {
        MessageDigest md5 = null;
        StringBuffer hexValue = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);

            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return hexValue.toString();
    }
}
