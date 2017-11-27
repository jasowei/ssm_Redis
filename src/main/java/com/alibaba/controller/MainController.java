package com.alibaba.controller;

import com.alibaba.bean.Student;
import com.alibaba.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dllo on 17/11/27.
 */

@Controller
public class MainController {

    @Autowired
    @Qualifier("studentService")
    private StudentService studentService;

    @RequestMapping(value = "/home")
    public String homePage(){
        return "home";
    }


    //JSON / XML
    @ResponseBody
    @RequestMapping(value = "/getAll")
    public List<Student> jsonData(){

         List<Student> students=studentService.findAll();
        return students;
    }



}
