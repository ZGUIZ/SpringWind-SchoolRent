package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.CheckStatement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.CheckStatementExtend;

import java.util.List;

/**
 * <p>
  * 账单 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2019-03-12
 */
public interface CheckStatementMapper extends BaseMapper<CheckStatement> {
    List<CheckStatement> listByPage(CheckStatementExtend extend);
}