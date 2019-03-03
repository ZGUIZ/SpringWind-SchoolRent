package com.baomidou.springwind.controller;

import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.Exception.MoneyNotEnoughException;
import com.baomidou.springwind.Exception.PassWordNotSameException;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.IRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            result.setCode(403);
            result.setMsg(e.getMessage());
        } catch (PassWordNotSameException e){
            e.printStackTrace();
            result.setResult(false);
            result.setCode(402);
            result.setMsg("密码错误！");
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setCode(403);
            result.setMsg(e.getMessage());
        }

	    return result;
    }

    @ResponseBody
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    public Result cancle(HttpServletRequest request,@RequestBody Rent rent){
        Result result = new Result();
        HttpSession session = request.getSession();

        Student student = (Student) session.getAttribute("student");

        try {
            rentService.cancelRent(student,rent);
            result.setResult(true);
        } catch (IllegalAuthroiyException e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMsg());
        } catch (IllegalStateException e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/list/id/{id}")
    public Result list(@PathVariable("id") String id){
	    Result result = new Result();
	    try {
	        IdleInfo idleInfo = new IdleInfo();
	        idleInfo.setInfoId(id);
            List<Rent> rentList = rentService.queryList(idleInfo);
            result.setResult(true);
            result.setData(rentList);
        } catch (Exception e){
	        e.printStackTrace();
	        result.setResult(false);
        }
	    return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getRelation/id/{id}")
    public Result getRelation(HttpServletRequest request,@PathVariable("id") String id){
	    HttpSession session = request.getSession();
	    Result result = new Result();
	    try{
	        Student student = (Student) session.getAttribute("student");
	        Rent rent = new Rent();
            result.setResult(true);
	        rent.setUserId(student.getUserId());
	        rent.setIdelId(id);
            Rent r = rentService.getCanRent(rent);
            result.setData(r);
        } catch (Exception e){
	        e.printStackTrace();
	        result.setResult(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/agree",method = RequestMethod.POST)
    public Result agree(HttpServletRequest request,@RequestBody Rent rent){
	    Result r = new Result();
	    HttpSession httpSession = request.getSession();
	    Student student = (Student) httpSession.getAttribute("student");
        try {
            boolean result = rentService.agreeRent(student,rent);
            r.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            r.setResult(false);
            r.setMsg(e.getMessage());
        }
        return r;
    }

    @ResponseBody
    @RequestMapping(value = "/queryMineRent")
    public Result queryMineRent(HttpServletRequest request, @RequestBody RentExtend rentExtend){
	    Result r = new Result();
	    HttpSession session = request.getSession();
	    Student student = (Student) session.getAttribute("student");
	    rentExtend.setUserId(student.getUserId());
	    List<Rent> rentList = rentService.selectForPage(rentExtend);
	    r.setResult(true);
	    r.setData(rentList);
	    return r;
    }

    @ResponseBody
    @RequestMapping(value = "/startRent")
    public Result startRent(HttpServletRequest request,@RequestBody Rent rent){
	    Result result = new Result();
	    try{
	        HttpSession session = request.getSession();
	        Student student = (Student) session.getAttribute("student");
	        boolean res = rentService.startRent(student,rent);
	        result.setResult(res);
        } catch (IllegalAuthroiyException e){
	        result.setResult(false);
	        result.setMsg(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "updateRent",method = RequestMethod.POST)
    public Result updateRent(@RequestBody Rent rent){
	    Result result = new Result();
	    boolean r = rentService.updateById(rent);
	    result.setResult(r);
	    return result;
    }
}
