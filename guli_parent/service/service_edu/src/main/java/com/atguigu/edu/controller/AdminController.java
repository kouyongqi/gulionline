package com.atguigu.edu.controller;

import com.atguigu.utils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/admin")
public class AdminController {
    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    @GetMapping("/info")
    public Result getInfo(){
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","");
    }
}
