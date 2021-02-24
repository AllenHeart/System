package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：ConstantPropertiesUtil
 * 创建时间：2020/9/26 7:43 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

/**
 * 常量类，读取配置文件application.properties中的配置
 * 当项目已启动,ConstantPropertiesUtil这个类会交给 spring 管理.
 * 而 spring 在管理过程中通过@Value 读取都 application.properties 的配置信息.并复制给常量池进行初始化之后
 * 解析完成后.那么实现InitializingBean接口 afterPropertiesSet这个方法就会执行.
 */
@Component //组件spring 管理
public class ConstantPropertiesUtils implements InitializingBean {

    //读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    //定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;


    //实现的属性初始化的异常的方法
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }
}
