package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
//@CrossOrigin //解决跨域问题 跨域组件由网关服务取代全局配置
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    /**
     * ===========================查询逻辑删除方法==================================
     */
    //2:com.atguigu.vod.service 业务注入实现方法
    @Autowired
    private EduTeacherService teacherService;

    //1: 查询讲师所有数据
    //resu风格写代码
    //访问地址: http:localhost:8001/eduservice/teacher/findAll
//    @ApiOperation(value = "查询所有讲师列表")
//    @GetMapping("findAll")
//    public List<EduTeacher> findAllTeacher(){
//        //调用 com.atguigu.vod.service 的方法实现查询所有的功能 返回的类型 list
//        List<EduTeacher> list = teacherService.list(null);
//        //返回该方法值
//        return list;
//    }


//    @ApiOperation(value = "逻辑删除讲师列表")
//    //2: 逻辑删除讲师的方法
//    @DeleteMapping("{id}")
//    //得到数据中的 id 值  返回的类型 Boolean 类型
//    public boolean removeTeacher(
//            @ApiParam(name = "id",value = "讲师 ID",required = true)
//            @PathVariable String id){
//        //
//        boolean flag = teacherService.removeById(id);
//        //返回值
//        return flag;

    /**
     * ===========================统一返回结果方法==================================
     */

    @ApiOperation(value = "查询所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用 teacherService 的 CRUD 方法实现查询All 所有的讲师列表
        List<EduTeacher> list = teacherService.list(null);
        return R.success().data("items", list);
    }

    @ApiOperation(value = "逻辑删除讲师列表")
    //2: 逻辑删除讲师的方法
    @DeleteMapping("{id}")
    //得到数据中的 id 值  返回的类型 Boolean 类型
    public R removeTeacher(
            @ApiParam(name = "id",value = "讲师 ID",required = true)
            @PathVariable String id){
        //
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.success();
        } else {
            return R.error();
        }

    }

    /**
     * ===========================分页查询讲师的方法==================================
     * 分页查询讲师的方法
     * current 代表当前页
     * limit 每页显示的记录数
     */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}") //get提交
    public R pageListTeacher(
                            @ApiParam(name = "current",value = "当前页数",required = true)
                            @PathVariable long current,
                            @ApiParam(name = "limit",value = "每页记录数",required = true)
                            @PathVariable long limit ){
        //创建 page 对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //try catch 去捕获当前自定义的异常类
//        try{
//            int i = 10/0;
//        }catch (Exception e){
//            //执行自定义异常类
//            throw new HeartException(20001,"执行了自定义异常处理....");
//        }


        //调用方法实现分页
        //调用方法时候.底层做了封装.把分页所有的数据封装到pageTeacher对象里面
        this.teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal(); //记录总页数
        //getRecords 返回的 list 集合
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list 集合


//        //创建 map 集合用 map 集合的方式存到集合中使用
//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//
//        //返回的值
//        return R.success().data(map);

        //返回的每页总数
        //返回每页的 list 的数据
        return R.success().data("total",total).data("rows:", records);
    }

    /**
     * ===========================条件查询带分页的方法==================================
     *
     *  RequestBody : 使用 json传递数据.把json数据封装到对应的对象里面  TeacherQuery对象
     *  ResponseBody: 返回数据.一般都返回json数据
     */
//    @ApiOperation(value = "条件查询对象封装")
//    @PostMapping("pageTeacherCondition/{current}/{limit}")
//    public R pageTeacherCondition(
//                                 @ApiParam(name = "current",value = "当前页数",required = true)
//                                 @PathVariable long current,
//                                 @ApiParam(name = "limit",value = "每页记录数",required = true)
//                                 @PathVariable long limit,
//                                 //传入封装数据的对象.用对象形式得到该条件
//                                 //@RequestBody(required = false) 返回 json 数据里面传值required=false 表示这里面值  可以为 null没有
//                                 @ApiParam(name = "teacherQuery", value = "查询封装对象", required = false)
//                                 @RequestBody(required = false) TeacherQuery teacherQuery){
//        //1: 创建 page 对象 传入查询的参数 当前页 记录数
//        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
//
//        //3: 构建QueryWapper 条件
//        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
//        //4: 多种条件组合查询
//        //在Mybatis_pius框架 利用动态 sql 语句用判断拼接 sql 语句 把封装 vo 的数据取出
//        String name = teacherQuery.getName(); //名称
//        Integer level = teacherQuery.getLevel(); //级别
//        String begin = teacherQuery.getBegin();//开始时间
//        String end = teacherQuery.getEnd(); //结束时间
//        //判断条件是否为空.如果不为空拼接条件
//
//        /**
//         * StringUtils springframework 的容器 jar
//         * isEmpty表示 name值为空或者为空字符串
//         */
//        //名称
//        if(!StringUtils.isEmpty(name)){
//            //模糊查询
//            //构建它的条件  like 的值 1: 对应数据库字段名称的值 name 2: 具体的值name最终存入的值
//            wrapper.like("name",name);
//        }
//
//        //级别
//        if(!StringUtils.isEmpty(level)){
//            wrapper.eq("level",level);
//        }
//
//        //开始时间  <=
//        if(!StringUtils.isEmpty(begin)){
//            wrapper.ge("gmt_create",begin);
//        }
//
//        //结束时间 wrapper 的框架查询方式  >=
//        if(!StringUtils.isEmpty(end)){
//            wrapper.le("gmt_modified",end);
//        }
//
//        /**
//         * 前端页面添加讲师的方法 增加排序方法
//         * 根据创建时间做一个降序排序.为了是每次添加数据都是第一列显示
//         */
//        wrapper.orderByDesc("gmt_modified");
//
//
//        //2: 调用方法实现条件分页的
//        this.teacherService.page(pageTeacher,wrapper);
//
//        long total = pageTeacher.getTotal(); //记录总页数
//        List<EduTeacher> records = pageTeacher.getRecords(); //数据 list 集合
//        return R.success().data("total:",total).data("rows:", records);
//    }

    //条件查询带分页的方法
    //@RequestBody(required = false) 这个值可以没有的意思
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> page = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper();

        if(!StringUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name",teacherQuery.getName());
        }

        if(!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level",teacherQuery.getLevel());
        }

        if(!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_create",teacherQuery.getBegin());
        }

        if(!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_modified",teacherQuery.getEnd());
        }

        wrapper.orderByDesc("gmt_modified");
        this.teacherService.page(page,wrapper);
        long total = page.getTotal(); //总记录数
        List<EduTeacher> records = page.getRecords();  //list集合

        return R.success().data("total",total).data("rows",records);
    }

    /**
     * ===========================添加讲师接口的方法==================================
     * RequestBody : 通过 json 传入数据并做封装到该方法中
     * ResponseBody: 返回 json 数据
     */
    @ApiOperation(value = "新增讲师的接口")
    @PostMapping("addTeacher")
    /**
     * 通过对象形式进行传递RequestBody
     */
    public R addTeacher(
                    @ApiParam(name = "eduTeacher",value = "添加讲师数据",required = false)
                    @RequestBody EduTeacher eduTeacher){ //传递 json 数据并封装到该对象中EduTeacher
        boolean save = teacherService.save(eduTeacher);
        //如果说 boolean 类型返回是 true那么返回数据成功
        if(save){
            return R.success();
            //如果说它是false 那么返回失败传递
        } else {
            return R.error();
        }
    }

    /**
     * ===========================根据讲师 id 修改查询==================================
     * RequestBody : 通过 json 传入数据并做封装到该方法中
     * ResponseBody: 返回 json 数据
     */
    @ApiOperation(value = "根据 id 修改查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(
            @ApiParam(name = "id",value = "讲师 ID",required = true)
            @PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.success().data("teacher",eduTeacher);
    }

    /**
     * ===========================讲师修改功能==================================
     * RequestBody : 通过 json 传入数据并做封装到该方法中
     * ResponseBody: 返回 json 数据
     */
    @ApiOperation(value = "修改讲师的数据")
    @PostMapping("updateTeacher")
    public R updateTeacher(
            @ApiParam(name = "eduTeacher",value = "修改讲师数据",required = true)
            @RequestBody EduTeacher eduTeacher){
        //调用teacherService的updateById根据对象修改数据
        boolean update = teacherService.updateById(eduTeacher);

        //判断 如果修改为 true 那么数据修改成功
        if (update) {
            return R.success();
            //如果失败那么 false
        } else {
            return R.error();
        }
    }
}

