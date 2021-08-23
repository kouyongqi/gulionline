package com.atguigu.edu.service;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.entity.frontvo.CourseInfoVo;
import com.atguigu.edu.entity.frontvo.CourseQueryVo;
import com.atguigu.edu.entity.vo.CourseInfo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author kou
 * @since 2021-07-31
 */
public interface CourseService extends IService<Course> {

    String addCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfoById(String courseid);

    void updateCourseInfoById(CourseInfo courseInfo);

    CoursePublishVo getCoursePublishInfo(String courseid);

    void publishedCourse(String courseid);

    int removeByCourseId(String courseid);

    List<Course> getHotCourseList();

    List<Course> getCourseByteacherId(QueryWrapper<Course> teacherQueryWrapper);

    Map<String, Object> getCoursePageInfoByVo(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    CourseInfoVo getCourseInfoVoById(String courseid);
}
