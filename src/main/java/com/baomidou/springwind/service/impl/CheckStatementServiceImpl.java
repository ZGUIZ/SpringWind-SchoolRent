package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.CheckStatement;
import com.baomidou.springwind.entity.CheckStatementExtend;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.CheckStatementMapper;
import com.baomidou.springwind.service.ICheckStatementService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 账单 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2019-03-12
 */
@Service
public class CheckStatementServiceImpl extends BaseServiceImpl<CheckStatementMapper, CheckStatement> implements ICheckStatementService {

    @Autowired
    private CheckStatementMapper mapper;

    @Override
    public List<CheckStatement> list(Student student,CheckStatementExtend extend) {
        extend.setUserId(student.getUserId());
        return mapper.listByPage(extend);
    }
}
