package com.atguigu.eduorder.client;

import com.atguigu.commonutils.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：EduClient
 * 类 描 述：TODO
 * 创建时间：2020/11/7 3:37 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 课程远程调用
 */
@Component //容器管理
@FeignClient(name = "service-edu")
public interface EduClient {

    //根据课程 id 查询课程信息
    @PostMapping("/eduservice/coursefont/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);

}

