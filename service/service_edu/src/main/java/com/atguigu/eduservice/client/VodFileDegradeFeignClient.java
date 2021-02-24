package com.atguigu.eduservice.client;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：VodFileDegradeFeignClient
 * 类 描 述：TODO
 * 创建时间：2020/10/18 11:45 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 熔断器的实现类
 * 封装定义 方法的异常处理
 */
@Component //交由 spring 容器去管理
public class VodFileDegradeFeignClient implements VodClient{

    //方法调用失败出错后以下执行方法
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("==熔断器==删除视频出错了!");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("==熔断器==批量删除多个视频出错了!");
    }
}












