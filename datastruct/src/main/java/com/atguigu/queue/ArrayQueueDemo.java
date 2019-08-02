package com.atguigu.queue;

public class ArrayQueueDemo {

    public static void main(String[] args) {

        System.out.println(4-(4/3)*3);

        System.out.println(1/3);

    }
}

class ArrayQueue {

    private int maxSize;
    private int[] arr;
    private int front;
    private int rear;

    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        //指向队列头部，分析出front是指向队列数据的前一个位置
        front = -1;
        //指向队列的尾部，分析出rear是指向队列的最后数据（含）
        rear = -1;
    }

    /**
     * 判断队列内容是否满
     */
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    /**
     * 判断队列是否空
     */
    public boolean isEmpty() {
        return front == rear;
    }

    public void addQueue(int n) {
        //判断是否满
        if (isFull()) {
            System.out.println("队列满，无法加入~");
            return;
        }
        rear++;
        arr[rear] = n;
    }

    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空~");
        }
        return arr[front + 1];
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空~");
        }
        front += 1;
        return arr[front];
    }

    /**
     * 显示队列的所有数据
     */
    public void showQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列空~");
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }


    }


}
