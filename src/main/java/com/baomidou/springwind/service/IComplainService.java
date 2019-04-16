package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Complain;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Manager;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.Student;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 异常申诉 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IComplainService extends IService<Complain> {
	boolean addComplain(Student student,Complain complain) throws UnsupportedEncodingException;
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	List<Complain> queryListByPage(RequestInfo<Complain> page);
	List<Complain> rentNeedsListByPage(RequestInfo<Complain> page);

	Complain queryById(String id);

	boolean deal(Manager manager, String id, int status);
}
