package com.atguigu.linkedlist;

public class CircleSingleLinkedList {

    //定义一个初始的头节点
    private Boy first = new Boy(0);

    /**
     * 形成一个单向环形的链表,num表示有几个小孩
     */
    public void addBoy(int nums) {

        if (nums < 1) {
            System.out.println("输入的值不正确");
            return;
        }

        //形成环形列表需要辅助指针
        Boy curBoy = null;

        for (int i = 1; i <= nums; i++) {
            //根据编号创建小孩对象
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                first.setNext(first);
                curBoy = first;
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    /**
     * 遍历单向的环形链表
     */
    public void showBoy(){
        if (first.getNext() == null){
            System.out.println("环形链表没有任何数据");
            return;
        }

        Boy curBoy = first;

        while (true){

            System.out.printf("小孩编号 %d\n" , curBoy.getNo());

            if (curBoy.getNext() == first){
                break;
            }

            curBoy = curBoy.getNext();
        }
    }






























}
