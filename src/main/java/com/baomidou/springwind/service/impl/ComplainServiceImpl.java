package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.ComplainMapper;
import com.baomidou.springwind.mapper.IdleInfoMapper;
import com.baomidou.springwind.mapper.RentNeedsMapper;
import com.baomidou.springwind.service.IComplainService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 异常申诉 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class ComplainServiceImpl extends BaseServiceImpl<ComplainMapper, Complain> implements IComplainService {

    @Autowired
    private ComplainMapper mapper;

    @Autowired
    private IdleInfoMapper idleInfoMapper;

    @Autowired
    private RentNeedsMapper rentNeedsMapper;

    @Override
    public boolean addComplain(Student student, Complain complain) throws UnsupportedEncodingException {
        complain.setComplainId(UUIDUtil.getUUID());
        complain.setUserId(student.getUserId());
        complain.setComplainTime(new Date());
        complain.setMsg(URLDecoder.decode(complain.getMsg(),"utf-8"));
        complain.setStatus(0);
        int count = mapper.insert(complain);
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Complain> queryListByPage(RequestInfo<Complain> page) {
        page.setAmmount(mapper.getCount(page));
        return mapper.queryForPage(page);
    }

    @Override
    public List<Complain> rentNeedsListByPage(RequestInfo<Complain> page) {
        page.setAmmount(mapper.getCountRentNeeds(page));
        return mapper.rentNeedsForPage(page);
    }

    @Override
    public Complain queryById(String id) {
        Complain complain = mapper.selectById(id);
        if(complain.getComplainType() == 1){
            IdleInfo idleInfo = idleInfoMapper.selectById(complain.getInfoId());
            complain.setIdleInfo(idleInfo);
        } else {
            RentNeeds rentNeeds = rentNeedsMapper.selectById(complain.getInfoId());
            complain.setRentNeeds(rentNeeds);
        }
        return complain;
    }

    @Override
    public boolean deal(Manager manager,String id, int status) {
        Complain complain = mapper.selectById(id);
        complain.setStatus(status);
        complain.setResponsePerson(manager.getUserId());
        complain.setResponseTime(new Date());
        mapper.updateById(complain);
        return true;
    }
}
