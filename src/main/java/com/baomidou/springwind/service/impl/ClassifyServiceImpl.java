package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.Classify;
import com.baomidou.springwind.mapper.ClassifyMapper;
import com.baomidou.springwind.service.IClassifyService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品类别 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class ClassifyServiceImpl extends BaseServiceImpl<ClassifyMapper, Classify> implements IClassifyService {

    public static final String allId = "0000000";
    public static final String allIcon = "https://schoolrent-1253946493.cos.ap-guangzhou.myqcloud.com/index/all_icon.png";

    @Autowired
    private ClassifyMapper mapper;

    @Override
    public List<Classify> queryFromIndex() {
        List<Classify> classifies = mapper.queryIndex();
        Classify classify = new Classify();
        classify.setClassifyId(allId);
        classify.setClassifyName("全部");
        classify.setImageUrl(allIcon);
        classifies.add(classify);
        return classifies;
    }

    @Override
    public List<Classify> queryAll() {
        return mapper.selectList(new EntityWrapper<>());
    }
}
