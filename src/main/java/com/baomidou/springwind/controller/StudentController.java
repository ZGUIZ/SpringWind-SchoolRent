package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.Exception.PassWordNotSameException;
import com.baomidou.springwind.Exception.PasswordErrorException;
import com.baomidou.springwind.common.EhcacheHelper;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.ISchoolService;
import com.baomidou.springwind.service.IStudentService;
import com.baomidou.springwind.utils.*;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

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
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value="/register",method = RequestMethod.POST)
    public String register(HttpServletRequest request,@RequestBody Student student){
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

                    //将数据放入session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("student",s);
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

    @Permission(action = Action.Skip)
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

    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value="loginMobile",method=RequestMethod.POST)
    public String loginMobile(HttpServletRequest request,@RequestBody Student student){
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
            //将数据放入session
            HttpSession session = request.getSession(true);
            session.setAttribute("student",s);
            session.setMaxInactiveInterval(-1);
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
    public DatatablesView<Student> queryList(RequestInfo requestInfo){
        DatatablesView<Student> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<Student> students=studentService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(students);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @ResponseBody
    @RequestMapping(value = "/queryUnLogin")
    public DatatablesView<Student> queryUnLogin(RequestInfo<Student> requestInfo){
        DatatablesView<Student> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());

        Student student = new Student();
        student.setStatus(2);
        requestInfo.setParam(student);

        List<Student> students=studentService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(students);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @RequestMapping("/toList")
    public String toList(){
        return "student/studentList";
    }

    @RequestMapping("/toUnLogin")
    public String toUnLogin(){
        return "student/unLoginList";
    }

    @ResponseBody
    @RequestMapping(value="/updateStudent", method = RequestMethod.POST)
    public Result updateStudentFromServer(@RequestBody Student student){
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

            return result;
        }

        //更新学生信息
        boolean success=studentService.updateById(student);
        Result result=new Result();
        result.setResult(success);
        return result;
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
    public Student queryById(@PathVariable("id") String id){
        Student student = studentService.selectById(id);
        School school = schoolService.selectById(student.getSchoolId());
        student.setSchool(school);
        return student;
    }

    @ResponseBody
    @RequestMapping(value="/add")
    public Student addStudent(){
        Student student = new Student();
        student.setBeanStatus("add");
        return student;
    }

    /**
     * 返回性别Select2选项
     * @return
     */
    @Permission(action = Action.Skip)
    @RequestMapping(value = "/getSex")
    @ResponseBody
    public List<Select2Bean> getSex(){
        List<Select2Bean> select2Beans = new ArrayList<>();
        Select2Bean man=new Select2Bean();
        man.setId("男");
        man.setText("男");
        select2Beans.add(man);
        Select2Bean woman = new Select2Bean();
        woman.setId("女");
        woman.setText("女");
        select2Beans.add(woman);
        return select2Beans;
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

    @ResponseBody
    @RequestMapping("/restPassword")
    public String resetPasswordFromUser(HttpServletRequest request){
        Result result=new Result();
        boolean res= false;
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute("student");
            res = studentService.resetPassword(student.getUserId());
            result.setResult(res);
            result.setMsg("重置成功！");
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("邮件发送异常");
        }
        return JSONObject.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("/restPayPassword")
    public String resetPayPasswordFromUser(HttpServletRequest request){
        Result result=new Result();
        boolean res= false;
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute("student");
            res = studentService.resetPayPassword(student.getUserId());
            result.setResult(res);
            result.setMsg("重置成功！");
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
    public Result delStudent(@RequestBody String json){
        Result result=new Result();
        try{
            Boolean res=studentService.delStudent(json);
            result.setResult(res);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private static final String MAIL_MESSAGE_TITLE = "邮箱验证";
    private static final String MAIL_VAIL_MSG="您的邮箱正在绑定校园租的账号，若非本人操作，请忽略此信息。此验证码有效时间10分钟，验证码为：";
    public static final String MAIL_VALI_CACHE_NAME = "captchaCache";

    /**
     * 此方法用于发送验证邮件到绑定的邮箱
     * @param address
     * @return
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value="/sendMailMessage")
    public String sendMailMessage(String address){

        Result result = new Result();

        //查询该邮箱是否注册
        Student student = new Student();
        student.setEmail(address);
        List<Student> students = studentService.selectList(new EntityWrapper<>(student));
        if(students != null && students.size()>0){
            result.setResult(false);
            result.setMsg("该邮箱已被注册");
            return  JSONObject.toJSONString(result);
        }

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
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
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

    @ResponseBody
    @RequestMapping(value = "/getCurrentUser")
    public Result getCurrentUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Result result = new Result();
        if(session == null){
            result.setResult(false);
            return result;
        }
        try {
            Student student = (Student) session.getAttribute("student");
            Student curr = studentService.getCurrentUser(student);
            session.setAttribute("student",curr);
            result.setResult(true);
            result.setData(curr);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/getBaseInfo",method = RequestMethod.POST)
    public Result getBaseInfo(@RequestBody Student student){
        Result result = new Result();
        try {
            Student s = studentService.getBaseInfo(student);
            result.setResult(true);
            result.setData(s);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/hasPayPassWord")
    public Result hasPayPassword(HttpServletRequest request){
        Result result = new Result();
        HttpSession httpSession = request.getSession();
        Student student = (Student) httpSession.getAttribute("student");
        boolean res = studentService.hasPayPassword(student);
        result.setResult(res);
        result.setCode(500);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/exitLogin")
    public void exitLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("student",null);
        session.removeAttribute("student");
        session.invalidate();
    }

    @ResponseBody
    @RequestMapping(value = "/updateInfo",method = RequestMethod.POST)
    public Result updateStudent(HttpServletRequest request,@RequestBody Student student){
        Result result = new Result();
        try {
            String userName = student.getUserName();
            if(userName!=null && !"".equals(userName.trim())){
                student.setUserName(URLDecoder.decode(userName,"utf-8"));
            }
            String realName = student.getRealName();
            if(realName!=null && !"".equals(realName.trim())){
                student.setRealName(URLDecoder.decode(realName,"utf-8"));
            }
            String sex =student.getSex();
            if (sex!=null && !"".equals(sex.trim())) {
                student.setSex(URLDecoder.decode(sex,"utf-8"));
            }
            Student s = (Student) request.getSession().getAttribute("student");
            student.setUserId(s.getUserId());
            studentService.updateById(student);
            result.setResult(true);
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("修改失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public Result updatePassword(HttpServletRequest request,@RequestBody PassWord passWord){
        Student s = (Student) request.getSession().getAttribute("student");
        Result result = new Result();
        result.setResult(false);
        try {
            boolean res = studentService.updatePassword(s,passWord);
            result.setResult(res);
            if(res){
                result.setMsg("修改成功！");
            } else {
                result.setMsg("修改失败！");
            }
        } catch (PasswordErrorException pee){
           result.setMsg("密码错误");
        } catch (PassWordNotSameException e){
            result.setMsg("两次输入的密码不一致");
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("修改失败！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updatePayPassword",method = RequestMethod.POST)
    public Result updatePayPassword(HttpServletRequest request,@RequestBody PassWord passWord){
        Student s = (Student) request.getSession().getAttribute("student");
        Result result = new Result();
        result.setResult(false);
        try {
            boolean res = studentService.updatePayPassword(s,passWord);
            result.setResult(res);
            if(res){
                result.setMsg("修改成功！");
            } else {
                result.setMsg("修改失败！");
            }
        } catch (PasswordErrorException pee){
            result.setMsg("密码错误");
        } catch (PassWordNotSameException e){
            result.setMsg("两次输入的密码不一致");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 此方法用于发送验证邮件到绑定的邮箱
     * @return
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value="/sendForgotMessage")
    public Result sendForgotMessage(@RequestBody Student param){

        Result result = new Result();
        String str = PassWordUtil.getRandomPassword(6);

        //查询该邮箱是否注册
        String address= param.getEmail();
        Student student = new Student();
        student.setEmail(param.getEmail());
        List<Student> students = studentService.selectList(new EntityWrapper<>(student));
        if(students == null || students.size()<=0){
            result.setResult(false);
            result.setMsg("该邮箱未注册");
            return  result;
        }

        try {
            //address = URLDecoder.decode(address,"utf-8");
            MailUtil.sendMail(address,"您的校园租账号正在找回密码","您的校园租账号正在找回密码，若非本人申请请忽略此信息。验证码："+str);
            EhcacheHelper.put(MAIL_VALI_CACHE_NAME,address,str);
            result.setResult(true);
            result.setMsg("发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg("邮件发送异常:"+e.getMessage());
        }
        return result;
    }

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value = "/validateForgot")
    public Result validateForgot(@RequestBody KeyValue keyValue){
        Result result = new Result();
        try {
            Object object = EhcacheHelper.get(MAIL_VALI_CACHE_NAME, keyValue.getKey());
            if (keyValue.getValue().equals(object)) {
                EhcacheHelper.remove(MAIL_VALI_CACHE_NAME,keyValue.getKey());
                result.setResult(true);
                PassWord passWord = new PassWord();
                String code = PassWordUtil.getRandomPassword(6);
                passWord.setCode(code);
                passWord.setMail(keyValue.getKey());
                result.setData(passWord);
                EhcacheHelper.put(MAIL_VALI_CACHE_NAME,keyValue.getKey(),code);
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
        return result;
    }

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value = "/modifyForgotPass")
    public Result modifyForgotPass(@RequestBody PassWord passWord){
        Result result = new Result();
        Object object = EhcacheHelper.get(MAIL_VALI_CACHE_NAME, passWord.getMail());
        if (passWord.getCode().equals(object)) {
            try {
                boolean res = studentService.modifyPassword(passWord);
                result.setResult(res);
                EhcacheHelper.remove(MAIL_VALI_CACHE_NAME,passWord.getMail());
            } catch (PassWordNotSameException e){
                result.setResult(false);
                result.setMsg("两次输入的密码不一致。");
                //将验证码重新放入
            } catch (Exception e){
                e.printStackTrace();
                result.setResult(false);
                result.setMsg(e.getMessage());
            }
        } else{
            result.setResult(false);
            result.setMsg("验证信息错误！");
        }
        return result;
    }

    /**
     * 更改邮箱
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMail")
    public Result updateMail(@RequestBody Student param){
        Result result= new Result();

        Student student = studentService.selectById(param.getUserId());
        Object object = EhcacheHelper.get(MAIL_VALI_CACHE_NAME, param.getEmail());
        if (param.getCode().equals(object)) {
            student.setEmail(param.getEmail());
            studentService.updateById(student);
            EhcacheHelper.remove(MAIL_VALI_CACHE_NAME,param.getEmail());
            result.setResult(true);
        } else{
            result.setResult(false);
            result.setMsg("验证码错误");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/sendMobileCode/{telephone}")
    public Result sendMobileCode(@PathVariable("telephone") String telephone){
        Result result = new Result();
        Student student = new Student();
        student.setTelephone(telephone);
        List<Student> students = studentService.selectList(new EntityWrapper<>(student));
        if(students.size()>0){
            result.setResult(false);
            result.setMsg("该手机号已经绑定其他用户");
            return result;
        }
        String code = SMSUtil.sendSMS(telephone);
        EhcacheHelper.put(MAIL_VALI_CACHE_NAME,telephone,code);
        result.setResult(true);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateMobile")
    public Result updatePhone(HttpServletRequest request,@RequestBody Student param){
        Result result = new Result();
        Student s = (Student) request.getSession().getAttribute("student");
        param.setUserId(s.getUserId());
        Student student = studentService.selectById(param.getUserId());
        Object object = EhcacheHelper.get(MAIL_VALI_CACHE_NAME, param.getTelephone());
        if (param.getCode().equals(object)) {
            student.setTelephone(param.getTelephone());
            studentService.updateById(student);
            EhcacheHelper.remove(MAIL_VALI_CACHE_NAME,param.getEmail());
            result.setResult(true);
        } else{
            result.setResult(false);
            result.setMsg("验证码错误");
        }
        return result;
    }
}
