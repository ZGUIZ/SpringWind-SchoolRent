package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.entity.IdleInfoExtend;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IIdleInfoService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 闲置信息 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/idleInfo")
public class IdleInfoController {

    @Autowired
    private IIdleInfoService idleInfoService;

    @RequestMapping(value = "/queryList")
    public ModelAndView queryList(){
        ModelAndView view=new ModelAndView("idle/grids");
        List<IdleInfo> idles=idleInfoService.selectList(new EntityWrapper<>());
        for(IdleInfo info:idles){
            System.out.println(info.getIdelInfo());
        }
        view.addObject("idles",idles);
        return view;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result addIdleInfo(HttpServletRequest request,@RequestBody IdleInfo idleInfo){
        Result result = new Result();
        Student student = (Student) request.getSession().getAttribute("student");
        if(student == null){
            result.setResult(false);
            result.setMsg("用户未登录");
            return result;
        }
        try {
            idleInfo.setSchoolId(student.getSchoolId());
            idleInfo.setUserId(student.getUserId());
            idleInfo.setTitle(URLDecoder.decode(idleInfo.getTitle(),"utf-8"));
            idleInfo.setIdelInfo(URLDecoder.decode(idleInfo.getIdelInfo(),"utf-8"));
            if(idleInfo.getAddress()!=null){
                idleInfo.setAddress(URLDecoder.decode(idleInfo.getAddress(),"utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int count = idleInfoService.addIdleInfo(idleInfo);
        if(count > 0){
            result.setResult(true);
            result.setMsg("发布成功！");
            result.setData(idleInfo);
        } else {
            result.setResult(false);
            result.setMsg("发布异常");
        }
        return result;
    }

    @RequestMapping(value = "/toList",method = RequestMethod.POST)
    @ResponseBody
    public Result toList(HttpServletRequest request,@RequestBody IdleInfoExtend idleInfo){
        Result result = new Result();
        Student student = (Student) request.getSession().getAttribute("student");
        if(student == null){
            result.setResult(false);
            result.setMsg("用户未登录");
            return result;
        }

        String search = idleInfo.getSearch();
        try {
            if (search != null) {
                idleInfo.setSearch(URLDecoder.decode(search, "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        idleInfo.setStatus(0); //查询所有未租赁的闲置
        idleInfo.setSchoolId(student.getSchoolId());
        List<IdleInfo> idleInfoList = idleInfoService.selectByPage(idleInfo);
        result.setResult(true);
        result.setData(idleInfoList);
        return result;
    }

    /**
     * 获取当前用户发布的租赁信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMinePush")
    public Result getMinePush(HttpServletRequest request){
        Result result = new Result();

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        IdleInfo idleInfo = new IdleInfo();
        idleInfo.setUserId(student.getUserId());
        Wrapper<IdleInfo> wrapper = new EntityWrapper<>(idleInfo);
        List<IdleInfo> idleInfoList = idleInfoService.selectList(wrapper);
        result.setResult(true);
        result.setData(idleInfoList);

        return result;
    }
}
