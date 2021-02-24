package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：UcenterClient
 * 类 描 述：TODO
 * 创建时间：2020/11/18 2:27 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterFileDegradeFeignClient.class)
public interface UcenterClient {

    //远程调用方法 查询某一天的注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
