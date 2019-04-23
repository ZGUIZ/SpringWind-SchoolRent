package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.ResponseInfo;
import com.baomidou.springwind.mapper.ResponseInfoMapper;
import com.baomidou.springwind.service.IResponseInfoService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 租赁信息回复 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class ResponseInfoServiceImpl extends BaseServiceImpl<ResponseInfoMapper, ResponseInfo> implements IResponseInfoService {

    @Autowired
    private ResponseInfoMapper responseInfoMapper;

    @Override
    public int addResponseInfo(ResponseInfo responseInfo) {
        responseInfo.setResponseId(UUIDUtil.getUUID());
        responseInfo.setResponseDate(new Date());
        responseInfo.setStatus(1);
        int i = responseInfoMapper.insert(responseInfo);
        return i;
    }

    @Override
    public List<ResponseInfo> queryResponseInfo(ResponseInfo responseInfo) {
        return responseInfoMapper.listByPage(responseInfo);
    }
}
