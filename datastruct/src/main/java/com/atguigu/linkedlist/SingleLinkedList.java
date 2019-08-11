package com.atguigu.linkedlist;

import java.util.Stack;

/**
 * 单链表：
 * 单链表的反转
 * 返回链表的头节点
 * 查找单链表中的倒数第index个结点
 * 获取链表长度
 * 删除对应no的节点
 * 根据newHeroNode更新对应的节点
 * 增加节点
 * 正序遍历
 */
public class SingleLinkedList {

    private HeroNode head = new HeroNode(0, "", "");


    /**
     * 合并两个有序的单链表，合并之后的链表依然有序【课后练习.】
     */
    public HeroNode mergeTwoList(HeroNode heroNode1, HeroNode heroNode2) {

        if (heroNode1.next.no > heroNode2.next.no) {

            head.next = heroNode2.next;

            head.next.next = mergeTwoList( heroNode1, heroNode2.next.next );
        }else{

            head.next = heroNode1.next;

            head.next.next = mergeTwoList(heroNode2,heroNode1.next.next);
        }


        return head;
    }

    public void add2(HeroNode heroNode){



    }

    /**
     * 利用栈先进后出的特点逆序打印链表
     *
     * @param heroNode
     */
    public static void reversePrint(HeroNode heroNode) {

        if (heroNode.next == null) {
            return;
        }
        Stack<HeroNode> stack = new Stack<>();

        HeroNode cur = heroNode.next;

        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }

    }

    /**
     * 单链表的反转
     *
     * @param heroNode
     */
    public static void reverseList(HeroNode heroNode) {

        if (heroNode.next == null || heroNode.next.next == null) {
            return;
        }

        HeroNode cur = heroNode.next;

        //当前操作节点的下一个节点
        HeroNode next = null;

        HeroNode reverseHead = new HeroNode(0, "", "");

        while (cur != null) {
            next = cur.next;

            cur.next = reverseHead.next;
            reverseHead.next = cur;

            cur = next;
        }

        heroNode.next = reverseHead.next;
    }


    /**
     * 返回链表的头节点
     */
    public HeroNode getHead() {
        return head;
    }

    /**
     * 查找单链表中的倒数第index个结点
     *
     * @param heroNode
     * @param index
     * @return
     */
    public HeroNode findReciprocalIndexHeroNode(HeroNode heroNode, int index) {

        while (heroNode.next == null) {
            System.out.println("空链表");
            return null;
        }

        int size = getLength();

        if (index <= 0 || index > size) {
            return null;
        }

        HeroNode temp = head.next;

        for (int i = 0; i < size - index; i++) {
            temp = temp.next;
        }

        return temp;

    }

    /**
     * 获取链表长度
     *
     * @return
     */
    public int getLength() {

        HeroNode temp = head;

        int length = 0;

        if (temp.next == null) {
            return length;
        }


        while (temp.next != null) {
            length++;
            temp = temp.next;
        }

        return length;
    }

    /**
     * 删除对应no的节点
     *
     * @param no
     */
    public void delete(int no) {

        HeroNode temp = head;
        boolean flag = false;

        while (true) {

            if (temp.next == null) {
                break;
            } else if (temp.next.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的节点 %d 不存在\n");
        }

    }

    /**
     * 根据newHeroNode更新对应的节点
     *
     * @param newHeroNode
     */
    public void update(HeroNode newHeroNode) {

        HeroNode temp = head.next;
        boolean flag = false;

        while (true) {

            if (temp == null) {
                break;
            } else if (temp.no == newHeroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;

        }

        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickName = newHeroNode.nickName;
        } else {
            System.out.printf("节点 no 未找到,不能修改", newHeroNode.no);
        }

    }

    /**
     * 增加节点
     *
     * @param heroNode
     */
    public void add(HeroNode heroNode) {

        HeroNode temp = head;

        boolean flag = false;
        while (true) {

            if (temp.next == null) {
                break;
            } else if (temp.next.no > heroNode.no) {
                break;
            } else if (heroNode.no == temp.next.no) {
                flag = true;
                break;
            }

            temp = temp.next;
        }

        if (flag) {
            System.out.printf("待插入的英雄编号 %d 已经有了,不能插入 \n", heroNode.no);
        } else {
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    /**
     * 正序遍历链表
     */
    public void list() {
        if (head.next == null) {
            System.out.println("当前链表为空");
            return;
        }

        HeroNode temp = head.next;

        while (true) {
            if (temp == null) {
                break;
            }

            System.out.printf("\t节点信息 no=%d , name = %s , nickName = %s", temp.no, temp.name, temp.nickName + "\n");
            temp = temp.next;
        }


    }


}
