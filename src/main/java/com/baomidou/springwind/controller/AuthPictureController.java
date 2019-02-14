package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.AuthPicture;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IAuthPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用于审核身份的图片 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/authPicture")
public class AuthPictureController {

    @Autowired
    private IAuthPictureService authPictureService;

    @ResponseBody
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Result add(HttpServletRequest request, @RequestBody AuthPicture authPicture){
        Result result = new Result();
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute("student");
            authPicture.setUserId(student.getUserId());
            boolean res = authPictureService.addOrRefresh(authPicture);
            result.setResult(res);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
        }
        return result;
    }
}
