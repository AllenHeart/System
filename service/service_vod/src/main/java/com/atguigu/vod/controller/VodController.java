package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.atguigu.vod.service.VodService;
import java.util.List;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：com.atguigu.vod.controller.VodController
 * 类 描 述：TODO
 * 创建时间：2020/10/14 10:00 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
@Api(description = "上传视频到视频点播 阿里云视频点播微服务")
//@CrossOrigin //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired //自动装配
    private VodService vodService;

    //1: 上传视频到阿里云的方法
    @ApiOperation(value = "上传视频到阿里云的方法")
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(
            @ApiParam(name = "file",value = "上传视频的文件名称",required = true)
            MultipartFile file) {
        String videoId = this.vodService.uploadAlyVideo(file);
        return R.success().data("videoId",videoId);

    }


    //2: 根据视频 id 删除aliyun 视频
    @ApiOperation(value = "根据视频 id 删除阿里云视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(
            @ApiParam(name = "id",value = "删除视频 id",required = true)
            @PathVariable String id) {

        try {
            //1: 初始化对象 InitVodClient 对象
            DefaultAcsClient client = InitVodClient.initVodClient(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET);

            //2: 创建 heepClient 的 request请求对象完成初始化
            DeleteVideoRequest request = new DeleteVideoRequest();

            //3: request 设置视频的 id
            request.setVideoIds(id);

            //4: 调用初始化对象的方法来实现删除操作
            client.getAcsResponse(request);

            //5: 如果删除成功返回一个 success 删除成功
            return R.success();
            //6: 如果删除失败 抛出异常删除失败
        } catch (Exception e) {
            //控制台输出日志错误信息
            e.printStackTrace();
            throw new HeartException(20001,"删除视频失败!");
        }
    }


    //删除多个阿里云视频的方法
    //参数多个 id 批量删除 videoIdList list 集合传递多个 id 值
    @ApiOperation(value = "批量删除阿里云视频")
    @DeleteMapping("delete-batch")
    public  R deleteBatch(
            @ApiParam(name = "videoIdList",value = "批量删除视频的方法",required = true)
            @RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return R.success();
    }

    //根据视频 id 获取视频凭证
    @ApiOperation(value = "根据视频 id 获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(
            @ApiParam(name = "id",value = "视频 id",required = false)
            @PathVariable String id){

        try {
            //1: 创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            //2: 创建获取凭证 request 和 response 响应对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //3: 向 request 设置视频 id
            request.setVideoId(id);

            //4: 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到 response 响应对象最终返回
            String playAuth = response.getPlayAuth();

            //返回 response 得到数据 playAuth
            return R.success().data("playAuth",playAuth);

        }catch (Exception e){
            //异常打印
            throw new HeartException(20001,"获取凭证失败!");
        }

    }
}
































