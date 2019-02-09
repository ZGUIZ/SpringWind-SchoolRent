package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.Classify;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 商品类别 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface ClassifyMapper extends BaseMapper<Classify> {
    List<Classify> queryIndex();
}