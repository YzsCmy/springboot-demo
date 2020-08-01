package com.unionman.springbootdemo.controller;

import com.unionman.springbootdemo.pojo.dto.StudentDTO;
import com.unionman.springbootdemo.pojo.vo.ResponseVO;
import com.unionman.springbootdemo.pojo.vo.StudentVO;
import com.unionman.springbootdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentService studentService;

    /**
     * 添加学生
     */
    @PostMapping
    public ResponseVO addStudent(@RequestBody List<StudentDTO> stuList){
        studentService.addStudent(stuList);
        return ResponseVO.success();
    }

    /**
     * 编辑学生
     * @param studentDto
     */
    @PutMapping
    public ResponseVO updateStudent(@RequestBody StudentDTO studentDto){
        studentService.updateStudent(studentDto);
        return ResponseVO.success();
    }

    /**
     * 删除学生
     * @param studentId
     */
    @DeleteMapping("/{studentId}")
    public ResponseVO deleteStudent(@PathVariable("studentId") String studentId){
        studentService.deleteStudent(studentId);
        return ResponseVO.success();
    }

    /**
     * 查询学生
     */
    @GetMapping
    public ResponseVO<List<StudentVO>> findStudents(StudentDTO studentDTO){
        List<StudentVO> studentVoPageVo = studentService.findStudents(studentDTO);
        return ResponseVO.success(studentVoPageVo);
    }

    @GetMapping("/findByName")
    public ResponseVO<List<StudentVO>> findStudentByName(String name){
        List<StudentVO> studentVoPageVo = studentService.findStudentByName(name);
        return ResponseVO.success(studentVoPageVo);
    }

    @GetMapping("/findByNameFuzzy")
    public ResponseVO<List<StudentVO>> findStudentByNameFuzzy(String name){
        List<StudentVO> studentVoPageVo = studentService.findStudentByNameFuzzy(name);
        return ResponseVO.success(studentVoPageVo);
    }
    @GetMapping("/age/{begin}/{end}")
    public ResponseVO<Object> findStudentByAgeRange(@PathVariable("begin")Integer begin,
                                                             @PathVariable("end")Integer end){
        if(begin > end){
            return ResponseVO.fail("年龄范围有误！");
        }
        if(begin < 0 ||end < 0){
            return ResponseVO.fail("年龄范围有误！");
        }
        List<StudentVO> studentVoPageVo = studentService.findStudentByAgeRange(begin,end);
        return ResponseVO.success(studentVoPageVo);
    }

    @GetMapping("/notname")
    public ResponseVO<Object> findStudentExceptName(String name){
        //if(begin > end){
        //    return ResponseVO.fail("年龄范围有误！");
        //}
        //if(begin < 0 ||end < 0){
        //    return ResponseVO.fail("年龄范围有误！");
        //}
        List<StudentVO> studentVoPageVo = studentService.findStudentExceptName(name);
        return ResponseVO.success(studentVoPageVo);
    }

    @PostMapping("/upload")
    public ResponseVO<Object> uploadHeadPortrait(MultipartFile file,String id) throws IOException {

        if(file==null){
            return ResponseVO.fail("文件为空，文件上传失败！");
        }

        StudentVO studentVO = studentService.uploadFile(file,id);
        if(studentVO==null){
            return ResponseVO.fail("不存在该学生！");
        }
        return ResponseVO.success(studentVO);
    }

}
