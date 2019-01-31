package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springwind.Exception.PassWordNotSameException;
import com.baomidou.springwind.common.EhcacheHelper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.ISchoolService;
import com.baomidou.springwind.service.IStudentService;
import com.baomidou.springwind.utils.DataTablesUtilJson;
import com.baomidou.springwind.utils.MailUtil;
import com.baomidou.springwind.utils.PassWordUtil;
import com.baomidou.springwind.utils.RSAUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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
import java.net.URLDecoder;
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
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/register",method = RequestMethod.POST)
    public String register(@RequestBody Student student){
        Result result=new Result();
        String confirmPassword = student.getConfirmPassword();

        try{
            if(student.getSchoolId() == null || "".equals(student.getSchoolId())){
                result.setResult(false);
                result.setMsg("学校不能为空！");
            } else if((student.getPassword() == null || "".equals(student.getPassword()))&(confirmPassword ==null || "".equals(confirmPassword))){
                result.setResult(false);
                result.setMsg("密码不能为空！");
            } else {
                student.setUserName(URLDecoder.decode(student.getUserName(),"utf-8"));
                Student s=studentService.studentRegister(student);
                if(s == null){
                    result.setResult(false);
                    result.setMsg("注册失败，可能该账号已经被注册！");
                } else{
                    result.setResult(true);
                    result.setData(s);
                    result.setMsg("注册成功！");
                }
            }
        } catch (DuplicateKeyException exception){
            exception.printStackTrace();
            result.setResult(false);
            result.setMsg("该账号已经被注册!");
        } catch (PassWordNotSameException e){
            result.setResult(false);
            result.setMsg("两次输入的密码不一致");
            e.printStackTrace();
        } catch (MySQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("该邮箱或用户名已经被注册!");
        } catch (Exception e){
            result.setResult(false);
            result.setMsg(e.getMessage());
            e.printStackTrace();
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
        Student s= null;
        try {
            student.setUserName(URLDecoder.decode(student.getUserName(),"utf-8"));
            s = studentService.login(student);
            if(s == null){
                result.setResult(false);
                result.setMsg("用户名或密码错误！");
            } else {
                result.setResult(true);
                result.setData(s);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }

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

    private static final String MAIL_MESSAGE_TITLE = "邮箱验证";
    private static final String MAIL_VAIL_MSG="您的邮箱正在绑定校园租的账号，若非本人操作，请忽略此信息。此验证码有效时间10分钟，验证码为：";
    private static final String MAIL_VALI_CACHE_NAME = "captchaCache";

    /**
     * 此方法用于发送验证邮件到绑定的邮箱
     * @param address
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/sendMailMessage")
    public String sendMailMessage(String address){

        Result result = new Result();
        String str = PassWordUtil.getRandomPassword(6);
        try {
            //address = URLDecoder.decode(address,"utf-8");
            MailUtil.sendMail(address,MAIL_MESSAGE_TITLE,MAIL_VAIL_MSG+str);
            EhcacheHelper.put(MAIL_VALI_CACHE_NAME,address,str);
            result.setResult(true);
            result.setMsg("发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("邮件发送异常:"+e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }

    /**
     * 此方法用于验证邮箱验证码是否正确
     * @param keyValue
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/validateMail",method = RequestMethod.POST)
    public String validateMail(@RequestBody KeyValue keyValue){
        Result result = new Result();
        try {
            Object object = EhcacheHelper.get(MAIL_VALI_CACHE_NAME, keyValue.getKey());
            if (keyValue.getValue().equals(object)) {
                EhcacheHelper.remove(MAIL_VALI_CACHE_NAME,keyValue.getKey());
                result.setResult(true);
                result.setMsg("验证信息正确");
            } else{
                result.setResult(false);
                result.setMsg("验证信息错误！");
            }
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("验证信息错误！");
        }
        return JSONObject.toJSONString(result);
    }
}
