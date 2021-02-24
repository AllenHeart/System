package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Abner
 * @since 2020-10-22
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //根据 List 集合查询 Banner数据
    List<CrmBanner> selectAllBanner();
}
