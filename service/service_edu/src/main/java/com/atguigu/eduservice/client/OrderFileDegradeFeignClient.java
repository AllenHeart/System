package com.atguigu.eduservice.client;

import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.stereotype.Component;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：OrderFileDegradeFeignClient
 * 类 描 述：TODO
 * 创建时间：2020/11/17 11:09 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
@Component
public class OrderFileDegradeFeignClient implements OrdersClient {

    //方法调用失败出错后以下执行方法
    @Override
    public Boolean isBuyCourse(String courseId, String memberId) {
        throw new HandlerException("当前课程已经支付!");
    }
}
