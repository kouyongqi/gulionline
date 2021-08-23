package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.excel.SubjectData;
import com.atguigu.edu.entity.subject.OneSubject;
import com.atguigu.edu.entity.subject.TwoSubject;
import com.atguigu.edu.listener.SubjectExcelListener;
import com.atguigu.edu.mapper.SubjectMapper;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-07-28
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectExcelListener subjectExcelListener;

    @Override
    public void readSubject(MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,subjectExcelListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getSubject() {
        //查询所有一级和二级课程(无条件)
        QueryWrapper<Subject> oneSubjectQuery= new QueryWrapper<>();

        oneSubjectQuery.eq("parent_id",0);

        List<Subject> onesubjects = baseMapper.selectList(oneSubjectQuery);

        QueryWrapper<Subject> twoSubjectQuery= new QueryWrapper<>();

        twoSubjectQuery.ne("parent_id",0);

        List<Subject> twosubjects = baseMapper.selectList(twoSubjectQuery);

        //封装一级课程
        List<OneSubject> finalSubjects = new ArrayList<>();

        for (Subject onesubject : onesubjects){

            OneSubject newoneSubject = new OneSubject();
            BeanUtils.copyProperties(onesubject,newoneSubject);

            finalSubjects.add(newoneSubject);

            List<TwoSubject> newchildren = new ArrayList<>();

            //封装二级课程到一级课程
            for (Subject twosubject : twosubjects){
                if (onesubject.getId().equals(twosubject.getParentId())){
                    TwoSubject newtwoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twosubject,newtwoSubject);
                    newchildren.add(newtwoSubject);
                }
            }

            newoneSubject.setChildren(newchildren);
        }


        return finalSubjects;
    }
}
