package com.unionman.springbootdemo.service;


import com.unionman.springbootdemo.pojo.dto.StudentDTO;
import com.unionman.springbootdemo.pojo.vo.StudentVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    /**
     * 添加用户
     * @param studentDtoList
     */
    void addStudent(List<StudentDTO> studentDtoList);

    /**
     * 编辑用户
     * @param studentDto
     */
    void updateStudent(StudentDTO studentDto);

    /**
     * 删除用户
     * @param studentId
     */
    void deleteStudent(String studentId);

    /**
     * 查询用户
     */
    List<StudentVO> findStudents(StudentDTO studentDTO);

    /**
     * 按名字完全匹配查询
     * @param name 名字
     */
    List<StudentVO> findStudentByName(String name);
    /**
     * 按名字模糊匹配查询
     * @param name 名字
     */
    List<StudentVO> findStudentByNameFuzzy(String name);

    List<StudentVO> findStudentByAgeRange(Integer begin, Integer end);

    List<StudentVO> findStudentExceptName(String name);

    StudentVO uploadFile(MultipartFile file, String id) throws IOException;
}
