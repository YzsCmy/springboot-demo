package com.unionman.springbootdemo.service.impl;

import com.unionman.springbootdemo.entity.Student;
import com.unionman.springbootdemo.fastdfs.FdfsClient;
import com.unionman.springbootdemo.pojo.dto.StudentDTO;
import com.unionman.springbootdemo.pojo.vo.StudentVO;
import com.unionman.springbootdemo.repository.StudentRepository;
import com.unionman.springbootdemo.service.StudentService;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FdfsClient fdfsClient;

    @Override
    public void addStudent(List<StudentDTO> studentDtoList) {
        List<Student> students = new ArrayList<>();
        studentDtoList.forEach(studentDTO -> {
            Student student = new Student();
            BeanUtils.copyProperties(studentDTO,student);
            students.add(student);
        });
        studentRepository.addStudent(students);

    }

    @Override
    public void updateStudent(StudentDTO studentDto) {
        Student student = new Student();
        student.setId(new ObjectId(studentDto.getId()));
        BeanUtils.copyProperties(studentDto,student);
        studentRepository.updateStudent(student);
    }

    @Override
    public void deleteStudent(String studentId) {
        studentRepository.deleteStudent(studentId);
    }

    @Override
    public List<StudentVO> findStudents(StudentDTO studentDTO) {
        List<Student> studentList = studentRepository.findStudents(studentDTO);
        if(studentList!=null && studentList.size()>0){
            List<StudentVO> studentVoList = new ArrayList<>();
            studentList.forEach(student->{
                StudentVO studentVo = new StudentVO();
                BeanUtils.copyProperties(student,studentVo);
                studentVo.setId(student.getId().toHexString());
                studentVoList.add(studentVo);
            });
            return studentVoList;
        }
        return null;
    }

    @Override
    public List<StudentVO> findStudentByName(String name) {
        //名字为空默认返回null
        if(StringUtils.isEmpty(name)){
            return null;
        }
        List<StudentVO> studentVoList = new ArrayList<>();
        List<Student> students = studentRepository.findStudentByName(name);
        students.forEach(student-> {
            StudentVO studentVo = new StudentVO();
            BeanUtils.copyProperties(student, studentVo);
            studentVo.setId(student.getId().toHexString());
            studentVoList.add(studentVo);
        });
        return studentVoList;
    }

    @Override
    public List<StudentVO> findStudentByNameFuzzy(String name) {
        List<StudentVO> studentVoList = new ArrayList<>();
        if(StringUtils.isEmpty(name)){
            //名字为空默认查询所有
            List<Student> studentList = studentRepository.findStudents(new StudentDTO());
            studentList.forEach(student->{
                StudentVO studentVo = new StudentVO();
                BeanUtils.copyProperties(student,studentVo);
                studentVo.setId(student.getId().toHexString());
                studentVoList.add(studentVo);
            });
            return studentVoList;
        }
        List<Student> stulist = studentRepository.findStudentByNameFuzzy(name);

        stulist.forEach(student-> {
            StudentVO studentVo = new StudentVO();
            BeanUtils.copyProperties(student, studentVo);
            studentVo.setId(student.getId().toHexString());
            studentVoList.add(studentVo);
        });
        return studentVoList;
    }

    @Override
    public List<StudentVO> findStudentByAgeRange(Integer begin, Integer end) {
        List<StudentVO> studentVoList = new ArrayList<>();
        List<Student> stulist = studentRepository.findStudentByAgeRange(begin,end);

        stulist.forEach(student-> {
            StudentVO studentVo = new StudentVO();
            BeanUtils.copyProperties(student, studentVo);
            studentVo.setId(student.getId().toHexString());
            studentVoList.add(studentVo);
        });

        return studentVoList;
    }

    @Override
    public List<StudentVO> findStudentExceptName(String name) {
        List<StudentVO> studentVoList = new ArrayList<>();
        List<Student> stulist = studentRepository.findStudentExceptName(name);

        stulist.forEach(student-> {
            StudentVO studentVo = new StudentVO();
            BeanUtils.copyProperties(student, studentVo);
            studentVo.setId(student.getId().toHexString());
            studentVoList.add(studentVo);
        });
        return studentVoList;
    }

    @Override
    public StudentVO uploadFile(MultipartFile file, String id) throws IOException {

        //获取文件扩展名
        String extName = FilenameUtils.getExtension(file.getOriginalFilename());
        //上传文件到fdfs
        String url = fdfsClient.uploadFile(file.getInputStream(),file.getSize(),extName);
        Student student = studentRepository.findById(id);
        if(student==null){
            return null;
        }
        student.setHeadPortrait(url);
        studentRepository.updateStudent(student);
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(student,studentVO);
        studentVO.setId(student.idToHex());
        return studentVO;
    }
}
