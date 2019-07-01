package com.example.demo;

import org.springframework.beans.BeanUtils;

public class Copy {

    public static void main(String[] args) {

        Student student = new Student();

        Teacher teacher = new Teacher();

        student.setName("张三");
        student.setOld("18");
        student.setScore("100");
        student.setSalary("60000");

        teacher.setSalary("62222222");

        BeanUtils.copyProperties(student,teacher);
        //BeanUtil.copyProperties(student,teacher,true, CopyOptions.create().setXXXX(true));


        System.out.println(teacher);

        System.out.println(student);
    }

}
