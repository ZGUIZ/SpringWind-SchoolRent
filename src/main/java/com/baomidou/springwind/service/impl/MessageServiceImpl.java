package com.baomidou.springwind.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.Message;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.MessageMapper;
import com.baomidou.springwind.service.IMessageService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.JPushUnits;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2019-04-01
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private MessageMapper mapper;

    @Override
    public List<Message> pushUnRead(Student student) {
        Message param  = new Message();
        param.setUserId(student.getUserId());
        param.setStatus(0);
        List<Message> messages = mapper.unReadMessage(param);
        //通过极光进行推送
        JPushUnits units = JPushUnits.newInstance();
        for(int i = 0;i<messages.size();i++){
            Message msg = messages.get(i);
            try {
                units.pushForUser(student.getUserId(), msg.getTitle());
                msg.setStatus(1);
                mapper.updateById(msg);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return messages;
    }

    @Override
    public boolean pushMessage(String title, String content, String userId) {
        Message message = new Message();
        message.setMsgId(UUIDUtil.getUUID());
        message.setTitle(title);
        message.setUserId(userId);
        message.setContent(content);
        message.setStatus(0);
        message.setCreateDate(new Date());

        //推送通知
        JPushUnits jPushUnits = JPushUnits.newInstance();
        try {
            jPushUnits.pushForUser(userId,message.getTitle());
            message.setStatus(1);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            message.setStatus(0);
        } catch (APIRequestException e) {
            e.printStackTrace();
            message.setStatus(0);
        }
        mapper.insert(message);
        return true;
    }

    @Transactional
    @Override
    public List<Message> listMessage(Student student) {
        Message param  = new Message();
        param.setUserId(student.getUserId());
        List<Message> messages = mapper.listMessage(param);
        for(Message msg : messages){
            msg.setStatus(2);
            mapper.updateById(msg);
        }
        return messages;
    }
}
