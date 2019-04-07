package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Manager;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.mapper.ManagerMapper;
import com.baomidou.springwind.service.IManagerService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.SHA1Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Manager login(Manager manager) {
        manager.setPassword(SHA1Util.encode(manager.getPassword()));
        return managerMapper.login(manager);
    }

    @Override
    public List<Manager> queryListByPage(RequestInfo requestInfo) {
        requestInfo.setAmmount(managerMapper.queryCount(requestInfo));
        return managerMapper.queryManager(requestInfo);
    }
}
