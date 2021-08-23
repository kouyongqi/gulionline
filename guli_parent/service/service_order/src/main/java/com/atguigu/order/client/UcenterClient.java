package com.atguigu.order.client;

import com.atguigu.order.client.impl.UcenterClientImpl;
import com.atguigu.utils.MemberOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @GetMapping("/ucenter/member/memberorderInfo/{memberid}")
    public MemberOrderVo getMemberOrderInfo(@PathVariable("memberid") String memberid);
}
