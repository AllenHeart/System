package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：UcenterFileDegradeFeignClient
 * 类 描 述：TODO
 * 创建时间：2020/11/18 2:30 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
@Component
public class UcenterFileDegradeFeignClient implements UcenterClient {

    //方法调用失败出错后以下执行方法
    @Override
    public R countRegister(String day) {
        return R.error().message("==熔断器==查询每天的注册人数出错了!");
    }
}
