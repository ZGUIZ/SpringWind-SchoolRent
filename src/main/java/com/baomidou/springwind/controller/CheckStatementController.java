package com.baomidou.springwind.controller;

import com.baomidou.springwind.entity.CheckStatement;
import com.baomidou.springwind.entity.CheckStatementExtend;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.service.ICheckStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 账单 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2019-03-12
 */
@Controller
@RequestMapping("/checkStatement")
public class CheckStatementController {

    @Autowired
    private ICheckStatementService checkStatementService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result list(HttpServletRequest request, @RequestBody CheckStatementExtend extend){
        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        List<CheckStatement> statementList = checkStatementService.list(student,extend);
        result.setResult(true);
        result.setData(statementList);
        return result;
    }
}
