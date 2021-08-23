package com.atguigu.edu.controller;


import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.entity.vo.CourseInfo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.entity.vo.CourseQuery;
import com.atguigu.edu.entity.vo.TeacherQuery;
import com.atguigu.edu.service.CourseService;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/saveCourse")
    public Result saveCourse(@RequestBody CourseInfo courseInfo){

        String courseid = courseService.addCourseInfo(courseInfo);

        return Result.ok().data("courseid",courseid);
    }

    @GetMapping("/getCourseInfo/{courseid}")
    public Result getCourseInfo(@PathVariable String courseid){

        CourseInfo courseInfo = courseService.getCourseInfoById(courseid);

        return Result.ok().data("courseinfo",courseInfo);
    }

    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfo courseInfo){
        courseService.updateCourseInfoById(courseInfo);
        return Result.ok();
    }

    @GetMapping("/getCoursePublish/{courseid}")
    public Result getCoursePublish(@PathVariable String courseid){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishInfo(courseid);

        return Result.ok().data("coursePublishVo",coursePublishVo);
    }

    //修改课程发布状态
    @PutMapping("/published/{courseid}")
    public Result published(@PathVariable String courseid){
        courseService.publishedCourse(courseid);
        return Result.ok();
    }

    /*基本分页课程*/
    @GetMapping("/pageCourse/{current}/{limit}")
    public Result pageTeacher(@PathVariable Long current,@PathVariable Long limit){

        //Page实现了IPage接口
        Page<Course> coursePage = new Page<>(current,limit);

        /*调用page方法，分页数据会被封装到coursePage中*/
        courseService.page(coursePage, null);

        if (coursePage!=null){
            long total = coursePage.getTotal();
            List<Course> records = coursePage.getRecords();
            HashMap<String, Object> Map = new HashMap<>();
            Map.put("total",total);
            Map.put("records",records);
            return Result.ok().data(Map);
        }else {
            return Result.error();
        }
    }

    /*条件分页课程*/
    @PostMapping("/pageCourseConditionnal/{current}/{limit}")
    public Result pageTeacherConditionnal(@PathVariable Long current, @PathVariable Long limit,
                                          @RequestBody(required = false) CourseQuery courseQuery){

        Page<Course> coursePage = new Page<>(current,limit);

        /*条件查询定义(动态sql)*/

        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();

        String name = courseQuery.getName();
        String price = courseQuery.getPrice();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            courseQueryWrapper.like("title",name);
        }

        if (!StringUtils.isEmpty(price)){
            courseQueryWrapper.eq("price",price);
        }

        if (!StringUtils.isEmpty(status)){
            courseQueryWrapper.eq("status",status);
        }

        /*这里没有使用between的原因（可能开始时间有，结束时间没有等情况）*/
        if (!StringUtils.isEmpty(begin)){
            courseQueryWrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)){
            courseQueryWrapper.lt("gmt_create",end);
        }

        //按创建时间升序排列
        courseQueryWrapper.orderByAsc("gmt_create");

        /*调用page方法，分页数据会被封装到teacherPage中*/
        courseService.page(coursePage,courseQueryWrapper);

        if (coursePage!=null){
            long total = coursePage.getTotal();
            List<Course> records = coursePage.getRecords();
            HashMap<String, Object> Map = new HashMap<>();
            Map.put("total",total);
            Map.put("records",records);
            return Result.ok().data(Map);
        }else {
            return Result.error();
        }

    }

    /*删除课程*/
    @DeleteMapping("/{courseid}")
    public Result deleteCourse(@PathVariable String courseid){
        int i = courseService.removeByCourseId(courseid);
        return Result.ok().data("deletcount",i);
    }
}

