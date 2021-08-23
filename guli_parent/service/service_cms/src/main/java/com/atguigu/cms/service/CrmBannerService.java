package com.atguigu.cms.service;

import com.atguigu.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author kou
 * @since 2021-08-10
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List getList();
}
