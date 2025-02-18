package com.crane.cpb.example;

import lombok.Data;

/**
 * (Student)实体类
 *
 * @author 全栈学习笔记
 * @since 2020-04-14 11:39:10
 */
@Data
public class Student  {
    private static final long serialVersionUID = -91969758749726312L;
    /**
    * 唯一标识id
    */
    private Integer id;
    /**
    * 姓名
    */
    private String name;
    /**
    * 年龄
    */
    private Integer age;
    //省略get，set方法，自己加上
}
