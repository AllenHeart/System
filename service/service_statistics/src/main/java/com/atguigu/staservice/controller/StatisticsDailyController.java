package com.atguigu.staservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Abner
 * @since 2020-11-18
 */
@Api(description = "网站统计日数据")
//@CrossOrigin  //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {

    @Autowired //自动装配
    private StatisticsDailyService staService;

    //统计某一天的注册人数.生成统计数据
    @ApiOperation(value = "统计一天的注册人数")
    @PostMapping("registerCount/{day}")
    public R registerCount(
            @ApiParam(name = "day",value = "日期的注册人数",required = false)
            @PathVariable String day){
        staService.registerCount(day);
        return R.success();
    }

    //图表显示.返回两个部分数据.日期 json 字符串数组.数量 json 数组
    @ApiOperation(value = "图表显示数据")
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(
            @ApiParam(name = "type",value = "统一因子类型",required = false)
            @PathVariable String type,
            @ApiParam(name = "begin",value = "开始时间",required = false)
            @PathVariable String begin,
            @ApiParam(name = "end",value = "结束时间",required = false)
            @PathVariable String end){

        Map<String,Object> map = staService.getShowData(type,begin,end);
        return R.success().data(map);
    }

}













