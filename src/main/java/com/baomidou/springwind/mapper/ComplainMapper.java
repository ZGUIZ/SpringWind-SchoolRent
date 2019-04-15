package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.Complain;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
  * 异常申诉 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface ComplainMapper extends BaseMapper<Complain> {

    /**
     * 分页条件查询
     * @param page
     * @return
     */
    List<Complain> queryForPage(RequestInfo<Complain> page);

    /**
     * 获取总条数
     * @param requestInfo
     * @return
     */
    Integer getCount(RequestInfo<Complain> requestInfo);

    /**
     * 分页条件查询
     * @param page
     * @return
     */
    List<Complain> rentNeedsForPage(RequestInfo<Complain> page);

    /**
     * 获取总条数
     * @param requestInfo
     * @return
     */
    Integer getCountRentNeeds(RequestInfo<Complain> requestInfo);

}