package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.ResponseInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 租赁信息回复 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IResponseInfoService extends IService<ResponseInfo> {
	int addResponseInfo(ResponseInfo responseInfo);

	List<ResponseInfo> queryResponseInfo(ResponseInfo responseInfo);
}
