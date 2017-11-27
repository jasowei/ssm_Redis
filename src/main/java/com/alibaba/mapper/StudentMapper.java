package com.alibaba.mapper;

import com.alibaba.bean.Student;

import java.util.List;

/**
 * Created by dllo on 17/11/27.
 */
public interface StudentMapper {

    List<Student> findAllStudent();

}
