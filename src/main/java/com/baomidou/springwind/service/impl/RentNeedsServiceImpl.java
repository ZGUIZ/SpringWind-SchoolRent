package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.entity.RentNeeds;
import com.baomidou.springwind.entity.RentNeedsExtend;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.RentNeedsMapper;
import com.baomidou.springwind.service.IRentNeedsService;
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
 * 用户发布的租赁需求 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class RentNeedsServiceImpl extends BaseServiceImpl<RentNeedsMapper, RentNeeds> implements IRentNeedsService {

    @Autowired
    private RentNeedsMapper rentNeedsMapper;

    @Override
    public boolean addRentNeeds(Student student, RentNeeds rentNeeds) throws UnsupportedEncodingException {
        //初始化
        rentNeeds.setInfoId(UUIDUtil.getUUID());
        rentNeeds.setUserId(student.getUserId());
        rentNeeds.setCreateDate(new Date());
        rentNeeds.setStatus(0);

        //标题和文本解码
        rentNeeds.setTitle(URLDecoder.decode(rentNeeds.getTitle(),"utf-8"));
        rentNeeds.setIdelInfo(URLDecoder.decode(rentNeeds.getIdelInfo(),"utf-8"));

        rentNeedsMapper.insert(rentNeeds);
        return true;
    }

    @Override
    public List<RentNeeds> queryRentNeeds(Student student, RentNeedsExtend rentNeedsExtend) {
        rentNeedsExtend.setSchoolId(student.getSchoolId());
        return rentNeedsMapper.queryRentNeedsByPage(rentNeedsExtend);
    }

    @Override
    public List<RentNeeds> queryMineNeeds(Student student, RentNeedsExtend rentNeedsExtend) {
        rentNeedsExtend.setUserId(student.getUserId());
        List<RentNeeds> needsList = rentNeedsMapper.queryMineNeedsByPage(rentNeedsExtend);
        for (int i = 0;i<needsList.size();i++){
            RentNeeds rentNeeds = needsList.get(i);
            rentNeeds.setStudent(student);
        }
        return needsList;
    }

    @Override
    public boolean delRentNeeds(Student student, String id) throws IllegalAuthroiyException {
        RentNeeds rentNeeds = rentNeedsMapper.selectById(id);
        if(!rentNeeds.getUserId().equals(student.getUserId())){
            throw new IllegalAuthroiyException("您没有执行该操作的权限！");
        }
        rentNeeds.setStatus(100);
        rentNeedsMapper.updateById(rentNeeds);
        return true;
    }
}
