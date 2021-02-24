package com.atguigu.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：ConstantVodUtils
 * 类 描 述：TODO
 * 创建时间：2020/10/18 7:32 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
/**
 * 常量类，读取配置文件application.properties中的配置
 * 当项目已启动,ConstantPropertiesUtil这个类会交给 spring 管理.
 * 而 spring 在管理过程中通过@Value 读取都 application.properties 的配置信息.并复制给常量池进行初始化之后
 * 解析完成后.那么实现InitializingBean接口 afterPropertiesSet这个方法就会执行.
 */
@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }
}