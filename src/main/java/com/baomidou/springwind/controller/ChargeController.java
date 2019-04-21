package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.IChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @ResponseBody
    @RequestMapping("/list")
    public DatatablesView<Charge> list(RequestInfo requestInfo){
	    DatatablesView<Charge> datatablesView = new DatatablesView<>();
	    datatablesView.setDraw(requestInfo.getDraw());
        List<Charge> charges = chargeService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
	    datatablesView.setData(charges);
	    datatablesView.setRecordsFiltered(requestInfo.getAmmount());
	    return datatablesView;
    }

    @RequestMapping("/toList")
    public String toList(){
	    return "/charge/chargeList";
    }

    @RequestMapping("/toForm")
    public String toForm(){
	    return "/charge/chargeForm";
    }

    @ResponseBody
    @RequestMapping("/id/{id}")
    public Result queryById(@PathVariable("id") String id){
	    Result result = new Result();
	    Charge charge = chargeService.queryById(id);
	    result.setResult(true);
	    result.setData(charge);
	    return result;
    }

    @ResponseBody
    @RequestMapping("/pass")
    public Result pass(HttpServletRequest request,String id,float money){
	    Result result = new Result();
        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");
	    boolean res = chargeService.pass(id,money,manager);
	    result.setResult(res);
	    return result;
    }

    @ResponseBody
    @RequestMapping("/unPass")
    public Result unPass(HttpServletRequest request,String id){
	    Result result = new Result();
	    HttpSession session = request.getSession();
	    Manager manager = (Manager) session.getAttribute("manager");
	    boolean res = chargeService.unPass(id,manager);
	    result.setResult(res);
	    return result;
    }
}
