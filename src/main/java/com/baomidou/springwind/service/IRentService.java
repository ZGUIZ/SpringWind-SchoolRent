package com.baomidou.springwind.service;

import com.baomidou.springwind.Exception.DataBaseUpdatExcepton;
import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.Exception.MoneyNotEnoughException;
import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.entity.Rent;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.RentExtend;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
 * 租赁信息关联表 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IRentService extends IService<Rent> {
    /**
     * 回应租赁请求
     * @param rentId
     * @param response
     * @param studentId
     * @return
     * @throws IllegalAuthroiyException
     * @throws DataBaseUpdatExcepton
     */
    boolean responseRent(String rentId, Integer response, String studentId) throws IllegalAuthroiyException, DataBaseUpdatExcepton;

    boolean responseFromCustomer(Rent rent,Student student) throws IllegalAuthroiyException, DataBaseUpdatExcepton;

    boolean addRent(Rent rent,Student student) throws Exception;

    List<Rent> queryList(IdleInfo idleInfo);

    int getCountOfRequest(Rent rent);
    Rent getCanRent(Rent rent);

    /**
     * 同意租赁
     * @param rent
     * @return
     */
    boolean agreeRent(Student student,Rent rent) throws Exception;

    List<Rent> selectForPage(RentExtend rentExtend);

    boolean cancelRent(Student student,Rent rent) throws IllegalAuthroiyException;

    boolean startRent(Student student,Rent rent) throws Exception;

    /**
     * 发布者开始租赁
     * @param student
     * @param idleInfo
     * @return
     * @throws Exception
     */
    boolean startRent(Student student,IdleInfo idleInfo) throws Exception;

    boolean delRent(Student student, Rent rent) throws Exception;

    boolean disagreeRent(Student student,Rent rent) throws Exception;

    boolean finishRent(Student student,Rent rent) throws IllegalAuthroiyException;

    void calRentalDaily();

    int getRentCount(String id);
}
