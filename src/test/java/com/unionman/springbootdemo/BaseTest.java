package com.unionman.springbootdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.unionman.springbootdemo.constant.DataConst;
import com.unionman.springbootdemo.entity.Student;
import com.unionman.springbootdemo.entity.StudentExaminationInfo;
import com.unionman.springbootdemo.fastdfs.FdfsClient;
import com.unionman.springbootdemo.pojo.dto.StudentDTO;
import com.unionman.springbootdemo.repository.impl.StuExaminationInfoRepository;
import com.unionman.springbootdemo.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.zxp.esclientrhl.index.ElasticsearchIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BaseTest {

    private String testData = "[{\n" +
            "\t\t\"name\": \"樊少皇\",\n" +
            "\t\t\"age\": 42,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"北京\",\n" +
            "\t\t\"sex\": \"男\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"刘多欣\",\n" +
            "\t\t\"age\": 24,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"惠州\",\n" +
            "\t\t\"sex\": \"女\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"曾小风\",\n" +
            "\t\t\"age\": 26,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"桂林\",\n" +
            "\t\t\"sex\": \"男\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"熊七\",\n" +
            "\t\t\"age\": 23,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"桂林\",\n" +
            "\t\t\"sex\": \"女\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"贾里鹏\",\n" +
            "\t\t\"age\": 25,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"兰州\",\n" +
            "\t\t\"sex\": \"男\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"沙师弟\",\n" +
            "\t\t\"age\": 28,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"惠州\",\n" +
            "\t\t\"sex\": \"男\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"赵英俊\",\n" +
            "\t\t\"age\": 41,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"天津\",\n" +
            "\t\t\"sex\": \"男\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"刘小甜\",\n" +
            "\t\t\"age\": 35,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"武汉\",\n" +
            "\t\t\"sex\": \"女\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"文闲\",\n" +
            "\t\t\"age\": 23,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"惠州\",\n" +
            "\t\t\"sex\": \"男\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"罗利\",\n" +
            "\t\t\"age\": 30,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"玉林\",\n" +
            "\t\t\"sex\": \"女\"\n" +
            "\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"name\": \"李秀熊\",\n" +
            "\t\t\"age\": 24,\n" +
            "\t\t\"headPortrait\": \"xxx\",\n" +
            "\t\t\"city\": \"贵港\",\n" +
            "\t\t\"sex\": \"男\"\n" +
            "\n" +
            "\t}\n" +
            "\n" +
            "\n" +
            "]";

    @Autowired
    private StudentService studentService;
    @Autowired
    private StuExaminationInfoRepository infoRepository;
    @Autowired
    private FdfsClient fdfsClient;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ElasticsearchIndex elasticsearchIndex;

    @Test
    public void addStudents(){

        JSONArray array = JSON.parseArray(testData);
        List<StudentDTO> students = array.toJavaList(StudentDTO.class);
        studentService.addStudent(students);
    }

    @Test
    public void uploadFileTest() throws FileNotFoundException {

        String path = "/home/umlinux/Desktop/aaa.jpg";
        File file = new File(path);
        String url = fdfsClient.uploadFile(file);
        System.out.println(url);
    }
    @Test
    public void testfindbyid() throws FileNotFoundException {

        Student student = mongoTemplate.findById("5f226ac15b004b75b847aeba", Student.class);
        System.out.println(student);

    }

    @Test
    public void addStudentExamInfos(){

        JSONArray array = JSON.parseArray(DataConst.STUEXAMDATA);
        List<StudentExaminationInfo> infoList = array.toJavaList(StudentExaminationInfo.class);
        infoRepository.addStudentExaminationInfo(infoList);
    }

    @Test
    public void testIndex() throws Exception {
        boolean exists = elasticsearchIndex.exists(StudentExaminationInfo.class);
        System.out.println(exists);

    }

    @Test
    public void testAggs() throws Exception {
        Map<String, Double> stringDoubleMap = infoRepository.statisticByHeight();

    }
    @Test
    public void testStatistic() throws Exception {
        Map<String, Long> resMap = infoRepository.statisticByClassName();
        System.out.println(resMap);

    }

    @Test
    public void commonTest() throws Exception {
        StudentExaminationInfo bothNearSighted = infoRepository.findInfoByName("李静");
        System.out.println(bothNearSighted);
        //Map<String, Long> resMap = infoRepository.statisticByClassName();
        //System.out.println(resMap);

    }


}
