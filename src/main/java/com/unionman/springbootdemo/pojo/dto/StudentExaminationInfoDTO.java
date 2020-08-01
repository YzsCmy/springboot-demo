package com.unionman.springbootdemo.pojo.dto;

import com.unionman.springbootdemo.entity.Posture;
import com.unionman.springbootdemo.entity.Vision;
import lombok.Getter;
import lombok.Setter;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/30
 */
@Setter
@Getter
public class StudentExaminationInfoDTO{

    private String id;

    /**
     * 班级
     */
    private String fclass;

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
     * 民族
     */
    private String nation;
    /**
     * 体质
     */
    private Posture posture;
    /**
     * 视力
     */
    private Vision vision;

}
