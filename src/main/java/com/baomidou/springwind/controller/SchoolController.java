package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.DatatablesView;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.entity.School;
import com.baomidou.springwind.entity.Select2Bean;
import com.baomidou.springwind.service.ISchoolService;
import com.baomidou.springwind.utils.DataTablesUtilJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 学校 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/school")
public class SchoolController {
    @Autowired
	private ISchoolService schoolService;

    /**
     * 获得城市列表
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/getCity")
    public List<Select2Bean> getCity(String province){
        List<Select2Bean> cities=schoolService.getCity(province);
        return cities;
    }
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("getProvince")
    public List<Select2Bean> getProvince(){
        List<Select2Bean> provinces=schoolService.getProvince();
        return provinces;
    }

    /**
     * 获得学校列表
     * @param city
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/queryList")
    public List<School> getSchool(String city){
        EntityWrapper<School> entityWrapper=new EntityWrapper();

        if(city == null || "".equals(city)){
            return  schoolService.selectList(entityWrapper);
        } else{
            List<School> school=schoolService.getSchool(city);
            //String json= "{ 'data':"+JSONArray.toJSONString()+"}";
            return school;
        }
    }

    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/getSchool")
    public List<Select2Bean>  getSchoolForSelect2(String city){
        List<Select2Bean> select2Beans = schoolService.getSchoolForSelect(city);
        return select2Beans;
    }

    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping("/id/{id}")
    public String getSchoolById(@PathVariable("id") String id){
        School school=schoolService.selectById(id);
        return JSONObject.toJSONString(school);
    }

    @ResponseBody
    @RequestMapping("/queryListByPage")
    public String queryList(RequestInfo requestInfo){
        DatatablesView<School> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<School> schools=schoolService.queryListByPage(requestInfo);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(schools);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return JSONObject.toJSONString(datatablesView);
    }

    @RequestMapping("/toList")
    public String toSchoolList(){
        return "school/schoolList";
    }

}
