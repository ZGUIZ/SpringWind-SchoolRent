package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.entity.Manager;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.service.IManagerService;
import com.baomidou.springwind.utils.RSAUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Action;
import java.security.PrivateKey;

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
}
