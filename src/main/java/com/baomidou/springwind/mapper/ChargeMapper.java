package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.Charge;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
  * 充值请求 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
public interface ChargeMapper extends BaseMapper<Charge> {
    /**
     * 分页条件查询
     * @param page
     * @return
     */
    List<Charge> queryForPage(RequestInfo<Charge> page);

    /**
     * 获取总条数
     * @param requestInfo
     * @return
     */
    Integer getCount(RequestInfo<Charge> requestInfo);

    Charge queryById(Charge charge);

    Charge queryByIdForUpdate(Charge charge);
}