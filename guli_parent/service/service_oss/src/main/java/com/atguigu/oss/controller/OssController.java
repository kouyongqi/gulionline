package com.atguigu.oss.controller;

import com.atguigu.oss.service.OssService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("/edu/ossfile")
@RequestMapping("/oss/ossfile")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    public Result upload(MultipartFile file){

        String url = ossService.uploadAvatar(file);

        return Result.ok().data("url",url);
    }

}
