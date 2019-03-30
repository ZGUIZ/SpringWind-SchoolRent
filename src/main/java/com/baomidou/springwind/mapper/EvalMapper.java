package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.Eval;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.EvalExtend;

import java.util.List;

/**
 * <p>
  * 评价信息 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
public interface EvalMapper extends BaseMapper<Eval> {
    List<Eval> selectByPage(EvalExtend evalExtend);
}