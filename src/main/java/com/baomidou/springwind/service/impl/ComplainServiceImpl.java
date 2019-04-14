package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Complain;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.ComplainMapper;
import com.baomidou.springwind.service.IComplainService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

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

    @Override
    public boolean addComplain(Student student, Complain complain) throws UnsupportedEncodingException {
        complain.setComplainId(UUIDUtil.getUUID());
        complain.setUserId(student.getUserId());
        complain.setComplainTime(new Date());
        complain.setMsg(URLDecoder.decode(complain.getMsg(),"utf-8"));
        int count = mapper.insert(complain);
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }
}
