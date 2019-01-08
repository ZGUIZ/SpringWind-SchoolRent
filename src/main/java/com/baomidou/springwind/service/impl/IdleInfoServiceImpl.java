package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.IdleInfoMapper;
import com.baomidou.springwind.service.IIdleInfoService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 闲置信息 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class IdleInfoServiceImpl extends BaseServiceImpl<IdleInfoMapper, IdleInfo> implements IIdleInfoService {

    @Autowired
    private IdleInfoMapper idleInfoMapper;

    @Deprecated
    @Transactional
    @Override
    public boolean addRentInfo(Student student, IdleInfo info) throws Exception {
        Date now=new Date();
        info.setCreateDate(now);
        info.setSchoolId(student.getSchoolId());
        Integer row=idleInfoMapper.insert(info);
        /*if(row > 0){
            IdleRel rel=new IdleRel();
            rel.setRelId(UUIDUtil.getUUID());
            rel.setInfoId(info.getInfoId());
            rel.setUserId(student.getUserId());
            rel.setInfoType("1");
            int r=idleRelMapper.insert(rel);
            if(r > 0){
                return true;
            } else {
                throw new Exception("依赖创建异常");
            }
        }*/
        return false;
    }
}
