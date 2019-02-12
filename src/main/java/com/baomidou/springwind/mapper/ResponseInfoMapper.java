package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ResponseInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.ResponseInfoExtend;

import java.util.List;

/**
 * <p>
  * 租赁信息回复 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface ResponseInfoMapper extends BaseMapper<ResponseInfo> {
    List<ResponseInfo> listByPage(ResponseInfo responseInfo);
}