package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.springwind.entity.Manager;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
