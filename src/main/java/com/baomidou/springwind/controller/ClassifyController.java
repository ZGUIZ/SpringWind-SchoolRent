package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.Classify;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.ClassifyMapper;
import com.baomidou.springwind.service.IClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 商品类别 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/classify")
public class ClassifyController {

    @Autowired
    private IClassifyService classifyService;


    @RequestMapping("/queryList")
    @ResponseBody
    public String queryList(){
        List<Classify> classifyMapperList=classifyService.queryAll();
        Result result=new Result();
        result.setResult(true);
        result.setData(classifyMapperList);
        return JSONObject.toJSONString(result);
    }

    @RequestMapping("/toList")
    public String toList(){
        return "classify/classifyList";
    }

    @ResponseBody
    @RequestMapping(value="/queryFromIndex")
    public String queryFromIndex(HttpServletRequest request){
        List<Classify> classifies = classifyService.queryFromIndex();
        Result result = new Result();
        result.setResult(true);
        result.setData(classifies);
        return JSONObject.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/getName/id/{id}")
    public Result getName(@PathVariable("id") String id){
        Result result = new Result();

        Classify classify = classifyService.selectById(id);
        if(classify == null){
            result.setResult(false);
            result.setMsg("未找到对应的类型");
        } else {
            result.setResult(true);
            result.setData(classify);
        }

        return result;
    }
}
