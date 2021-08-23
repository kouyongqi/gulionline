package com.atguigu.edu.controller;


import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.service.VideoService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-07-31
 */
@RestController
@RequestMapping("/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;


    @Autowired
    private VodClient vodClient;


    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody Video video){
        boolean save = videoService.save(video);

        return Result.ok();
    }

    //删除课时小节时，要把视频也删除(使用springcloud调用微服务)
    @DeleteMapping("/deleteVideo/{videoid}")
    public Result deleteVideo(@PathVariable String videoid){

        //删除视频
        Video video = videoService.getById(videoid);
        String videoSourceId = video.getVideoSourceId();

        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteVod(videoSourceId);
        }

        //删除小节
        boolean b = videoService.removeById(videoid);

        return Result.ok();
    }

    @GetMapping("/getVideo/{videoid}")
    public Result getVideo(@PathVariable String videoid){
        Video video = videoService.getById(videoid);

        return Result.ok().data("video",video);
    }

    @PostMapping("/updateVideo")
    public Result updateVideo(@RequestBody Video video){
        boolean update = videoService.updateById(video);

        return Result.ok();
    }

}

