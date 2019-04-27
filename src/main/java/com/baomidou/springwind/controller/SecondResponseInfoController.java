package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.ResponseInfo;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.SecondResponseInfo;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IMessageService;
import com.baomidou.springwind.service.IResponseInfoService;
import com.baomidou.springwind.service.ISecondResponseInfoService;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Date;

/**
 * <p>
 * 二级回复信息 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2019-02-12
 */
@Controller
@RequestMapping("/secondResponseInfo")
public class SecondResponseInfoController {

    @Autowired
    private ISecondResponseInfoService secondResponseInfoService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IResponseInfoService responseInfoService;

    /**
     * 添加
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result addResponseInfo(HttpServletRequest request, @RequestBody SecondResponseInfo secondResponseInfo){
        Result result = new Result();
        HttpSession httpSession = request.getSession();
        //从Session中获取用户信息
        Student student = (Student) httpSession.getAttribute("student");
        try{
            secondResponseInfo.setResponseInfo(URLDecoder.decode(secondResponseInfo.getResponseInfo(),"utf-8"));
            secondResponseInfo.setResponseId(UUIDUtil.getUUID());
            secondResponseInfo.setUserId(student.getUserId());
            secondResponseInfo.setStatus(1);
            secondResponseInfo.setResponseDate(new Date());

            boolean count = secondResponseInfoService.insert(secondResponseInfo);
            if(count){
                result.setResult(true);
                result.setMsg("回复成功！");
            } else {
                result.setResult(false);
                result.setMsg("回复失败");
            }

            //推送信息
            ResponseInfo responseInfo = responseInfoService.selectById(secondResponseInfo.getParentId());
            if(responseInfo!=null){
                StringBuffer sb = new StringBuffer("@");
                sb.append(student.getUserName()).append("回复了您的消息\"").append(responseInfo.getResponseInfo()).append("\"");
                sb.append("内容为：").append(secondResponseInfo.getResponseInfo());
                messageService.pushMessage("有人回复了您",sb.toString(),responseInfo.getUserId());
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("回复失败");
        }
        return result;
    }
}
