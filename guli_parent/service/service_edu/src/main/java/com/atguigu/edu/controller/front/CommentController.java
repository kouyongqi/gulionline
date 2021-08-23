package com.atguigu.edu.controller.front;


import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.edu.client.UcenterClient;
import com.atguigu.edu.entity.Comment;
import com.atguigu.edu.service.CommentService;
import com.atguigu.utils.JwtUtil;
import com.atguigu.utils.MemberVo;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/edu/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @Autowired
    private UcenterClient ucenterClient;


    @GetMapping("/getCourseComments/{courseid}/{current}/{limit}")
    public Result getCourseComments(@PathVariable String courseid,
                                    @PathVariable Long current,
                                    @PathVariable Long limit){

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id",courseid);

        Page<Comment> commentPage = new Page<>(current, limit);

        commentService.page(commentPage,commentQueryWrapper);

        long total = commentPage.getTotal();

        List<Comment> records = commentPage.getRecords();
        HashMap<String, Object> commentMap = new HashMap<>();
        commentMap.put("total",total);
        commentMap.put("records",records);

        return Result.ok().data(commentMap);

    }

    @PostMapping("/saveComment")
    public Result saveComment(@RequestBody Comment comment,
                              HttpServletRequest request){
        String memberId = JwtUtil.getMemberIdByJwtToken(request);

        if (StringUtils.isEmpty(memberId)){
            throw new DefineException(20001,"请登录");
        }

        MemberVo memberVoInfo = ucenterClient.getMemberVoInfo(memberId);

        comment.setMemberId(memberVoInfo.getMemberId());
        comment.setNickname(memberVoInfo.getNickname());
        comment.setAvatar(memberVoInfo.getAvatar());

        commentService.save(comment);

        return Result.ok();
    }


}

