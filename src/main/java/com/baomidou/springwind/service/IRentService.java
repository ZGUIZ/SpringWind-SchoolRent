package com.baomidou.springwind.service;

import com.baomidou.springwind.Exception.DataBaseUpdatExcepton;
import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.entity.Rent;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Student;

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
}
