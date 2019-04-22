package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.*;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 资金提现请求 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface ICapitalCashService extends IService<CapitalCash> {
	boolean addCapitalCash(Student student,CapitalCash capitalCash);

	List<CapitalCash> queryListByPage(RequestInfo<CapitalCash> page);

	CapitalCash queryById(String id);

	boolean pass(String id, Manager manager,float money);
	boolean unPass(String id,Manager manager);
}
