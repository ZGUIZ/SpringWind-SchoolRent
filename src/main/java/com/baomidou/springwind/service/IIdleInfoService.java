package com.baomidou.springwind.service;

import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.IdleInfoExtend;
import com.baomidou.springwind.entity.Student;

import java.util.List;

/**
 * <p>
 * 闲置信息 服务类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface IIdleInfoService extends IService<IdleInfo> {
    /**
     * 发布闲置
     * @param student
     * @param info
     * @return
     */
    @Deprecated
    boolean addRentInfo(Student student,IdleInfo info) throws Exception;

    int addIdleInfo(IdleInfo idleInfo);

    List<IdleInfo> selectByPage(IdleInfoExtend idleInfo);

    boolean closeIdle(IdleInfo idleInfo) throws Exception;

    /**
     * 同意后取消
     * @param idleInfo
     * @return
     * @throws Exception
     */
    boolean cancleRent(IdleInfo idleInfo) throws Exception;
}
