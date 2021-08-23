package com.atguigu.statistics.shedule;

import com.atguigu.statistics.service.DailyService;
import com.atguigu.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SheduleTask {
    @Autowired
    private DailyService dailyService;
    //在每天凌晨1点，把前一天数据进行数据查询添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        dailyService.insertStaticsInfo(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
