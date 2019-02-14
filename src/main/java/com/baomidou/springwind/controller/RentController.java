package com.baomidou.springwind.controller;

import com.baomidou.springwind.Exception.MoneyNotEnoughException;
import com.baomidou.springwind.Exception.PassWordNotSameException;
import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.entity.Rent;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IRentService;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 租赁信息关联表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private IRentService rentService;

	@ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result addRent(HttpServletRequest request, @RequestBody Rent rent){
	    Result result = new Result();
	    HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");

        rent.setUserId(student.getUserId());

        try {
            boolean r = rentService.addRent(rent,student);
            result.setResult(r);
        } catch (MoneyNotEnoughException e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        } catch (PassWordNotSameException e){
            e.printStackTrace();
            result.setResult(false);
            result.setCode(401);
            result.setMsg("密码错误！");
        }

	    return result;
    }

    @ResponseBody
    @RequestMapping(value = "/cancle",method = RequestMethod.POST)
    public Result cancle(HttpServletRequest request,@RequestBody Rent rent){
        Result result = new Result();
        HttpSession session = request.getSession();
        if(session == null){
            result.setResult(false);
            result.setMsg("用户未登录");
            result.setCode(401);
            return result;
        }

        Student student = (Student) session.getAttribute("student");
        if(session == null){
            result.setResult(false);
            result.setCode(401);
            result.setMsg("用户未登录");
            return result;
        }

        if(student.getUserId().equals(rent)){
            rent.setStatus(6);
        }

        rentService.updateById(rent);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    private Result list(@RequestBody IdleInfo idleInfo){
	    Result result = new Result();
	    try {
            List<Rent> rentList = rentService.queryList(idleInfo);
            result.setResult(true);
            result.setData(rentList);
        } catch (Exception e){
	        e.printStackTrace();
	        result.setResult(false);
        }
	    return result;
    }
}
