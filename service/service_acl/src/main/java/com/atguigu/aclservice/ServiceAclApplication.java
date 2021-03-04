package com.atguigu.aclservice;

import  org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 权限控制启动类
 */
@SpringBootApplication  //启动类
@EnableDiscoveryClient //服务注册
@ComponentScan(basePackages = {"com.atguigu"}) //指定扫描位置
@MapperScan("com.atguigu.aclservice.mapper") //映射
public class ServiceAclApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAclApplication.class, args);
    }
}
