package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.*;
import com.baomidou.mybatisplus.service.IService;

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

	OrderComplian queryById(String id);

	/**
	 * 同意赔偿请求
	 * @param manager
	 * @param id
	 * @param money
	 * @return
	 * @throws Exception
	 */
	boolean agree(Manager manager,String id, float money) throws Exception;

	/**
	 * 不同意赔偿请求
	 */
	boolean disagree(Manager manager,String id,float money) throws Exception;
}
