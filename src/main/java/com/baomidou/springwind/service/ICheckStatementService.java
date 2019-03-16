package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.CheckStatement;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.CheckStatementExtend;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
 * 账单 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2019-03-12
 */
public interface ICheckStatementService extends IService<CheckStatement> {
	List<CheckStatement> list(Student student,CheckStatementExtend extend);
}
