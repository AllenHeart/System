package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：VodClient
 * 类 描 述：调用者 消费者
 * 创建时间：2020/10/17 5:01 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */
//fallback 指向该类熔断器出错执行 VodFileDegradeFeignClient里面的方法
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class) //@FeignClient注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
@Component //交由 spring管理该接口
public interface VodClient {

    //1: 定义服务调度的方法指向 生产者提供业务功能给消费者
    //2: 根据视频 id 删除aliyun 视频
    // @PathVariable 注解一定要指定参数名称.否则出错
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);


    //1: 定义调用删除多个视频的方法
    //删除多个阿里云视频的方法
    //参数多个 id 批量删除 videoIdList list 集合传递多个 id 值
    @DeleteMapping("/eduvod/video/delete-batch")
    public  R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}

















