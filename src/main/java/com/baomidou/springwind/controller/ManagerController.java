package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.IManagerService;
import com.baomidou.springwind.utils.RSAUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Action;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private IManagerService managerService;

    /**
     * 管理员注册
     */
    @ResponseBody
	@RequestMapping("register")
    public String register(Manager manager, String confirmPassword){
        Result result=new Result();
	    if(manager.getAccount() == null || "".equals(manager.getAccount())){
	        result.setResult(false);
	        result.setMsg("账号不能为空！");
        } else if((manager.getPassword() == null || "".equals(manager.getPassword()))&(confirmPassword ==null|| "".equals(confirmPassword))){
            result.setResult(false);
            result.setMsg("密码不能为空！");
        } else if(!confirmPassword.equals(manager.getPassword())){
            result.setResult(false);
            result.setMsg("两次输入的密码不一致！");
        } else {
	        Boolean res=managerService.insert(manager);
	        if(res){

                result.setResult(true);
                result.setMsg("注册成功！");
            } else{
                result.setResult(false);
                result.setMsg("注册失败，可能该账号已经被注册！");
            }
        }
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @Permission(action = com.baomidou.kisso.annotation.Action.Skip)
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(HttpServletRequest request,@RequestBody Manager param){
        Result result = new Result();

        Manager manager=managerService.login(param);
        if(manager == null){
            result.setResult(false);
            result.setMsg("账号或密码错误！");
        }  else {
            result.setResult(true);
            result.setMsg("登录成功!");
            HttpSession session = request.getSession();
            session.setAttribute("manager",manager);
        }
        return result;
    }

    @RequestMapping("/toLogin")
    @Permission(action = com.baomidou.kisso.annotation.Action.Skip)
    public String toLogin(){
        return "/login";
    }

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    @RequestMapping("/toList")
    public String toList(){
        return "/manager/managerList";
    }

    @ResponseBody
    @RequestMapping(value = "/queryListByPage")
    public DatatablesView<Manager> queryList(RequestInfo requestInfo){
        DatatablesView<Manager> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<Manager> students=managerService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(students);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody Manager manager){
        Result result = new Result();
        try {
            boolean res = false;
            if(manager.getBeanStatus().equals("add")){
                res = managerService.add(manager);
            } else {
                managerService.updateById(manager);
                res = true;
            }

            result.setResult(res);
        } catch (MySQLIntegrityConstraintViolationException | DuplicateKeyException e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("添加失败，该邮箱已经被注册");
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/toForm")
    public String toForm(){
        return "/manager/managerForm";
    }

    @ResponseBody
    @RequestMapping(value = "/toAdd")
    public Manager toAdd(){
        Manager manager = new Manager();
        manager.setBeanStatus("add");
        return manager;
    }

    @ResponseBody
    @RequestMapping(value = "/validate")
    public Result validateMail(@RequestBody Manager manager){
        Result result = new Result();
        try {
            boolean res = managerService.getValidate(manager.getMail());
            result.setResult(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("验证码发送失败，请检查您的邮箱输入是否正确！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/id/{id}")
    public Manager queryById(@PathVariable("id") String id){
        Manager manager = managerService.selectById(id);
        manager.setBeanStatus("edit");
        return manager;
    }

    @RequestMapping(value = "/exit")
    public String exit(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping(value = "/inEntry/{id}")
    public Result inEntry(@PathVariable("id") String id){
        Result result = new Result();
        Manager manager = managerService.selectById(id);
        manager.setStatus(2);
        boolean res = managerService.updateById(manager);
        result.setResult(res);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/queryInEntry")
    public DatatablesView<Manager> queryInEntry(RequestInfo requestInfo){
        DatatablesView<Manager> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<Manager> students=managerService.queryInEntry(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(students);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @RequestMapping("/toInEntryList")
    public String toInEntryList(){
        return "/manager/inEntryList";
    }

    @ResponseBody
    @RequestMapping(value = "/entry/{id}")
    public Result entry(@PathVariable("id") String id){
        Result result = new Result();
        Manager manager = managerService.selectById(id);
        manager.setStatus(1);
        boolean res = managerService.updateById(manager);
        result.setResult(res);
        return result;
    }
}
