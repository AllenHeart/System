package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：WxApiController
 * 类 描 述：TODO
 * 创建时间：2020/10/30 7:07 PM
 * 创 建 人：huanghao
 * @version: V1.8
 */
@Api(description = "实现微信二维码支付")
//@CrossOrigin //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@Controller //只是请求地址,不需要返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired //自动装配
    private UcenterMemberService memberService;

    //1: 生成微信扫描二维码的方法
    @GetMapping("login")
    public String getWxCode() {

        //请求地址是: 固定的地址.后面拼接参数 可以用 但不实在 不建议
//        String url = "https://open.weixin.qq.com/connect/qrconnect?" +
//                "appId=" +ConstantWxUtils.WX_OPEN_APP_ID + "&response_type=code";

        // 微信开放平台授权baseUrl  %s 相当于? 问号 代表是占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对这个rect_url 进行 URLEncode 编码 utf-8 格式
        // 回调地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;//获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");//编码格式
        } catch (Exception e) {
            //控制异常打印
            e.printStackTrace();
        }


        //生成qrcodeUrl formant往 &s 设置baseUrl 设置里面的值最终 重定向返回
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl, //返回的服务器重定向地址的 数据
                "atguigu"
        );

        //返回的值是 重定向 请求微信的地址 url
        return "redirect:" + url;
    }


    //获取扫描之后,拿取用户的个人信息.添加到 mysql 数据库,回调地址
    @ApiOperation(value = "获取扫描人的信息")
    @GetMapping("callback")
    public String callback(
            @ApiParam(name = "code", value = "获取的code参数", required = false)
                    String code,
            @ApiParam(name = "state", value = "地址", required = false)
                    String state) {

        //1: 先获取 code 的值.临时票据.类似于验证码
        try {
            //2: 拿着code验证码 请求微信提供的固定的地址.得到两个值.access_token.和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            //3: 拼接三个参数.id 密钥. 和 code 的值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code

            );

            //4: 请求这个拼接好的地址.最终得到返回两个值 access_token.和 openid
            //5: 使用 httpClient 发送请求.得到返回结果 不用浏览器也可以模拟发送请求和响应过程

            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            //6: 从 accessTokenInfo 字符串获取出来两个值.access_token 和 openid
            //7: 把 accessTokenInfo 字符串转换 map 里面的 key 获取对应值
            //8: 使用 Google 的 json转换工具 Gson
            //解析 json字符串
            Gson gson = new Gson();
            //HashMap 可以把 json 转换成字符串
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token"); //访问令牌
            String openId = (String) mapAccessToken.get("openId"); //授权用户唯一标识


            //最终 把扫描人的信息添加数据库里面
            //判断数据表里面是否存在相同微信信息.根据 openid 判断
            UcenterMember member = memberService.getOpenIdMember(openId);
            //判断member 表有没有数据 没有openid 唯一的值做添加 有就不添加数据
            if (member == null) {//member 是空,表没有相同微信数据.进行添加

                // 转换成 Gson 得到 access_token 和 openid,再去请求微信提供固定的地址 url ,最终获取扫码的人 用户信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                //拼接两个参数
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openId);

                //使用 httpClient 发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);

                //获取返回 userInfo 字符串扫描人的信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname"); //用户的名称
                String headimgurl = (String) userInfoMap.get("headimgurl"); //用户的头像信息

                member = new UcenterMember();
                member.setOpenid(openId);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);

                System.out.println("用户的信息" + userInfo);

            }

            System.out.println("数据库添加成功" + member);
            System.out.println("访问令牌的信息=========" + accessTokenInfo);


            //使用 jwt 根据 member 对象生成 token 字符串 cookie 无法实现跨域问题 使用 jwt 可以实现路径传递
            String jwtToken = JwtUtils.getJwtToken(
                    member.getId(),
                    member.getNickname());//id 和名称

            //最后, 返回首页面,通过路径传递token 字符串对象
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            //异常日志打印
            throw new HeartException(20001,"二维码验证失败,请重新登录!");
        }
    }
}













