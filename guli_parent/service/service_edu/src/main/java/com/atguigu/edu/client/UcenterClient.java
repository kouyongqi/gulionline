package com.atguigu.edu.client;

import com.atguigu.edu.client.impl.UcenterClientImpl;
import com.atguigu.utils.MemberVo;
import com.atguigu.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @GetMapping("/ucenter/member/getmemberinfo/{memberid}")
    public MemberVo getMemberVoInfo(@PathVariable String memberid);
}
