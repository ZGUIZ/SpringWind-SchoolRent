package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.AuthPicture;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.RequestInfo;

import java.util.List;

/**
 * <p>
 * 用于审核身份的图片 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IAuthPictureService extends IService<AuthPicture> {
	boolean addOrRefresh(AuthPicture authPicture);

	List<AuthPicture> queryListByPage(RequestInfo requestInfo);

	AuthPicture selectById(AuthPicture picture);

	boolean passValidate(String id);
	boolean unPassValidate(String id);
}
