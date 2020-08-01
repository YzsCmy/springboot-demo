package com.unionman.springbootdemo.controller;

import com.unionman.springbootdemo.entity.StudentExaminationInfo;
import com.unionman.springbootdemo.pojo.vo.ResponseVO;
import com.unionman.springbootdemo.pojo.vo.StudentExaminationInfoVO;
import com.unionman.springbootdemo.service.StudentExaminationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/30
 */
@RestController
@RequestMapping("/exam")
public class StudentExaminationInfoController {

    @Autowired
    private StudentExaminationInfoService infoService;

    /**
     * 批量增加学生体检信息
     * @param infos
     */
    @PostMapping
    ResponseVO addStudentExaminationInfo(List<StudentExaminationInfo> infos){
        infoService.addStudentExaminationInfo(infos);
        return ResponseVO.success("添加数据成功！");
    }

    /**
     * 名字完全匹配检索
     * @param name
     * @return
     */
    @GetMapping
    ResponseVO findInfoByName(String name){
        if(StringUtils.isEmpty(name)){
            return ResponseVO.fail("名称不能为空!");
        }
        StudentExaminationInfoVO infoByName = infoService.findInfoByName(name);
        return ResponseVO.success(infoByName);
    }

    /**
     * 名字模糊检索
     * @param name
     * @return
     */
    @GetMapping("/fuzzy")
    ResponseVO<List> findInfoByNameFuzzy(String name){
        if(name==null){
            name="";
        }
        List<StudentExaminationInfoVO> res = infoService.findInfoByNameFuzzy(name);
        return ResponseVO.success(res);
    }

    /**
     * 年龄范围检索
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/age/{begin}/{end}")
    ResponseVO<List> findInfoByAgeRange(@PathVariable("begin") Integer begin, @PathVariable("end") Integer end){

        List<StudentExaminationInfoVO> res = infoService.findInfoByAgeRange(begin, end);

        return ResponseVO.success(res);

    }

    /**
     * 两只眼睛视力都低于4.0
     * @return
     */
    @GetMapping("/both")
    ResponseVO<List> findBothNearSighted(){

        List<StudentExaminationInfoVO> res = infoService.findBothNearSighted();
        return ResponseVO.success(res);
    }

    /**
     * 其中一只眼睛低于4.0、一只高于4.0
     * @return
     */
    @GetMapping("/one")
    ResponseVO<List>  findNearSighted(){
        List<StudentExaminationInfoVO> res = infoService.findNearSighted();
        return ResponseVO.success(res);
    }

    /**
     * 非近视学生检索(两只眼睛视力都高于4.0)
     * @return
     */
    @GetMapping("/none")
    ResponseVO<List> findNotNearSighted(){
        List<StudentExaminationInfoVO> res = infoService.findNotNearSighted();
        return ResponseVO.success(res);
    }

    /**
     * 检索三围内有任意一个值在40到60之间的数据
     * @param low
     * @param high
     * @return
     */
    @GetMapping("/meas/{low}/{high}")
    ResponseVO<List> findByMeasurementsRange(@PathVariable("low") Integer low,
                                             @PathVariable("high") Integer high){
        List<StudentExaminationInfoVO> res = infoService.findByMeasurementsRange(low, high);

        return ResponseVO.success(res);
    }

    /**
     * 根据班级统计学生数量
     * @return
     */
    @GetMapping("/aggs/class")
    ResponseVO<Map<String,Long>> statisticByClassName(){

        Map<String, Long> res = infoService.statisticByClassName();
        return ResponseVO.success(res);
    }

    /**
     * 统计身高的最大值、最小值、平均值、身高总和
     * @return
     */
    @GetMapping("/aggs/height")
    ResponseVO<Map<String,Double>> statisticByHeight(){
        Map<String, Double> res = infoService.statisticByHeight();
        return ResponseVO.success(res);
    }

}
