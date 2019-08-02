package com.atguigu.linkedlist;

public class SingleLinkeListDemo
{
    public static void main(String[] args) {

        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(3, "宋江3", "及时雨3");
        HeroNode hero3 = new HeroNode(4, "宋江4", "及时雨4");
        HeroNode hero4 = new HeroNode(2, "宋江2", "及时雨2");
        SingleLinkedList singleLinkedList = new SingleLinkedList();

        singleLinkedList.add(hero1);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero4);

        singleLinkedList.list();

        System.out.println("=======================");

        singleLinkedList.update(new HeroNode(2,"路明非","废柴"));

        singleLinkedList.list();

        System.out.println("=====================");

        singleLinkedList.update(new HeroNode(5,"楚子航","黄金瞳的魔术师"));

        System.out.println("=============================");

        singleLinkedList.delete(2);
        singleLinkedList.list();
        System.out.println("=============================");
        singleLinkedList.delete(1);
        singleLinkedList.list();
        System.out.println("=============================");
        singleLinkedList.delete(4);
        singleLinkedList.list();


    }

}
