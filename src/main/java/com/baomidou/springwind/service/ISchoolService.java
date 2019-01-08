package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.School;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Select2Bean;

import java.util.List;

/**
 * <p>
 * 学校 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface ISchoolService extends IService<School> {
    /**
     * 获取城市列表
     * @return
     */
	List<Select2Bean> getCity(String province);

    /**
     * 获取省份
     * @return
     */
	List<Select2Bean> getProvince();

    /**
     * 根据城市获取学校
     * @param city
     * @return
     */
	List<School> getSchool(String city);
	List<Select2Bean> getSchoolForSelect(String city);

	List<School> queryListByPage(RequestInfo<School> page);

	Integer getCount(RequestInfo<School> requestInfo);
}
