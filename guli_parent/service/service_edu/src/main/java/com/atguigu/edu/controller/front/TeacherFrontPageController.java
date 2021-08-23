package com.atguigu.edu.controller.front;


import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.TeacherService;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/frontteacher")
public class TeacherFrontPageController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/teacherpage/{currentpage}/{limit}")
    public Result teacherPage(@PathVariable Long currentpage,@PathVariable Long limit){
        //String到Long的转化
        Page<Teacher> teacherPage = new Page<Teacher>(currentpage, limit);

        Map<String,Object> teachermap = teacherService.getFrontPageInfo(teacherPage);

        return Result.ok().data(teachermap);
    }


    @GetMapping("/teacherinfoandcourseinfo/{teacherid}")
    public Result teacherInfoAndCourseInfo(@PathVariable String teacherid){

        Teacher teacher = teacherService.getById(teacherid);

        if (teacher!=null){
            QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
            courseQueryWrapper.eq("teacher_id",teacherid);
            List<Course> courseList = courseService.getCourseByteacherId(courseQueryWrapper);
            return Result.ok().data("teacher",teacher).data("courselist",courseList);
        }else {
            throw new DefineException(20001,"教师不存在");
        }
    }

}
