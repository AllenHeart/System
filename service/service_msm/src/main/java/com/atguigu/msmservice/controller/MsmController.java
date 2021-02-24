package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：MsmController
 * 类 描 述：TODO
 * 创建时间：2020/10/28 2:05 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
@Api(description = "短信服务的控制类")
//@CrossOrigin  //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@RestController //
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired //自动装配
    private MsmService msmService;


    @Autowired //Redis 封装的模版
    private RedisTemplate<String, String> redisTemplate;

    //发送短信的方式
    @ApiOperation(value = "阿里云的短信服务发送方式")
    @GetMapping("send/{phone}")
    public R sendMsm(@ApiParam(name = "phone",value = "根据手机号验证",required = true)
                         @PathVariable String phone){

        //1: 从 Redis 获取验证码.如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);

        //判断 code 不等空 那么返回
        if (!StringUtils.isEmpty(code)){
            return R.success();
        }

        //2: 如果 redis 获取.不到.进行阿里云发送验证码操作

        //生成随机盐.传递阿里云的进行发送
        code = RandomUtil.getFourBitRandom();

        //创建 Map 集合 存储到集合中 得到最终数据 发送验证码
        Map<String, Object> param = new HashMap<>();
        param.put("code",code);

        //调用 Service 发送短信的方法
        boolean isSend = msmService.send(param,phone);
        if (isSend) {
            //调用 Redis 的方法 set 到 redis key 和 value 进行最后的取值
            //设置 redis 的 key phone || value code || redis 的验证码有效时间的分钟时间
            redisTemplate.opsForValue().set(phone,code,5,TimeUnit.MINUTES);
            return R.success();
        } else {
            return R.error().message("短信发送失败!");
        }
    }
}



















