package com.atguigu.edu.controller.front;


import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.edu.client.OrderClient;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.chapter.ChapterVo;
import com.atguigu.edu.entity.frontvo.CourseInfoVo;
import com.atguigu.edu.entity.frontvo.CourseQueryVo;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.CourseService;
import com.atguigu.utils.CourseOrderInfoVo;
import com.atguigu.utils.JwtUtil;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/frontcourse")
public class CourseFrontPageController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //获取课程分页数据
    @PostMapping("/conditionalteacherinfo/{current}/{limit}")
    public Result conditionalTeacherInfo(@PathVariable Long current, @PathVariable Long limit,
                                         @RequestBody(required = false) CourseQueryVo courseQueryVo){

        Page<Course> coursePage = new Page<>(current,limit);

        Map<String,Object> coursemap = courseService.getCoursePageInfoByVo(coursePage,courseQueryVo);

        return Result.ok().data(coursemap);
    }

    //获取课程详情数据
    @GetMapping("/courseinfoandchapterandvideo/{courseid}")
    public Result courseInfoAndChapterAndVideo(@PathVariable String courseid,
                                               HttpServletRequest httpServletRequest){

        String memberId = JwtUtil.getMemberIdByJwtToken(httpServletRequest);

        //根据课程id返回课程信息
        CourseInfoVo courseInfoVo = courseService.getCourseInfoVoById(courseid);

        //根据课程id返回章节和小节信息
        List<ChapterVo> chaptervos = chapterService.getChapter(courseid);

        if (memberId!=null){
            //根据课程id和用户id查询课程是否购买
            boolean isPay = orderClient.judgeIfPay(courseid, memberId);
            return Result.ok().data("courseinfovo",courseInfoVo).data("chaptervos",chaptervos).data("isPay",isPay);
        }else {
            throw new DefineException(20001,"请登录");
        }

    }

    //获取订单中课程数据
    @GetMapping("/courseorderinfo/{courseid}")
    public CourseOrderInfoVo getCourseOrderInfo(@PathVariable String courseid){

        CourseOrderInfoVo courseOrderInfoVo = new CourseOrderInfoVo();

        CourseInfoVo courseInfoVo = courseService.getCourseInfoVoById(courseid);

        BeanUtils.copyProperties(courseInfoVo,courseOrderInfoVo);

        return courseOrderInfoVo;
    }


}
