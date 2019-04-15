package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.OrderComplian;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.RequestInfo;

import java.util.List;

/**
 * <p>
  * 订单投诉 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
public interface OrderComplianMapper extends BaseMapper<OrderComplian> {

    /**
     * 分页条件查询
     * @param page
     * @return
     */
    List<OrderComplian> queryForPage(RequestInfo<OrderComplian> page);

    /**
     * 获取总条数
     * @param requestInfo
     * @return
     */
    Integer getCount(RequestInfo<OrderComplian> requestInfo);

}