package com.atguigu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：ApiGatewayApplication
 * 类 描 述：TODO
 * 创建时间：2020/11/20 3:09 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * Spring Cloud GateWay 网关服务
 */
@SpringBootApplication
@EnableDiscoveryClient //Nacos 服务注册
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class,args);
    }
}
