package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Capital;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Student;

/**
 * <p>
 * 用户资金信息 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-28
 */
public interface ICapitalService extends IService<Capital> {
    String add = "add";
    String sub="sub";

    /**
     * 更改余额
     * @param user
     * @param amount
     * @param type
     * @return
     * @throws Exception
     */
	int changeCapital(Student user,Float amount,String type) throws Exception;

	int transfer(Student from,Student to,Float amount) throws Exception;
}
