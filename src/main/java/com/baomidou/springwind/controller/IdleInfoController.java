package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.service.IIdleInfoService;
import com.baomidou.springwind.service.IStudentService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 闲置信息 前端控制器
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/idleInfo")
public class IdleInfoController {

    @Autowired
    private IIdleInfoService idleInfoService;

    @Autowired
    private IStudentService studentService;

    @RequestMapping(value = "/queryList")
    public ModelAndView queryList(){
        ModelAndView view=new ModelAndView("idle/grids");
        List<IdleInfo> idles=idleInfoService.selectList(new EntityWrapper<>());
        for(IdleInfo info:idles){
            System.out.println(info.getIdelInfo());
        }
        view.addObject("idles",idles);
        return view;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result addIdleInfo(HttpServletRequest request,@RequestBody IdleInfo idleInfo){
        Result result = new Result();
        Student student = (Student) request.getSession().getAttribute("student");
        if(student == null){
            result.setResult(false);
            result.setMsg("用户未登录");
            return result;
        }
        try {
            idleInfo.setSchoolId(student.getSchoolId());
            idleInfo.setUserId(student.getUserId());
            idleInfo.setTitle(URLDecoder.decode(idleInfo.getTitle(),"utf-8"));
            idleInfo.setIdelInfo(URLDecoder.decode(idleInfo.getIdelInfo(),"utf-8"));
            if(idleInfo.getAddress()!=null){
                idleInfo.setAddress(URLDecoder.decode(idleInfo.getAddress(),"utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int count = idleInfoService.addIdleInfo(idleInfo);
        if(count > 0){
            result.setResult(true);
            result.setMsg("发布成功！");
            result.setData(idleInfo);
        } else {
            result.setResult(false);
            result.setMsg("发布异常");
        }
        return result;
    }

    @RequestMapping(value = "/toList",method = RequestMethod.POST)
    @ResponseBody
    public Result toList(HttpServletRequest request,@RequestBody IdleInfoExtend idleInfo){
        Result result = new Result();
        Student student = (Student) request.getSession().getAttribute("student");
        if(student == null){
            result.setResult(false);
            result.setMsg("用户未登录");
            return result;
        }

        String search = idleInfo.getSearch();
        try {
            if (search != null) {
                idleInfo.setSearch(URLDecoder.decode(search, "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        idleInfo.setStatus(0); //查询所有未租赁的闲置
        idleInfo.setSchoolId(student.getSchoolId());
        List<IdleInfo> idleInfoList = idleInfoService.selectByPage(idleInfo);
        result.setResult(true);
        result.setData(idleInfoList);
        return result;
    }

    /**
     * 获取当前用户发布的租赁信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMinePush")
    public Result getMinePush(HttpServletRequest request,@RequestBody IdleInfoExtend idleInfo){
        Result result = new Result();
        Student student = (Student) request.getSession().getAttribute("student");
        idleInfo.setSchoolId(student.getSchoolId());
        idleInfo.setUserId(student.getUserId());
        List<IdleInfo> idleInfoList = idleInfoService.selectByPage(idleInfo);
        result.setResult(true);
        result.setData(idleInfoList);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserPush")
    public Result getPush(@RequestBody IdleInfoExtend idleInfo){
        Result result = new Result();
        Student student;
        student = studentService.selectById(idleInfo.getUserId());
        idleInfo.setSchoolId(student.getSchoolId());
        idleInfo.setUserId(student.getUserId());
        List<IdleInfo> idleInfoList = idleInfoService.selectByPage(idleInfo);
        result.setResult(true);
        result.setData(idleInfoList);
        return result;
    }

    /**
     * 自主下架
     * @param request
     * @param idleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/close",method = RequestMethod.POST)
    public Result closeIdle(HttpServletRequest request,@RequestBody IdleInfo idleInfo){
        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        idleInfo.setUserId(student.getUserId());
        try {
            boolean r = idleInfoService.closeIdle(idleInfo);
            result.setResult(r);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 取消或完成租赁
     * @param idleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    public Result cancleRent(@RequestBody IdleInfo idleInfo){
        Result result = new Result();
        if(idleInfo.getInfoId() == null || "".equals(idleInfo.getInfoId())){
            result.setResult(false);
            result.setMsg("请选择对应的闲置信息！");
            return result;
        }
        try {
            boolean r = idleInfoService.cancleRent(idleInfo);
            result.setResult(r);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateIdle(HttpServletRequest request,@RequestBody IdleInfo idleInfo){
        //字符编码转换
        try {
            idleInfo.setTitle(URLDecoder.decode(idleInfo.getTitle(), "utf-8"));
            idleInfo.setIdelInfo(URLDecoder.decode(idleInfo.getIdelInfo(), "utf-8"));
            if (idleInfo.getAddress() != null) {
                idleInfo.setAddress(URLDecoder.decode(idleInfo.getAddress(), "utf-8"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        idleInfo.setUserId(student.getUserId());
        try {
            result.setResult(true);
            idleInfoService.updateIdleInfo(idleInfo);
        } catch (IllegalAuthroiyException e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMsg());
        } catch (Exception e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result delIdle(HttpServletRequest request,@RequestBody IdleInfo idleInfo){
        Result result = new Result();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        idleInfo.setUserId(student.getUserId());
        try{
            boolean res = idleInfoService.delIdleInfo(idleInfo);
            result.setResult(res);
        }catch (IllegalAuthroiyException e){
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/id/{id}")
    @ResponseBody
    public Result findById(@PathVariable("id") String id){
        IdleInfo idleInfo = idleInfoService.findById(id);
        Result result = new Result();
        result.setResult(true);
        result.setData(idleInfo);
        return result;
    }

    @ResponseBody
    @RequestMapping("/fromService/id/{id}")
    public Result queryFromService(@PathVariable("id") String id){
        Result result = new Result();
        IdleInfo idleInfo = idleInfoService.findFromServiceById(id);
        result.setResult(true);
        result.setData(idleInfo);
        return result;
    }

    /**
     * 服务端
     */
    @ResponseBody
    @RequestMapping(value = "/queryListByPage")
    public DatatablesView<IdleInfo> queryList(RequestInfo requestInfo){
        DatatablesView<IdleInfo> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<IdleInfo> idleInfos=idleInfoService.queryListByPage(requestInfo,IIdleInfoService.COMMENT);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(idleInfos);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @ResponseBody
    @RequestMapping(value = "/queryDelByPage")
    public DatatablesView<IdleInfo> queryDel(RequestInfo requestInfo){
        DatatablesView<IdleInfo> datatablesView=new DatatablesView<>();
        datatablesView.setDraw(requestInfo.getDraw());
        List<IdleInfo> idleInfos=idleInfoService.queryListByPage(requestInfo,IIdleInfoService.DEL);
        datatablesView.setRecordsTotal(requestInfo.getAmmount());
        datatablesView.setData(idleInfos);
        datatablesView.setRecordsFiltered(requestInfo.getAmmount());
        return datatablesView;
    }

    @RequestMapping(value = "/toList")
    public String toList(){
        return "/idle/idleList";
    }

    @RequestMapping(value = "/toDel")
    public String toDel(){
        return "/idle/delIdleList";
    }

    @RequestMapping(value = "/toForm")
    public String toForm(){
        return "/idle/idleForm";
    }

    @ResponseBody
    @RequestMapping(value = "/delByManager/{id}")
    public Result delByManager(@PathVariable("id") String id){
        Result result = new Result();
       boolean res = idleInfoService.delByManager(id);
        result.setResult(res);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/reShow/{id}")
    public Result reShow(@PathVariable("id") String id){
        Result result = new Result();
        IdleInfo idleInfo = idleInfoService.selectById(id);
        idleInfo.setStatus(0);
        boolean res = idleInfoService.updateById(idleInfo);
        result.setResult(res);
        return result;
    }
}
