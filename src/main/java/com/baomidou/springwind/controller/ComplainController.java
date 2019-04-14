package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.Complain;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 异常申诉 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/complain")
public class ComplainController {

    @Autowired
    private IComplainService complainService;

    @ResponseBody
    @RequestMapping(value = "/add")
    public Result add(HttpServletRequest request, @RequestBody Complain complain){
        Result result = new Result();
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute("student");
            boolean res = complainService.addComplain(student,complain);
            result.setResult(res);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
