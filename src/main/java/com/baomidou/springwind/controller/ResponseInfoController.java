package com.baomidou.springwind.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.ResponseInfo;
import com.baomidou.springwind.entity.ResponseInfoExtend;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IResponseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 * 租赁信息回复 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/responseInfo")
public class ResponseInfoController {

    @Autowired
    private IResponseInfoService responseInfoService;

    /**
     * 添加
     * @param request
     * @param responseInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result addResponseInfo(HttpServletRequest request, @RequestBody ResponseInfo responseInfo){
        Result result = new Result();
        HttpSession httpSession = request.getSession();
        if(httpSession == null){
            result.setResult(false);
            result.setMsg("用户未登录!");
            return result;
        }
        //从Session中获取用户信息
        Student student = (Student) httpSession.getAttribute("student");
        if(student == null){
            result.setResult(false);
            result.setMsg("用户未登录!");
            return result;
        }
        try{
            responseInfo.setResponseInfo(URLDecoder.decode(responseInfo.getResponseInfo(),"utf-8"));
            responseInfo.setUserId(student.getUserId());
            int count = responseInfoService.addResponseInfo(responseInfo);
            if(count>0){
                result.setResult(true);
                result.setMsg("回复成功！");
            } else {
                result.setResult(false);
                result.setMsg("回复失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("回复失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/toList",method = RequestMethod.POST)
    public Result toList(@RequestBody ResponseInfo responseInfo){
        Result result = new Result();
        try {
            responseInfo.setStatus(1);
            List<ResponseInfo> list = responseInfoService.queryResponseInfo(responseInfo);
            result.setResult(true);
            result.setData(list);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
        }
        return result;
    }
}
