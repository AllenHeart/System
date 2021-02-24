package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 课程章节 前端控制器
 * </p>
 *
 *  课程信息的章节
 *
 * @author testjava
 * @since 2020-09-30
 */
@Api(description = "课程大纲列表")
//@CrossOrigin //解决浏览器跨域问题  跨域组件由网关服务取代全局配置
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired //自动装配
    private EduChapterService chapterService;

    //课程大纲列表,根据课程 id 进行查询
    @ApiOperation(value = "课程大纲列表,根据课程 id 进行查询")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(
            @ApiParam(name = "courseId",value = "根据课程 id 查询",required = true)
            @PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.success().data("allChapterVideo",list);
    }

    //课程管理添加章节
    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(
            @ApiParam(name = "eduChapter",value = "添加章节",required = true)
            @RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.success();
    }

    //根据章节的 id 查询
    @ApiOperation(value = "根据章节 id 查询")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(
            @ApiParam(name = "chapterId", value = "根据章节id 查询",required = true)
            @PathVariable String chapterId){
        EduChapter eduChapter = this.chapterService.getById(chapterId);
        return R.success().data("chapter",eduChapter);
    }

    //修改章节信息
    @ApiOperation(value = "修改章节信息")
    @PostMapping("updateChapter")
    public R updateChapter(
            @ApiParam(name = "eduChapter",value = "修改章节信息",required = true)
            @RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.success();
    }

    //删除章节的方法
    @ApiOperation(value = "删除章节的方法")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(
            @ApiParam(name = "chapterId",value = "删除章节的方法" ,required = true)
            @PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag){
            return R.success();
        } else {
            return R.error();
        }
    }
}










