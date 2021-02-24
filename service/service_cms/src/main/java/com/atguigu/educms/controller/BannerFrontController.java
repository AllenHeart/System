package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 *     前台接口 Banner 轮播图显示
 * </p>
 *
 * @author Abner
 * @since 2020-10-22
 */
@Api(description = "前端管理")
@RestController
@RequestMapping("/educms/bannerFront")
//@CrossOrigin  //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
public class BannerFrontController {

    @Autowired //自动装配
    private CrmBannerService bannerService;

    //根据 Banner entity 实体类查询所有
    @ApiOperation(value = "根据 List 集合查询 Banner 所有数据")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.success().data("list",list);
    }
}

