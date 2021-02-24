package com.atguigu.commonutils;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：R
 * 创建时间：2020/9/14 11:11 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回数据格式结果的类
 */
@Data //lombok 注解生成 set 方法get. toSting 有参构造 无参构造
public class R {


    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //把构造方法作为私有化
    private R(){}

    //成功静态方法
    public static R success(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("返回成功!");

        //返回的值
        return r;
    }

    //失败的静态方法
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("返回失败!");

        //返回的值
        return r;
    }

    /**
     *
     * @param success
     * @return
     */
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object>map){
        this.setData(map);
        return this;
    }
}
