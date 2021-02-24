package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.HeartException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-10-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired //Redis 验证码的校验自动装配
    private RedisTemplate<String, String> redisTemplate;

    //最终实现用户的登录方法
    @Override
    public String login(UcenterMember member) {
        //1: 获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //2: 手机号和密码的非空的判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new HeartException(20001, "用户登录失败!");
        }

        //3: 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);

        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if (mobileMember == null) {//没有这个手机号
            throw new HeartException(20001, "用户您的手机号不存在!请输入正确的号码!");
        }

        //判断用户的密码
        /**
         * 因为存储到数据库密码肯定加密的
         * 把输入的密码进行加密.再和数据库密码进行比较
         * 加密方式: Md5的方式
         */
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new HeartException(20001, "用户您的密码错误,请重新输入正确的密码!");
        }

        //判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new HeartException(20001, "该用户被禁用,请输入正确的信息!");
        }

        //登录成功 生成 token 的字符串,使用 Jwt 的工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        //返回 JwtToken
        return jwtToken;
    }

    //用户注册方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();//验证码
        String mobile = registerVo.getMobile();//用户手机号
        String nickname = registerVo.getNickname();//用户的名称
        String password = registerVo.getPassword();//用户的密码

        //非空判断
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new HeartException(20001, "用户注册失败!");
        }


        //先获取 redis 的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        //判断验证码
        if (!code.equals(redisCode)) {
            throw new HeartException(20001, "输入验证码失败,请成功输入!");
        }

        //判断用户的注册手机号是否重复,表里面存在相同的手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);

        //调用 selectCount
        Integer count = baseMapper.selectCount(wrapper);

        //判断 count 的值 > 1  手机号注册失败
        if (count > 0) {
            throw new HeartException(20001, "用户手机号注册失败!");
        }

        //如果依据验证码 成功 手机号成功 那么将数据最后添加到数据库中
        UcenterMember user = new UcenterMember();

        //set 存入数据值
        user.setMobile(mobile); //用户的手机号
        user.setNickname(nickname); //用户的名称
        user.setPassword(MD5.encrypt(password)); //用户的密码需要做一个加密的 Md5
        user.setIsDisabled(false);// 用户不禁用
        user.setAvatar("https://edu-2002.oss-cn-beijing.aliyuncs.com/2020/10/27/b79d84094b21448faa10883d919df5e7file.png");//默认头像
        System.out.println(user);

        //返回到 baseMappr 添加到数据库
        baseMapper.insert(user);
    }

    //根据 openid 做查询
    @Override
    public UcenterMember getOpenIdMember(String openId) {
        //根据 QueryWrapper 对象查询 openid
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        //设置查询条件 根据 openid 查询
        wrapper.eq("openid", openId);

        //调用 baseMapper 实现最终的查询
        UcenterMember member = baseMapper.selectOne(wrapper);

        //返回 最终的对象查询结果
        return member;
    }

    //查询某一天的注册人数
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegisterDay(day);
    }
}






































