package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.Message;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 消息 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2019-04-01
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    /**
     * 推送未推送的消息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pushUnRead")
    public Result pushUnRead(HttpServletRequest request){
        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        List<Message> messages = messageService.pushUnRead(student);
        result.setResult(true);
        result.setData(messages);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public Result list(HttpServletRequest request){
        Result result = new Result();

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        List<Message> messages = messageService.listMessage(student);
        result.setResult(true);
        result.setData(messages);

        return result;
    }
}
