package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Abner 
 * @since 2020-10-28
 */
@Api(description = "登录与注册的业务")
//@CrossOrigin  //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired //自动装配
    private UcenterMemberService memberService;

    //用户登录
    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R loginUser(
            @ApiParam(name = "member",value = "根据手机号",required = true)
            @RequestBody UcenterMember member){
        //调用 Service 的方法实现登录
        //返回 token 的值.使用 Jwt 生成随机盐
        String token = this.memberService.login(member);
        //返回 token 的登录数据
        return R.success().data("token",token);
    }

    //用户注册
    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public R registerUser(
            @ApiParam(name = "register",value = "用户注册对象",required = true)
            @RequestBody RegisterVo registerVo){
        this.memberService.register(registerVo);
        return R.success();

    }

    //根据 token 获取用户的信息
    @ApiOperation(value = "获取 token 用户的信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(
            @ApiParam(name = "request",value = "请求头获取用户的信息",required = true)
            HttpServletRequest request){

        //直接调用 jwtUtils 的方法实现获取用户的信息.根据 request 对象获取头信息.返回用户的id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //调用数据库根据用户 id 的获取用户的信息
        UcenterMember member = this.memberService.getById(memberId);

        return R.success().data("userInfo",member);
    }

    //根据订单用户 id 获取用户信息 远程调用wx 支付模块
    @ApiOperation(value = "根据订单用户 id 获取用户信息")
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(
            @ApiParam(name = "id",value = "订单用户 id",required = false)
            @PathVariable String id){
        //通过 订单 di 查询
        UcenterMember member = this.memberService.getById(id);
        //把 member 对象里面的值cp 给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        //BeaUtils
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //查询某一天的注册人数 远程调用统计分析模块
    @ApiOperation(value = "查询某一天的注册认识")
    @GetMapping("countRegister/{day}")
    public R countRegister(
            @ApiParam(name = "day",value = "每天的人数 id",required = true)
            @PathVariable String day){
        Integer count = memberService.countRegister(day);
        return R.success().data("countRegister",count);
    }
}






















