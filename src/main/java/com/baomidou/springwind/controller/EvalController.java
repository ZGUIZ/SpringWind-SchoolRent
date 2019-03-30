package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.Eval;
import com.baomidou.springwind.entity.EvalExtend;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.IEvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 * 评价信息 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
@Controller
@RequestMapping("/eval")
public class EvalController {

    @Autowired
    private IEvalService evalService;

	@ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(HttpServletRequest request, @RequestBody Eval eval){
        Result result = new Result();
        try {
            eval.setContent(URLDecoder.decode(eval.getContent(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("内容转码异常");
            return result;
        }

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");

        int row = evalService.add(student,eval);
        if(row>0){
            result.setResult(true);
        } else {
            result.setResult(false);
            result.setMsg("插入失败！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/listByPage",method = RequestMethod.POST)
    public Result listByPage(@RequestBody EvalExtend extend){
	    Result result = new Result();

	    try {
            List<Eval> evals = evalService.getByPage(extend);
            result.setResult(true);
            result.setData(evals);
        } catch (Exception e){
	        e.printStackTrace();
	        result.setResult(false);
	        result.setMsg(e.getMessage());
        }

	    return result;
    }
}
