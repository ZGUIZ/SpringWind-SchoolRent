package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.Manager;
import com.baomidou.mybatisplus.mapper.BaseMapper;

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
}