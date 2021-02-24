package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-09-30
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //定义最终发布的返回对象的方法
    public CoursePublishVo getPublishCourseInfo(String courseId);

    //根据课程 id.编写 sql 语句实现查询课程的基本信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
