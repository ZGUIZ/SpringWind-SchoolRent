package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ManagerRole;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Select2Bean;

import java.util.List;

/**
 * <p>
 * 管理员角色表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IManagerRoleService extends IService<ManagerRole> {
	List<Select2Bean> queryForSelect();
}
