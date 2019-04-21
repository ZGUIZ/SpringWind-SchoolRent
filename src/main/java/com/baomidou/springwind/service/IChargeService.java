package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Charge;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Manager;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
 * 充值请求 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
public interface IChargeService extends IService<Charge> {
	boolean addCharge(Student student,Charge charge);

	List<Charge> queryListByPage(RequestInfo<Charge> page);

	Charge queryById(String id);

	boolean pass(String id,float money,Manager manager);
	boolean unPass(String id, Manager manager);
}
