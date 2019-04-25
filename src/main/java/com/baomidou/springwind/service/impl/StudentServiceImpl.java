package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.Exception.PassWordNotSameException;
import com.baomidou.springwind.Exception.PasswordErrorException;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.CapitalMapper;
import com.baomidou.springwind.mapper.StudentMapper;
import com.baomidou.springwind.service.IStudentService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.MailUtil;
import com.baomidou.springwind.utils.PassWordUtil;
import com.baomidou.springwind.utils.RSAUtil;
import com.baomidou.springwind.utils.SHA1Util;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.*;

/**
 * <p>
 * 学生学号 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentMapper, Student> implements IStudentService {

    private static final String mailTitle="校园租密码重置";
    private static final String mailContext = "您在校园租的账号密码已经被重置，请及时修改，重置密码为：";
    private static final String payContext = "您在校园租的账号支付密码已经被重置，请及时修改，重置密码为：";

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CapitalMapper capitalMapper;

    @Deprecated
    @Transactional
    @Override
    public Integer register(Student student) throws Exception {
        Date now=new Date();
        //设置学生信息的基本参数
        String userId=UUID.randomUUID().toString().replace("-","");
        student.setUserId(userId);
        student.setCreateDate(now);

        //如果密码为空，则创建随机密码
        String password = null;
        if(student.getPassword() == null){
            password = PassWordUtil.getRandomPassword();
            student.setPassword(SHA1Util.encode(password));
        }
        int row=studentMapper.insert(student);
        initCaption(row,userId);
        if(password != null){
            MailUtil.sendMail(student.getEmail(),mailTitle,mailContext+password);
        }
        return -1;
    }

    /**
     * 初始化账号资金信息
     * @param row
     * @param userId
     * @throws Exception
     */
    private void initCaption(int row,String userId) throws Exception {
        Date now=new Date();
        if(row >0){
            //初始化账号资金信息
            Capital capital=new Capital();
            String capital_id=UUID.randomUUID().toString().replace("-","");
            capital.setCapitalId(capital_id);
            capital.setUserId(userId);
            capital.setUpdateTime(now);
            int capRow=capitalMapper.insert(capital);
            if(capRow<=0){
                throw new Exception("账号初始化失败，请重新注册！");
            }
        } else {
            throw new Exception("学生注册错误！");
        }
    }

    @Override
    public Student login(String account, String password) {
        Student student = new Student();
        student.setStudentId(account);
        student.setPassword(SHA1Util.encode(password));
        Student result=studentMapper.login(student);
        return result;
    }

    @Override
    public Student login(Student student){
        PrivateKey privateKey=RSAUtil.restorePrivateKey(RSAUtil.getKeys().get(RSAUtil.PRIVATE_KEY));
        String p = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(student.getPassword()));
        //String p = RSAUtil.RSADecode(privateKey, student.getPassword().getBytes("utf-8"));
        String p2 = SHA1Util.encode(p);
        student.setPassword(p2);
        Student res= studentMapper.login(student);
        return res;
    }

    @Override
    public List<Student> queryList() {
        return studentMapper.queryList();
    }

    @Override
    public List<Student> queryListByPage(RequestInfo<Student> page) {
        page.setAmmount(studentMapper.getCount(page));
        return studentMapper.queryForPage(page);
    }

    @Override
    public Integer getCount(RequestInfo<Student> requestInfo) {
        return studentMapper.getCount(requestInfo);
    }

    /**
     * 重置密码
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    @Override
    public Boolean resetPassword(String id) throws UnsupportedEncodingException, MessagingException {
        String password = PassWordUtil.getRandomPassword();
        Student student = studentMapper.selectById(id);
        student.setPassword(SHA1Util.encode(password));
        studentMapper.updateById(student);
        //发送邮件
        MailUtil.sendMail(student.getEmail(),mailTitle,mailContext+password);
        return true;
    }

    @Override
    public Boolean resetPayPassword(String id) throws UnsupportedEncodingException, MessagingException {
        String password = PassWordUtil.getRandomPassword();
        Student student = studentMapper.selectById(id);
        student.setPayPassword(SHA1Util.encode(password));
        studentMapper.updateById(student);
        //发送邮件
        MailUtil.sendMail(student.getEmail(),mailTitle,payContext+password);
        return true;
    }

    @Transactional
    @Override
    public Boolean delStudent(String jsonList) {
        JSONArray array = JSONArray.parseArray(jsonList);
        for(int i=0;i<array.size();i++){
            Student student =Student.getObjectFromJsonObject(array.getJSONObject(i));
            student.setStatus(100);
            studentMapper.updateById(student);
        }
        return true;
    }

    @Transactional
    @Override
    public Student studentRegister(Student student) throws Exception {
        Date now=new Date();
        PrivateKey privateKey=RSAUtil.restorePrivateKey(RSAUtil.getKeys().get(RSAUtil.PRIVATE_KEY));
        String p = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(student.getPassword()));

        //比较密码和确认密码是否一致
        String confirm = RSAUtil.RSADecode(privateKey,Base64.decodeBase64(student.getConfirmPassword()));
        if(!p.equals(confirm)){
            throw new PassWordNotSameException();
        }

        String p2 = SHA1Util.encode(p);
        student.setPassword(p2);

        //比较支付密码
        String pass = RSAUtil.RSADecode(privateKey,Base64.decodeBase64(student.getPayPassword()));
        String confrimPay = RSAUtil.RSADecode(privateKey,Base64.decodeBase64(student.getConfirmPayPassword()));

        if(!pass.equals(confrimPay)){
            throw new PassWordNotSameException();
        }
        student.setPayPassword(SHA1Util.encode(pass));

        String userId=UUID.randomUUID().toString().replace("-","");
        student.setUserId(userId);
        student.setCreateDate(now);



        int row=studentMapper.insert(student);
        //初始化账号资金信息
        initCaption(row,userId);
        return student;
    }

    @Override
    public boolean hasPayPassword(Student student) {
        Student s = studentMapper.selectById(student.getStudentId());
        if(s.getPayPassword()!=null && !"".equals(s.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(Student student, PassWord passWord) throws PasswordErrorException {
        boolean flag=false;
        Student s = studentMapper.selectById(student.getUserId());

        PrivateKey privateKey=RSAUtil.restorePrivateKey(RSAUtil.getKeys().get(RSAUtil.PRIVATE_KEY));

        String p = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(passWord.getOldPassword()));
        p = SHA1Util.encode(p);
        if (!s.getPassword().equals(p)) {
            throw new PasswordErrorException("密码错误！");
        }

        String newPassword = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(passWord.getNewPassword()));
        String confirmPassword = RSAUtil.RSADecode(privateKey,Base64.decodeBase64(passWord.getConfirmPaswword()));

        if(newPassword == null){
            throw new PassWordNotSameException();
        }
        //比较密码和确认密码是否一致
        //String confirm = RSAUtil.RSADecode(privateKey,Base64.decodeBase64(passWord.getOldPassword()));

        if(!newPassword.equals(confirmPassword)){
            throw new PassWordNotSameException();
        }

        String np = SHA1Util.encode(newPassword);

        s.setPassword(np);
        int count = studentMapper.updateById(s);
        if(count>0){
            flag = true;
        }

        return flag;
    }

    @Override
    public boolean updatePayPassword(Student student, PassWord passWord) throws PasswordErrorException {
        boolean flag=false;
        Student s = studentMapper.selectById(student.getUserId());

        PrivateKey privateKey=RSAUtil.restorePrivateKey(RSAUtil.getKeys().get(RSAUtil.PRIVATE_KEY));

        String p = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(passWord.getOldPassword()));
        p = SHA1Util.encode(p);
        if (!s.getPayPassword().equals(p)) {
            throw new PasswordErrorException("密码错误！");
        }

        String newPassword = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(passWord.getNewPassword()));
        String confirmPassword = RSAUtil.RSADecode(privateKey,Base64.decodeBase64(passWord.getConfirmPaswword()));

        if(newPassword == null){
            throw new PassWordNotSameException();
        }

        if(!newPassword.equals(confirmPassword)){
            throw new PassWordNotSameException();
        }

        String np = SHA1Util.encode(newPassword);

        s.setPayPassword(np);

        int count = studentMapper.updateById(s);
        if(count>0){
            flag = true;
        }

        return flag;
    }

    @Override
    public Student getBaseInfo(Student student) {
        return studentMapper.getBaseInfo(student);
    }

    @Override
    public Student getCurrentUser(Student student){
        return studentMapper.getCurrentUser(student);
    }

    @Override
    public boolean modifyPassword(PassWord passWord) {
        Student student = new Student();
        student.setEmail(passWord.getMail());
        EntityWrapper entityWrapper = new EntityWrapper(student);
        List<Student> students = studentMapper.selectList(entityWrapper);
        if(students.size()!=1){
            return false;
        } else {
            PrivateKey privateKey=RSAUtil.restorePrivateKey(RSAUtil.getKeys().get(RSAUtil.PRIVATE_KEY));
            String newPassword = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(passWord.getNewPassword()));
            String confirmPassword = RSAUtil.RSADecode(privateKey,Base64.decodeBase64(passWord.getConfirmPaswword()));

            if(newPassword == null){
                throw new PassWordNotSameException();
            }

            //比较密码和确认密码是否一致
            if(!newPassword.equals(confirmPassword)){
                throw new PassWordNotSameException();
            }
            student = students.get(0);
            String np = SHA1Util.encode(newPassword);
            student.setPassword(np);
            int count = studentMapper.updateById(student);
            if(count>0){
                return true;
            }
            return false;
        }
    }

    @Override
    public List<Integer> getUserCount() {
        List<Integer> counts = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        for(int i = 1;i<=month;i++){
            Integer count = studentMapper.selectUserCount(year,i);
            if(count == null){
                count = 0;
            }
            counts.add(count);
        }
        return counts;
    }
}
