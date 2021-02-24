package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Abner
 * @since 2020-11-18
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //统计某一天的注册人数.生成统计数据
    void registerCount(String day);

    //图表显示.返回两个部分数据.日期 json 字符串数组.数量 json 数组
    Map<String,Object> getShowData(String type, String begin, String end);
}
