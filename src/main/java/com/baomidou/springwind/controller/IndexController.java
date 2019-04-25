package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.Manager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 首页
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-13
 */
@Controller
public class IndexController extends BaseController {

	/**
	 * 首页
	 */
	@Permission(action = Action.Skip)
	@RequestMapping("/index")
	public String index(Model model) {
		return "/index";
	}

	@RequestMapping("/toIndex")
	public String toIndex(){
		return "/index/index";
	}

	/**
	 * 主页
	 */
	@Login(action = Action.Skip)
	@Permission(action = Action.Skip)
	@RequestMapping("/home")
	public String home() {
		return "/home";
	}

	@Login(action = Action.Skip)
	@Permission(action = Action.Skip)
	@RequestMapping("/")
	public String index(HttpServletRequest request){
		HttpSession session = request.getSession();
		Manager manager = (Manager) session.getAttribute("manager");
		if (manager == null) {
			return "/login";
		} else {
			return "/index";
		}
	}
}
