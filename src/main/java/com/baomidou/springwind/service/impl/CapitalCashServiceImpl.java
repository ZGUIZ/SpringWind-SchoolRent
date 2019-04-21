package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.CapitalCash;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.CapitalCashMapper;
import com.baomidou.springwind.service.ICapitalCashService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 资金提现请求 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class CapitalCashServiceImpl extends BaseServiceImpl<CapitalCashMapper, CapitalCash> implements ICapitalCashService {

    @Autowired
    private CapitalCashMapper mapper;

    @Override
    public boolean addCapitalCash(Student student, CapitalCash capitalCash) {

        capitalCash.setUserId(student.getUserId());
        capitalCash.setCashId(UUIDUtil.getUUID());
        capitalCash.setStatus(0);
        capitalCash.setRequestTime(new Date());
        int count = mapper.insert(capitalCash);
        if(count>0) {
            return true;
        }
        return false;

    }
}
