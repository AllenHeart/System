package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程管理 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-30
 */
@Api(description = "课程管理")
@RestController
//@CrossOrigin //解决浏览器跨域问题 跨域组件由网关服务取代全局配置
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired //自动装配
    private EduCourseService courseService;

    //课程列表 条件查询分页实现
    @ApiOperation(value = "课程列表条件查询带分页的方法")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageCourseCondition(
            @ApiParam(name = "current",value = "当前页数",required = true)
            @PathVariable long current,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit,
            @ApiParam(name = "courseQuery",value = "查询封装对象",required = true)
            @RequestBody(required = false)CourseQuery courseQuery) {

        Page<EduCourse> page = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper();

        if(!StringUtils.isEmpty(courseQuery.getTitle())) {
            wrapper.like("title",courseQuery.getTitle());
        }

        if(!StringUtils.isEmpty(courseQuery.getStatus())) {
            wrapper.eq("status",courseQuery.getStatus());
        }

        if(!StringUtils.isEmpty(courseQuery.getBegin())) {
            wrapper.ge("gmt_create",courseQuery.getBegin());
        }

        if(!StringUtils.isEmpty(courseQuery.getEnd())) {
            wrapper.le("gmt_modified",courseQuery.getEnd());
        }

        this.courseService.page(page,wrapper);
        long total = page.getTotal(); //总记录数
        List<EduCourse> records = page.getRecords();  //list集合

        return R.success().data("total",total).data("rows",records);
    }


    //TODO 完善添加查询带分页

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(
            @ApiParam(name = "courseInfoVo",value = "添加课程信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo){

        //返回添加之后的课程对应的 id 值.为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.success().data("courseId",id);
    }

    //根据课程 唯一 id 值查询课程基本信息
    @ApiOperation(value = "根据课程 id 查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(
            @ApiParam(name = "courseId",value = "根据课程 id 值查询课程基本信息",required = true)
            @PathVariable String courseId){
        CourseInfoVo  courseInfoVo = courseService.getCourseInfo(courseId);
        return R.success().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "修改课程信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.success();
    }

    //根据课程 id查询课程确认信息
    @ApiOperation(value = "根据课程 id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(
            @ApiParam(name = "id",value = "查询课程信息",required = true)
            @PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.success().data("publishCourse",coursePublishVo);
    }

    //课程最终发布 修改课程状态
    @ApiOperation(value = "修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(
            @ApiParam(name = "id",value = "修改课程状态",required = true)
            @PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id); //参数传入 id
        eduCourse.setStatus("Normal"); //设置课程发布的状态
        courseService.updateById(eduCourse);
        return R.success();
    }

    //删除课程信息
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId ){
        courseService.removeCourse(courseId);
        return R.success();
    }

}





























