package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.service.ICheckStatementService;
import com.baomidou.springwind.service.IMessageService;
import com.baomidou.springwind.service.IOrderComplianService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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

    @Autowired
    private IdelPicMapper picMapper;

    @Autowired
    private CapitalMapper capitalMapper;

    @Autowired
    private CheckStatementMapper checkStatementMapper;

    @Override
    @Transactional
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
                List<IdelPic> urls = complian.getUrls();
                for(int i = 0;i< urls.size();i++){
                    IdelPic idelPic = new IdelPic();
                    idelPic.setPicId(UUIDUtil.getUUID());
                    idelPic.setIdelId(complian.getComplainId());
                    idelPic.setPicUrl(urls.get(i).getPicUrl());
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

    @Override
    public List<OrderComplian> queryListByPage(RequestInfo<OrderComplian> page) {
        page.setAmmount(mapper.getCount(page));
        return mapper.queryForPage(page);
    }

    @Override
    public OrderComplian queryById(String id) {
        OrderComplian complain = mapper.selectById(id);
        IdleInfo idleInfo = idleInfoMapper.selectById(complain.getInfoId());
        List<String> ids = new ArrayList<>();
        ids.add(complain.getComplainId());
        List<IdelPic> pics = picMapper.queryIdlePic(ids);
        complain.setUrls(pics);
        complain.setIdleInfo(idleInfo);
        return complain;
    }

    @Transactional
    @Override
    public boolean agree(Manager manager,String id, float money) throws Exception {
        OrderComplian complian = mapper.selectById(id);
        IdleInfo idleInfo = idleInfoMapper.selectById(complian.getInfoId());

        //获取正在租赁的信息
        Rent param = new Rent();
        param.setIdelId(idleInfo.getInfoId());
        param.setStatus(10);
        EntityWrapper<Rent> wrapper = new EntityWrapper<>(param);
        List<Rent> rents = rentMapper.selectList(wrapper);

        //假设找不到对应的数据，正常会有唯一一条数据
        if(rents == null || rents.size()!=1){
            throw new Exception("找不到对应的数据");
        }
        Rent rent = rents.get(0);

        //将赔偿金转移到发布者账户
        addComplainMoney(idleInfo.getUserId(),money,rent);
        //将剩余资金返还
        returnBackRental(rent.getUserId(),rent);

        //修改租赁状态为已完成
        idleInfo.setStatus(3);
        rent.setStatus(5);
        idleInfoMapper.updateById(idleInfo);
        rentMapper.updateById(rent);

        //设置为已处理
        complian.setStatus(1);
        complian.setResponsePerson(manager.getUserId());
        mapper.updateById(complian);

        //推送双方用户
        StringBuffer pusherContext = new StringBuffer("您对发布的《").append(idleInfo.getTitle())
                .append("》申请的赔偿请求已经通过。赔偿金额为：").append(money).append("，请及时查收!感谢您对校园租的支持。");
        messageService.pushMessage("您申请的赔偿请求通过",pusherContext.toString(),idleInfo.getUserId());
        StringBuffer rentContext = new StringBuffer("您租赁的商品《").append(idleInfo.getTitle()).append("》剩余租金已经返还，请及时查收!")
                .append("感谢您对校园租的支持。");
        messageService.pushMessage("您租赁商品的剩余押金已经返还",rentContext.toString(),rent.getUserId());
        return true;
    }

    @Override
    public boolean disagree(Manager manager, String id, float money) throws Exception {
        OrderComplian complian = mapper.selectById(id);
        IdleInfo idleInfo = idleInfoMapper.selectById(complian.getInfoId());

        //获取正在租赁的信息
        Rent param = new Rent();
        param.setIdelId(idleInfo.getInfoId());
        param.setStatus(10);
        EntityWrapper<Rent> wrapper = new EntityWrapper<>(param);
        List<Rent> rents = rentMapper.selectList(wrapper);

        //假设找不到对应的数据，正常会有唯一一条数据
        if(rents == null || rents.size()!=1){
            throw new Exception("找不到对应的数据");
        }
        Rent rent = rents.get(0);
        //返还用户押金
        returnBackRental(rent.getUserId(),rent);

        //修改租赁状态为已完成
        idleInfo.setStatus(3);
        rent.setStatus(5);
        idleInfoMapper.updateById(idleInfo);
        rentMapper.updateById(rent);

        //设置为已处理
        complian.setStatus(1);
        complian.setResponsePerson(manager.getUserId());
        mapper.updateById(complian);

        //推送双方用户
        StringBuffer pusherContext = new StringBuffer("您对发布的《").append(idleInfo.getTitle())
                .append("》申请的赔偿请求未通过通过。感谢您对校园租的支持。");
        messageService.pushMessage("您申请的赔偿请求通过",pusherContext.toString(),idleInfo.getUserId());
        StringBuffer rentContext = new StringBuffer("您租赁的商品《").append(idleInfo.getTitle()).append("》剩余租金已经返还，请及时查收!")
                .append("感谢您对校园租的支持。");
        messageService.pushMessage("您租赁商品的剩余押金已经返还",rentContext.toString(),rent.getUserId());
        return true;
    }

    /**
     * 添加赔偿金到指定账号
     */
    private void addComplainMoney(String userId,float money,Rent rent){
        //获取发布者账号资金信息
        Capital pusherCapital = capitalMapper.selectForUpdate(userId);
        //赔偿金额到发布者账号
        pusherCapital.setCapital(pusherCapital.getCapital()+money);
        capitalMapper.updateById(pusherCapital);
        rent.setLastRental(rent.getLastRental() - money);
        //记录到账单
        CheckStatement checkStatement = new CheckStatement();
        checkStatement.setUserId(userId);
        checkStatement.setStateId(UUIDUtil.getUUID());
        checkStatement.setAmount(money);
        checkStatement.setMemo("赔偿金");
        checkStatement.setCreateDate(new Date());
        checkStatement.setType(ICheckStatementService.ADD);
        checkStatementMapper.insert(checkStatement);
    }

    private void returnBackRental(String userId,Rent rent){
        float last = rent.getLastRental();
        //获取租赁者账号资金信息
        Capital renterCapital = capitalMapper.selectForUpdate(userId);

        //扣除赔偿金额后将剩余租金返回租赁者账号
        renterCapital.setCapital(renterCapital.getCapital() + last);
        capitalMapper.updateById(renterCapital);
        rent.setLastRental(0f);

        CheckStatement checkStatement = new CheckStatement();
        checkStatement.setUserId(userId);
        checkStatement.setStateId(UUIDUtil.getUUID());
        checkStatement.setAmount(last);
        checkStatement.setMemo("押金返还");
        checkStatement.setCreateDate(new Date());
        checkStatement.setType(ICheckStatementService.ADD);
        checkStatementMapper.insert(checkStatement);
    }
}
