package com.unionman.springbootdemo.entity;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "um_t_student")
public class Student {

    @Id
    private ObjectId id;

    /**
     * 头像
     */
    private String headPortrait;

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

    public String idToHex(){
        return id.toHexString();
    }
}
