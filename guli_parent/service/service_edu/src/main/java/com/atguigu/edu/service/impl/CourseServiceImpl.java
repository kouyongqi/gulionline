package com.atguigu.edu.service.impl;

import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.*;
import com.atguigu.edu.entity.frontvo.CourseInfoVo;
import com.atguigu.edu.entity.frontvo.CourseQueryVo;
import com.atguigu.edu.entity.vo.CourseInfo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.mapper.ChapterMapper;
import com.atguigu.edu.mapper.CourseDescriptionMapper;
import com.atguigu.edu.mapper.CourseMapper;
import com.atguigu.edu.mapper.VideoMapper;
import com.atguigu.edu.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-07-31
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private VodClient vodClient;

    @Override
    public String addCourseInfo(CourseInfo courseInfo) {
        //保存课程信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo,course);

        int insert = baseMapper.insert(course);

        if (insert<=0){
            throw new DefineException(20001,"课程数据添加失败");
        }

        String cid = course.getId();
        String description = courseInfo.getDescription();

        //保存课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(description);
        courseDescription.setId(cid);

        int insert1 = courseDescriptionMapper.insert(courseDescription);

        if (insert1<=0){
            throw new DefineException(20001,"课程描述数据添加失败");
        }

        //返回课程id
        return cid;
    }

    @Override
    public CourseInfo getCourseInfoById(String courseid) {

        CourseInfo courseInfo = new CourseInfo();

        //先查询课程信息
        Course course = baseMapper.selectById(courseid);

        if (course==null){
            throw new DefineException(20001,"课程数据未找到");
        }

        BeanUtils.copyProperties(course,courseInfo);

        CourseDescription courseDescription = courseDescriptionMapper.selectById(courseid);

        if (courseDescription==null){
            throw new DefineException(20001,"课程描述数据未找到");
        }

        courseInfo.setDescription(courseDescription.getDescription());

        return courseInfo;
    }

    @Override
    public void updateCourseInfoById(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo,course);

        baseMapper.updateById(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfo.getId());
        courseDescription.setDescription(courseInfo.getDescription());

        courseDescriptionMapper.updateById(courseDescription);

    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String courseid) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseid);
        return publishCourseInfo;
    }

    @Override
    public void publishedCourse(String courseid) {
        Course course = new Course();
        course.setId(courseid);
        course.setStatus("Normal");
        baseMapper.updateById(course);
    }

    @Override
    public int removeByCourseId(String courseid) {
        //删除课时信息和课时视频（阿里云）
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseid);
        //只查询video_source_id的结果
        //videoWrapper.select("video_source_id");
        List<Video> videos = videoMapper.selectList(videoWrapper);

        if (videos.size()>0){
            ArrayList<String> idstrings = new ArrayList<>();
            for (Video video :videos){
                String videoSourceId = video.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)){
                    idstrings.add(videoSourceId);
                }
            }
            vodClient.deleteVodBatch(idstrings);
            videoMapper.delete(videoWrapper);
        }

        //删除章节描述信息
        QueryWrapper<CourseDescription> courseDescriptionQueryWrapper = new QueryWrapper<>();

        courseDescriptionQueryWrapper.eq("id",courseid);

        courseDescriptionMapper.delete(courseDescriptionQueryWrapper);

        //删除章节信息
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();

        chapterQueryWrapper.eq("course_id",courseid);

        chapterMapper.delete(chapterQueryWrapper);

        //删除课程信息
        int i = baseMapper.deleteById(courseid);

        if (i<=0){
            throw new DefineException(20001,"删除失败");
        }else {
            return i;
        }

    }

    @Cacheable(key = "'selectCourseList'",value = "course")
    @Override
    public List<Course> getHotCourseList() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("LIMIT 8");

        List<Course> courseList = courseMapper.selectList(courseQueryWrapper);

        return courseList;
    }

    @Override
    public List<Course> getCourseByteacherId(QueryWrapper<Course> courseQueryWrapper) {

        List<Course> courses = courseMapper.selectList(courseQueryWrapper);

        return courses;
    }

    @Override
    public Map<String, Object> getCoursePageInfoByVo(Page<Course> coursePage, CourseQueryVo courseQueryVo) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();

        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();

        //多条件拼接
        if (!StringUtils.isEmpty(subjectParentId)){
            courseQueryWrapper.eq("subject_parent_id",subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)){
            courseQueryWrapper.eq("subject_id",subjectId);
        }

        if (!StringUtils.isEmpty(buyCountSort)){
            courseQueryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(gmtCreateSort)){
            courseQueryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(priceSort)){
            courseQueryWrapper.orderByDesc("price");
        }

        courseMapper.selectPage(coursePage,courseQueryWrapper);

        HashMap<String, Object> courseHashMap = new HashMap<>();

        courseHashMap.put("current",coursePage.getCurrent());
        courseHashMap.put("total",coursePage.getTotal());
        courseHashMap.put("size",coursePage.getSize());
        courseHashMap.put("pages",coursePage.getPages());
        courseHashMap.put("next",coursePage.hasNext());
        courseHashMap.put("previous",coursePage.hasPrevious());
        courseHashMap.put("courselist",coursePage.getRecords());

        return courseHashMap;
    }

    @Override
    public CourseInfoVo getCourseInfoVoById(String courseid) {

        CourseInfoVo courseInfoVo = courseMapper.getCourseInfoVo(courseid);

        return courseInfoVo;
    }
}
