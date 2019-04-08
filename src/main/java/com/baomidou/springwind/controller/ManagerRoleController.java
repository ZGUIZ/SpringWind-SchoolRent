package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.Select2Bean;
import com.baomidou.springwind.service.IManagerRoleService;
import com.baomidou.springwind.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 管理员角色表 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/managerRole")
public class ManagerRoleController {

    @Autowired
    private IManagerRoleService managerRoleService;

    @ResponseBody
    @RequestMapping(value = "/list")
    public List<Select2Bean> list(){
        return managerRoleService.queryForSelect();
    }
}
