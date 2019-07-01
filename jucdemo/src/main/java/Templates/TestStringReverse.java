package Templates;

public class TestStringReverse{
    public static void main(String[] args) {
        String input = "There is a dog";
        System.out.println("逆转后的字符串为：" + reverseWords(input));
    }
    public static String reverseWords(String input) {
        String str = "";
        char[] arr = input.toCharArray();//将字符串转化成字符数组

        int index = 0;//index用来记录每个单词的起始索引

        for (int i = 0; i < arr.length; i++) {//遍历字符数组，将空格前边的单词挨个拼接到str中

            if(arr[i] == ' '){
//                for(int j = i - 1; j >= index; j--){//根据空格的位置将空格前边一个单词密续追加到str中
//
//                    str += arr[j];
//                }
//                str += ' ';//单词拼接完成后，拼接一个空格

                for(int j = 0; j<=i;j++){
                    str += arr[j];

                }

                str += ' ';

                index = i + 1;//让index指向下一个单词的起始位置

            }
        }
        //将最后一个单词拼接上
        for(int i = arr.length - 1; i >= index; i--){
            str += arr[i];
        }
        return str;
    }
}
