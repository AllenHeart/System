package com.atguigu.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：StaApplication
 * 类 描 述：TODO
 * 创建时间：2020/11/18 2:00 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 网站统计日数据启动类
 */
@SpringBootApplication
@EnableScheduling //开启程序定时任务
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan("com.atguigu.staservice.mapper") //Mapper 扫描
@EnableFeignClients //远程调用
@EnableDiscoveryClient //Nacos服务注册
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
