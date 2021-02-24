package com.atguigu.canal;

import com.atguigu.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：CanalApplication
 * 类 描 述：Canal 数据同步
 * 创建时间：2020/11/19 11:23 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * Canal数据 同步 增量订阅 消费组件
 */
@SpringBootApplication
public class CanalApplication implements CommandLineRunner {

    @Resource //自动装配
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {

        //项目启动. 执行canal 客户端进行远程数据监听
        canalClient.run();
    }
}










