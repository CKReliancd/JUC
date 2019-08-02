package com.atguigu.linkedlist;

public class SingleLinkedList {

    HeroNode head = new HeroNode(0, "", "");

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
        }else {
            System.out.printf("要删除的节点 %d 不存在\n");
        }

    }

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

            System.out.printf("\t节点信息 no=%d , name = %s , nickName = %s", temp.no, temp.name, temp.nickName);
            temp = temp.next;
        }


    }


}
