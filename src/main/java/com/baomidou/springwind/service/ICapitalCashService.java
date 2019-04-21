package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.CapitalCash;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Student;

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
}
