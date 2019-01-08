package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.ISchoolService;
import com.baomidou.springwind.service.IStudentService;
import com.baomidou.springwind.utils.DataTablesUtilJson;
import com.baomidou.springwind.utils.PassWordUtil;
import com.baomidou.springwind.utils.RSAUtil;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生学号 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ISchoolService schoolService;

    /**
     * 学生账号注册
     * @param student
     * @param confirmPassword
     * @return
     */
    @ResponseBody
    @RequestMapping("/register")
    public String register(Student student,String confirmPassword){
        Result result=new Result();
        try{
            if(student.getSchoolId() == null || "".equals(student.getSchoolId())){
                result.setResult(false);
                result.setMsg("学号不能为空！");
            } else if((student.getPassword() == null || "".equals(student.getPassword()))&(confirmPassword ==null || "".equals(confirmPassword))){
                result.setResult(false);
                result.setMsg("密码不能为空！");
            } else if(!confirmPassword.equals(student.getPassword())){
                result.setResult(false);
                result.setMsg("两次输入的密码不一致！");
            } else {
                Boolean res=studentService.insert(student);
                if(!res){
                    result.setResult(false);
                    result.setMsg("注册失败，可能该账号已经被注册！");
                } else{
                    result.setResult(true);
                    result.setMsg("注册成功！");
                }
            }
        } catch (DuplicateKeyException exception){
            result.setResult(false);
            result.setMsg("该账号已经被注册!");
        } catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(String account,String password){
        Result result=new Result();
        //私钥解密密码
        PrivateKey privateKey=RSAUtil.restorePrivateKey(RSAUtil.getKeys().get(RSAUtil.PRIVATE_KEY));
        String p = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(password));
        Student student=studentService.login(account,p);
        if(student == null){
            result.setResult(false);
            result.setMsg("账号或密码错误！");
        } else if(student.getStatus() == 2) {
            result.setResult(false);
            result.setMsg("该账号禁止登陆");
        } else {
                result.setResult(true);
                result.setMsg("登录成功!");
                result.setData(student);
                result.setParamType("Student");
        }
        return JSONObject.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value="loginMobile",method=RequestMethod.POST)
    public String loginMobile(@RequestBody Student student){
        Result result = new Result();
        Student s=studentService.login(student);
        result.setResult(true);
        result.setData(s);
        return JSONObject.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/queryList")
    public String queryList(){
        List<Student> students=studentService.queryList();
        return DataTablesUtilJson.arrayToDataTablesJson(students);
    }

    @ResponseBody
    @RequestMapping(value = "/queryListByPage")
    public String queryList(RequestInfo requestInfo){
        DatatablesView<Student> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<Student> students=studentService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(students);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return JSONObject.toJSONString(datatablesView);
    }

    @RequestMapping("/toList")
    public String toList(){
        return "student/studentList";
    }

    @ResponseBody
    @RequestMapping(value="/updateStudent", method = RequestMethod.POST)
    public String updateStudent(@RequestBody String json){
        Student student=Student.getObjectFromJsonObject(json);

        //如果是新增学生
        if("add".equals(student.getBeanStatus())){
            Result result=new Result();
            try {
                int i = studentService.register(student);
                if(i>0){
                    result.setResult(true);
                    result.setMsg("注册成功！");
                } else{
                    result.setResult(false);
                    result.setMsg("注册失败！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setResult(false);
                result.setMsg("注册失败:"+e.getMessage());
            }

            return JSONObject.toJSONString(result);
        }

        //更新学生信息
        boolean success=studentService.updateById(student);
        Result result=new Result();
        result.setResult(success);
        return JSONObject.toJSONString(result);
    }

    @RequestMapping(value = "toForm/id/{id}")
    public String toFrom(@PathVariable("id") String id){
        /*ModelAndView view = new ModelAndView("student/studentForm");
        Student student=studentService.selectById(id);
        view.addObject("student",student);*/
        return "student/studentForm";
    }

    @RequestMapping(value = "/id/{id}")
    @ResponseBody
    public String queryById(@PathVariable("id") String id){
        Student student = studentService.selectById(id);
        School school = schoolService.selectById(student.getSchoolId());
        student.setSchool(school);
        return JSONObject.toJSONString(student);
    }

    @ResponseBody
    @RequestMapping(value="/add")
    public String addStudent(){
        Student student = new Student();
        student.setBeanStatus("add");
        return JSONObject.toJSONString(student);
    }

    /**
     * 返回性别Select2选项
     * @return
     */
    @RequestMapping(value = "/getSex")
    @ResponseBody
    public String getSex(){
        List<Select2Bean> select2Beans = new ArrayList<>();
        Select2Bean man=new Select2Bean();
        man.setId("男");
        man.setText("男");
        select2Beans.add(man);
        Select2Bean woman = new Select2Bean();
        woman.setId("女");
        woman.setText("女");
        select2Beans.add(woman);
        return JSONArray.toJSONString(select2Beans);
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetPasssWord/id/{id}")
    public String restartPassword(@PathVariable("id") String id){
        Result result=new Result();
        boolean res= false;
        try {
            res = studentService.resetPassword(id);
            result.setResult(res);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("邮件发送异常");
        }
        return JSONObject.toJSONString(result);
    }

    /**
     * 批量删除
     */
    @ResponseBody
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public String delStudent(@RequestBody String json){
        Result result=new Result();
        try{
            Boolean res=studentService.delStudent(json);
            result.setResult(res);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }
}
