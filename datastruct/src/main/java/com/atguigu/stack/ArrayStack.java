package com.atguigu.stack;

public class ArrayStack {
    private int maxSize;
    private int[] stack;
    private int top = -1;

    //构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int value) {
        if (isFull()) {
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空");
        }
        int value = stack[top];
        top--;
        return value;
    }

    public void list() {
        if (isEmpty()) {
            System.out.println("栈空，无法遍历");
            return;
        }
        for (int i = top; i >= 0; i++) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }

    }

    /**
     * 返回运算符的优先级用数字表示
     * 数字越大，则优先级就越高
     */
    public int priority(int oper) {

        if(oper == '*'||oper == '/') {
            return 1;
        } else if (oper == '+'||oper == '-'){
            return 0;
        }else {
            //假定目前的表达式只有+，-，*，/
            return -1;
        }

    }


}
