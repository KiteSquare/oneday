package com.oneday.service.state;

import java.util.Arrays;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/2/20 14:46
 */
public class Test {
    public static void main(String[] s) {
        int n=10000000;
        int range = 2;
        java.util.Random random=new java.util.Random();
        java.util.Random random1=new java.util.Random();
        int k=0;
        int count = 0;
        int[] counts = new int[range];
        int[] counts1 = new int[range];
        int[] counts2 = new int[50];
        for (int i=0 ; i<n; i++) {
            k = random.nextInt(range);
            if (counts1[k] > 0){
                counts1[k] ++;
            } else {
                for (int j=0 ; j<range; j++) {
                    counts2[counts1[j]] ++;
                    if (counts1[j] > 0 && counts1[j]>counts[j]) {
                        counts[j] = counts1[j];
                        break;
                    }
                }
                counts1 = new int[range];
                counts1[k] ++;
            }

            if (k == 0) {
                count++;
            }
        }
        System.out.println("命中3的次数"+count);

        System.out.println("每个数字连续出现的最高次数"+Arrays.toString(counts));
        System.out.println("连续出现同一个数字的次数与频率"+Arrays.toString(counts2));
//        count = 0;
//        for (int i=0 ; i<n; i++) {
//            k = random.nextInt(10);
//            int m = random1.nextInt(10);
//            if (k == m) {
//                count++;
//            }
//        }
//        System.out.println(count);
    }
}
