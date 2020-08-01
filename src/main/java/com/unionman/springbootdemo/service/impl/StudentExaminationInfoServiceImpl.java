package com.unionman.springbootdemo.service.impl;

import com.unionman.springbootdemo.constant.PrefixConst;
import com.unionman.springbootdemo.entity.StudentExaminationInfo;
import com.unionman.springbootdemo.pojo.vo.StudentExaminationInfoVO;
import com.unionman.springbootdemo.repository.StudentExaminationInfoRepository;
import com.unionman.springbootdemo.service.StudentExaminationInfoService;
import com.unionman.springbootdemo.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/31
 */
@Service
public class StudentExaminationInfoServiceImpl implements StudentExaminationInfoService {

    @Autowired
    private StudentExaminationInfoRepository infoRepository;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public void addStudentExaminationInfo(List<StudentExaminationInfo> infos) {

        infoRepository.addStudentExaminationInfo(infos);

    }

    @Override
    public StudentExaminationInfoVO findInfoByName(String name) {
        String key = PrefixConst.MATCH_NAME_PRE + name;
        boolean exists = redisUtils.exists(key);
        if(exists){

            StudentExaminationInfoVO info = redisUtils.get(key, StudentExaminationInfoVO.class);
            return info;
        }else {
            StudentExaminationInfo info = infoRepository.findInfoByName(name);
            if(info==null){
                return null;
            }
            StudentExaminationInfoVO infoVO = new StudentExaminationInfoVO();
            BeanUtils.copyProperties(info,infoVO);
            redisUtils.set(key,infoVO,-1);
            return infoVO;
        }
    }

    @Override
    public List<StudentExaminationInfoVO> findInfoByNameFuzzy(String name) {
        String key = PrefixConst.MATCH_NAME_FUZZY_PRE + name;
        boolean exists = redisUtils.exists(key);
        if(exists){

            List<StudentExaminationInfoVO> info = redisUtils.get(key, List.class);
            return info;
        }else {
            List<StudentExaminationInfo> info = infoRepository.findInfoByNameFuzzy(name);
            if(info==null&&info.isEmpty()){
                return null;
            }
            List<StudentExaminationInfoVO> infoVO = new ArrayList<>();
            for (StudentExaminationInfo temp : info) {
                StudentExaminationInfoVO vo = new StudentExaminationInfoVO();
                BeanUtils.copyProperties(temp,vo);
                infoVO.add(vo);
            }
            redisUtils.set(key,infoVO,-1);
            return infoVO;
        }
    }

    @Override
    public List<StudentExaminationInfoVO> findInfoByAgeRange(Integer begin, Integer end) {
        List<StudentExaminationInfo> infos = infoRepository.findInfoByAgeRange(begin, end);
        if(infos==null&&infos.isEmpty()){
            return null;
        }
        List<StudentExaminationInfoVO> infoVO = new ArrayList<>();
        for (StudentExaminationInfo temp : infos) {
            StudentExaminationInfoVO vo = new StudentExaminationInfoVO();
            BeanUtils.copyProperties(temp,vo);
            infoVO.add(vo);
        }
        return infoVO;
    }

    @Override
    public List<StudentExaminationInfoVO> findBothNearSighted() {
        String key = PrefixConst.BOTH_NEAR_SIGHTED;
        boolean exists = redisUtils.exists(key);
        if(exists){

            List<StudentExaminationInfoVO> info = redisUtils.get(key, List.class);
            return info;
        }else {
            List<StudentExaminationInfo> info = infoRepository.findBothNearSighted();
            if(info==null&&info.isEmpty()){
                return null;
            }
            List<StudentExaminationInfoVO> infoVO = new ArrayList<>();
            for (StudentExaminationInfo temp : info) {
                StudentExaminationInfoVO vo = new StudentExaminationInfoVO();
                BeanUtils.copyProperties(temp,vo);
                infoVO.add(vo);
            }
            redisUtils.set(key,infoVO,-1);
            return infoVO;
        }
    }

    @Override
    public List<StudentExaminationInfoVO> findNearSighted() {
        String key = PrefixConst.ONE_NEAR_SIGHTED;
        boolean exists = redisUtils.exists(key);
        if(exists){

            List<StudentExaminationInfoVO> info = redisUtils.get(key, List.class);
            return info;
        }else {
            List<StudentExaminationInfo> info = infoRepository.findNearSighted();
            if(info==null&&info.isEmpty()){
                return null;
            }
            List<StudentExaminationInfoVO> infoVO = new ArrayList<>();
            for (StudentExaminationInfo temp : info) {
                StudentExaminationInfoVO vo = new StudentExaminationInfoVO();
                BeanUtils.copyProperties(temp,vo);
                infoVO.add(vo);
            }
            redisUtils.set(key,infoVO,-1);
            return infoVO;
        }
    }

    @Override
    public List<StudentExaminationInfoVO> findNotNearSighted() {
        String key = PrefixConst.NONE_NEAR_SIGHTED;
        boolean exists = redisUtils.exists(key);
        if(exists){

            List<StudentExaminationInfoVO> info = redisUtils.get(key, List.class);
            return info;
        }else {
            List<StudentExaminationInfo> info = infoRepository.findNotNearSighted();
            if(info==null&&info.isEmpty()){
                return null;
            }
            List<StudentExaminationInfoVO> infoVO = new ArrayList<>();
            for (StudentExaminationInfo temp : info) {
                StudentExaminationInfoVO vo = new StudentExaminationInfoVO();
                BeanUtils.copyProperties(temp,vo);
                infoVO.add(vo);
            }
            redisUtils.set(key,infoVO,-1);
            return infoVO;
        }
    }

    @Override
    public List<StudentExaminationInfoVO> findByMeasurementsRange(Integer low, Integer high) {
        List<StudentExaminationInfo> infos = infoRepository.findByMeasurementsRange(low, high);
        if(infos==null&&infos.isEmpty()){
            return null;
        }
        List<StudentExaminationInfoVO> infoVO = new ArrayList<>();
        for (StudentExaminationInfo temp : infos) {
            StudentExaminationInfoVO vo = new StudentExaminationInfoVO();
            BeanUtils.copyProperties(temp,vo);
            infoVO.add(vo);
        }
        return infoVO;
    }

    @Override
    public Map<String, Long> statisticByClassName() {
        String key = PrefixConst.AGGS_CLASS;
        boolean exists = redisUtils.exists(key);
        if(exists){

            Map<String, Long> info = redisUtils.get(key, Map.class);
            return info;
        }else {
            Map<String, Long> info = infoRepository.statisticByClassName();

            redisUtils.set(key,info,-1);
            return info;
        }
    }

    @Override
    public Map<String, Double> statisticByHeight() {
        String key = PrefixConst.AGGS_HEIGHT;
        boolean exists = redisUtils.exists(key);
        if(exists){

            Map<String, Double> info = redisUtils.get(key, Map.class);
            return info;
        }else {
            Map<String, Double> info = infoRepository.statisticByHeight();
            redisUtils.set(key,info,-1);
            return info;
        }
    }
}
