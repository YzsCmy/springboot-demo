package com.unionman.springbootdemo.entity;

import lombok.Data;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.enums.DataType;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/30
 */
@Data
public class Vision {

    /**
     * 左眼视力
     */
    @ESMapping(datatype = DataType.float_type)
    private Float rightEye;
    /**
     * 右眼视力
     */
    @ESMapping(datatype = DataType.float_type)
    private Float leftEye;
}
