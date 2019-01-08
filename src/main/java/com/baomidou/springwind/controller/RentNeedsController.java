package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.springwind.entity.RentNeeds;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.mapper.RentNeedsMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 用户发布的租赁需求 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/rentNeeds")
public class RentNeedsController {

    @Autowired
    private RentNeedsMapper rentNeedsMapper;

    @RequestMapping("queryList")
    @ResponseBody
    public String queryList(String json){
        RequestInfo<RentNeeds> requestInfo = RequestInfo.getObjectFromJson(json);
        EntityWrapper<RentNeeds> wrapper=new EntityWrapper<>();
        wrapper.setEntity(requestInfo.getParam());
        List<RentNeeds> rentNeeds=rentNeedsMapper.selectPage(new RowBounds(requestInfo.getStart(), requestInfo.getEnd()),wrapper);
        return JSONArray.toJSONString(rentNeeds);
    }
}
