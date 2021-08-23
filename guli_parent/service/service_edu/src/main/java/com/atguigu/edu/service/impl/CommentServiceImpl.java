package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.Comment;
import com.atguigu.edu.mapper.CommentMapper;
import com.atguigu.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-08-17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
