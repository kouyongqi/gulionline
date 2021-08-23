package com.atguigu.order.controller;


import com.atguigu.order.service.PayLogService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/order/pay-log")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //返回支付二维码和订单信息
    @GetMapping("/createNative/{orderno}")
    public Result createNative(@PathVariable String orderno){

        Map<String,Object> nativeinfo = payLogService.createNativeInfo(orderno);

        return Result.ok().data(nativeinfo);
    }

    //查询支付状态/添加支付记录，更新订单状态
    @GetMapping("/queryPayStatus/{orderno}")
    public Result queryPayStatus(@PathVariable String orderno){
        //调用查询接口
        Map<String, String> map = payLogService.queryPayStatus(orderno);

        if (map == null) {//出错
            return Result.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        //支付中
        return Result.ok().code(25000).message("支付中");
    }

}

