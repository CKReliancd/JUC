package com.atguigu.linkedlist;


public class HeroNode {
    public int no;
    public String name;
    public String nickName;
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +"\n"+
                ", name='" + name + "\n"+
                ", nickName='" + nickName + "\n"+
                '}';
    }
}

