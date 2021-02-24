package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.CourseWebVoOrder;
import com.atguigu.eduservice.client.OrdersClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：CourseFrontController
 * 类 描 述：TODO
 * 创建时间：2020/11/4 9:18 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
@Api(description = "查询课程数据")
//@CrossOrigin //浏览器跨域的问题 跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/eduservice/coursefont")
public class CourseFrontController {

    @Autowired //自动装配 课程显示
    private EduCourseService courseService;

    @Autowired //自动装配 查询章节和小节
    private EduChapterService chapterService;

    @Autowired //远程调用 根据课程 id.和用户 id 查询订单表中的订单状态
    private OrdersClient ordersClient;

    //1: 条件查询带分页查询课程
    @ApiOperation(value = "查询课程数据")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public  R getFrontCourseList(
            @ApiParam(name = "page",value = "当前页",required = false)
            @PathVariable long page,
            @ApiParam(name = "limit",value = "每页记录数",required = false)
            @PathVariable long limit,
            @ApiParam(name = "courseFrontVo",value ="课程实体类的对象",required = false)
            @RequestBody(required = false) CourseFrontVo courseFrontVo){

        //创建 page 对象并把 要得到的数据 传入对象
        Page<EduCourse> pageCourse = new Page<>(page,limit);

        //map 集合
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);

        //返回 map 对象
        return R.success().data(map);
    }

    //2: 课程详情的方法
    @ApiOperation(value = "根据课程 Id 查询基本信息")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(
            @ApiParam(name = "courseId",value = "课程 id",required = false)
            @PathVariable String courseId,
            @ApiParam(name = "request",value = "用户 id",required = false)
            HttpServletRequest request){

       //根据课程 id.编写 sql 语句实现查询课程的基本信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程 id 查询章节和小节的数据
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //根据课程 id 和用户 id 查询当前课程是否已经支付过了 jwtUtils 的token 值获取用户 id
        Boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        //返回课程基本对象
        return R.success()
                .data("courseWebVo",courseWebVo)
                .data("chapterVideoList",chapterVideoList)
                .data("isBuy",buyCourse);
    }

    //根据课程 id 查询课程信息
    @ApiOperation(value = "根据课程 id 查询课程信息")
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(
            @ApiParam(name = "id",value = "课程 id",required = true)
            @PathVariable String id){
        //根据课程 id.编写 sql 语句实现查询课程的基本信息
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        //创建调用端和被调用端的对象
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        //根据 BeaUtils,复制给对象
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        //并返回该对象数据
        return courseWebVoOrder;
    }

}





































