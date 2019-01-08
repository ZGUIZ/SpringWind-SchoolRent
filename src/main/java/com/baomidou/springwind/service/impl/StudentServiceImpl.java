package com.baomidou.springwind.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.springwind.entity.Capital;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CapitalMapper capitalMapper;

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

        if(password != null){
            MailUtil.sendMail(student.getEmail(),mailTitle,mailContext+password);
        }
        return row;
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
    public Student login(Student student) {
        PrivateKey privateKey=RSAUtil.restorePrivateKey(RSAUtil.getKeys().get(RSAUtil.PRIVATE_KEY));
        String p = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(student.getPassword()));
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
}
