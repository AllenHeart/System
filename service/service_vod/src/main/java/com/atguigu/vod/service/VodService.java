package com.atguigu.vod.service;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：VodService
 * 类 描 述：TODO
 * 创建时间：2020/10/14 10:02 AM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * 服务方法
 */
public interface VodService {

    //上传视频到阿里云的方法
    String uploadAlyVideo(MultipartFile file);

    //删除多个阿里云视频的方法
    void removeMoreAlyVideo(List videoIdList);
}
