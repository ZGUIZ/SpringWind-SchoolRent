package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Manager;
import com.baomidou.springwind.mapper.ManagerMapper;
import com.baomidou.springwind.service.IManagerService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class ManagerServiceImpl extends BaseServiceImpl<ManagerMapper, Manager> implements IManagerService {
	
}
