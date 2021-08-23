package com.atguigu.order.service.impl;

import com.atguigu.order.client.UcenterClient;
import com.atguigu.order.entity.Order;
import com.atguigu.order.client.EduClient;
import com.atguigu.order.mapper.OrderMapper;
import com.atguigu.order.service.OrderService;
import com.atguigu.order.utils.OrderNoUtil;
import com.atguigu.utils.CourseOrderInfoVo;
import com.atguigu.utils.MemberOrderVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-08-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public String inserOrder(String courseid, String memberid) {

        CourseOrderInfoVo courseOrderInfo = eduClient.getCourseOrderInfo(courseid);

        MemberOrderVo memberOrderInfo = ucenterClient.getMemberOrderInfo(memberid);


        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseid);
        order.setCourseTitle(courseOrderInfo.getTitle());
        order.setCourseCover(courseOrderInfo.getCover());
        order.setTeacherName(courseOrderInfo.getTeacherName());
        order.setTotalFee(courseOrderInfo.getPrice());
        order.setMemberId(memberid);
        order.setMobile(memberOrderInfo.getMobile());
        order.setNickname(memberOrderInfo.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        orderMapper.insert(order);

        return order.getOrderNo();
    }
}
