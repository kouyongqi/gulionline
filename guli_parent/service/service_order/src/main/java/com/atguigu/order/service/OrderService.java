package com.atguigu.order.service;

import com.atguigu.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author kou
 * @since 2021-08-17
 */
public interface OrderService extends IService<Order> {

    String inserOrder(String courseid, String memberid);
}
