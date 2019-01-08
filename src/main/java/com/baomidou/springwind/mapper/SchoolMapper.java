package com.baomidou.springwind.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.School;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.Select2Bean;
import sun.misc.Request;

import java.util.List;

/**
 * <p>
  * 学校 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface SchoolMapper extends BaseMapper<School> {

    /**
     * 获取学校所在位置
     * @return
     */
    List<Select2Bean> getCity(School school);

    /**
     * 获取省份
     * @return
     */
    List<Select2Bean> getProvince();

    /**
     * 获取学校信息
     * @param school
     * @return
     */
    List<School> getSchool(School school);
    List<Select2Bean> getSchoolForSelect(School city);
    int updateProvince(School school);

    /**
     * 分页条件查询
     * @param page
     * @return
     */
    List<School> queryForPage(RequestInfo<School> page);

    /**
     * 获取总条数
     * @param requestInfo
     * @return
     */
    Integer getCount(RequestInfo<School> requestInfo);
}