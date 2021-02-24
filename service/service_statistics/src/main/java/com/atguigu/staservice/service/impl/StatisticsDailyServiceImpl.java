package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-11-18
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired //自动装配 远程调用
    private UcenterClient ucenterClient;

    //统计某一天的注册人数.生成统计数据
    @Override
    public void registerCount(String day) {


        //添加记录之前删除统计表中相同的日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);


        /**
         * 1: 远程调用得到某一天的注册人数
         */
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        /**
         * 2: 把获取数据添加数据库.统计分析表里面
         */
        StatisticsDaily sta = new StatisticsDaily();

        //mysql设置值
        sta.setRegisterNum(countRegister); //注册人数
        sta.setDateCalculated(day);           //统计日期

        //RandomUtils 的工具类 随机数生成值
        sta.setVideoViewNum(RandomUtils.nextInt(100, 200)); //视频的播放数
        sta.setLoginNum(RandomUtils.nextInt(100, 200));//登录数量
        sta.setCourseNum(RandomUtils.nextInt(100, 200)); //课程的播放数量

        //记录 存储 加到mysql 中
        baseMapper.insert(sta);

    }

    //图表显示.返回两个部分数据.日期 json 字符串数组.数量 json 数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //对象封装操作类
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        //根据条件查询
        wrapper.between("date_calculated", begin, end);

        //指定查询一个字段.精准匹配查询
        wrapper.select("date_calculated", type);

        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        //因为返回有两个部分数据.日期.和 日期的对应数量
        //前端要求.数组将后端 转变为 json 的结构
        //对应后端 Java 代码是 list 集合
        //创建两个 list 集合.一个日期 list.一个数量 list

        //日期集合 return 数组形式
        List<String> dateList = new ArrayList<>();


        //数量集合 return 数组形式
        List<Integer> QuantityList = new ArrayList<>();

        //遍历查询所有数据 list 集合.进行封装
        for (int i = 0; i < staList.size(); i++) {
            //得到遍历的对象
            StatisticsDaily daily = staList.get(i);

            //封装日期的 List 集合
            dateList.add(daily.getDateCalculated());

            /**
             *  switch case 语句判断一个变量与一系列值中某个值是否相等
             */
            //封装对应的数量
            switch (type) {
                case "login_num":
                    QuantityList.add(daily.getLoginNum()); //学员登录数统计
                    break;
                case "register_num":
                    QuantityList.add(daily.getRegisterNum());//学员注册数统计
                    break;
                case "video_view_num":
                    QuantityList.add(daily.getVideoViewNum());//课程播放数统计
                    break;
                case "course_num":
                    QuantityList.add(daily.getCourseNum());//每日课程数统计
                    break;
                default:
                    break;
            }

        }

        //把封装之后的两个集合.放到 map 集合中.进行返回
        //创建 Map 集合做数据 返回 最后 存入 MySQL 中
        Map<String, Object> map = new HashMap<>();
        map.put("dateList",dateList); //日期返回存储
        map.put("QuantityList",QuantityList); //数量返回存储

        //返回 map
        return map;
    }
}













