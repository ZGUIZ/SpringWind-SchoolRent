package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Message;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
 * 消息 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2019-04-01
 */
public interface IMessageService extends IService<Message> {
	List<Message> pushUnRead(Student student);
}
