package com.atguigu.linkedlist;

/**
 * @author Reliance
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {

        //测试单向链表的添加和遍历
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        //创建一个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();

        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.list();

        HeroNode newHero = new HeroNode(2, "小卢", "玉麒麟~~~~~~");

        singleLinkedList.updateHero(newHero);
        singleLinkedList.list();




    }
}

/**
 * 定义单向链表管理Hero
 */
class SingleLinkedList {
    /**
     * 先初始化一个头节点，头节点一般不会动
     */
    private HeroNode head = new HeroNode(0, "", "");

    /**
     * 编写更新方法
     * 1、首先找到新添加的节点的位置，是通过辅助变量（指针），通过遍历搞定
     * 2、新的节点.next = temp.next
     * 3、temp.next = 新的节点
     */

    public void updateHero(HeroNode newHeroNode){

        if (head.next == null){
            System.out.println("链表为空");
            return;
        }

        //先找到节点
        HeroNode temp = head.next;
        boolean flag = false;
        while (true){
            if (temp.next == null){
                break;
            }
            if (temp.no == newHeroNode.no){
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag){
            temp.name = newHeroNode.name;
            temp.nickName = newHeroNode.nickName;
        }else {
            System.out.printf("没有找到编号为%d的节点，不能修改",newHeroNode.no);
        }

    }


    /**
     * 编写添加方法
     * 第一种方法在添加英雄时，直接添加到链表的尾部
     * 1、找到当前链表的最后节点
     * 2、将最后这个节点的next指向新的节点
     * 遍历：通过一个辅助变量帮助遍历整个链表
     */
    public void add(HeroNode heroNode) {
        //因为头节点不能动，所以我们需要有一个临时的节点，作为辅助，new一个HeroNode对象temp
        HeroNode temp = head;
        //找到链表的最后
        while (true) {
            //说明链表已经到最后了
            if (temp.next == null) {
                break;
            }
            //如果没有找到最后，则将temp后移
            temp = temp.next;
        }
        //当退出while循环后，temp就是链表的最后
        temp.next = heroNode;
    }

    /**
     * 第二种方式添加英雄，根据排名将英雄插入到指定位置
     * （如果有这个排名，则添加失败）
     */
    public void addByOrder(HeroNode heroNode){
        /**
         * 头节点不能动，因此我们仍然通过一个辅助指针（变量）来帮助我们找到添加的位置
         * 因为单链表，head不动，用temp辅助，我们找到的temp是位于添加位置的前一个节点
         * 这是因为已知量只有heroNode节点，temp，和temp.next，如果是找的是当前节点，
         * 那么temp表示的就是当前节点，那么在当前节点和上一个节点的中间位置，则上一个节点
         * 无法表示，所以只能以temp.nextt表示要找的节点，从小到大的话temp.next.no > heroNode.no就表示找到了位置，在
         * 在这个位置进行操作，让temp.next -> hero.next,。让hero -> temp.next
         */
        HeroNode temp = head;

        //判断添加的编号是否存在，默认flase
        boolean flag = false;

        while (true){

            if (temp.next == null){
                //说明已经在链表的最后
                break;
            }

            if (temp.next.no > heroNode.no){
                //从小到大排列，位置找到了，就在temp的后面插入
                break;
            }else if (temp.next.no == heroNode.no){
                //说明希望添加的heroNode的编号已然存在
                flag = true;
                break;
            }
            temp = temp.next;
        }

        //根据flag判断是否找到要修改的节点
        if (flag){
            System.out.printf("准备插入的英雄编号%d已经存在了，不能加入\n",heroNode.no);
        }else {
            //插入到链表中，temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }



    /**
     * 显示链表【遍历】
     */
    public void list() {
        //判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //因为头节点不能动，因此我们需要一个temp临时节点，作为辅助
        HeroNode temp = head.next;
        while (true) {
            //判断是否到最后，到了就退出
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }

    }

}


/**
 * 先创建Hero节点
 */
class HeroNode {
    public int no;
    public String name;
    public String nickName;
    /**
     * 对象里面创建一个对象，这是成链表的根源
     */
    public HeroNode next;

    public HeroNode(int hNo, String hName, String hNickName) {
        this.no = hNo;
        this.name = hName;
        this.nickName = hNickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName +
                '}';
    }
}

