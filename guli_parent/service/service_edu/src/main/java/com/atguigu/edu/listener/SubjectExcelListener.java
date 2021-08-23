package com.atguigu.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.excel.SubjectData;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    @Autowired
    private SubjectService subjectService;

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //一次取一行数据
        if (subjectData==null){
            throw new DefineException(20001,"数据不存在");
        }

        //存在时就不再保存
        String oneName = subjectData.getOneSubjectName();
        Subject existsubject1 = existOneSubjectName(oneName);

        //不存在时保存
        if (existsubject1==null){
            //创建对象 添加属性
            existsubject1 = new Subject();
            existsubject1.setParentId("0");
            existsubject1.setTitle(oneName);
            //底层只有insert
            subjectService.save(existsubject1);
        }

        String pid = existsubject1.getId();

        String twoName = subjectData.getTwoSubjectName();
        Subject existsubject2 = existTwoSubjectName(twoName,pid);

        //不存在时
        if (existsubject2==null){
            //创建对象 添加属性
            existsubject2 = new Subject();
            existsubject2.setParentId(pid);
            existsubject2.setTitle(twoName);
            subjectService.save(existsubject2);
        }

    }

    public Subject existOneSubjectName(String onesubjectname){
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();

        subjectQueryWrapper.eq("title",onesubjectname);
        subjectQueryWrapper.eq("parent_id",0);

        Subject one = subjectService.getOne(subjectQueryWrapper);

        return one;
    }

    public Subject existTwoSubjectName(String twosubjectname,String pid){
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();

        subjectQueryWrapper.eq("title",twosubjectname);
        subjectQueryWrapper.eq("parent_id",pid);

        Subject two = subjectService.getOne(subjectQueryWrapper);

        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
