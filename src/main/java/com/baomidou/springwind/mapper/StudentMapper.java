package com.baomidou.springwind.mapper;

import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.Student;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 学生学号 Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
public interface StudentMapper extends BaseMapper<Student> {
    Student login(Student student);

    List<Student> queryList();

    /**
     * 分页条件查询
     * @param page
     * @return
     */
    List<Student> queryForPage(RequestInfo<Student> page);

    /**
     * 获取总条数
     * @param requestInfo
     * @return
     */
    Integer getCount(RequestInfo<Student> requestInfo);

    Student getBaseInfo(Student student);

    Student getCurrentUser(Student student);

    Student selectForUpdate(Student student);

    Integer selectUserCount(@Param("year")int year,@Param("month")int month);
}