package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.Message;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.MessageMapper;
import com.baomidou.springwind.service.IMessageService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.JPushUnits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
