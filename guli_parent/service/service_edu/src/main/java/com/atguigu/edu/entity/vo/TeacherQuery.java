package com.atguigu.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer level;

    //注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    //begin和end对应表gmt_create
    private String begin;

    private String end;
}
