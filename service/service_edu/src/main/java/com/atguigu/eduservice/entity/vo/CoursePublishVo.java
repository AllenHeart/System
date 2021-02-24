package com.atguigu.eduservice.entity.vo;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：CoursePublishVo
 * 创建时间：2020/10/11 3:12 AM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 封装的最终发布实体类
 */
@Data
@ApiModel(value = "课程发布信息")
public class CoursePublishVo {

    /**
     * 课程 id 值
     */
    private String id;

    /**
     * 课程名称
     */
    private String title;

    /**
     * 课程封面
     */
    private String cover;

    /**
     * 课程数
     */
    private Integer lessonNum;

    /**
     * 课程一级分类
     */
    private String subjectLevelOne;

    /**
     * 课程的二级分类
     */
    private String subjectLevelTwo;

    /**
     * 课程的讲师名称
     */
    private String teacherName;

    /**
     * 课程价格
     */
    private String price;//只用于显示信息
}
