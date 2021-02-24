package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：ScheduledTask
 * 类 描 述：TODO
 * 创建时间：2020/11/18 7:51 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 七子表达式 cron :https://cron.qqe2.com/
 */
@Component
public class ScheduledTask {

    @Autowired //自动装配
    private StatisticsDailyService staService;

    /**
     * 测试
     * 每天七点到二十三点每五秒执行一次
     * 定时任务 0/5 * * * * ? 表示每隔 5 秒钟会执行该方法
     */
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1(){
//        System.out.println("---------------------任务定时器执行了...");
//    }

    /**
     * 在每天凌晨1 点.把前一天的数据进行.把数据查询进行添加
     */
    @Scheduled(cron = "0 0 1 * * ?") //七子表达式的默认只能是六位日期 * * * * * ? 秒 分 小时 日 月 周 (年指向当前年份)
    public void task2() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
        System.out.println("=====================凌晨 1 点定时器任务执行了...");
    }

}
