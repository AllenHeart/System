package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：TeacherFrontController
 * 类 描 述：TODO
 * 创建时间：2020/11/1 6:16 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
@Api(description = "根据讲师列表显示名师数据")
//@CrossOrigin //解决浏览器跨域的问题 跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired //自动装配 调用讲师 Service 方法
    private EduTeacherService teacherService;


    @Autowired //自动装配 调用课程 Service 方法
    private EduCourseService courseService;

    //1: 分页查询讲师的方法
    @ApiOperation(value = "名师的分页查询讲师的方法")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit){

        //创建 page 对象 往里面传入 查询参数的值
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        //调用 Service 的方法 实现分页查询的功能 并通过 Map 集合方式将数据存储到 集合中做到拿取到数据
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);

        //返回分页所有的数据
        return R.success().data(map);
    }


    //2: 讲师详情的功能
    @ApiOperation(value = "讲师的详情信息")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(
            @ApiParam(name = "teacherId",value = "讲师的 id",required = true)
            @PathVariable String teacherId){

        //(1): 根据讲师 id 查询讲师的基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        //(2): 根据讲师 id查询所讲课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //查询条件
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> coursesList = courseService.list(wrapper);

        //返回该对象
        return R.success().data("teacher",eduTeacher).data("courseList",coursesList);
    }
}

















