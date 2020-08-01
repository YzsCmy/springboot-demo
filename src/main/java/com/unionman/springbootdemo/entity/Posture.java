package com.unionman.springbootdemo.entity;

import lombok.Data;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.enums.DataType;

import java.util.List;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/30
 */
@Data
public class Posture {

    /**
     * 体重
     */
    @ESMapping(datatype = DataType.integer_type)
    private Integer bodyWeight;
    /**
     * 三围
     */
    @ESMapping(datatype = DataType.integer_type)
    private List<Integer> measurements;
    /**
     * 身高
     */
    @ESMapping(datatype = DataType.integer_type)
    private Integer height;
}
