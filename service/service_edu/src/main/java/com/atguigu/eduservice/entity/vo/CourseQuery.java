package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：CourseQuery
 * 创建时间：2020/10/12 6:33 AM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */
@Data
public class CourseQuery {

    /**
     * 课程提交的标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 最终提交的状态
     */
    @ApiModelProperty(value = "课程提交的最终状态")
    private String status;

    /**
     * 课程提交的开始
     */
    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;

    /**
     * 课程提交的结束
     */
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
