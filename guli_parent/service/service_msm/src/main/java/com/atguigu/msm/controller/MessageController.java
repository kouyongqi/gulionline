package com.atguigu.msm.controller;


import com.atguigu.msm.service.MessageService;
import com.atguigu.msm.utils.RandomUtil;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msm/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping("/send/{phone}")
    public Result sendMessage(@PathVariable String phone){

        //可以从redis获取到验证码(还未过期)
        String code = redisTemplate.opsForValue().get(phone);

        if(!StringUtils.isEmpty(code)) return Result.ok();

        //获取不到则从阿里云/第三方短信服务重新发送
        String fourBitRandom = RandomUtil.getFourBitRandom();

        HashMap<String, String> params = new HashMap<>();
        params.put("code",fourBitRandom);

        boolean isSend = messageService.send(params,phone);

        if(isSend) {
            //设置验证码过期时间 把数据放入redis中
            redisTemplate.opsForValue().set(phone, fourBitRandom,5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("发送短信失败");
        }
    }

}
