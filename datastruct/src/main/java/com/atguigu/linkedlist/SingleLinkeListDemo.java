package com.atguigu.linkedlist;

public class SingleLinkeListDemo
{
    public static void main(String[] args) {

        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero4 = new HeroNode(2, "宋江2", "及时雨2");
        HeroNode hero2 = new HeroNode(3, "宋江3", "及时雨3");
        HeroNode hero3 = new HeroNode(4, "宋江4", "及时雨4");

        HeroNode hero5 = new HeroNode(5, "宋江5", "及时雨5");
        HeroNode hero6 = new HeroNode(6, "宋江6", "及时雨6");
        HeroNode hero7 = new HeroNode(7, "宋江7", "及时雨7");
        HeroNode hero8 = new HeroNode(8, "宋江8", "及时雨8");


        SingleLinkedList singleLinkedList1 = new SingleLinkedList();

        singleLinkedList1.add(hero5);
        singleLinkedList1.add(hero6);
        singleLinkedList1.add(hero7);
        singleLinkedList1.add(hero8);

        singleLinkedList1.list();

        System.out.println();
        System.out.println("==========================");
        System.out.println();

        SingleLinkedList singleLinkedList = new SingleLinkedList();

        singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);

        singleLinkedList.list();

        System.out.println("=========================");

        SingleLinkedList singleLinkedList2 = new SingleLinkedList();

        System.out.println(singleLinkedList2.mergeTwoList(singleLinkedList.getHead(), singleLinkedList1.getHead()));


//        SingleLinkedList.reversePrint(SingleLinkedList.getHead());

//        SingleLinkedList.reverseList(singleLinkedList.getHead());
//
//        System.out.println("======================");
//
//        singleLinkedList.list();

//        System.out.println(SingleLinkedList.findReciprocalIndexHeroNode(singleLinkedList.getHead(),1));

//        singleLinkedList.list();




       /*


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
        System.out.println("=====================");
        System.out.println(SingleLinkedList.getLength());*/

    }

}
