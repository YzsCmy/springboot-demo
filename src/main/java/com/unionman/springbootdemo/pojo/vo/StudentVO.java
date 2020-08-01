package com.unionman.springbootdemo.pojo.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentVO {

    private String id;


    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 城市
     */
    private String city;

    /**
     * 头像
     */
    private String headPortrait;
}
