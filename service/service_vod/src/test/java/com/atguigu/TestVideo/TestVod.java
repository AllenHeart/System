package com.atguigu.TestVideo;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：TestVod
 * 类 描 述：TODO
 * 创建时间：2020/10/14 8:54 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
public class TestVod {

    public static void main(String[] args) throws ClientException {
//        //本地文件上传接口
//        String accessKeyId = ""<Your accessKeyId>""; //oss 存储对象 id
//        String accessKeySecret= ""<Your accessKeySecret>""; //存储对象的密钥
//
//        String title = "What If I Want to Move Faster - upload by sdk";   //上传之后文件名称
//        String fileName = "/Users/huanghao/uploadvideo/What If I Want to Move Faster.mp4";  //本地文件路径和名称
//
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }

        getPlayAuth();
    }



    //根据视频 id 获取视频的 aliyun 视频的凭证
    public static void getPlayAuth() throws ClientException {
        //1: 创建初始化对象值
        DefaultAcsClient client = InitObject.initVodClient("<Your accessKeyId>",
                "<Your accessKeySecret>");

        //2: 创建视频的地址请求头 request 和 响应数据 response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //3: 向 request 设置 id 值
        request.setVideoId("");

        //4: 调用初始化对象的方法得到凭证
        response  = client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());
    }


    //跟据视频id获取视频地址
    public static void getPlay() throws ClientException {
        //1: 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("<Your accessKeyId>",
                "<Your accessKeySecret>");

        //2: 创建视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //3: 像request对象里面设置视频id
        request.setVideoId("");

        //4: 调用初始化对象里面的方法,传递request获取数据
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
