package com.oneday.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 对称加密
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/6/13 14:55
 */
public class AESEncrypter {
    /**
     * 加密
     * @param content
     * @param strKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String content,String strKey ) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return  encrypted;
    }

    /**
     * 解密
     * @param strKey
     * @param content
     * @return
     * @throws Exception
     */
    public static String decrypt(byte[] content,String strKey ) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(content);
        String originalString = new String(original);
        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

        return skeySpec;
    }
    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return base64Code.isEmpty() ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }
    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(encrypt(content, encryptKey));
    }
    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return encryptStr.isEmpty() ? null : decrypt(base64Decode(encryptStr), decryptKey);
    }
    public static void main(String[] args) throws Exception {
        long st = System.currentTimeMillis();
        System.out.println("start：" + st);

        for (int i=0; i< 1000000; i++) {
            String test = "{\"code\":\"0\",\"message\":\"成功!\",\"data\":{\"id\":3,\"name\":\"呵呵哒昵称\",\"signature\":\"世界到底有多大，我到底走多远\",\"phone\":\"18244273481\",\"sex\":1,\"sexDisplay\":\"女\",\"age\":29,\"ageDisplay\":\"29岁\",\"height\":168,\"heightDisplay\":\"168cm\",\"education\":5,\"educationDisplay\":\"大专\",\"industry\":null,\"income\":3,\"incomeDisplay\":\"5万-10万\",\"head\":\"http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg\",\"detail\":\"度假酒店开始看拒绝度假酒店,我重来都不相信命运，命运是你给的？不是你给的？命运在自己手中，生活在自己手中\",\"distance\":0.0,\"marriage\":1,\"marriageDisplay\":\"未婚\",\"birth\":\"1988-01-01\",\"city\":\"石家庄市\",\"provinceCode\":\"130000\",\"province\":\"河北省\",\"cityCode\":\"130100\",\"weight\":60,\"weightDisplay\":\"60kg\"}}";
//            System.out.println("加密前：" + test);
            test += i;
            String key = "17e393859e7241bc23d43269d2408615e157e5235204d301";
//            System.out.println("密钥：" + key);

            String encrypt = aesEncrypt(test, key);
//            System.out.println("加密后：" + encrypt);

            String decrypt = aesDecrypt(encrypt, key);
//            System.out.println("解密后：" + decrypt);
        }
        long et = System.currentTimeMillis();
        System.out.println("et：" + et);
        System.out.println("all cost：" + (et-st)/1000 +"s");

        System.out.println("per cost：" + (float)(et-st)/1000000 +"ms");
    }
}
