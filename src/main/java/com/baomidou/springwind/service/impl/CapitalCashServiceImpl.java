package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.CapitalCashMapper;
import com.baomidou.springwind.mapper.CapitalMapper;
import com.baomidou.springwind.mapper.CheckStatementMapper;
import com.baomidou.springwind.service.ICapitalCashService;
import com.baomidou.springwind.service.IMessageService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private CapitalMapper capitalMapper;

    @Autowired
    private CheckStatementMapper checkStatementMapper;

    @Autowired
    private IMessageService messageService;

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

    @Override
    public List<CapitalCash> queryListByPage(RequestInfo<CapitalCash> page) {
        page.setAmmount(mapper.getCount(page));
        return mapper.queryForPage(page);
    }

    @Override
    public CapitalCash queryById(String id) {
        CapitalCash capitalCash = new CapitalCash();
        capitalCash.setCashId(id);
        return mapper.queryById(capitalCash);
    }

    @Override
    public boolean pass(String id, Manager manager, float money) {
        Date now = new Date();
        CapitalCash param = new CapitalCash();
        param.setCashId(id);
        CapitalCash capitalCash = mapper.queryByIdForUpdate(param);
        capitalCash.setStatus(1);
        capitalCash.setRequestTime(now);
        capitalCash.setResponsePerson(manager.getUserId());
        mapper.updateById(capitalCash);

        //转账
        Capital capital = capitalMapper.selectForUpdate(capitalCash.getUserId());
        if(capital.getCapital()<money){
            unPass(id,manager);
            return false;
        }
        capital.setCapital(capital.getCapital()-money);
        capitalMapper.updateById(capital);

        //账单记账
        CheckStatement cs = new CheckStatement();
        cs.setStateId(UUIDUtil.getUUID());
        cs.setAmount(money);
        cs.setType(1);
        cs.setCreateDate(new Date());
        cs.setMemo("提现");
        cs.setUserId(capitalCash.getUserId());
        checkStatementMapper.insert(cs);


        //发送通知
        StringBuffer content = new StringBuffer("您提现的金额");
        content.append(String.valueOf(money)).append("已经到账，请注意查收。感谢您对校园租的支持");
        messageService.pushMessage("您提现的资金已经到账",content.toString(),capitalCash.getUserId());
        return true;
    }

    @Override
    public boolean unPass(String id, Manager manager) {
        Date now = new Date();
        CapitalCash param = new CapitalCash();
        param.setCashId(id);
        CapitalCash capitalCash = mapper.queryByIdForUpdate(param);
        capitalCash.setStatus(2);
        capitalCash.setRequestTime(now);
        capitalCash.setResponsePerson(manager.getUserId());
        mapper.updateById(capitalCash);

        //发送通知
        messageService.pushMessage("您的提现请求被拒绝","您的提现请求被拒绝，请检查余额是否充足",capitalCash.getUserId());
        return true;
    }


}
