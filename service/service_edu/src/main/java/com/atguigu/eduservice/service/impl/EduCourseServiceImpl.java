package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-30
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述的注入
    @Autowired //自动装配
    private EduCourseDescriptionService courseDescriptionService;

    //课程小节service
    @Autowired //自动装配
    private EduVideoService eduVideoService;

    //课程章节service
    @Autowired //自动装配
    private EduChapterService eduChapterService;

    /**
     * 实现添加课程基本信息的方法======================================================
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1: 向课程表表添加课程基本信息
        //CourseInfoVo对象转换 eduCourse 对象 用到工具类 BeaUtils类 courseInfoVo的值 get 出来然后 educourse的值 set 进去
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);

        //返回值会返回成功加了几条记录.Mybatis 的方法如果大于 1 表示回调成功.如果小于 1 那么回调失败
        int insert = baseMapper.insert(eduCourse);
        //判断如果 大于等于 0 那么输出个异常
        if (insert <= 0){
            throw new HeartException(20001,"添加课程信息失败!");
        }


        //获取到添加之后课程 id 值
        String id = eduCourse.getId();

        /**
         * 向课程简介表添加课程简介信息edu_course_description=====================================
         *
         */
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());

        //设置课程简介的id 值就是课程管理 id 值
        courseDescription.setId(id);

        courseDescriptionService.save(courseDescription);
        //返回课程的唯一的多对多表结构的 id 值
        return id;
    }

    //根据课程 id 值查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //1: 先查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2: 查询简介表 描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1: 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        //educourse默认返回值是 int 类型 并要判断给方法的行数
        if (update == 0){
            throw new HeartException(20001,"修改课程信息失败!");

        }

        //修改简介表/描述表
        EduCourseDescription description = new EduCourseDescription();
        //修改里面的两个参数
        description.setId(courseInfoVo.getId());
        description.setId(courseInfoVo.getDescription());

        this.courseDescriptionService.updateById(description);
    }

    //根据课程 id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程信息
    @Override
    public void removeCourse(String courseId) {
        //1: 根据课程 id 删除小节
        this.eduVideoService.removeVideoByCourseId(courseId);

        //2: 根据课程 id 删除课程的简介 描述
        this.eduChapterService.removeCourseId(courseId);

        //3: 根据课程 id 删除描述
        this.courseDescriptionService.removeById(courseId);

        //4: 根据课程 id 删除课程本身信息
        int result = baseMapper.deleteById(courseId);
        //判断 result结果  大于等于 1 成功 等于等于 0 失败
        if (result == 0){ //失败返回值
            throw new HeartException(20001,"删除课程信息失败!");
        }
    }

    //条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        //2: 根据讲师 id 查询所讲的课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        //3: 判断条件值是否为空 非空判断
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) //通过 StringUtils包的工具类,判断课程的一级分类是否为空
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());

        //4: 非空判断
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) //通过 StringUtils包的工具类,判断课程的二级分类是否为空
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());

        //5: 销量排序 判断
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) //销量排序
            wrapper.orderByDesc("buy_count");

        //6: 最新时间排序 判断
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) //最新时间排序
            wrapper.orderByDesc("gmt_create");

        //7: 价格排序 判断
        if (StringUtils.isEmpty(courseFrontVo.getPriceSort())) //价格排序
            wrapper.orderByDesc("price");

        baseMapper.selectPage(pageCourse,wrapper);

        //把分页的数据获取出来放到map中
        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();

        boolean hasNext = pageCourse.hasNext(); //是否有下一页
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页


        //把分页的数据获取出来.放到 map 集合中最终返回分页的数据
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //返回对象
        return map;
    }

    //根据课程 id.编写 sql 语句实现查询课程的基本信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

}









































