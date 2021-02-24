package com.atguigu.eduorder.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-11-06
 */
@Api(description = "支付日志表记录")
//@CrossOrigin  //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {

    @Autowired //自动装配 调用支付业务
    private PayLogService payLogService;

    //生成微信的支付二维码接口
    @ApiOperation(value = "生成微信的支付二维码接口")
    @GetMapping("createNative/{orderNo}")
    public R createNative(
            @ApiParam(name = "orderNo", value = "支付的订单号 id")
            @PathVariable String orderNo) {

        //订单返回的信息.包含二维码地址.还有其他信息 返回集合的类型
        Map map = this.payLogService.createNative(orderNo);

        System.out.println("-----------返回二维码 map 集合:"+ map);

        //返回集合 取值更为方便
        return R.success().data(map);
    }


    //查询订单支付的状态
    //参数,订单号.根据订单号查询.支付的状态
    @ApiOperation(value = "查询订单支付的状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(
            @ApiParam(name = "orderNo", value = "订单号 id", required = true)
            @PathVariable String orderNo) {

        //根据订单号查询订单支付状态
        Map<String, String> map = payLogService.queryPayStatus(orderNo);

        System.out.println("*************查询订单状态返回二维码 map 集合:"+ map);

        //判断 map 的集合中 如果等等于空 返回一个状态码
        if (map == null) {
            return R.error().message("支付出错了!");
        }

        //如果返回的 map 集合里面不为空.通过 map 获取订单的状态
        if(map.get("trade_state").equals("SUCCESS"))  { //支付成功!

            //如果 map 的 key 值是成功的那么添加记录到支付表里面 并且更新订单表订单状态为 1
            this.payLogService.updateOrderStatus(map);
            //返回数据
            return R.success().message("支付成功!");
        }

        //25000 表示是支付中的状态,不在任何提示,再去发送请求再等待
        return R.success().code(25000).message("正在支付中...请稍后...");
    }
}


















