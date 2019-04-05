package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.AuthPicture;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.RequestInfo;

import java.util.List;

/**
 * <p>
  * 用于审核身份的图片 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface AuthPictureMapper extends BaseMapper<AuthPicture> {
    List<AuthPicture> getByPage(RequestInfo requestInfo);
    AuthPicture selectById(AuthPicture picture);
    int getCount(RequestInfo picture);
}