package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Eval;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.EvalExtend;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
 * 评价信息 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
public interface IEvalService extends IService<Eval> {
	int add(Student student,Eval eval);
	List<Eval> getByPage(EvalExtend extend);
}
