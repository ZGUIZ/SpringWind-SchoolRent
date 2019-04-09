package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.Manager;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.RequestInfo;

import java.util.List;

/**
 * <p>
  * 管理员表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface ManagerMapper extends BaseMapper<Manager> {
    Manager login(Manager manager);
    List<Manager> queryManager(RequestInfo requestInfo);
    int queryCount(RequestInfo requestInfo);

    List<Manager> queryInEntry(RequestInfo requestInfo);
    int queryInEntryCount(RequestInfo requestInfo);
}