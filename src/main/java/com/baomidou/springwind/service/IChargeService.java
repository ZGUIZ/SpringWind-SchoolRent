package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Charge;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Student;

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
}
