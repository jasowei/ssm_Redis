package com.alibaba.service;

import com.alibaba.bean.Student;

import java.util.List;

/**
 * Created by dllo on 17/11/27.
 */
public interface StudentService {
    List<Student> findAll();
}
