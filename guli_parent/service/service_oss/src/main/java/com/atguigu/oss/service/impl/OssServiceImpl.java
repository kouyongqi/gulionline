package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.OssProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadAvatar(MultipartFile file){
        try {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
            String endpoint = OssProperties.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
            String accessKeyId = OssProperties.ACCESS_KEY_ID;
            String accessKeySecret = OssProperties.ACCESS_KEY_SECRET;
            String backetName = OssProperties.BUCKET_NAME;
            String fileName = file.getOriginalFilename();

            //以时间格式作为文件名进行分类，并添加uuid作为唯一值
            fileName= UUID.randomUUID().toString().replaceAll("-","")+fileName;
            fileName = new DateTime().toString("yyyy/MM/dd")+"/"+fileName;

        // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt或者以文件名称）。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(backetName, fileName, inputStream);
        // 关闭OSSClient。
            ossClient.shutdown();

            return "https://edu-kou.oss-cn-beijing.aliyuncs.com/"+fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
