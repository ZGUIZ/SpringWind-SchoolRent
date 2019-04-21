package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Charge;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.ChargeMapper;
import com.baomidou.springwind.service.IChargeService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 充值请求 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
@Service
public class ChargeServiceImpl extends BaseServiceImpl<ChargeMapper, Charge> implements IChargeService {

    @Autowired
    private ChargeMapper mapper;

    @Override
    public boolean addCharge(Student student, Charge charge) {
        charge.setChargeId(UUIDUtil.getUUID());
        charge.setUserId(student.getUserId());
        charge.setRequestDate(new Date());
        int count = mapper.insert(charge);
        if(count>0){
            return true;
        }
        return false;
    }
}
