package com.alibaba.service.impl;

import com.alibaba.bean.Student;
import com.alibaba.mapper.StudentMapper;
import com.alibaba.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dllo on 17/11/27.
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    public List<Student> findAll() {
        return studentMapper.findAllStudent();
    }
}
