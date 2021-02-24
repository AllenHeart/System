package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-30
 */
public interface EduChapterService extends IService<EduChapter> {

    //根据课程 id 查询章节小节方法
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //如果删除章节下面的小节,不让进行删除操作
    boolean deleteChapter(String chapterId);

    //2: 根据课程 id 删除课程的简介 描述
    void removeCourseId(String courseId);
}
