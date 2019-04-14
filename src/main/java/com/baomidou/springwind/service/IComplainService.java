package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Complain;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Student;

import java.io.UnsupportedEncodingException;

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
}
