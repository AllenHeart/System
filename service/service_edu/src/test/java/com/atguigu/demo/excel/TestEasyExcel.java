package com.atguigu.demo.excel;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：TestEasyExcel
 * 创建时间：2020/9/27 5:20 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现读写的数据
 */
public class TestEasyExcel {

    public static void main(String[] args) {
        //实现 excel写的操作
        //设置写入文件夹地址和 Excel 文件名称
//        String filename = "/Users/huanghao/Documents/Student.xlsx";

        //调用 easyexcel 里面的方法实现写操作
        //write 方法两个参数.第一个参数文件路径名称.第二个参数实体类 class
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());


        //实现读操作
        String filename = "/Users/huanghao/Documents/Student.xlsx";
        //Excle 的监听器方法
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //创建方法返回 list 集合
    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
