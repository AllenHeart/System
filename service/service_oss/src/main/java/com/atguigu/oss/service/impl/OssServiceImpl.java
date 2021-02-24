package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：OssServiceImpl
 * 创建时间：2020/9/26 8:06 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

/**
 * 业务层的实现类
 * 编写上传头像的具体方法
 */
@Service
public class OssServiceImpl implements OssService {

    //上传头像的代码
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，
        // 创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        //1:文件路径的值
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //2: file 文件名称
            String filename = file.getOriginalFilename();

            //3: 上传文件流。
            InputStream inputStream = file.getInputStream();

            //在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //342423224.jpg
            filename = uuid+filename;

            //把文件按照日期格式分类
            //2020/11/21/01.jpg 日期格式
            //获取当前的日期

            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接地址进行分类
            filename = datePath+"/"+filename;


            /**
             * ossClient参数:
             *     第一个参数: bucketName名称
             *     第二个参数: 上传到 OSS 文件路径和文件名称. /01/images/.jpg
             *     第三个参数: 上传文件的输入流
             */
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云 oss 的路径拼接出来
            //  https://  edu-2002 . oss-cn-beijing.aliyuncs.com / WechatIMG3.jpeg
            String url = "https://" +bucketName+ "." +endpoint+ "/" +filename;
            //返回
            return url;
        } catch (Exception e){
            //异常打印
            e.printStackTrace();
            return null;
        }
    }
}
