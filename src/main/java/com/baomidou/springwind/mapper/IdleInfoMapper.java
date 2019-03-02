package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.IdleInfoExtend;

import java.util.List;

/**
 * <p>
  * 闲置信息 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IdleInfoMapper extends BaseMapper<IdleInfo> {
    List<IdleInfo> selectByPage(IdleInfoExtend idleInfo);

    IdleInfo selectForUpdate(IdleInfo idleInfo);

    IdleInfo findById(IdleInfo idleInfo);
}