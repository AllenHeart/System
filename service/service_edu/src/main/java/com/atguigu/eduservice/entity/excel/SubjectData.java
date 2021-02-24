package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：SubjectData
 * 创建时间：2020/9/27 8:02 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

/**
 * 创建Excel 对应 的实体类
 * oneSubjectName: 一级分类
 * twoSubjectName: 二级分类
 */
@Data //get 和 set 方法
public class SubjectData {

    //设置列对应的属性 index 对应的事一级分类
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    //设置列对应的属性 index 对应的是二级分类
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
