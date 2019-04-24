package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.entity.RentNeeds;
import com.baomidou.springwind.entity.ResponseInfo;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.IdleInfoMapper;
import com.baomidou.springwind.mapper.RentNeedsMapper;
import com.baomidou.springwind.mapper.ResponseInfoMapper;
import com.baomidou.springwind.mapper.StudentMapper;
import com.baomidou.springwind.service.IMessageService;
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

    @Autowired
    private IdleInfoMapper idleInfoMapper;

    @Autowired
    private RentNeedsMapper rentNeedsMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private IMessageService messageService;

    @Override
    public int addResponseInfo(ResponseInfo responseInfo) {
        responseInfo.setResponseId(UUIDUtil.getUUID());
        responseInfo.setResponseDate(new Date());
        responseInfo.setStatus(1);
        int i = responseInfoMapper.insert(responseInfo);

        IdleInfo idleInfo = idleInfoMapper.selectById(responseInfo.getInfoId());
        if(idleInfo != null){
            Student to = studentMapper.selectById(idleInfo.getInfoId());
            StringBuffer sb = new StringBuffer("@");
            sb.append(responseInfo.getStudent().getUserName()).append("提问了您的商品\"").append(idleInfo.getTitle()).append("\"");
            sb.append("：").append(responseInfo.getResponseInfo());
            messageService.pushMessage("有人对您的商品信息提问了",sb.toString(),to.getUserId());
        } else {
            RentNeeds rentNeeds = rentNeedsMapper.selectById(responseInfo.getInfoId());
            if(rentNeeds!=null) {
                Student to = studentMapper.selectById(rentNeeds.getUserId());
                StringBuffer sb = new StringBuffer("@");
                sb.append(responseInfo.getStudent().getUserName()).append("回复了您的帖子\"").append(rentNeeds.getTitle()).append("\"");
                sb.append("：").append(responseInfo.getResponseInfo());
                messageService.pushMessage("有人回复了您的帖子", sb.toString(), to.getUserId());
            }
        }
        return i;
    }

    @Override
    public List<ResponseInfo> queryResponseInfo(ResponseInfo responseInfo) {
        return responseInfoMapper.listByPage(responseInfo);
    }
}
