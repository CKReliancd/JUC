package thread;

import java.util.Stack;

public class Test3 {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String inStr = "I am a student.";
        Stack<String> strStack = new Stack<String>();
        String temp = "";
        char [] inChar = inStr.toCharArray();

        for (int i = 0; i < inChar.length; i++) {
            if (inChar[i]!=' ') {
                temp += inChar[i];
            }
            else {
                strStack.push(temp);
                temp="";
            }
        }
        strStack.push(temp);//压入最后一个值
        while(!strStack.isEmpty()){
            System.out.print(strStack.pop()+" ");
        }
    }



}
