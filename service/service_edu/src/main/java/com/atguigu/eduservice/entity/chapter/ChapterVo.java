package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 类 描 述：课程大纲功能用于封装章节数据
 * 项目名称：guli_parent
 * 类 名 称：ChapterVo
 * 创建时间：2020/10/5 9:21 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    //表示章节多个多的关系有小节 建立关系
    private List<VideoVo> children = new ArrayList<>();
}
