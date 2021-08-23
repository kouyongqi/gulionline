package com.atguigu.cms.service.impl;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.mapper.CrmBannerMapper;
import com.atguigu.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author kou
 * @since 2021-08-10
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    private CrmBannerMapper crmBannerMapper;

    @Cacheable(key = "'selectBannerList'",value = "banner")   //key是由selectBannerList+banner组成
    @Override
    public List getList() {
        QueryWrapper<CrmBanner> crmbannerQueryWrapper = new QueryWrapper<>();
        crmbannerQueryWrapper.orderByDesc("id");
        crmbannerQueryWrapper.last("LIMIT 2");

        List<CrmBanner> crmBanners = crmBannerMapper.selectList(crmbannerQueryWrapper);

        return crmBanners;
    }
}
