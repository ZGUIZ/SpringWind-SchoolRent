package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.ICapitalCashService;
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

    @ResponseBody
    @RequestMapping("/list")
    public DatatablesView<CapitalCash> list(RequestInfo requestInfo){
        DatatablesView<CapitalCash> datatablesView = new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<CapitalCash> charges = capitalCashService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(charges);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @RequestMapping("/toList")
    public String toList(){
        return "/capitalCash/capitalCashList";
    }

    @RequestMapping("/toForm")
    public String toForm(){
        return "/capitalCash/capitalCashForm";
    }

    @ResponseBody
    @RequestMapping("/id/{id}")
    public Result queryById(@PathVariable("id") String id){
        Result result = new Result();
        CapitalCash capitalCash = capitalCashService.queryById(id);
        result.setResult(true);
        result.setData(capitalCash);
        return result;
    }

    @ResponseBody
    @RequestMapping("/pass")
    public Result pass(HttpServletRequest request,String id,float money){
        Result result = new Result();
        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");
        boolean res = capitalCashService.pass(id,manager,money);
        if(res == false){
            result.setMsg("余额不足");
        }
        result.setResult(res);
        return result;
    }

    @ResponseBody
    @RequestMapping("/unPass")
    public Result unPass(HttpServletRequest request,String id){
        Result result = new Result();
        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");
        boolean res = capitalCashService.unPass(id,manager);
        result.setResult(res);
        return result;
    }
}
