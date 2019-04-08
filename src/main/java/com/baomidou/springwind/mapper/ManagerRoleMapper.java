package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.ManagerRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.springwind.entity.Select2Bean;

import java.util.List;

/**
 * <p>
  * 管理员角色表 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface ManagerRoleMapper extends BaseMapper<ManagerRole> {
    List<Select2Bean> queryForSelect();
}