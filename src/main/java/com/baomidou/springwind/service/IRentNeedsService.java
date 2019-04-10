package com.baomidou.springwind.service;

import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.entity.*;
import com.baomidou.mybatisplus.service.IService;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 用户发布的租赁需求 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IRentNeedsService extends IService<RentNeeds> {
	boolean addRentNeeds(Student student,RentNeeds rentNeeds) throws UnsupportedEncodingException;
	List<RentNeeds> queryRentNeeds(Student student, RentNeedsExtend rentNeedsExtend);
	List<RentNeeds> queryMineNeeds(Student student, RentNeedsExtend rentNeedsExtend);
	boolean delRentNeeds(Student student,String id) throws IllegalAuthroiyException;

	String COMMENT = "comment";
	String DEL = "del";
	List<RentNeeds> queryListByPage(RequestInfo param, String type);

	boolean delByManager(String id);
	boolean reShowByManager(String id);
}
