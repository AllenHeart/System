package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 类 描 述：封装课程管理实体类
 * 项目名称：guli_parent
 * 类 名 称：CourseInfoVo
 * 创建时间：2020/9/30 3:14 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */
@Data
public class CourseInfoVo {

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "二级分类的ID")
    private String subjectId;

    @ApiModelProperty(value = "一级分类的ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    //0.01  BigDecimal类型可以精确到圆角分
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
}
