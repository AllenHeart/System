package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-10-22
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //@Cacheable Springboot缓存注解
    @Cacheable(value = "banner",key = "'selectIndexList'")

    //根据 List 集合查询 Banner 所有的数据
    @Override
    public List<CrmBanner> selectAllBanner() {

        //根据 edu_course banner id 进行降序排序 order by limit desc 8 显示排序之后两条记录
        QueryWrapper<CrmBanner> wrapper  = new QueryWrapper<>();
        wrapper.orderByDesc("id");

        //last方法.拼接 sql 语句查询两条记录
        wrapper.last("limit 2");

        //调用 baseMapper实现查询 Banner 所有的数据
        List<CrmBanner> list = baseMapper.selectList(null);

        return list;
    }
}
