package com.atguigu.eduorder.client;

import com.atguigu.commonutils.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：UcenterClient
 * 类 描 述：TODO
 * 创建时间：2020/11/7 3:38 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 订单远程调用
 */
@Component //交于容器管理
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    //根据订单用户 id 获取用户信息
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
