package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.School;
import com.baomidou.springwind.entity.Select2Bean;
import com.baomidou.springwind.mapper.SchoolMapper;
import com.baomidou.springwind.service.ISchoolService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学校 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class SchoolServiceImpl extends BaseServiceImpl<SchoolMapper, School> implements ISchoolService {

    @Autowired
    private SchoolMapper mapper;

    @Override
    public List<Select2Bean> getCity(String province) {
        School school=new School();
        if(province != null && !"".equals(province.trim())){
            school.setProvince(province);
        }
        return mapper.getCity(school);
    }

    @Override
    public List<Select2Bean> getProvince() {
        return mapper.getProvince();
    }

    @Override
    public List<School> getSchool(String city) {
        if(city!=null && !"".equals(city)){
            School school=new School();
            school.setCity(city);
            return mapper.getSchool(school);
        }
        return null;
    }

    @Override
    public List<Select2Bean> getSchoolForSelect(String city) {
        if(city!=null&&!"".equals(city)){
            School school=new School();
            school.setCity(city);
            return mapper.getSchoolForSelect(school);
        }
        return null;
    }

    @Override
    public List<School> queryListByPage(RequestInfo<School> page) {
        page.setAmmount(mapper.getCount(page));
        return mapper.queryForPage(page);
    }

    @Override
    public Integer getCount(RequestInfo<School> requestInfo) {
        return mapper.getCount(requestInfo);
    }
}
