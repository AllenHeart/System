package com.atguigu.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.aclservice.service.IndexService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "用户登录页面")
@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @ApiOperation(value = "根据 token 获取用户信息")
    @GetMapping("info")
    public R info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.success().data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @ApiOperation(value = "获取菜单的 id")
    @GetMapping("menu")
    public R getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return R.success().data("permissionList", permissionList);
    }

    /**
     * 用户登出
     * @return
     */
    @ApiOperation(value = "用户登出")
    @PostMapping("logout")
    public R logout(){
        return R.success();
    }

}
