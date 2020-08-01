package com.unionman.springbootdemo.repository;

import com.unionman.springbootdemo.entity.Student;
import com.unionman.springbootdemo.pojo.dto.StudentDTO;

import java.util.List;


public interface StudentRepository {

    /**
     * 添加用户
     * @param students
     */
    void addStudent(List<Student> students);

    /**
     * 编辑用户
     * @param student
     */
    void updateStudent(Student student);

    /**
     * 删除用户
     * @param studentId
     */
    void deleteStudent(String studentId);

    /**
     * 查询用户
     */
    List<Student> findStudents(StudentDTO studentDTO);

    /**
     * 按名字完全匹配查询
     * @param name 名字
     */
    List<Student> findStudentByName(String name);

    /**
     * 按年龄段检索
     * @param begin 起始年龄
     * @param end 结束年龄
     */
    List<Student> findStudentByAgeRange(Integer begin, Integer end);

    /**
     * 查询符合所给定名字以外的记录
     * @param name 名字
     */
    List<Student> findStudentExceptName(String name);

    /**
     * 按名字模糊查询
     * @param name 名字
     */
    List<Student> findStudentByNameFuzzy(String name);

    Student findById(String id);
}
