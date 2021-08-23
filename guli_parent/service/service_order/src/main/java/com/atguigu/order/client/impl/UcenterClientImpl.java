package com.atguigu.order.client.impl;

import com.atguigu.order.client.UcenterClient;
import com.atguigu.utils.MemberOrderVo;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public MemberOrderVo getMemberOrderInfo(String memberid) {
        return null;
    }
}
