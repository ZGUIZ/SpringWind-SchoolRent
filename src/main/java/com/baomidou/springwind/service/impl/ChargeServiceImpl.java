package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.CapitalMapper;
import com.baomidou.springwind.mapper.ChargeMapper;
import com.baomidou.springwind.mapper.CheckStatementMapper;
import com.baomidou.springwind.service.ICapitalService;
import com.baomidou.springwind.service.IChargeService;
import com.baomidou.springwind.service.IMessageService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private CapitalMapper capitalMapper;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private CheckStatementMapper checkStatementMapper;

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

    @Override
    public List<Charge> queryListByPage(RequestInfo<Charge> page) {
        page.setAmmount(mapper.getCount(page));
        return mapper.queryForPage(page);
    }

    @Override
    public Charge queryById(String id) {
        Charge charge = new Charge();
        charge.setChargeId(id);
        return mapper.queryById(charge);
    }

    @Transactional
    @Override
    public boolean pass(String id, float money,Manager manager) {
        Date now = new Date();
        Charge param = new Charge();
        param.setChargeId(id);
        Charge charge = mapper.queryByIdForUpdate(param);
        charge.setStatus(1);
        charge.setResponseDate(now);
        charge.setResponsePerson(manager.getUserId());
        mapper.updateById(charge);
        //转账
        Capital capital = capitalMapper.selectForUpdate(charge.getUserId());
        capital.setCapital(capital.getCapital()+money);
        capitalMapper.updateById(capital);
        //账单记账
        CheckStatement cs = new CheckStatement();
        cs.setStateId(UUIDUtil.getUUID());
        cs.setAmount(money);
        cs.setType(0);
        cs.setCreateDate(new Date());
        cs.setMemo("充值");
        cs.setUserId(charge.getUserId());
        checkStatementMapper.insert(cs);


        //发送通知
        StringBuffer content = new StringBuffer("您充值的金额");
        content.append(String.valueOf(money)).append("已经到账，感谢您对校园租的支持");
        messageService.pushMessage("您充值的资金已经到账",content.toString(),charge.getUserId());

        return true;
    }

    @Transactional
    @Override
    public boolean unPass(String id,Manager manager){
        Charge param = new Charge();
        param.setChargeId(id);
        Charge charge = mapper.queryByIdForUpdate(param);
        charge.setStatus(2);
        charge.setRequestDate(new Date());
        charge.setResponsePerson(manager.getUserId());
        mapper.updateById(charge);

        //发送通知
        messageService.pushMessage("您的充值未通过","您的充值请求未通过，请提交符合要求的证明文件。",charge.getUserId());
        return true;
    }
}
