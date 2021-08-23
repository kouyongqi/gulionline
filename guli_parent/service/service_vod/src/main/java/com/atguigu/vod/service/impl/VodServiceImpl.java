package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.VodProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVodFile(MultipartFile file) {
        String videoid = null;
        try {
            String accessKeyId = VodProperties.ACCESS_KEY_ID;
            String accessKeySecret = VodProperties.ACCESS_KEY_SECRET;
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));

            //初始化
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title,fileName,inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            videoid = null;
            if (response.isSuccess()) {
                videoid = response.getVideoId();  //VideoId
            } else {
                /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
                videoid = response.getVideoId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videoid;
    }
}
