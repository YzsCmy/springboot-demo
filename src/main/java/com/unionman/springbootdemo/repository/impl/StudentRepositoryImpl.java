package com.unionman.springbootdemo.repository.impl;

import com.unionman.springbootdemo.entity.Student;
import com.unionman.springbootdemo.pojo.dto.StudentDTO;
import com.unionman.springbootdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;


@Repository
public class StudentRepositoryImpl implements StudentRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addStudent(List<Student> students) {
        mongoTemplate.insertAll(students);
    }

    @Override
    public void updateStudent(Student student) {
        Query updateQuery = new Query();
        updateQuery.addCriteria(Criteria.where("_id").is(student.getId().toHexString()));
        Update update = new Update();
        update.set("city",student.getCity());
        update.set("age",student.getAge());
        update.set("name",student.getName());
        update.set("sex",student.getSex());
        update.set("headPortrait",student.getHeadPortrait());
        mongoTemplate.updateMulti(updateQuery,update,Student.class);
    }

    @Override
    public void deleteStudent(String studentId) {
        Query deleteQuery = new Query();
        deleteQuery.addCriteria(Criteria.where("_id").is(studentId));
        mongoTemplate.remove(deleteQuery,Student.class);
    }

    @Override
    public List<Student> findStudents(StudentDTO studentDTO) {
        Query query = new Query();

        if(studentDTO.getCity() != null){
            Pattern pattern = Pattern.compile("^.*" + studentDTO.getCity() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("address").regex(pattern));
        }

        if(studentDTO.getAge() != null){
            query.addCriteria(Criteria.where("age").is(studentDTO.getAge()));

        }

        if(studentDTO.getSex() != null){
            query.addCriteria(Criteria.where("sex").is(studentDTO.getSex()));

        }


        if(studentDTO.getName() != null){
            query.addCriteria(Criteria.where("name").is(studentDTO.getName()));

        }

        // 排序方式
        //Sort sort = new Sort(Sort.Direction.DESC, "timestamp");

        // 分页条件
        //Pageable pageable = PageRequest.of(studentDTO.getCurrentPage(), studentDTO.getPageSize(), sort);
        List<Student> studentList = mongoTemplate.find(query, Student.class);
        long count = mongoTemplate.count(query, Student.class);
        return studentList;
    }

    @Override
    public List<Student> findStudentByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        List<Student> students = mongoTemplate.find(query, Student.class);
        return students;
    }

    @Override
    public List<Student> findStudentByNameFuzzy(String name) {
        Query query = new Query();
        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        List<Student> students = mongoTemplate.find(query, Student.class);
        return students;
    }

    @Override
    public Student findById(String id) {
        return mongoTemplate.findById(id,Student.class);
    }

    @Override
    public List<Student> findStudentByAgeRange(Integer begin, Integer end) {
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gte(begin).lte(end));
        List<Student> students = mongoTemplate.find(query, Student.class);
        return students;
    }

    @Override
    public List<Student> findStudentExceptName(String name) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria.norOperator(Criteria.where("name").is(name)));
        List<Student> students = mongoTemplate.find(query, Student.class);
        return students;
    }
}
