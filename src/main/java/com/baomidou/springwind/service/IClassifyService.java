package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.Classify;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品类别 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IClassifyService extends IService<Classify> {
	List<Classify> queryFromIndex();
	List<Classify> queryAll();
}
