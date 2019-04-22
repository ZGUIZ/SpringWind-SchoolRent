package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.CapitalCash;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.RequestInfo;

import java.util.List;

/**
 * <p>
  * 资金提现请求 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface CapitalCashMapper extends BaseMapper<CapitalCash> {
    /**
     * 分页条件查询
     * @param page
     * @return
     */
    List<CapitalCash> queryForPage(RequestInfo<CapitalCash> page);

    /**
     * 获取总条数
     * @param requestInfo
     * @return
     */
    Integer getCount(RequestInfo<CapitalCash> requestInfo);

    CapitalCash queryById(CapitalCash capitalCash);

    CapitalCash queryByIdForUpdate(CapitalCash capitalCash);
}