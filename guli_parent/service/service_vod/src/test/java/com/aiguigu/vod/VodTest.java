package com.aiguigu.vod;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

public class VodTest {
    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        //设置视频id
        request.setVideoId("cb0b960321da462f9fe15d5fcfb0a758");
        return client.getAcsResponse(request);
    }

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("cb0b960321da462f9fe15d5fcfb0a758");
        return client.getAcsResponse(request);
    }

    /*测试获取视频信息*/
    public static void main(String[] argv) {
        //初始化GetPlayInfoResponse
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            //初始化DefaultAcsClient
            DefaultAcsClient client = VodInit.initVodClient("LTAI5tDgUNxzdHRc4mfDq5Ty", "14Vpz772k4LRXWOAkThA54mmg36MtK");
            response = getPlayInfo(client);
            //返回视频列表信息
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    //测试获取视频凭证
    @Test
    public void testauth(){
        //初始化GetVideoPlayAuthResponse
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            //初始化DefaultAcsClient
            DefaultAcsClient client = VodInit.initVodClient("LTAI5tDgUNxzdHRc4mfDq5Ty", "14Vpz772k4LRXWOAkThA54mmg36MtK");
            response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
