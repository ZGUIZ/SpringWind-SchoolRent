package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.springwind.entity.AuthPicture;
import com.baomidou.springwind.mapper.AuthPictureMapper;
import com.baomidou.springwind.service.IAuthPictureService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用于审核身份的图片 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class AuthPictureServiceImpl extends BaseServiceImpl<AuthPictureMapper, AuthPicture> implements IAuthPictureService {

    @Autowired
    private AuthPictureMapper mapper;

    @Override
    public boolean addOrRefresh(AuthPicture authPicture) {
        AuthPicture param = new AuthPicture();
        param.setUserId(authPicture.getUserId());
        param.setType(authPicture.getType());
        Wrapper<AuthPicture> wrapper = new EntityWrapper<>(param);
        List<AuthPicture> authPictures = mapper.selectList(wrapper);
        if(authPictures.size()>0){
            AuthPicture old = authPictures.get(0);
            old.setStatus(3);
            old.setPicUrl(authPicture.getPicUrl());
            old.setCreateDate(new Date());
            int count = mapper.updateById(old);
            if(count> 0){
                return true;
            } else {
                return false;
            }
        } else {
            authPicture.setStatus(3);
            authPicture.setCreateDate(new Date());
            authPicture.setPicId(UUIDUtil.getUUID());
            int count = mapper.insert(authPicture);
            if(count> 0){
                return true;
            } else {
                return false;
            }
        }
    }
}
