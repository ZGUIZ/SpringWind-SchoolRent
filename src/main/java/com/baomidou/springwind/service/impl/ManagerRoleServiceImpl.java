package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ManagerRole;
import com.baomidou.springwind.entity.Select2Bean;
import com.baomidou.springwind.mapper.ManagerRoleMapper;
import com.baomidou.springwind.service.IManagerRoleService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 管理员角色表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class ManagerRoleServiceImpl extends BaseServiceImpl<ManagerRoleMapper, ManagerRole> implements IManagerRoleService {

    @Autowired
    private ManagerRoleMapper managerRoleMapper;

    @Override
    public List<Select2Bean> queryForSelect() {
        return managerRoleMapper.queryForSelect();
    }
}
