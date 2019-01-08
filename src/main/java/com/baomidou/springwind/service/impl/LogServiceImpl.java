package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Log;
import com.baomidou.springwind.mapper.LogMapper;
import com.baomidou.springwind.service.ILogService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements ILogService {
	
}
