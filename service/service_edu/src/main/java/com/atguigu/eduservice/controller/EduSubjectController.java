package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-27
 */
@Api(description = "EasyExcel添加课程科目")
//@CrossOrigin //解决跨域 跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired //自动装配注入业务
    private EduSubjectService subjectService;

    //获取课程分类
    //获取上传过来文件.把文件内容读取出来
    @ApiOperation(value = "上传 Excel 文件")
    @PostMapping("addSubject")
    public R addSubject(
            @ApiParam(name = "file",value = "上传 excel 文件",required = true)
                    MultipartFile file){
        //上传过来的 excel 的文件
        this.subjectService.saveSubject(file,subjectService);
        //返回
        return R.success();
    }

    //课程分类列表(树形结构)
    @ApiOperation(value = "课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list 集合的泛型是一级分类
        List<OneSubject> list = this.subjectService.getAllOneTwoSubject();
        return R.success().data("list",list);
    }

}

