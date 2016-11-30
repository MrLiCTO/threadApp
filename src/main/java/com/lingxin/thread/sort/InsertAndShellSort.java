package com.lingxin.thread.sort;

/**
 * Created by Administrator on 2016/11/29.
 */
public class InsertAndShellSort {
    public static void insertSort(int[] arr){
        for (int i = 1; i <arr.length; i++) {
            int key=arr[i];
            int j=i-1;
            while(j>=0&&arr[j]>key){
                arr[j+1]=arr[j];
                j--;
            }
            arr[j+1]=key;
        }
    }

    public static void shellSort(int[] arr){
        int h=1;
        while(h<=arr.length/3){
            h=h*3+1;
        }
        while (h>0){
            for (int i = h; i < arr.length; i++) {
                if(arr[i]<arr[i-h]){
                    int tem=arr[i];
                    int j=i-h;
                    while (j>=0&&arr[j]>tem){
                        arr[j+h]=arr[j];
                        j-=h;
                    }
                    arr[j+h]=tem;
                }
            }
            h=(h-1)/3;
        }
    }

}
