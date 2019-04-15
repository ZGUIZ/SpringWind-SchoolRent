package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Complain;
import com.baomidou.springwind.entity.OrderComplian;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
 * 订单投诉 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
public interface IOrderComplianService extends IService<OrderComplian> {
	boolean addComplain(OrderComplian complian, Student student) throws Exception;

	List<OrderComplian> queryListByPage(RequestInfo<OrderComplian> page);
}
