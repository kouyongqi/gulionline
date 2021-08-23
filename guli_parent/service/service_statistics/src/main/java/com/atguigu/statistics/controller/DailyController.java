package com.atguigu.statistics.controller;


import com.atguigu.statistics.entity.SearchObjectVo;
import com.atguigu.statistics.service.DailyService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-08-19
 */
@RestController
@RequestMapping("/statistics/daily")     //"/" http://localhost:8008/
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @GetMapping("/saveStaticsInfo/{day}")
    public Result saveStaticsInfo(@PathVariable String day){

        dailyService.insertStaticsInfo(day);

        return Result.ok();
    }


    @PostMapping("/getDateAndData")
    public Result getDateAndData(@RequestBody SearchObjectVo searchObjectVo){

        Map<String,Object> ListMap= dailyService.getDateAndDataList(searchObjectVo);

        return Result.ok().data(ListMap);
    }

}

