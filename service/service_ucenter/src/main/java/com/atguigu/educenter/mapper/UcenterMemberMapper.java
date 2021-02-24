package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Abner
 * @since 2020-10-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //查询某一天的注册人数
    Integer countRegisterDay(String day);
}
