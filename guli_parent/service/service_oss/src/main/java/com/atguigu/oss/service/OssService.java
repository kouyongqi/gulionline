package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface OssService {
    String uploadAvatar(MultipartFile file);
}
