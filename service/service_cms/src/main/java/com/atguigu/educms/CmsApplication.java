package com.atguigu.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项 目 名 称：前台管理系统
 * 类 名 称：CmsApplication
 * 类 描 述：TODO
 * 创建时间：2020/10/22 4:27 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
@SpringBootApplication //启动类
@ComponentScan(basePackages = {"com.atguigu"})  //指定扫描位置
@MapperScan("com.atguigu.educms.mapper") //mysql 映射扫描配置
@EnableDiscoveryClient //Nacous 服务注册
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
