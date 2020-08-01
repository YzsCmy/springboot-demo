package com.unionman.springbootdemo.service;

import com.unionman.springbootdemo.entity.StudentExaminationInfo;
import com.unionman.springbootdemo.pojo.vo.StudentExaminationInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/30
 */
public interface StudentExaminationInfoService {

    /**
     * 批量增加学生体检信息
     * @param infos
     */
    void addStudentExaminationInfo(List<StudentExaminationInfo> infos);

    /**
     * 名字完全匹配检索
     * @param name
     * @return
     */
    StudentExaminationInfoVO findInfoByName(String name);

    /**
     * 名字模糊检索
     * @param name
     * @return
     */
    List<StudentExaminationInfoVO> findInfoByNameFuzzy(String name);

    /**
     * 年龄范围检索
     * @param begin
     * @param end
     * @return
     */
    List<StudentExaminationInfoVO> findInfoByAgeRange(Integer begin,Integer end);

    /**
     * 两只眼睛视力都低于4.0
     * @return
     */
    List<StudentExaminationInfoVO> findBothNearSighted();

    /**
     * 其中一只眼睛低于4.0、一只高于4.0
     * @return
     */
    List<StudentExaminationInfoVO> findNearSighted();

    /**
     * 非近视学生检索(两只眼睛视力都高于4.0)
     * @return
     */
    List<StudentExaminationInfoVO> findNotNearSighted();

    /**
     * 检索三围内有任意一个值在40到60之间的数据
      * @param low
     * @param high
     * @return
     */
    List<StudentExaminationInfoVO> findByMeasurementsRange(Integer low, Integer high);

    /**
     * 根据班级统计学生数量
     * @return
     */
    Map<String,Long> statisticByClassName();

    /**
     * 统计身高的最大值、最小值、平均值、身高总和
     * @return
     */
    Map<String,Double> statisticByHeight();

}
