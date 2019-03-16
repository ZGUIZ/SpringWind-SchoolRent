package com.baomidou.springwind.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.springwind.entity.Capital;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.ICapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户资金信息 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/capital")
public class CapitalController {
    @Autowired
    private ICapitalService capitalService;

    @ResponseBody
    @RequestMapping(value = "/getMyCapital")
    public Result getMyCapital(HttpServletRequest request){
        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        Capital res = capitalService.selectByUser(student);
        result.setResult(true);
        result.setData(res);
        return result;
    }
}
