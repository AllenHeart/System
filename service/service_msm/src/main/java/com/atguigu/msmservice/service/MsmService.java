package com.atguigu.msmservice.service;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：MsmService
 * 类 描 述：
 * 创建时间：2020/10/28 2:07 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

import java.util.Map;

/**
 * 短信服务的接口
 */
public interface MsmService {

    //发送短信的方式
    boolean send(Map<String,Object> param, String phone);
}
