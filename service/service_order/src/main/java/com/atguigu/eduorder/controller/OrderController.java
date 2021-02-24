package com.atguigu.eduorder.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-11-06
 */
@Api(description = "课程订单模块")
//@CrossOrigin  //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired //自动装配
    private OrderService orderService;

    //1: 生成订单的方法
    @ApiOperation(value = "根据课程id 生成订单的方法")
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(
            @ApiParam(name = "courseId",value = "课程 id",required = false)
            @PathVariable String courseId,
            @ApiParam(name = "request",value = "用户对象",required = false)
            HttpServletRequest request){

        //创建订单.并返回订单号
        String orderNo = this.orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.success().data("orderId",orderNo);
    }


    //2: 根据订单号 id 查询订单信息
    @ApiOperation(value = "根据订单号 id 查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(
            @ApiParam(name = "orderId",value = "订单号 Id 值",required = false)
            @PathVariable String orderId){

        //根据订单号id,创建查询条件
        QueryWrapper<Order> wrapper = new QueryWrapper();
        wrapper.eq("order_no",orderId);

        Order order = orderService.getOne(wrapper);
        return R.success().data("item",order);
    }

    //3: 根据课程 id.和用户 id 查询订单表中的订单状态 远程调用
    @ApiOperation(value = "根据课程 id.和用户 id 查询订单表中的订单状态")
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public Boolean isBuyCourse(
            @ApiParam(name = "courseId",value = "课程id",required = true)
            @PathVariable String courseId,
            @ApiParam(name = "memberId",value = "用户 id",required = true)
            @PathVariable String memberId){
        //根据条件查询课程 id || 用户 id
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id ",courseId);//查询课程 courseId
        wrapper.eq("member_id",memberId);//查询用户 memberId
        wrapper.eq("status","1"); //支付状态

        //根据 Wrapper 条件，查询总记录数
        int count = orderService.count(wrapper);

        //判断 如果说 count 大于 0 已支付
        if (count > 0){
            return true;
        } else { //如果查不出记录 返回 false
            return false;
        }
    }
}
















