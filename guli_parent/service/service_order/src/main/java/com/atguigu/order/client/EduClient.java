package com.atguigu.order.client;

import com.atguigu.order.client.impl.EduClientImpl;
import com.atguigu.order.client.impl.UcenterClientImpl;
import com.atguigu.utils.CourseOrderInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-edu",fallback = EduClientImpl.class)
public interface EduClient {
    @GetMapping("/edu/frontcourse/courseorderinfo/{courseid}")
    public CourseOrderInfoVo getCourseOrderInfo(@PathVariable("courseid") String courseid);
}
