package com.atguigu.statistics.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SearchObjectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;

    private String begin;

    private String end;
}
