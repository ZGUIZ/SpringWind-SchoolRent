package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.IAuthPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @RequestMapping(value = "/toList")
    public String toList(){
        return "authPic/authPicList";
    }

    @ResponseBody
    @RequestMapping(value = "/listByPage")
    public DatatablesView<AuthPicture> queryList(RequestInfo<AuthPicture> requestInfo,Integer status){
        AuthPicture authPicture = new AuthPicture();
        authPicture.setStatus(status);
        requestInfo.setParam(authPicture);

        DatatablesView<AuthPicture> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<AuthPicture> idleInfos=authPictureService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(idleInfos);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @ResponseBody
    @RequestMapping(value = "/id/{id}")
    public AuthPicture selectById(@PathVariable("id") String id){
        AuthPicture authPicture = new AuthPicture();
        authPicture.setPicId(id);
        return authPictureService.selectById(authPicture);
    }

    @ResponseBody
    @RequestMapping(value = "/passVal/{id}")
    public Result passValidate(@PathVariable("id") String id){
        Result result = new Result();
        boolean res = authPictureService.passValidate(id);
        result.setResult(res);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/unPassVal/{id}")
    public Result unPassValidate(@PathVariable("id") String id){
        Result result = new Result();
        boolean res = authPictureService.unPassValidate(id);
        result.setResult(res);
        return result;
    }
}
