package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 类 描 述：用户登录
 * 项目名称：guli_parent
 * 类 名 称：EduLoginController
 * 创建时间：2020/9/23 5:10 AM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */
//@CrossOrigin //解决跨域 跨域组件由网关服务取代全局配置
@Api(description = "前端页面的接口调用")
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    /**
     * Login 实现登录
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R login(){
        return R.success().data("token","admin");
    }

    /**
     * info 实现数据信息
     * @return
     */
    @ApiOperation(value = "用户的信息")
    @GetMapping("info")
    public R info(){
        return R.success().data("roles","[admin]")
                          .data("name","admin")
                          .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
