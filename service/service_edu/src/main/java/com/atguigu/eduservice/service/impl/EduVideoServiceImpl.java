package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-30
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired //自动装配
    private VodClient vodClient;

    //1 根据课程id删除小节
    // TODO 删除小节，删除对应视频文件
    @Override
    public void removeVideoByCourseId(String courseId) {

        //根据课程 id 查询课程所有的课程 id 数据
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        //设置条件 根据课程 course_id 查询条件
        wrapperVideo.eq("course_id",courseId);
        //根据指定的列查询 video_sourse_id 的数据
        wrapperVideo.select("video_source_id");

        //调用 beseMapper 查询条件
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        //遍历List<EduVideo> 变成 List<String>
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            //取出 eduVideo 数据
            String videoSourceId = eduVideo.getVideoSourceId();

            //判断 video_sourse_id 的值不等于空 那么在放到
            if (!StringUtils.isEmpty(videoSourceId)){

                //放到 videoIds 集合里面最终实现
                videoIds.add(videoSourceId);
            }
        }

        //判断如果 videoIds 的值也不等于null  因为videoIds 是集合那么 size 判断用> 0如果有值的的话调方法进行删除
        if (videoIds.size() >0 ){
            //根据多个视频 id 删除多个视频
            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}











