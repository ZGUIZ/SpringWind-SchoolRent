package com.baomidou.springwind.common;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.kisso.web.interceptor.SSOPermissionInterceptor;
import com.baomidou.springwind.entity.Manager;
import com.baomidou.springwind.entity.Student;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

public class LoginHandlerIntercepter extends SSOPermissionInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        Manager manager = (Manager) session.getAttribute("manager");
        if(student!=null || manager != null){
            //登陆成功的用户
            return true;
        }else{
            try {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                Permission pm = (Permission) method.getAnnotation(Permission.class);
                if (pm != null) {
                    if (pm.action() == Action.Skip) {
                        return true;
                    }
                }
            }catch (ClassCastException e){
                super.preHandle(request,response,handler);
            }
            return false;
        }
    }
}
