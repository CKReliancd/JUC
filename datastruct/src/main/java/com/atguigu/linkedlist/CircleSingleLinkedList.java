package com.atguigu.linkedlist;

/**
 * 环形队列解决约瑟夫问题
 */
public class CircleSingleLinkedList {

    //定义一个初始的头节点
    private Boy first = new Boy(0);

    public void countBoy4(int startNo, int countNum, int nums) {
        if (first == null || countNum < 1 || countNum > nums) {
            System.out.println("输入信息有误");
            return;
        }

        Boy helper = first;
        while (true) {

            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }

        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        while (true) {
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }

            System.out.printf("小孩%d出列\n", first.getNo());
            first = first.getNext();
            helper.setNext(first);

            if (helper == first) {
                System.out.printf("最后的小孩%d出列\n", first.getNo());
                break;
            }
        }
    }


    /**
     * 完成游戏的思路分析->实现代码
     * 1) 在first 前面 设计一个辅助指针（helper） , 即将helper 指针定位到 first 前面
     * 2) 将first指针移动到 startNo 这个小孩(helper 对应移动)
     * 3) 开始数 countNum 个数[first 和 helper会对应的移动]
     * 4) 删除first 指向的这个小孩节点
     */
    public void countBoy3(int startNo, int countNum, int nums) {
        if (first == null || countNum < 1 || countNum > nums) {
            System.out.println("输入信息有误");
            return;
        }

        // 1)在first 前面 设计一个辅助指针（helper） , 即将helper 指针定位到 first 前面
        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }

        // 2) 将first指针移动到 startNo 这个小孩(helper 对应移动)
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        while (true) {

            // 3) 开始数 countNum 个数,让first指向要出队的节点，helper做出相同移动
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }

            // 4) 删除first 指向的这个小孩节点
            System.out.printf("小孩%d 出列\n", first.getNo());
            first = first.getNext();
            helper.setNext(first);

            if (helper == first) {
                System.out.printf("最后出列的小孩%d", first.getNo());
                break;
            }
        }
    }


    /**
     * 根据用户的输入，计算小孩的出圈顺序
     *
     * @param startNo  从几号开始
     * @param countNum 数几下
     * @param nums     圈里共有多少人
     */
    public void countBoy(int startNo, int countNum, int nums) {

        //先对数据进行校验
        if (first.getNext() == null || startNo < 1 || startNo > nums) {
            System.out.println("输入的数据有误");
            return;
        }

        //1)创建辅助指针，帮助出圈
        Boy helper = first;

        //需求：创建一个辅助变量helper，事先应该指向环形链表的最后一个节点
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }

        //2)小孩报数之前，先让first和helper移动 startNo-1次
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        //3)小孩报数时，让first和helper指针同时移动m-1次，first指向要出圈的节点，然后出圈，
        while (true) {

            //这是循环操作，直到圈中只有一个节点
            if (helper == first) {
                break;
            }

            //让first和helper指针同时移动countNum - 1
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }

            //这时first指向的节点，就是要出圈的节点
            System.out.printf("小孩%d 出圈\n", first.getNo());

            //这时将first指向的小孩的节点出圈
            first = first.getNext();
            helper.setNext(first);

        }
        System.out.printf("圈中最后一个节点 %d\n", first.getNo());

    }

    public void countBoy2(int startNo, int countNum, int nums) {
        // 对参数进行判断
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数有误，重新输入!");
            return;
        }
        // 完成游戏的思路
        /*
         * 完成游戏的思路分析->实现代码
         *
         * 1) 在first 前面 设计一个辅助指针（helper） , 即将helper 指针定位到 first 前面 2) 将first
         * 指针移动到 startNo 这个小孩(helper 对应移动) 3) 开始数 countNum 个数[first 和 helper
         * 会对应的移动] 4) 删除first 指向的这个小孩节点 5) 思路
         *
         */
        Boy helper = first;
        // 1)即将helper 指针定位到 first 前面
        while (true) {
            if (helper.getNext() == first) {
                break;
            }
            helper = helper.getNext();
        }
        // 2)将first 指针移动到 startNo 这个小孩(helper 对应移动)
        for (int j = 0; j < startNo - 1; j++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        // 开始数数，按照给定的值，每数到一个小孩就出圈, 直到环形链表只有一个节点
        while (true) {
            if (helper == first) {
                // 只有一个人
                break;
            }
            // 3) 开始数 countNum 个数[first 和 helper 会对应的移动]
            for (int j = 0; j < countNum - 1; j++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // 输出出圈的人的信息
            System.out.printf("小孩%d出圈\n", first.getNo());
            // 将first 指向的节点删除
            first = first.getNext();
            helper.setNext(first);

        }
        // 当while结束后, 只有一个人
        System.out.printf("最后留在圈的人是 小孩编号为 %d\n", first.getNo());

    }

    public void addBoy1(int nums) {
        if (nums < 1) {
            System.out.println("输入信息有误");
            return;
        }

        Boy cur = null;
        for (int i = 1; i < nums; i++) {

            Boy boy = new Boy(i);

            if (i == 1) {
                first = boy;
                first.setNext(first);
                cur = first;

            } else {
                cur.setNext(boy);
                boy.setNext(first);
                cur = boy;
            }

        }

    }

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
                //设置当前节点的下一个节点是新的节点
                curBoy.setNext(boy);

                //最后一个节点始终连着头节点
                boy.setNext(first);

                //把当前节点后移
                curBoy = boy;
            }
        }
    }

    /**
     * 遍历单向的环形链表
     */
    public void showBoy() {
        if (first.getNext() == null) {
            System.out.println("环形链表没有任何数据");
            return;
        }

        Boy curBoy = first;

        while (true) {

            System.out.printf("小孩编号 %d\n", curBoy.getNo());

            if (curBoy.getNext() == first) {
                break;
            }

            curBoy = curBoy.getNext();
        }
    }


}
