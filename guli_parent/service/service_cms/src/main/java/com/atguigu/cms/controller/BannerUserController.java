package com.atguigu.cms.controller;


import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author kou
 * @since 2021-08-10
 */
@RestController
@RequestMapping("/cms/banneruser")
public class BannerUserController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("getallbanner")
    public Result getAllBanner(){

        List<CrmBanner> bannerList = crmBannerService.getList();

        return Result.ok().data("banners",bannerList);
    }

}

