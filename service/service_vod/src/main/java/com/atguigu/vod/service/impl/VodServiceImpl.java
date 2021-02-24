package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.atguigu.vod.service.VodService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：VodServiceImpl
 * 类 描 述：TODO
 * 创建时间：2020/10/14 10:03 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 服务的实现类
 */
@Service
public class VodServiceImpl implements VodService {

    //上传视频到阿里云的方法
    @Override
    public String uploadAlyVideo(MultipartFile file) {
        try{

            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();

            //title：上传之后显示名称 iastIndexOf 文件名称的截取 取值
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            //inputStream 上传文件输入流
            InputStream inputStream = file.getInputStream();

            //accessKeyId 存储对象的 id , accessKeySecret存储对象的 密钥
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);


            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除多个阿里云视频的方法
    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            //1: 初始化对象 InitVodClient 对象
            DefaultAcsClient client = InitVodClient.initVodClient(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET);

            //2: 创建 heepClient 的 request请求对象完成初始化
            DeleteVideoRequest request = new DeleteVideoRequest();

            //3: videoIdList用集合形式进行 转换成 1,2,3
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            //4: request 设置视频的 id
            request.setVideoIds(videoIds);

            //5: 调用初始化对象的方法来实现删除操作
            client.getAcsResponse(request);

            //6: 如果删除失败 抛出异常删除失败
        } catch (Exception e) {
            //控制台输出日志错误信息
            e.printStackTrace();
            throw new HeartException(20001,"批量删除视频失败!");
        }
    }

    //测试 List 集合进行StringUtils.join 方法
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("222");
        list.add("333");

        //111.222.333
        String join = StringUtils.join(list.toArray(), ",");
        System.out.println(join);
    }
}















