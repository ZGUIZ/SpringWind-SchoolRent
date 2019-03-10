package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.RentNeedsMapper;
import com.baomidou.springwind.service.IRentNeedsService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 用户发布的租赁需求 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/rentNeeds")
public class RentNeedsController {

    @Autowired
    private RentNeedsMapper rentNeedsMapper;

    @Autowired
    private IRentNeedsService rentNeedsService;

    @RequestMapping("queryList")
    @ResponseBody
    public String queryList(String json){
        RequestInfo<RentNeeds> requestInfo = RequestInfo.getObjectFromJson(json);
        EntityWrapper<RentNeeds> wrapper=new EntityWrapper<>();
        wrapper.setEntity(requestInfo.getParam());
        List<RentNeeds> rentNeeds=rentNeedsMapper.selectPage(new RowBounds(requestInfo.getStart(), requestInfo.getEnd()),wrapper);
        return JSONArray.toJSONString(rentNeeds);
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(HttpServletRequest request,@RequestBody RentNeeds rentNeeds){
        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        try {
            boolean r = rentNeedsService.addRentNeeds(student,rentNeeds);
            result.setResult(r);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result.setResult(false);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result list(HttpServletRequest request, @RequestBody RentNeedsExtend rentNeedsExtend){
        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        try{
            List<RentNeeds> rentNeeds = rentNeedsService.queryRentNeeds(student,rentNeedsExtend);

            result.setResult(true);
            result.setData(rentNeeds);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
