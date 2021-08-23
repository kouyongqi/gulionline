package com.atguigu.edu.service;

import com.atguigu.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author kou
 * @since 2021-07-14
 */
/*IService中封装了一些方法*/
public interface TeacherService extends IService<Teacher> {

    List<Teacher> getHotTeacherList();

    Map<String, Object> getFrontPageInfo(Page<Teacher> teacherPage);
}
