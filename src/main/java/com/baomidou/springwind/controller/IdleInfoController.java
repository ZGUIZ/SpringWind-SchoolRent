package com.baomidou.springwind.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.service.IIdleInfoService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

}
