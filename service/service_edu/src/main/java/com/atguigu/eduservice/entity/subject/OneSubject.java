package com.atguigu.eduservice.entity.subject;

/**
 * 类 描 述：一级分类数据封装
 * 项目名称：guli_parent
 * 类 名 称：OneSubject
 * 创建时间：2020/9/28 3:45 AM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程分类管理实体类
 * 一级分类
 */
@Data
public class OneSubject {

    private String id;
    private String title;

    //一个一级分类有多个二级分类 要与一级分类相对于的建立关系
    private List<TwoSubject> children = new ArrayList<>();

}
