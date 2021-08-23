package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.utils.Result;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.VodInit;
import com.atguigu.vod.utils.VodProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@RequestMapping("/edu/vod")
@RequestMapping("/vod/vod")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadfile")
    public Result uploadFile(MultipartFile file){
        String vodid = vodService.uploadVodFile(file);

        if (vodid!=null){
            return Result.ok().data("vodid",vodid);
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("/deletevod/{videoSourceId}")
    public Result deleteVod(@PathVariable String videoSourceId){
        try {
            DefaultAcsClient client = VodInit.initVodClient(VodProperties.ACCESS_KEY_ID, VodProperties.ACCESS_KEY_SECRET);

            DeleteVideoResponse response = new DeleteVideoResponse();

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(videoSourceId);

            response = client.getAcsResponse(request);

            return Result.ok().data("RequestId",response.getRequestId());
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        return Result.error();
    }

    //批量删除
    @DeleteMapping("/deletevodbatch")
    public Result deleteVodBatch(@RequestParam("vodSourceIdList") List<String> vodSourceIdList){
        try {
            DefaultAcsClient client = VodInit.initVodClient(VodProperties.ACCESS_KEY_ID, VodProperties.ACCESS_KEY_SECRET);

            DeleteVideoResponse response = new DeleteVideoResponse();

            DeleteVideoRequest request = new DeleteVideoRequest();

            String videoSourceIdList = StringUtils.join(vodSourceIdList, ",");

            request.setVideoIds(videoSourceIdList);

            response = client.getAcsResponse(request);

            return Result.ok().data("RequestId",response.getRequestId());
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        return Result.error();
    }


    //获取视频凭证
    @GetMapping("/getvodsourseid/{sourseid}")
    public Result getVodSourseId(@PathVariable String sourseid){

        //初始化GetVideoPlayAuthResponse
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            //初始化DefaultAcsClient
            DefaultAcsClient client = VodInit.initVodClient(VodProperties.ACCESS_KEY_ID, VodProperties.ACCESS_KEY_SECRET);

            //请求和响应
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(sourseid);
            response = client.getAcsResponse(request);

            return Result.ok().data("playAuth",response.getPlayAuth());
        } catch (Exception e) {
            throw new DefineException(20001,"视频播放凭证获取失败");
        }
    }

}
