package com.atguigu.edu.client;

import com.atguigu.edu.client.impl.VodClientImpl;
import com.atguigu.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodClientImpl.class)  //被调用服务名
@Component
public interface VodClient {
    //被调用服务的方法（api） 参数必须一致
    @DeleteMapping("/vod/vod/deletevod/{videoSourceId}")
    public Result deleteVod(@PathVariable("videoSourceId") String videoSourceId);

    @DeleteMapping("/vod/vod/deletevodbatch")
    public Result deleteVodBatch(@RequestParam("vodSourceIdList") List<String> vodSourceIdList);
}
