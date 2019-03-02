package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.entity.Rent;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.RentExtend;

import java.util.List;

/**
 * <p>
  * 租赁信息关联表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface RentMapper extends BaseMapper<Rent> {
    List<Rent> queryList(IdleInfo idleInfo);

    Integer getCountOfRequest(Rent rent);
    List<Rent> getCanRent(Rent rent);

    List<Rent> selectForUpdate(Rent rent);

    List<Rent> selectForPage(RentExtend rentExtend);
}