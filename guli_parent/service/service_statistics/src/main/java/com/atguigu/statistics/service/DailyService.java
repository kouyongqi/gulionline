package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.entity.SearchObjectVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author kou
 * @since 2021-08-19
 */
public interface DailyService extends IService<Daily> {

    void insertStaticsInfo(String day);

    Map<String, Object> getDateAndDataList(SearchObjectVo searchObjectVo);
}
