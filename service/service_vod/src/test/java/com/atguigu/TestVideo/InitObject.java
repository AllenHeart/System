package com.atguigu.TestVideo;

/**
 * 项 目 名 称：${project_name}
 * 类 名 称：${type_name}
 * 创 建 人：${user}
 * 创建时间：${date} ${time}
 * 类 描 述：
 *
 * @version: V1.8
 */

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 初始化
 */
public class InitObject {

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
