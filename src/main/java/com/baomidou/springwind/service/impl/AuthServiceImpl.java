package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Auth;
import com.baomidou.springwind.mapper.AuthMapper;
import com.baomidou.springwind.service.IAuthService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 绑定第三方账号的关联表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class AuthServiceImpl extends BaseServiceImpl<AuthMapper, Auth> implements IAuthService {
	
}
