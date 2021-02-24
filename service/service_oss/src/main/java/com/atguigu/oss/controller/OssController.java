package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：OssController
 * 创建时间：2020/9/26 8:05 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

/**
 * 对象存储 Oss 控制层
 */
//@CrossOrigin  //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@RestController
@Api(description = "对象存储OSS头像上传")
@RequestMapping("/eduoss/fileOss")
public class    OssController {

    @Autowired //自动装配注入业务层方法
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    @ApiOperation(value = "上传头像的方法")
    public R uploadOssFile(
            @ApiParam(name = "file" ,value = "头像上传",required = true)
            MultipartFile file){
        //获取上传文件.MultipartFile 方法
        //数据库当中的头像上传是地址信息 所以 返回上传的 oss 的路径
        String url = ossService.uploadFileAvatar(file);
        //返回地址
        return R.success().data("url",url);
    }
}
