package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.Charge;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 充值请求 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
@Controller
@RequestMapping("/charge")
public class ChargeController {
	@Autowired
    private IChargeService chargeService;

	@ResponseBody
    @RequestMapping("/add")
    public Result add(HttpServletRequest request, @RequestBody Charge charge){
	    Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        boolean res = chargeService.addCharge(student,charge);
        result.setResult(res);
	    return result;
    }
}
