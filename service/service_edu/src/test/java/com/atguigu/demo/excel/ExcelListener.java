package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：ExcelListener
 * 创建时间：2020/9/27 7:33 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //一行一行的去读取 excel 内容
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("一行一行读取:" +data);
    }

    //读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头:"+ headMap);
    }

    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
