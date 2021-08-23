package com.atguigu.edu.controller.front;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.TeacherService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/user")
public class UserController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;


    @GetMapping("getallcourseandteacher")
    public Result getAllCourseAndTeacher(){
        List<Teacher> teacherList = teacherService.getHotTeacherList();
        List<Course> courseList = courseService.getHotCourseList();

        return Result.ok().data("teacherList",teacherList).data("courseList",courseList);
    }
}
