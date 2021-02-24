package com.atguigu.servicebase.exceptionhandler;

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：HeartException
 * 创建时间：2020/9/16 6:13 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义的异常类
 */
@Data
@AllArgsConstructor  //生成一个全参数的构造函数
@NoArgsConstructor   //生成一个无参数的构造函数
public class HeartException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String msg;
}
