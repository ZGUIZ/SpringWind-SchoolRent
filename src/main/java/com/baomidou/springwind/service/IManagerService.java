package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Manager;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.RequestInfo;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IManagerService extends IService<Manager> {
	Manager login(Manager manager);
	List<Manager> queryListByPage(RequestInfo requestInfo);

	boolean add(Manager manager) throws Exception;
	boolean getValidate(String mail) throws UnsupportedEncodingException, MessagingException;
}
