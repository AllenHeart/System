package com.atguigu.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：CourseFrontVo
 * 类 描 述：TODO
 * 创建时间：2020/11/4 3:18 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 封装前端的课程对象
 */

@ApiModel(value = "课程查询对象", description = "课程查询对象封装")
@Data
public class CourseFrontVo {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;

}



