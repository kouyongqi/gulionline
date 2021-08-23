package com.atguigu.edu.client;

import com.atguigu.edu.client.impl.OrderClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-order",fallback = OrderClientImpl.class)
public interface OrderClient {
    @GetMapping("/order/order/judgeIfPay/{courseid}/{memberid}")
    public boolean judgeIfPay(@PathVariable("courseid") String courseid, @PathVariable("memberid") String memberid);
}
