package com.atguigu.edu.client.impl;

import com.atguigu.edu.client.OrderClient;
import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient {

    @Override
    public boolean judgeIfPay(String courseid, String memberid) {
        return false;
    }
}
