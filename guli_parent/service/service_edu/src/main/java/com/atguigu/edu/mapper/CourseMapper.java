package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.frontvo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author kou
 * @since 2021-07-31
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getPublishCourseInfo(String courseid);

    CourseInfoVo getCourseInfoVo(String courseid);
}
