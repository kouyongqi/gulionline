package com.atguigu.edu.client.impl;

import com.atguigu.edu.client.VodClient;
import com.atguigu.utils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient {
    @Override
    public Result deleteVod(String videoSourceId) {
        return Result.error().message("删除视频出错");
    }

    @Override
    public Result deleteVodBatch(List<String> vodSourceIdList) {
        return Result.error().message("删除多个视频出错");
    }
}
