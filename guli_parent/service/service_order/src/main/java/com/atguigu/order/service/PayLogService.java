package com.atguigu.order.service;

import com.atguigu.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author kou
 * @since 2021-08-17
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNativeInfo(String orderno);

    Map<String, String> queryPayStatus(String orderno);

    void updateOrderStatus(Map<String, String> map);
}
