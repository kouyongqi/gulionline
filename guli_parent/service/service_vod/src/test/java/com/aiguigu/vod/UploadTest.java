package com.aiguigu.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

public class UploadTest {
    //账号AK信息请填写(必选)
    private static final String accessKeyId = "LTAI5tDgUNxzdHRc4mfDq5Ty";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "14Vpz772k4LRXWOAkThA54mmg36MtK";

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("cb0b960321da462f9fe15d5fcfb0a758");
        return client.getAcsResponse(request);
    }

    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");  //VideoId
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        // 一、视频文件上传
        // 视频标题(必选)
        String title = "测试视频上传";
        // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。
        // 任何上传方式文件名必须包含扩展名
        String fileName = "D:/百度云/1-阿里云上传测试视频/6 - What If I Want to Move Faster.mp4";
        // 本地文件上传
        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);

        /**
        // 待上传视频的网络流地址
        String url = "http://xxxx.xxxx.com/xxxx.mp4";
        // 2.网络流上传
        // 文件扩展名，当url中不包含扩展名时，需要设置该参数
        String fileExtension = "mp4";
        //testUploadURLStream(accessKeyId, accessKeySecret, title, url, fileExtension);
        **/

    }
}
