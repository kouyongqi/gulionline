package com.atguigu.statistics.service.impl;

import com.atguigu.statistics.client.UcenterClient;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.entity.SearchObjectVo;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-08-19
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private DailyMapper dailyMapper;

    @Override
    public void insertStaticsInfo(String day) {

        //如果表中已经存在相同日期的数据，先删除再添加(或者先查询 存在修改/不存在添加)
        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();

        dailyQueryWrapper.eq("date_calculated",day);

        dailyMapper.delete(dailyQueryWrapper);


        Integer registerCount = ucenterClient.getRegisterCount(day);

        Daily daily = new Daily();
        daily.setRegisterNum(registerCount);
        daily.setDateCalculated(day);

        dailyMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getDateAndDataList(SearchObjectVo searchObjectVo) {
        String type = searchObjectVo.getType();
        String begin = searchObjectVo.getBegin();
        String end = searchObjectVo.getEnd();

        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();

        if (begin!=null){
            dailyQueryWrapper.ge("date_calculated",begin);
        }

        if (end!=null){
            dailyQueryWrapper.le("date_calculated",end);
        }

        if (type!=null){
            dailyQueryWrapper.select("date_calculated",type);
        }

        List<Daily> dailies = dailyMapper.selectList(dailyQueryWrapper);

        ArrayList<String> Dates = new ArrayList<>();
        ArrayList<Integer> Datas = new ArrayList<>();

        for (Daily daily : dailies){

            Dates.add(daily.getDateCalculated());

            //字符串内容相等最好用equals (type = "login_num") 判断的是地址
            if (type.equals("login_num")){
                Datas.add(daily.getLoginNum());
            }else if (type.equals("register_num")){
                Datas.add(daily.getRegisterNum());
            }else if (type.equals("video_view_num")){
                Datas.add(daily.getVideoViewNum());
            }else {
                Datas.add(daily.getCourseNum());
            }
        }

        HashMap<String, Object> dataHashMap = new HashMap<>();
        dataHashMap.put("DateList",Dates);
        dataHashMap.put("DataList",Datas);

        return dataHashMap;
    }
}
