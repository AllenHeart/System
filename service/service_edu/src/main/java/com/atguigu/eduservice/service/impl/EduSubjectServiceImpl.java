package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
            //捕获异常
        } catch (Exception e){
            //打印信息
            e.printStackTrace();
        }
    }

    //课程分类列表(树形结构)
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1: 查询所有的一级分类 parent_id = 0
        QueryWrapper<EduSubject> wrapperOnt = new QueryWrapper<>();
        wrapperOnt.eq("parent_id","0");
        List<EduSubject> oneSubjectsList = baseMapper.selectList(wrapperOnt);

        //2: 查询所有的二级分类 parenr_id != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        /**
         * ==========封装一级分类数据==========
         */
        //创建list 集合.用于存储最终封装的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //查询出来所有的一级分类通过 list 遍历.得到每个一级分类对象.获取每一个分类对象值
        //封装到我们要求的 list 集合里面.List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubjectsList.size(); i++) { //遍历 oneSubjectsList集合
            //得到oneSubjectsList的 EduSubject集合的对象
            EduSubject eduSubject = oneSubjectsList.get(i);

            //把eduSubject 里面的值获取出来.放到 OneSubject 里面去
            OneSubject oneSubject = new OneSubject();

            //第一种方式获取 Onsubject
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());

            //第二种方式简化获取 工具类
            //用 Spring 框架的 BeanUtils 工具类更好的将 eduSubject 的数据值复制到对应的 OneSubject 对象里面去
            BeanUtils.copyProperties(eduSubject,oneSubject);

            //多个 OneSubjectList放到 finalSubjectList
            finalSubjectList.add(oneSubject);



            /**
             * ==========封装一级分类数据==========
             *
             * 在一级分类循环遍历查询所有的二级分类
             * 创建 list 集合封装每个一级分类的二级分类
             */
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            //遍历二级分类的 List 集合
            for (int j = 0; j < twoSubjectList.size(); j++) {
                //获取每个二级分类的数据
                EduSubject tSubject = twoSubjectList.get(j);

                //判断二级分类 parent_id 和一级分;1 的 id 是否一致
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    //把 tSubject 的值赋值到twoSubject 的里面去.放到 twlSubjectList 里面去
                    TwoSubject twoSubject = new TwoSubject();

                    BeanUtils.copyProperties(tSubject,twoSubject);

                    //返回到twoFinalSubjectList对象中
                    twoFinalSubjectList.add(twoSubject);
                }
             }

            //把一级下面所有的二级分类放到一级分类数据中的
            oneSubject.setChildren(twoFinalSubjectList);

        }
        //返回
        return finalSubjectList;
    }
}
















