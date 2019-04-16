package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.IComplainService;
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

    @ResponseBody
    @RequestMapping(value = "/listIdle")
    public DatatablesView list(RequestInfo requestInfo){
        DatatablesView<Complain> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());

        Complain param = new Complain();
        param.setComplainType(1);
        requestInfo.setParam(param);

        List<Complain> complains=complainService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(complains);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @ResponseBody
    @RequestMapping("/id/{id}")
    public Result queryById(@PathVariable("id") String id){
        Result result = new Result();
        Complain complain = complainService.queryById(id);
        result.setResult(true);
        result.setData(complain);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deal")
    public Result deal(HttpServletRequest request,String id,int type){
        Result result = new Result();
        try {
            HttpSession session = request.getSession();
            Manager manager = (Manager) session.getAttribute("manager");
            boolean res = complainService.deal(manager,id,type);
            result.setResult(res);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return  result;
    }

    @ResponseBody
    @RequestMapping(value = "/listRentNeeds")
    public DatatablesView listRentNeeds(RequestInfo requestInfo){
        DatatablesView<Complain> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());

        Complain param = new Complain();
        param.setComplainType(2);
        requestInfo.setParam(param);

        List<Complain> complains=complainService.rentNeedsListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(complains);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @RequestMapping("/toList")
    public String toList(){
        return "/complain/complainList";
    }

    @RequestMapping("/toRentNeedsList")
    public String toRentNeedsList(){
        return "/complain/rentNeedsList";
    }

    @RequestMapping("/toForm")
    public String toForm(){
        return "/complain/complainForm";
    }
}
