package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：indexFrontController
 * 类 描 述：TODO
 * 创建时间：2020/10/25 2:38 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 处理前端banner页面控制层
 */
@Api(description = "查询前台页面控制层")
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin //解决浏览器跨域问题 跨域组件由网关服务取代全局配置
public class indexFrontController {

    @Autowired //自动装配 课程 service 方法
    private EduCourseService courseService;

    @Autowired //自动装配 讲师的 service 方法
    private EduTeacherService teacherService;


    //查询前端的 8 条热门课程.查询 4 条 mysql 记录
    @ApiOperation(value = "查询前端的 8 条记录")
    @GetMapping("index")
    public R index(){
        // 查询课程数据的 8 条记录
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //根据 课程的 id 查询数据
        wrapper.orderByAsc("id");
        //mybatis_plus last方法.拼接 sql 语句查询 8 条记录
        wrapper.last("limit 8");

        //调用 baseMapper实现查询 Banner 所有的数据
        List<EduCourse> eduList = courseService.list(wrapper);


        //查询前 4 条热门讲师的数据做显示
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        //根据讲师 id 查询
        wrapperTeacher.orderByDesc("id");
        //wrapper 的 last 方法拼接条件
        wrapperTeacher.last("limit 4");

        //调用 teacher 的 service 方法实现最终的数据
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        //返回数据
        return R.success().data("eduList",eduList).data("teacherList",teacherList);
    }
}



































