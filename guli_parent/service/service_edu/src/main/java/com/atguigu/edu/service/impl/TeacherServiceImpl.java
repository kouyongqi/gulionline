package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.mapper.TeacherMapper;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-07-14
 */
/*ServiceImpl中自动导入了mapper接口的代理对象，并实现了IService（默认），（如果我们有另外定义其他的方法，也可以进行实现）*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    @Cacheable(key = "'selectTeacherList'",value = "teacher") //第一次查询时 调数据库获取放到redis中（形成键值对 json数据） 之后从redis缓存中获取
    @Override
    public List<Teacher> getHotTeacherList() {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("LIMIT 4");

        List<Teacher> teacherList = teacherMapper.selectList(teacherQueryWrapper);

        return teacherList;

    }

    @Override
    public Map<String, Object> getFrontPageInfo(Page<Teacher> teacherPage) {

        teacherMapper.selectPage(teacherPage,null);

        HashMap<String, Object> teacherHashMap = new HashMap<>();

        teacherHashMap.put("current",teacherPage.getCurrent());
        teacherHashMap.put("total",teacherPage.getTotal());
        teacherHashMap.put("size",teacherPage.getSize());
        teacherHashMap.put("pages",teacherPage.getPages());
        teacherHashMap.put("next",teacherPage.hasNext());
        teacherHashMap.put("previous",teacherPage.hasPrevious());
        teacherHashMap.put("teacherlist",teacherPage.getRecords());

        return teacherHashMap;
    }
}
