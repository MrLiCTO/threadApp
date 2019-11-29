package com.lingxin.thread.dataStruct;

import java.util.Arrays;

public class BinHeap {
    private int[] array;
    private int size;

    public BinHeap(int capcity) {
        this.array = new int[capcity];
    }

    public BinHeap() {
        this(32);
    }

    public void offer(int key) {
        if (size >= array.length)
            resize();
        array[size++] = key;
        upAdjust();
    }

    public Integer poll() {
        if (size == 0)
            return null;
        int data = array[0];
        array[0] = array[--size];
        downAdjust();
        return data;
    }

    public void resize() {//扩容
        int newSize = size * 2;
        this.array = Arrays.copyOf(this.array, newSize);
    }

    public void upAdjust() {//上浮
        int childIndex = size - 1;
        int parentIndex = childIndex / 2;
        int temp = array[childIndex];
        while (childIndex > 0 && temp > array[parentIndex]) {
            array[childIndex] = array[parentIndex];
            childIndex = parentIndex;
            parentIndex = childIndex / 2;
        }
        array[childIndex] = temp;
    }

    public void downAdjust() {//下沉
        int parentIndex = 0;
        int temp = array[parentIndex];
        int childIndex=1;
        while (childIndex < size) {
            if (childIndex+1<size&&array[childIndex]<array[childIndex+1]){
                childIndex++;
            }
            if (temp>array[childIndex])
                break;
            array[parentIndex]=array[childIndex];
            parentIndex=childIndex;
            childIndex=childIndex*2+1;
        }
        array[childIndex]=temp;
    }

    public static void main(String[] args) {

    }
}
