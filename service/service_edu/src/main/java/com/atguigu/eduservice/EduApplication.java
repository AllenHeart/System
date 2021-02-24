package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 类 描 述：教学相关api接口服务
 * 项目名称：guli_parent
 * 类 名 称：EduApplication
 * 创建时间：2020/9/14 5:45 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */
/**
 * @EnableDiscoveryClient
 * 配置服务消费者，从而服务消费者可以通过 Nacos 的服务注册发现功能从 Nacos server 上获取到它要调用的服务。
 */
@EnableDiscoveryClient //注解表示servicimageUrle_edu 的业务注册到中心 开启服务注册发现功能：
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})//当项目启动时会去组件扫描com.atguigu的所有配置信息如果不添加就会扫描不到//Swagger 包的组件扫描规则
@EnableFeignClients //在调用端加这个注解 远程调用
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
