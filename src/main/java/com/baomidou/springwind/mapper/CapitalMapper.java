package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.Capital;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.Student;

/**
 * <p>
  * 用户资金信息 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface CapitalMapper extends BaseMapper<Capital> {

    /**
     * 更新前查询
     * @param
     * @param
     * @param
     * @return
     */
    Capital selectForUpdate(String userId);

    /**
     * 更新数据
     * @param capital
     * @return
     */
    int update(Capital capital);

}