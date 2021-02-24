package com.atguigu.eduorder.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：PayLogWxUtils
 * 类 描 述：TODO
 * 创建时间：2020/11/17 3:10 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 创建订单支付的工具类
 */
@Component
public class PayLogWxUtils implements InitializingBean {

    @Value("${wx.app_id}")
    private String appId;

    @Value("${wx.app_partner}")
    private String appPartner;

    @Value("${wx.partner_key}")
    private String partnerKey;

    @Value("${wx.app_notifyurl}")
    private String appnNotifyurl;

    public static String WX_APP_ID;
    public static String WX_APP_PARTNER;
    public static String WX_PARTNER_KEY;
    public static String WX_APP_NOTIFYURL;

    //赋值
    @Override
    public void afterPropertiesSet() throws Exception {
        WX_APP_ID = appId;
        WX_APP_PARTNER = appPartner;
        WX_PARTNER_KEY = partnerKey;
        WX_APP_NOTIFYURL = appnNotifyurl;
    }
}
