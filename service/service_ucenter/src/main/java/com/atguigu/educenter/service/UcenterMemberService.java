package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Abner
 * @since 2020-10-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //实现用户登录的接口
    String login(UcenterMember member);

    //用户注册接口
    void register(RegisterVo registerVo);

    //根据 openid 判断
    UcenterMember getOpenIdMember(String openId);

    //查询某一天的注册人数
    Integer countRegister(String day);
}
