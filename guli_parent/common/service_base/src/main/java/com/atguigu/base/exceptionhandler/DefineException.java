package com.atguigu.base.exceptionhandler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefineException extends RuntimeException{

    private Integer code;

    private String msg;

}
