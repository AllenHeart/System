package com.atguigu.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项 目 名 称：短信api接口服务
 * 类 名 称：MsmApplication
 * 类 描 述：TODO
 * 创建时间：2020/10/28 2:03 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * aliyun 短信服务启动层
 */
@EnableDiscoveryClient //配置注册服务
@ComponentScan(basePackages = {"com.atguigu"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
public class MsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class,args);
    }
}
