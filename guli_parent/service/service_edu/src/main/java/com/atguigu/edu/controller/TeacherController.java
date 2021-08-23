package com.atguigu.edu.controller;


import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.entity.vo.TeacherQuery;
import com.atguigu.edu.service.TeacherService;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-07-14
 */
@RestController  /*responsebody+contorller*/
@RequestMapping("/edu/teacher")
public class TeacherController {


    @Autowired
    private TeacherService teacherService;

    /*查询所有讲师*/
    @GetMapping("/findAll")
    public Result findTeacher(){
        List<Teacher> list = teacherService.list(null);
        /*返回json字符串*/
        return Result.ok().data("items",list);
    }

    /*删除讲师*/
    @DeleteMapping("/{id}")
    public Result deleteTeacher(@PathVariable String id){
        boolean b = teacherService.removeById(id);

        if (b==true){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /*基本分页讲师*/
    @GetMapping("/pageTeacher/{current}/{limit}")
    public Result pageTeacher(@PathVariable Long current,@PathVariable Long limit){

        Page<Teacher> teacherPage = new Page<>(current,limit);

        /*调用page方法，分页数据会被封装到teacherPage中*/
        teacherService.page(teacherPage,null);

        if (teacherPage!=null){
            long total = teacherPage.getTotal();
            List<Teacher> records = teacherPage.getRecords();
            HashMap<String, Object> Map = new HashMap<>();
            Map.put("total",total);
            Map.put("records",records);
            return Result.ok().data(Map);
        }else {
            return Result.error();
        }
    }

    /*条件分页讲师*/
    @PostMapping("/pageTeacherConditionnal/{current}/{limit}")
    public Result pageTeacherConditionnal(@PathVariable Long current, @PathVariable Long limit,
                                          @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<Teacher> teacherPage = new Page<>(current,limit);

        /*条件查询定义(动态sql)*/

        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            teacherQueryWrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)){
            teacherQueryWrapper.eq("level",level);
        }

        /*这里没有使用between的原因（可能开始时间有，结束时间没有等情况）*/
        if (!StringUtils.isEmpty(begin)){
            teacherQueryWrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)){
            teacherQueryWrapper.lt("gmt_create",end);
        }

        //按创建时间升序排列
        teacherQueryWrapper.orderByAsc("gmt_create");

        /*调用page方法，分页数据会被封装到teacherPage中*/
        teacherService.page(teacherPage,teacherQueryWrapper);

        if (teacherPage!=null){
            long total = teacherPage.getTotal();
            List<Teacher> records = teacherPage.getRecords();
            HashMap<String, Object> Map = new HashMap<>();
            Map.put("total",total);
            Map.put("records",records);
            return Result.ok().data(Map);
        }else {
            return Result.error();
        }

    }

    /*添加讲师*/
    @PostMapping("/saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher){

        boolean save = teacherService.save(teacher);

        if (save){
            return Result.ok();
        }else {
            return Result.error();
        }

    }

    /*查询回显讲师*/
    @GetMapping("/getTeacher/{id}")
    public Result getTeacher(@PathVariable String id){

        Teacher teacher = teacherService.getById(id);

        if (teacher!=null){
            return Result.ok().data("teacher",teacher);
        }else {
            return Result.error();
        }
    }

    /*修改讲师数据*/
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher){

        boolean b = teacherService.updateById(teacher);

        if (b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

}

