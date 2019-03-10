package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.RentNeeds;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.RentNeedsExtend;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
  * 用户发布的租赁需求 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface RentNeedsMapper extends BaseMapper<RentNeeds> {
    List<RentNeeds> queryRentNeedsByPage(RentNeedsExtend rentNeedsExtend);
}