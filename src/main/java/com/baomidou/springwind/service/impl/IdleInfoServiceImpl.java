package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.service.IIdleInfoService;
import com.baomidou.springwind.service.IMessageService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 闲置信息 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class IdleInfoServiceImpl extends BaseServiceImpl<IdleInfoMapper, IdleInfo> implements IIdleInfoService {

    @Autowired
    private IdleInfoMapper idleInfoMapper;
    @Autowired
    private IdelPicMapper idelPicMapper;

    @Autowired
    private RentMapper rentMapper;

    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private CheckStatementMapper checkStatementMapper;
    @Autowired
    private IMessageService messageService;

    @Deprecated
    @Transactional
    @Override
    public boolean addRentInfo(Student student, IdleInfo info) throws Exception {
        Date now=new Date();
        info.setCreateDate(now);
        info.setSchoolId(student.getSchoolId());
        Integer row=idleInfoMapper.insert(info);
        /*if(row > 0){
            IdleRel rel=new IdleRel();
            rel.setRelId(UUIDUtil.getUUID());
            rel.setInfoId(info.getInfoId());
            rel.setUserId(student.getUserId());
            rel.setInfoType("1");
            int r=idleRelMapper.insert(rel);
            if(r > 0){
                return true;
            } else {
                throw new Exception("依赖创建异常");
            }
        }*/
        return false;
    }
    @Transactional
    @Override
    public int addIdleInfo(IdleInfo idleInfo) {
        List<IdelPic> idelPics = idleInfo.getPicList();
        Date now = new Date();
        idleInfo.setInfoId(UUIDUtil.getUUID());
        idleInfo.setCreateDate(now);
        idleInfo.setStatus(0);
        int count = idleInfoMapper.insert(idleInfo);
        if (count <= 0){
            return count;
        }

        //保存图片信息
        for(int i = 0;i<idelPics.size();i++){
            IdelPic pic = idelPics.get(i);
            pic.setPicId(UUIDUtil.getUUID());
            pic.setIdelId(idleInfo.getInfoId());
            idelPicMapper.insert(pic);
        }
        return count;
    }

    @Override
    public List<IdleInfo> selectByPage(IdleInfoExtend idleInfo) {
        List<IdleInfo> idleInfoList = idleInfoMapper.selectByPage(idleInfo);
        if(idleInfoList.size()<=0){
            return idleInfoList;
        }
        //获取所有的闲置ID
        List<String> ids = new ArrayList<>();
        for(int i = 0;i<idleInfoList.size();i++){
            IdleInfo info = idleInfoList.get(i);
            ids.add(info.getInfoId());
        }

        //查询图片
        List<IdelPic> picList = idelPicMapper.queryIdlePic(ids);
        for(int i = 0;i<idleInfoList.size();i++){
            IdleInfo info = idleInfoList.get(i);
            List<IdelPic> pics = new ArrayList<>();
            for(int j = 0; j<picList.size();j++){
                IdelPic pic = picList.get(j);
                if(pic.getIdelId().equals(info.getInfoId())){
                    pics.add(pic);
                }
            }
            info.setPicList(pics);
        }
        return idleInfoList;
    }

    @Transactional
    @Override
    public boolean closeIdle(IdleInfo idleInfo) throws Exception {
        IdleInfo info = idleInfoMapper.selectForUpdate(idleInfo);
        if(info.getStatus() != 0){
            throw new Exception("状态异常！");
        }
        Rent rent = new Rent();
        rent.setIdelId(idleInfo.getInfoId());
        //归还所有押金
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        for(int i=0;i<rentList.size();i++){
            Rent r= rentList.get(i);
            r.setStatus(2);
            Capital capital = capitalMapper.selectForUpdate(r.getUserId());
            capital.setCapital(capital.getCapital()+r.getLastRental());
            capitalMapper.updateById(capital);

            Date now = new Date();
            CheckStatement cs = new CheckStatement();
            cs.setStateId(UUIDUtil.getUUID());
            cs.setAmount(r.getLastRental());
            cs.setType(0);
            cs.setCreateDate(now);
            cs.setMemo("被拒返还押金");
            cs.setUserId(r.getUserId());
            checkStatementMapper.insert(cs);

            r.setLastRental(0f);
            rentMapper.updateById(r);
        }
        idleInfo.setStatus(4);
        idleInfoMapper.updateById(idleInfo);
        return true;
    }

    @Transactional
    @Override
    public boolean cancleRent(IdleInfo idleInfo) throws Exception {
        IdleInfo info = idleInfoMapper.selectForUpdate(idleInfo);
        if(info.getStatus() != 1 && info.getStatus() != 2 && info.getStatus() != 5 && info.getStatus() != 8){
            throw new Exception("状态异常！");
        }
        Rent rent = new Rent();
        rent.setIdelId(idleInfo.getInfoId());
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        for(int i = 0;i<rentList.size();i++){
            Rent r = rentList.get(i);
            //如果正在确认租户而没开始
            if(r.getStatus() == 1 || r.getStatus() == 5 || r.getStatus() == 9){
                info.setStatus(0);
                r.setStatus(2);
            } else if(r.getStatus() == 4 || r.getStatus() == 8){
                //如果正在租赁
                info.setStatus(3);
                r.setStatus(5);
            } else{
                continue;
            }
            idleInfoMapper.updateById(info);
            //归还押金
            Capital capital = capitalMapper.selectForUpdate(r.getUserId());
            capital.setCapital(capital.getCapital() + r.getLastRental());

            //记录进账单
            Date now = new Date();
            CheckStatement cs = new CheckStatement();
            cs.setStateId(UUIDUtil.getUUID());
            cs.setAmount(r.getLastRental());
            cs.setType(0);
            cs.setCreateDate(now);
            cs.setMemo("被拒返还押金");
            cs.setUserId(r.getUserId());
            checkStatementMapper.insert(cs);

            r.setLastRental(0f);
            rentMapper.updateById(r);
            capitalMapper.updateById(capital);
        }
        return true;
    }

    /**
     * 拒绝所有用户的租赁请求
     * @param idleInfo
     */
    protected void refuseAllUser(IdleInfo idleInfo){
        Rent rent = new Rent();
        rent.setIdelId(idleInfo.getInfoId());
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        for(int i = 0;i<rentList.size();i++){
            Rent r = rentList.get(i);
            r.setStatus(2);
            Capital capital = capitalMapper.selectForUpdate(r.getUserId());
            capital.setCapital(capital.getCapital()+r.getLastRental());

            Date now = new Date();
            CheckStatement cs = new CheckStatement();
            cs.setStateId(UUIDUtil.getUUID());
            cs.setAmount(r.getLastRental());
            cs.setType(0);
            cs.setCreateDate(now);
            cs.setMemo("被拒返还押金");
            cs.setUserId(r.getUserId());
            checkStatementMapper.insert(cs);

            r.setLastRental(0f);
            rentMapper.updateById(r);
            capitalMapper.updateById(capital);
        }
    }

    /**
     * 返还用户超出的租金
     */
    protected void backCapital(IdleInfo before,IdleInfo after) throws Exception {
        Rent rent = new Rent();
        rent.setIdelId(before.getInfoId());
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        for(int i = 0;i<rentList.size();i++){
            Rent r = rentList.get(i);
            float num = before.getDeposit() - after.getDeposit();
            if(r.getLastRental() < num || num < 0){
                throw new Exception("资金状态异常！");
            }
            r.setLastRental(r.getLastRental() - num);
            Capital capital = capitalMapper.selectForUpdate(r.getUserId());
            capital.setCapital(capital.getCapital() + num);

            Date now = new Date();
            CheckStatement cs = new CheckStatement();
            cs.setStateId(UUIDUtil.getUUID());
            cs.setAmount(r.getLastRental());
            cs.setType(0);
            cs.setCreateDate(now);
            cs.setMemo("被拒返还押金");
            cs.setUserId(r.getUserId());
            checkStatementMapper.insert(cs);

            rentMapper.updateById(r);
            capitalMapper.updateById(capital);
        }
    }

    @Transactional
    @Override
    public boolean updateIdleInfo(IdleInfo idleInfo) throws Exception {
        IdleInfo info = idleInfoMapper.selectForUpdate(idleInfo);
        if(!info.getUserId().equals(idleInfo.getUserId())){
            throw new IllegalAuthroiyException("您没有对此商品执行对应操作的权限！");
        }
        if(info.getStatus() != 0 && info.getStatus() != 1){
            throw new Exception("该商品状态不支持修改信息！");
        }

        //如果租金或押金提高，则先拒绝所有用户
        if(idleInfo.getDeposit() > info.getDeposit() || idleInfo.getRetal() > idleInfo.getRetal()){
            refuseAllUser(info);
        } else if(idleInfo.getDeposit() < info.getDeposit()){
            backCapital(info,idleInfo);
        }

        idleInfoMapper.updateById(idleInfo);
        List<IdelPic> pics = idleInfo.getPicList();
        if(pics == null ||pics.size()<=0){
            throw new Exception("商品图片不能为空！");
        }
        for(int i = 0;i<pics.size();i++){
            IdelPic pic = pics.get(i);
            if(pic.getBeanStatus()==null || pic.getBeanStatus().equals("")){
                continue;
            }
            if("add".equals(pic.getBeanStatus())){
                pic.setPicId(UUIDUtil.getUUID());
                pic.setIdelId(info.getInfoId());
                idelPicMapper.insert(pic);
            } else if("del".equals(pic.getBeanStatus())){
                idelPicMapper.deleteById(pic);
            }
        }
        return true;
    }

    @Override
    public boolean delIdleInfo(IdleInfo idleInfo) throws Exception {
        IdleInfo info = idleInfoMapper.selectById(idleInfo);
        if(!idleInfo.getUserId().equals(info.getUserId())){
            throw new IllegalAuthroiyException("您没有对此商品执行对应操作的权限！");
        }
        if(info.getStatus() != 3 && info.getStatus() != 4 && info.getStatus() != 100){
            throw new Exception("状态异常！");
        }
        info.setStatus(101);
        idleInfoMapper.updateById(info);
        return true;
    }

    @Override
    public IdleInfo findById(String id) {
        IdleInfo idleInfo = new IdleInfo();
        idleInfo.setInfoId(id);
        IdleInfo info = idleInfoMapper.findById(idleInfo);
        List<String> ids = new ArrayList<>();
        ids.add(info.getInfoId());
        List<IdelPic> pics = idelPicMapper.queryIdlePic(ids);
        info.setPicList(pics);
        return info;
    }

    private static final String picUrlPrefix = "https://schoolrent-1253946493.cos.ap-guangzhou.myqcloud.com";
    @Override
    public IdleInfo findFromServiceById(String id) {
        IdleInfo idleInfo = new IdleInfo();
        idleInfo.setInfoId(id);
        IdleInfo info = idleInfoMapper.findById(idleInfo);
        List<String> ids = new ArrayList<>();
        ids.add(info.getInfoId());
        List<IdelPic> pics = idelPicMapper.queryIdlePic(ids);
        for(int i = 0;i<pics.size();i++){
            IdelPic pic = pics.get(i);
            pic.setPicId(picUrlPrefix+pic.getPicUrl());
        }
        info.setPicList(pics);
        return info;
    }

    @Override
    public List<IdleInfo> queryListByPage(RequestInfo param,String type) {
        List<IdleInfo> idleInfoList = null;

        IdleInfo idleInfo = new IdleInfo();
        idleInfo.setIdelInfo(param.getSearchString());

        switch (type){
            case COMMENT:
                param.setAmmount(idleInfoMapper.getCount(idleInfo));
                idleInfoList = idleInfoMapper.queryForPage(param);
                break;
            case DEL:
                idleInfo.setStatus(100);
                param.setAmmount(idleInfoMapper.getCount(idleInfo));
                idleInfoList = idleInfoMapper.queryDel(param);
                break;
        }
        return idleInfoList;
    }

    @Transactional
    @Override
    public boolean delByManager(String id) {
        IdleInfo idleInfo = idleInfoMapper.selectById(id);
        idleInfo.setStatus(100);
        int count = idleInfoMapper.updateById(idleInfo);
        if(count <= 0){
            return false;
        }
        //归还所有押金
        Rent rent = new Rent();
        rent.setIdelId(id);
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        for(int i = 0;i<rentList.size();i++){
            Rent r = rentList.get(i);
            backRental(r);
        }

        return true;
    }

    @Transactional
    @Override
    public boolean addDestoryInfo(IdleInfo idleInfo) throws Exception {
        IdleInfo info = idleInfoMapper.selectById(idleInfo.getInfoId());

        //查询对应的租赁信息
        Rent param = new Rent();
        param.setIdelId(info.getInfoId());
        //状态为确认的信息
        param.setStatus(1);
        EntityWrapper<Rent> wrapper = new EntityWrapper<>();
        wrapper.setEntity(param);
        List<Rent> rents = rentMapper.selectList(wrapper);
        if(rents.size()!=1){
            throw new Exception("找不到对应的租赁信息！");
        }

        Rent rent = rents.get(0);
        //如果商品并非处于确定用户阶段
        if(info.getStatus() != 1 || rent.getStatus() != 1){
            throw new Exception("状态异常！");
        }

        info.setDestoryInfo(idleInfo.getDestoryInfo());
        //设置状态为提交损毁信息
        info.setStatus(5);
        rent.setStatus(9);

        idleInfoMapper.updateById(info);
        rentMapper.updateById(rent);

        StringBuffer title = new StringBuffer("您的闲置\"");
        title.append(info.getTitle()).append("\"被填写损毁信息，请在及时处理是否开始租赁");
        StringBuffer content =new StringBuffer("您的闲置\"");
        content.append(info.getTitle()).append("\"被填写损毁信息，内容为：\"");
        content.append(info.getDestoryInfo()).append("\"，请在及时处理是否开始租赁");
        messageService.pushMessage(title.toString(),content.toString(),info.getUserId());
        return true;
    }

    /**
     * 归还押金
     * @param rent
     */
    private void backRental(Rent rent){
        Capital capital = capitalMapper.selectForUpdate(rent.getUserId());
        capital.setCapital(capital.getCapital() + rent.getLastRental());
        rent.setLastRental(0f);
        capitalMapper.updateById(capital);
        rentMapper.updateById(rent);
    }
}
