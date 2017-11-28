package com.oneday.service.impl;

import java.util.Random;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/7/12 13:16
 */
public class Test {
    public static  void main(String[] s) {
//        Integer[] source = new Integer[100];
//        for (int i = 0; i<source.length; i++) source[i]=source.length;
//        Random random=new Random();
//        for (int j=0; j<50000;j++) {
//            for (int i = 0; i<source.length; i++) {
//                if (source[i] >0) {
//                    source[i]--;
//                    source[random.nextInt(source.length)]++;
//                }
//
//            }
//        }
//
////        for (int i = 0; i<source.length; i++) {
////            System.out.println(source[i]);
////
////        }
//        solve(0, source.length-1, source);
//        for (int i = 0; i<source.length; i++) {
//            System.out.println(source[i]);
//
//        }
//
//        int max = source[source.length-1];
//        Integer[] result = new Integer[max/10+1];
//        for (int i = 0; i<result.length; i++) {
//            result[i]=0;
//
//        }
//        for (int k = 0; k<source.length; k++) {
//            result[source[k]/10]++;
//        }
//        for (int i = 0; i<result.length; i++) {
//            System.out.println(result[i]);
//
//        }
        probability();
//        System.out.println("all:"+n);
    }
    public static int n=0;
    public static void solve(int i,int j, Integer[] source) {
        if (i>=j) return;
        int k1=i,k2=j, v=source[i],temp;
        while (k1<k2) {
            if (source[k1+1] < v) {
                source[k1] = source[k1+1];
                source[k1+1] = v;
                k1++;
                n++;
            } else {
                temp = source[k1+1];
                source[k1+1] = source[k2];
                source[k2] = temp;
                k2--;
                n++;
            }
        }

        solve(i,k2-1, source);
        solve(k2+1,j, source);
    }



    public static void ss() {
        float step = 0.5f;
        int result = 0;
        long st = System.currentTimeMillis();
        for (float a=28;a <= 42; a=a+step ) {
            for (float b=28;b<= 42; b=b+step ) {
                for (float c=28;c <= 42; c=c+step ) {
                    if ((a + b + c) <= 90 ) {
                        result ++;
                        System.out.println(a+"\t" + b + "\t" + c);
                    }
                }
            }
        }
        long et = System.currentTimeMillis();
        System.out.println("平均值小于等于30的穷举数为："+result);
        System.out.println("平均值小于等于30的概率为："+(float)result/(29*29*29));
        System.out.println("花费时间为："+(et-st)/1000 + "秒");
    }

    public static void probability() {
        int n = 100000, rand, x=0, y=0,z1=0,z2=0;
        Random random = new Random();
        random.nextInt(1);
        for (int j=0;j<10000;j++) {
            x = 0;
            y=0;
            for (int i=0; i< n; i++) {
                rand = random.nextInt(2);
                if (rand ==0) {
                    rand = random.nextInt(2);
                    if (rand == 0) {
                        if (random.nextInt(2) == 0) {
                            x++;
                        } else {
                            y++;
                        }

                    }
                }
            }
            if (x > y) {
                z1++;
            } else if (x < y) {
                z2++;
            }
        }
        System.out.println(String.format("x: %s, y: %s", x, y));
        System.out.println(z1+"\t" + z2);
    }


}
