package com.atguigu.oss.service;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：OssService
 * 创建时间：2020/9/26 8:05 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

import org.springframework.web.multipart.MultipartFile;

/**
 * 业务层实现头像上传oss 方法
 */
public interface OssService {
    //上传头像到 OSS 中
    String uploadFileAvatar(MultipartFile file);
}
