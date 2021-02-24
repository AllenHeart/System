package com.atguigu.educenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项 目 名 称：guli_parent
 * 类 名 称：RegisterVo
 * 类 描 述：TODO
 * 创建时间：2020/10/29 1:31 PM
 * 创 建 人：huanghao
 *
 * @version: V1.8
 */

/**
 * 创建实体类,封装注册数据.包含验证码属性
 */
@Data
public class RegisterVo {

    @ApiModelProperty(value = "用名称")
    private String nickname;

    @ApiModelProperty(value = "用户手机号")
    private String mobile;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

}
