package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.IdelPic;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 闲置信息相关图片 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IdelPicMapper extends BaseMapper<IdelPic> {
    List<IdelPic> queryIdlePic(List<String> ids);
}