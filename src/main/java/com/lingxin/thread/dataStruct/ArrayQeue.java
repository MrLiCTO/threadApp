package com.lingxin.thread.dataStruct;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayQeue<T> {
    private Object[] arr; //数据
    private int head; //队头
    private int rear; //队尾

    Lock lock = new ReentrantLock();
    Condition full = lock.newCondition();
    Condition empty = lock.newCondition();


    public ArrayQeue(int capcity) {
        this.arr = new Object[capcity];
    }

    public void push(T t) throws Exception {
        lock.lock();
        try {
            while ((rear + 1) % arr.length == head) {
                System.out.println("队列已满！请稍等-------------" + System.currentTimeMillis() / 1000);
                full.await();
            }
            arr[rear] = t;
            rear = (rear + 1) % arr.length;
            empty.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

    public T pop() throws Exception {

        lock.lock();
        T t = null;
        try {
            while (rear == head) {
                System.out.println("队列已空！请稍等-------------" + System.currentTimeMillis() / 1000);
                empty.await();
            }
            t = (T) arr[head];
            head = (head + 1) % arr.length;
            full.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            return t;
        }
    }

    public void printData() throws Exception {
        for (int i = head; i == (rear + 1) % arr.length; i = (i + 1) % arr.length + 1)
            System.out.println(arr[i]);
    }

    public static void main(String[] args) throws Exception {
        ArrayQeue<Integer> qeue = new ArrayQeue<Integer>(10);
        qeue.pop();
/*        new Thread(() -> {
            while (true) {
                try {
                    Random random = new Random();

                    Thread.sleep(1000);
                    qeue.push(random.nextInt(10));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (true) {
                try {

                    System.out.println("消费了：" + qeue.pop());
                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }
}
