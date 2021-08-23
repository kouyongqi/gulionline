package com.atguigu.edu.service;

import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author kou
 * @since 2021-07-28
 */
public interface SubjectService extends IService<Subject> {
    void readSubject(MultipartFile file);

    List<OneSubject> getSubject();
}
