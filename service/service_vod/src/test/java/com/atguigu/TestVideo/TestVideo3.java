package com.atguigu.TestVideo;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：TestVideo3
 * 类 描 述：TODO
 * 创建时间：2020/10/15 5:45 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
public class TestVideo3 {

    public static void main(String[] args) throws Exception{

        String accessKeyId = "xxxxx";
        String accessKeySecret = "xxxxx";

        String title = "6 - What If I Want to Move Faster - upload by sdk";   //上传之后文件名称
        String fileName = "C:\\Users\\Abner\\Desktop\\在线教育\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        getPlayAuth();
    }

    public static void getPlayAuth() throws Exception{
        //根据视频id获取视频凭证
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("xxxx",
                "xxxx");
        //创建视频地址request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //像request对象里面设置视频id
        request.setVideoId("xxx");
        //调用初始化对象里面的方法,传递request获取数据
        response  = client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());
    }


    //跟据视频id获取视频地址
    public static void getPlayUrl()throws Exception {
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("xxxxxx",
                "xxxxx");
        //创建视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //像request对象里面设置视频id
        request.setVideoId("xx");
        //调用初始化对象里面的方法,传递request获取数据
        response  = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

}
