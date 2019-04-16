package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.IOrderComplianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.baomidou.springwind.service.IRentNeedsService.COMMENT;

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

    /**
     * 服务端
     */
    @ResponseBody
    @RequestMapping(value = "/queryListByPage")
    public DatatablesView<OrderComplian> queryList(RequestInfo requestInfo){
        DatatablesView<OrderComplian> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<OrderComplian> complians=complianService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(complians);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @RequestMapping(value = "/toList")
    public String toList(){
        return "/orderComplain/orderComplainList";
    }

    @ResponseBody
    @RequestMapping(value = "/queryById/{id}")
    public Result queryById(@PathVariable("id") String id){
        Result result = new Result();
        OrderComplian complain = complianService.queryById(id);
        result.setResult(true);
        result.setData(complain);
        return result;
    }

    @RequestMapping(value = "/toForm")
    public String toForm(){
        return "/orderComplain/orderComplainForm";
    }

    /**
     * 同意赔偿
     * @param id
     * @param money
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/agree")
    public Result agree(HttpServletRequest request,String id,float money){
        Result result = new Result();
        try {
            HttpSession session = request.getSession();
            Manager manager = (Manager) session.getAttribute("manager");
            boolean res = complianService.agree(manager,id,money);
            result.setResult(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/disagree")
    public Result disagree(HttpServletRequest request,String id){
        Result result = new Result();
        try {
            HttpSession session = request.getSession();
            Manager manager = (Manager) session.getAttribute("manager");
            boolean res = complianService.disagree(manager,id,0);
            result.setResult(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
