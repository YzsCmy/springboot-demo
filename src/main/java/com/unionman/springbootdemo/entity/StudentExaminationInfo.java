package com.unionman.springbootdemo.entity;

import lombok.Data;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/30
 */
@Data
@ESMetaData(indexName = "um_t_student_examination_info",number_of_replicas = 0,number_of_shards = 5)
public class StudentExaminationInfo {

    @ESID
    private String id;

    /**
     * 班级
     */
    @ESMapping(datatype = DataType.keyword_type)
    private String fclass;

    /**
     * 姓名
     */
    @ESMapping(datatype = DataType.keyword_type)
    private String name;

    /**
     * 年龄
     */
    @ESMapping(datatype = DataType.keyword_type)
    private Integer age;

    /**
     * 性别
     */
    @ESMapping
    private String sex;

    /**
     * 民族
     */
    @ESMapping
    private String nation;
    /**
     * 体质
     */
    @ESMapping(datatype = DataType.nested_type,nested_class = Posture.class)
    private Posture posture;
    /**
     * 视力
     */
    @ESMapping(datatype = DataType.nested_type,nested_class = Vision.class)
    private Vision vision;

}
