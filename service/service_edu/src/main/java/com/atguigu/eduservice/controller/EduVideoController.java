package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *  web 层 添加小节的方法
 * @author testjava
 * @since 2020-09-30
 */
@Api(description = "课程管理-实现添加小节")
//@CrossOrigin //解决浏览器跨域注解 跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired //自动装配
    private EduVideoService videoService;

    //服务调度 生产者所定义的方法 提供实现
    @Autowired//自动装配
    private VodClient vodClient;

    //根据小节id查询
    @ApiOperation(value = "根据小节查询")
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(
            @ApiParam(name = "edVideo",value = "根据 id 查询小节",required = true)
            @PathVariable String id) {
        EduVideo eduVideo = this.videoService.getById(id);
        return R.success().data("eduVideo",eduVideo);
    }


    //添加小节
    @ApiOperation(value = "添加小节数据")
    @PostMapping("addVideo")
    public R addVideo(
            @ApiParam(name = "eduVideo",value = "添加小节",required = true)
            @RequestBody EduVideo eduVideo){
        this.videoService.save(eduVideo);
        return R.success();
    }

    //删除小节.删除小节时候.同时把里面的视频删除
    @ApiOperation(value = "删除小节数据")
    @DeleteMapping("{id}")
    public R deleteVideo(
            @ApiParam(name = "id",value = "删除小节",required = false)
            @PathVariable String id){

        //1: 服务调度 先根据小节 id 获取视频 id.调用方法实现视频删除的方法
        EduVideo eduVideo = this.videoService.getById(id);

        String videoSourceId = eduVideo.getVideoSourceId();

        //2: 根据视频 id, VodClient服务调度 远程调用视频的删除
        videoService.removeVideoByCourseId(videoSourceId);

        //4: 判断小节里面是否有视频 id 如果不等于null 然后调用方法进行
        if(!StringUtils.isEmpty(videoSourceId)){
            //3: 根据视频 id, VodClient服务调度 远程调用视频的删除
            R result = vodClient.removeAlyVideo(videoSourceId);

            //熔断技术 服务发现 判断方法
            if (result.getSuccess() == false){
                throw new HeartException(20001,"删除视频失败.熔断器....");
            }
        }

        //5: 删除小节 顺序 先删视频 在删小节数据
        this.videoService.removeById(id);
        return R.success();
    }

    //修改小节 TODO
    @ApiOperation(value = "修改小节数据")
    @PostMapping("updateVideo")
    public R updateVideo(
            @ApiParam(name = "eduVideo",value = "修改小节数据",required = true)
            @RequestBody EduVideo eduVideo){
        this.videoService.updateById(eduVideo);
        return R.success();
    }
}













