package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Capital;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.CapitalMapper;
import com.baomidou.springwind.service.ICapitalService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 用户资金信息 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-28
 */
@Service
public class CapitalServiceImpl extends BaseServiceImpl<CapitalMapper, Capital> implements ICapitalService {

    @Autowired
    private CapitalMapper mapper;

    @Transactional
    @Override
    public int changeCapital(Student user, Float amount, String type) throws Exception{
        Capital capital=mapper.selectForUpdate(user.getUserId());
        if(add.equals(type)){
            capital.setCapital(capital.getCapital()+amount);
        } else if(sub.equals(type)){
            capital.setCapital(capital.getCapital()-amount);
        } else {
            throw new Exception("参数异常！");
        }
        capital.setUpdateTime(new Date());
        int row = mapper.update(capital);
        return row;
    }

    @Transactional
    @Override
    public int transfer(Student from, Student to, Float amount) throws Exception {
        Capital fromCapital = mapper.selectForUpdate(from.getUserId());
        Capital toCapital=mapper.selectForUpdate(to.getUserId());
        /**
         * 如果余额不足
         */
        if(fromCapital.getCapital()<amount){
            throw new Exception("余额不足！");
        }
        fromCapital.setCapital(fromCapital.getCapital()-amount);
        toCapital.setCapital(toCapital.getCapital()+amount);
        Date now=new Date();
        fromCapital.setUpdateTime(now);
        toCapital.setUpdateTime(now);
        int row=mapper.update(fromCapital);
        if(row<=0){
            throw new Exception("转账异常！");
        }
        int rec=mapper.update(toCapital);
        if(rec<=0){
            throw new Exception("入账异常！");
        }
        return rec;
    }
}
