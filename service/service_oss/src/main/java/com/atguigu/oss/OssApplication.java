package com.atguigu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 类 描 述：对象存储 OSS 启动类
 * 项目名称：guli_parent
 * 类 名 称：OssApplication
 * 创建时间：2020/9/26 7:28 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

/**
 * 对象存储 OSS 启动类
 * 启动时候报错: OSS 对象会先去找数据库配置.但是现在模块因为不需要操作数据库.只是要上传到 Oss 功能
 * 没有配置数据库
 * 解决方式:
 *  1: 添加数据库配置信息
 *  2: 在启动类添加注解.默认不去加载数in:name springboot stars:>300据库配置
 */

//在@SpringBootApplication注解上加上exclude，解除自动加载DataSourceAutoConfiguration
@EnableDiscoveryClient //配置将服务注册中心
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.atguigu"})  //Swagger 包的组件扫描规则
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
