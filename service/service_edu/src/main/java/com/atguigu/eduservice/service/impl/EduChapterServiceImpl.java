package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-30
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired//自动装配
    private EduVideoService videoService;//注入小节的 Service 方面后阶段的查询

    //实现课程 id 查询章节小节的方法封装
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1: 根据课程 id 查询里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2: 根据课程 id 查询里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        /**
         * 通过实现课程 id 查询所有的章节得到的数据
         * 把查询的章节数据做集合遍历
         */
        //3: 遍历查询章节 list 集合进行封装
        //创建 list 集合用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter 对象的值赋值到 ChapterVo 里面去
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //把 ChapterVo 放在最终的 List 集合中去
            finalList.add(chapterVo);

            //创建集合,用于封装章节的小节 进行封装
            List<VideoVo> videoList = new ArrayList<>();

            /**
             * 小节存在章节 用嵌套 for 存循环取值
             */
            //4: 遍历查询小节 list 集合进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                //得到每个小节数值j
                EduVideo eduVideo = eduVideoList.get(j);
                //判断: 小节里面的 chapterid 和章节里面的id 值是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                //进行封装
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo, videoVo);
                //放到小节封装集合里去
                videoList.add(videoVo);

            }

        }
        //把封装之后小节 list 集合.放到章节对象里面
            chapterVo.setChildren(videoList);
    }

        //返回参数
        return finalList;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapter 章节id.查询小节表.如果查询数据.不进行删除.
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);

        //判断
        if (count > 0){ //查询出小节.不进行删除
            throw new HeartException(200001,"不能删除!");
        } else { //不能查询数据.进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            //如果说 删除成功返回值 是 1
            //1 > 0 判断为 true
            //失败 0 > 0 为 false
            return result > 0;
        }
    }

    //2: 根据课程 id 删除课程的简介 描述
    @Override
    public void removeCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
















