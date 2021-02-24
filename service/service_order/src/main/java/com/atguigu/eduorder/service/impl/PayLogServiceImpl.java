package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.entity.PayLog;
import com.atguigu.eduorder.mapper.PayLogMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.service.PayLogService;
import com.atguigu.eduorder.utils.HttpClient;
import com.atguigu.eduorder.utils.PayLogWxUtils;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-11-06
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired //自动装配 订单数据
    private OrderService orderService;

    //生成微信的支付二维码接口
    @Override
    public Map createNative(String orderNo) {
        try {
            /**
             * 1: 根据订单号查询订单信息
             */
            // 根据条件查询 order_no 的订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            // 通过 eq 设置条件 order_no. 传递到 orderNo 对象中
            wrapper.eq("order_no", orderNo);

            //调用 service 中的查询的方法
            Order order = this.orderService.getOne(wrapper);

            /**
             * 2: 使用 map集合设置生成二维码需要的参数
             */
            Map m = new HashMap(); //封装参数
            //设置支付参数
            m.put("appid", PayLogWxUtils.WX_APP_ID); //微信id
            m.put("mch_id", PayLogWxUtils.WX_APP_PARTNER); //商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr()); //随机字符串 随后生成的二维码都是不一样
            m.put("body", order.getCourseTitle());  //课程标题信息
            m.put("out_trade_no", orderNo); //唯一标识订单号 BigDecimal 类型针对价格对象 可以做到圆角分效果 longValue 转换成 long类型 value 字符串类型
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + ""); //订单价格
            m.put("spbill_create_ip", "127.0.0.1"); //ip 的支付地址本地 如果在实际项目 这里给为实际的域名 baidu.com
            m.put("notify_url", PayLogWxUtils.WX_APP_NOTIFYURL); //回调地址
            m.put("trade_type", "NATIVE"); //支付类型 根据价格做支付类型


            /**
             * 3: 发送 httpClint 请求数据.传递参数 xml格式
             */
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");//请求的地址微信开发提供固定

            //设置 xml 格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, PayLogWxUtils.WX_PARTNER_KEY));

            //微信的固定请求地址是 https 默认不支持
            client.setHttps(true);

            //最后执行请求 post 发送
            client.post();

            /**
             * 4: 得到发送请求返回结果
             */
            //得到返回的内容.是使用 xml 格式传输的和存储的 返回第三方的数据
            String xml = client.getContent();

            //把 xml 格式转换成 map 集合.把 map 集合返回 转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据,的封装
            Map map = new HashMap();
            //封装
            map.put("out_trade_no", orderNo);   //订单号
            map.put("course_id", order.getCourseId()); //课程信息 id
            map.put("total_fee", order.getTotalFee()); //订单金额
            map.put("result_code", resultMap.get("result_code")); //返回二维码操作的状态码
            map.put("code_url", resultMap.get("code_url")); //二维码地址

            //返回
            return map;
        } catch (Exception e) {
            //异常打印
            throw new HeartException(20001, "生成二维码失败!");
        }

    }


    //查询订单号查询订单支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        /**
         * 1: 封装参数
         */
        try {
            Map m = new HashMap();
            m.put("appid", PayLogWxUtils.WX_APP_ID); //微信的 id
            m.put("mch_id", PayLogWxUtils.WX_APP_PARTNER); //商户号
            m.put("out_trade_no", orderNo); //唯一标识订单号
            m.put("nonce_str", WXPayUtil.generateNonceStr()); //随机字符串 随后生成的二维码都是不一样

            /**
             * 2: 发送 httpClient 的请求
             */
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            //请求的地址微信开发提供固定

            //设置 xml 格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, PayLogWxUtils.WX_PARTNER_KEY));//商品的 key 值

            //微信的固定请求地址是 https 默认不支持
            client.setHttps(true);

            //最后执行请求 post 发送
            client.post();

            /**
             * 3: 得到请求返回的数据
             */
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //转成 Map 再返回
            return resultMap;
        } catch (Exception e) {
            return null;
//            throw new HeartException(20001, "支付状态异常!");
        }

    }


    //添加支付表记录和更新订单表的状态
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        /**
         * 1: 从 Map 集合中获取订单号
         */
        String orderNo = map.get("out_trade_no");

        //根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        // 通过 eq 设置条件 order_no. 传递到 orderNo 对象中
        wrapper.eq("order_no", orderNo);

        Order order = orderService.getOne(wrapper);

        //更新订单表中的订单状态
        //判断 order 对象的的 getStatus 如果等等于 1 那么不需要做相关操作
        if (order.getStatus().intValue() == 1) {
            return;
        }

        order.setStatus(1); //设置支付的状态 为 1 代表支付 0 代表未支付
        //修改 order 的支付状态
        this.orderService.updateById(order);

        //向支付表里面添加支付记录
        PayLog payLog = new PayLog();
        //设置 payLog 的参数
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());        //订单完成时间
        payLog.setPayType(1);                 //支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));//订单的流水号
        payLog.setAttr(JSONObject.toJSONString(map));//json 转换 其他属性字符串

        //向支付日志表添加记录
        baseMapper.insert(payLog);
    }
}


















