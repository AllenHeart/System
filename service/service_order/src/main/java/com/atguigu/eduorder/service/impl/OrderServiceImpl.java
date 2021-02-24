package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.CourseWebVoOrder;
import com.atguigu.commonutils.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Abner
 * @since 2020-11-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired //自动装配 远程调用 熔断器 用户课程信息服务
    private EduClient eduClient;

    @Autowired //自动装配 远程调用 熔断器 用户订单信息服务
    private UcenterClient ucenterClient;


    //1: 生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用获取到订单用户 id 信息
        UcenterMemberOrder userInfoOrder = this.ucenterClient.getUserInfoOrder(memberId);

        //通过远程调用获取课程 id的信息
        CourseWebVoOrder courseInfoOrder = this.eduClient.getCourseInfoOrder(courseId);

        //创建 Order 对象.向 order 对象里面设置需要的数据
        Order order = new Order();

        //设置所需的数据
        order.setOrderNo(OrderNoUtil.getOrderNo());         //订单号
        order.setCourseId(courseId);                        //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());   //设置课程标题
        order.setCourseCover(courseInfoOrder.getCover());   //设置课程封面
        order.setTeacherName(courseInfoOrder.getTeacherName());//设置老师姓名
        order.setTotalFee(courseInfoOrder.getPrice());      //订单金额
        order.setMemberId(memberId);                        //会员编号
        order.setMobile(userInfoOrder.getMobile());         //手机号
        order.setNickname(userInfoOrder.getNickname());     //设置昵称

        order.setStatus(0);                                 //订单状态: 0未支付 1已支付
        order.setPayType(1);                                //支付类型:  微信支付1

        //order 假如数据 然后加到数据库
        baseMapper.insert(order);

        //最终返回订单号
        return order.getOrderNo();
    }
}



























