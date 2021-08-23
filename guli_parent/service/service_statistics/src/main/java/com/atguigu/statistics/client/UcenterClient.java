package com.atguigu.statistics.client;

import com.atguigu.statistics.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/ucenter/member/getRegisterCount/{day}")
    public Integer getRegisterCount(@PathVariable("day") String day);
}
