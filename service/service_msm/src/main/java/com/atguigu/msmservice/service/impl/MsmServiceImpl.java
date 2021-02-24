package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：MsmServiceImpl
 * 类 描 述：TODO
 * 创建时间：2020/10/28 2:08 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 短信服务的实现类
 */
@Service
public class MsmServiceImpl implements MsmService {

    //最终发送短信的方法
    @Override
    public boolean send(Map<String, Object> param, String phone) {

        //判断 手机号是否为空
        if(StringUtils.isEmpty(phone))
            return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4GEknFvPrcpqaRG2gkRc", "IDq05yaZugYY06exYXn9trlumRoppY");

        //创建实现最终发送 client
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的相关参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST); //提交的方式 post
        request.setSysDomain("dysmsapi.aliyuncs.com"); //发现短信要请求哪个地方 哪个服务
        request.setSysVersion("2017-05-25"); //版本号
        request.setSysAction("SendSms"); //请求的哪个方法
        request.putQueryParameter("RegionId", "cn-hangzhou");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers",phone); //设置手机号
        request.putQueryParameter("SignName", "浩浩谷粒在线教育平台网站"); //申请 aliyun 签名名称
        request.putQueryParameter("TemplateCode", "SMS_205133661"); //申请 aliyun 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证数据必须是转换 json数据传递

        //实现最终发送短信信息
        try {
            CommonResponse response = client.getCommonResponse(request);
            //输出
            System.out.println(response.getData());
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e){
            //异常控制台日志打印
            e.printStackTrace();
            return false;

        }
    }
}




















