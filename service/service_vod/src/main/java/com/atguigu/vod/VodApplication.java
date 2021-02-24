package com.atguigu.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项 目 名 称：视频点播api接口服务
 * 类 名 称：com.atguigu.vod.VodApplication
 * 类 描 述：TODO
 * 创建时间：2020/10/14 9:57 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 启动类@EnableFeignClients //在调用端加这个注解
 */
@EnableDiscoveryClient //配置注册服务
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.atguigu"})
public class VodApplication {

    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
