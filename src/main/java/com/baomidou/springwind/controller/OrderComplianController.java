package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.OrderComplian;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IOrderComplianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 订单投诉 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
@Controller
@RequestMapping("/orderComplain")
public class OrderComplianController {

    @Autowired
    private IOrderComplianService complianService;

    @ResponseBody
    @RequestMapping("/add")
    public Result add(HttpServletRequest request, @RequestBody OrderComplian complian){
        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        try {
            boolean res = complianService.addComplain(complian,student);
            result.setResult(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
