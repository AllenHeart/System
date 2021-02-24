package com.atguigu.eduservice.listener;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：SubjectExcelListener
 * 创建时间：2020/9/27 8:13 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 读取文件的监听器
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为 SubjectExcelListener 不能呢交给 Spring 容器进行管理.需要自己手动new 对象.不能注入其他对象
    //不能实现读取数据库的信息
    //手动需要自动装配注入 实现业务的方法
    public EduSubjectService subjectService;
    //无参构造
    public SubjectExcelListener() { }
    //有参构造的实现方法
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取 Excel 内容.一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null ){
            throw new HeartException(20001,"Excel文件数据为空");
        }

        //一行一行读取.每次读取有两个值.第一个值一级分类.第二个值二级分类
        /**
         * 判断一级分类是否重复==============================================
         */
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        //判断Excel 一级分类 如果没有相同的一级分类.进行添加
        if (existOneSubject == null){

            //existOneSubject里面的对象参数
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());//一级分类的名称

            //返回一级分类的对象值
            subjectService.save(existOneSubject);
        }

        //创建parent_id 的变量 获取一级分类的 id 值
        String pid = existOneSubject.getId();

        //二级分类
        /**
         * 判断二级分类是否重复==============================================
         */
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) { //

            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName()); //二级分类名称

            //返回二级分类的对象值
            subjectService.save(existTwoSubject);
        }

    }


    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        //返回的对象值一级分类
        return oneSubject;
    }


    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        //返回的对象值二级分类
        return twoSubject;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}





















