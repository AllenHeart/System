package com.atguigu.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项 目 名 称：登录和注册的服务
 * 类 名 称：UcenterApplication
 * 类 描 述：TODO
 * 创建时间：2020/10/28 4:59 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 登录和注册的服务
 */
@SpringBootApplication
@EnableDiscoveryClient //Nacos 服务注册中心
@ComponentScan(basePackages = {"com.atguigu"}) //组件扫描
@MapperScan("com.atguigu.educenter.mapper")
public class UcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
