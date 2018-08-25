package com.study.nobest;

/**
 * Created by jianshengli on 2018/4/10.
 */

public class DataStruct {

    public static void main(String[] args) {

        order1();
    }
    public void order() {
        int[] arr = {1, 2};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                }

            }
        }
    }

    //先确定第一个的位置的值
    public static  void order1() {
        int[] arr = {1, 2,3};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                }

            }
        }
    }

}
