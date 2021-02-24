package com.atguigu.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：OrdersClient
 * 类 描 述：TODO
 * 创建时间：2020/11/17 11:05 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 远程调用接口 实现查询课程 id 和用户 id的课程是否已经支付
 */
@Component
@FeignClient(name = "service-order",fallback = OrderFileDegradeFeignClient.class)
public interface OrdersClient {

    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
