package com.atguigu.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项 目 名 称：订单相关api接口服务
 * 类 名 称：com.atguigu.eduorder.OrderApplication
 * 类 描 述：TODO
 * 创建时间：2020/11/6 10:00 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
/**
 * 课程支付启动模块
 */
@SpringBootApplication //启动类
@EnableDiscoveryClient //Nacos服务注册：
@EnableFeignClients //远程调用
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan("com.atguigu.eduorder.mapper")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
