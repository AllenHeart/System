package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     后台的 Banner 管理接口
 * </p>
 *
 * @author Abner
 * @since 2020-10-22
 */
@Api(description = "后台管理系统")
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin  //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
public class BannerAdminController {

    @Autowired //自动装配
    private CrmBannerService crmBannerService;


    //1: 分页查询 banner
    @ApiOperation(value = "分页查询 banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(
            @ApiParam(name = "page",value = "查询当前页",required = true)
            @PathVariable long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        crmBannerService.page(pageBanner,null);
        return R.success().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    //2: 根据 Banner id 查询数据
    @ApiOperation(value = "根据 Banner 查询 id")
    @GetMapping("getBanner/{id}")
    public R getBanner(
            @ApiParam(name = "id",value = "根据 Banner查询 id",required = true)
            @PathVariable String id){
        crmBannerService.getById(id);
        return R.success();

    }

    //3: 添加 banner save
    @ApiOperation(value = "添加 banner 数据")
    @PostMapping("addBanner")
    public R addBanner(
            @ApiParam(name = "crmBanner",value = "添加Banner 数据",required = true)
            @RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.success();
    }

    //4: 修改 Banner
    @ApiOperation(value = "修改 banner")
    @PutMapping("update")
    public R updateById(
            @ApiParam(name = "crmBanner",value = "修改 Banner",required = true)
            @RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.success();
    }

    //5: 删除 Banner
    @ApiOperation(value = "删除 Banner")
    @DeleteMapping("remove/{id}")
    public R remove(
            @ApiParam(name = "id",value = "根据 Banner删除id",required = true)
            @PathVariable String id){
        crmBannerService.removeById(id);
        return R.success();
    }

}

















































