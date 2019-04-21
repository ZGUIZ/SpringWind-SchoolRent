package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.CapitalCash;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.ICapitalCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 资金提现请求 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/capitalCash")
public class CapitalCashController {

    @Autowired
    private ICapitalCashService capitalCashService;

	@ResponseBody
    @RequestMapping("/add")
    public Result add(HttpServletRequest request, @RequestBody CapitalCash capitalCash){
	    Result result = new Result();
	    HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        boolean res = capitalCashService.addCapitalCash(student,capitalCash);
        result.setResult(res);
	    return result;
    }
}
