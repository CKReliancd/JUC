package com.atguigu.queue;

import java.util.Scanner;

public class CirCleArrayQueueDemo {
    public static void main(String[] args) {
        System.out.println("~~环形队列案例~~");
        //初始化一个队列
        CircleArrayQueue queue = new CircleArrayQueue(4);
        char key;
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("s(show): 表示显示队列");
            System.out.println("e(exit): 表示退出程序");
            System.out.println("a(add): 表示添加队列数据");
            System.out.println("g(get): 表示取出队列数据");
            System.out.println("h(head): 查看队列头的数据(不改变队列)");

            key = scanner.next().charAt(0);

            switch (key) {
                default:
                    break;
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = queue.getQueue();
                        System.out.println("从队列中取出数据为：" + res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = queue.headQueue();
                        System.out.println("队列头元素值为=" + res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;

            }


        }


    }


}

class CircleArrayQueue {

    private int maxSize;
    private int[] arr;
    private int front;
    private int rear;

    public CircleArrayQueue(int arrSize) {
        this.maxSize = arrSize;
        arr = new int[arrSize];
        front = 0;
        rear = 0;
    }

    /**
     * 判断队列满的方法
     * 队列容量空出一个作为约定
     */
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    /**
     * 判断队列为空的条件
     *
     * @return
     */
    public boolean isEmpty() {
        return rear == front;
    }

    /**
     * 添加数据到队列
     */
    public void addQueue(int n) {
        if (isEmpty()){
            System.out.println("队列满~");
            return;
        }
        arr[rear] = n;
        rear = (rear+1)%maxSize;
    }

    /**
     * 取出队列的数据（按照先进先出的原则）
     */
    public int getQueue() {
        if (isEmpty()){
            throw new RuntimeException("队列空~");
        }
        int value = arr[front];
        front = (front+1)%maxSize;
        return value;
    }

    /**
     * 求出当前环形队列有几个元素
     */
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    /**
     * 显示队列的所有数据
     */
    public void showQueue() {
        if(isEmpty()){
            System.out.println("队列空~");
            return;
        }
        //思路：从front开始取，取有效个元素
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n",i,arr[i]);
        }

    }

    /**
     * 查看队列的头元素，但是不是改变队列
     */
    public int headQueue() {
        if (isEmpty()){
            throw new RuntimeException("队列空");
        }
        return arr[front];
    }


}
