package com.atguigu.sparsearray;


import org.junit.Test;

import java.io.*;

public class sparsearray {

    public static void main(String[] args) {

        //创建一个原始的二维数组 11 * 11
        int chessArr1[][] = new int[11][11];

        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;

        //输出一个原始的二维数组
        System.out.println("输出一个原始的二维数组");

        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }


        //将二维数组转为稀疏数组
        //1、先遍历二维数组  得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }
        //2、创建对应的稀疏数组,并给其赋值
        int sparseArr[][] = new int[sum + 1][3];

        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        //遍历二维数组，将非0的值存放到sparseArr中
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                }
            }
        }

        //输出稀疏数组的形式
        System.out.println();
        System.out.println("得到稀疏数组为---------");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }

        /*
            1、先读取稀疏数组的第一行，根据第一行的数据，创建原始二维数组
            2、再读取稀疏数组后几行的数据（从第二行开始）,并赋值给原始的二维数组
         */

        int chessArray2[][] = new int[sparseArr[0][0]][sparseArr[0][1]];

        for (int i = 1; i < sparseArr.length; i++) {
            chessArray2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        for (int[] row : chessArray2) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        String string= "";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("E:\\BaiduNetdiskDownload\\spareArray.txt"));

//            for (int[] spare : sparseArr) {
//                for (int e : spare) {
//                    string = String + e + "\t";
//                }
//            }
            fos.write(string.getBytes());
            System.out.println(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Test
    public void perfectOnputStream() {
        //1、创建FileOnputStream的实例，同时打开指定文件
        FileOutputStream fos = null;
        FileInputStream fis = null;

        byte[] bytes = new byte[3];
        try {
            fis = new FileInputStream("E:\\BaiduNetdiskDownload\\aa.txt");
            fos = new FileOutputStream("./hello.txt");
            //2、读取指定文件的内容
            int len = 0;

            while ((len = fis.read(bytes)) != -1) {
                String s = new String(bytes, 0, len);
                fos.write(s.getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    @Test
    public void perfectInputStream() {
        //1、创建FileInputStream的实例，同时打开指定文件
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("E:\\BaiduNetdiskDownload\\aa.txt");
            //2、读取指定文件的内容
            byte[] bytes = new byte[3];
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                System.out.print(new String(bytes, 0, len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //3、关闭流


}