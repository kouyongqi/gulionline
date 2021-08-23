package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {

    String uploadVodFile(MultipartFile file);
}
