package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：DemoData
 * 创建时间：2020/9/27 5:16 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

/**
 * 实体类
 */
@Data
public class DemoData {

//    //设置 excel 表头名称
//    @ExcelProperty("学生编号")
//    private Integer sno;
//
//    //设置 excel 表头名称
//    @ExcelProperty("学生姓名")
//    private String sname;

    //设置列对应的属性 index 对应的事一级分类
    @ExcelProperty(value = "学生编号" ,index = 0)
    private Integer sno;

    //设置列对应的属性 index 对应的是二级分类
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;
}
