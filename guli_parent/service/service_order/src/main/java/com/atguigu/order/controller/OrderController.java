package com.atguigu.order.controller;


import com.atguigu.base.exceptionhandler.DefineException;
import com.atguigu.order.entity.Order;
import com.atguigu.order.service.OrderService;
import com.atguigu.utils.JwtUtil;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/order/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/createOrder/{courseid}")
    public Result createOrder(@PathVariable String courseid,
                              HttpServletRequest request){
        String memberid = JwtUtil.getMemberIdByJwtToken(request);

        if (memberid!=null){
            String orderNo = orderService.inserOrder(courseid,memberid);
            return Result.ok().data("orderNo",orderNo);
        }else {
            throw new DefineException(20001,"请登录");
        }
    }

    @GetMapping("/getOrderInfo/{orderno}")
    public Result getOrderInfo(@PathVariable String orderno){

        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();

        orderQueryWrapper.eq("order_no",orderno);

        //目前是一个订单信息对应一个支付信息（没有购物车那种多个订单信息对应一个支付信息）
        Order order = orderService.getOne(orderQueryWrapper);

        if (order!=null){
            return Result.ok().data("orderinfo",order);
        }else {
            throw new DefineException(20001,"订单不存在");
        }
    }

    //根据课程id和用户id查询订单是否付款
    @GetMapping("/judgeIfPay/{courseid}/{memberid}")
    public boolean judgeIfPay(@PathVariable String courseid,@PathVariable String memberid){

        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();

        orderQueryWrapper.eq("course_id",courseid);
        orderQueryWrapper.eq("member_id",memberid);
        orderQueryWrapper.eq("status",1);

        int count = orderService.count(orderQueryWrapper);

        if (count>0){
            return true;
        }else {
            return false;
        }

    }

}

