package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.IdelPicMapper;
import com.baomidou.springwind.mapper.IdleInfoMapper;
import com.baomidou.springwind.mapper.OrderComplianMapper;
import com.baomidou.springwind.mapper.RentMapper;
import com.baomidou.springwind.service.IMessageService;
import com.baomidou.springwind.service.IOrderComplianService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单投诉 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
@Service
public class OrderComplianServiceImpl extends BaseServiceImpl<OrderComplianMapper, OrderComplian> implements IOrderComplianService {

    @Autowired
    private OrderComplianMapper mapper;
    @Autowired
    private RentMapper rentMapper;
    @Autowired
    private IdleInfoMapper idleInfoMapper;

    @Autowired
    private IdelPicMapper idelPicMapper;

    @Autowired
    private IMessageService messageService;

    @Transactional
    @Override
    public boolean addComplain(OrderComplian complian, Student student) throws Exception {
        IdleInfo idleInfo = idleInfoMapper.selectById(complian.getInfoId());

        //查找对应的租赁信息
        Rent param = new Rent();
        param.setIdelId(idleInfo.getInfoId());
        param.setStatus(4);
        EntityWrapper<Rent> wrapper = new EntityWrapper<>(param);
        List<Rent> rentList = rentMapper.selectList(wrapper);
        if(rentList == null || rentList.size()!= 1){
            throw new Exception("找不到对应的信息！");
        }
        Rent rent = rentList.get(0);

        if(!student.getUserId().equals(idleInfo.getUserId())){
            throw new Exception("您没有对应的权限!");
        }

        //如果剩余租金小于申请额，则直接填写剩余额
        if(rent.getLastRental()< complian.getMoney()){
            complian.setMoney(rent.getLastRental());
        }

        try {
            complian.setComplainId(UUIDUtil.getUUID());
            complian.setContext(URLDecoder.decode(complian.getContext(),"utf-8"));
            complian.setUserId(student.getUserId());
            complian.setComplainDate(new Date());
            complian.setStatus(0);
            mapper.insert(complian);

            //插入图片
            if(complian.getUrls() != null){
                List<String> urls = complian.getUrls();
                for(int i = 0;i< urls.size();i++){
                    IdelPic idelPic = new IdelPic();
                    idelPic.setPicId(UUIDUtil.getUUID());
                    idelPic.setIdelId(complian.getComplainId());
                    idelPic.setPicUrl(urls.get(i));
                    idelPicMapper.insert(idelPic);
                }
            }

            //设置为提交赔偿
            rent.setStatus(10);
            idleInfo.setStatus(10);

            rentMapper.updateById(rent);
            idleInfoMapper.updateById(idleInfo);

            StringBuffer content = new StringBuffer("您租赁的商品\"");
            content.append(idleInfo.getTitle()).append("\"卖家发出了赔偿请求,赔偿金额为：");
            content.append(complian.getMoney()).append("。在解决前请保证联系方式可用，避免不必要损失。感谢您使用校园租！");
            messageService.pushMessage("您租赁的商品卖家发出了赔偿请求",content.toString(),rent.getUserId());
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
