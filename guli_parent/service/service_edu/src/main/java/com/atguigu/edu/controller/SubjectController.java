package com.atguigu.edu.controller;


import com.atguigu.edu.entity.subject.OneSubject;
import com.atguigu.edu.service.SubjectService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-07-28
 */
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/readfile")
    public Result readfile(MultipartFile file){
        subjectService.readSubject(file);
        return Result.ok();
    }

    @GetMapping("/getAllSubject")
    public Result getAllSubject(){

        List<OneSubject> Subjects = subjectService.getSubject();

        return Result.ok().data("subjects",Subjects);
    }

}

