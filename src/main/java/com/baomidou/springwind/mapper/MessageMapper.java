package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.Message;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 消息 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2019-04-01
 */
public interface MessageMapper extends BaseMapper<Message> {
    List<Message> unReadMessage(Message message);
}