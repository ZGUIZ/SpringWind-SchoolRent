package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.Result;
import com.baomidou.springwind.entity.Student;
import com.baomidou.mybatisplus.service.IService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 学生学号 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IStudentService extends IService<Student> {

    /**
     * 学生注册
     * @param student
     * @return
     * @throws Exception
     */
    Integer register(Student student) throws Exception;

    Student login(String account,String password);
    Student login(Student student);
    List<Student> queryList();

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<Student> queryListByPage(RequestInfo<Student> page);

    /**
     * 海阔去数量
     * @param requestInfo
     * @return
     */
    Integer getCount(RequestInfo<Student> requestInfo);

    /**
     * 重置密码
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    Boolean resetPassword(String id) throws UnsupportedEncodingException, MessagingException;

    /**
     * 批量删除
     * @param jsonList
     * @return
     */
    Boolean delStudent(String jsonList);
}
