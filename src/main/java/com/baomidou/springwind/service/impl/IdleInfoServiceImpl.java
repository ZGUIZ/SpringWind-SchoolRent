package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.CapitalMapper;
import com.baomidou.springwind.mapper.IdelPicMapper;
import com.baomidou.springwind.mapper.IdleInfoMapper;
import com.baomidou.springwind.mapper.RentMapper;
import com.baomidou.springwind.service.IIdleInfoService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(info.getStatus() != 1 && info.getStatus() != 2){
            throw new Exception("状态异常！");
        }
        Rent rent = new Rent();
        rent.setIdelId(idleInfo.getInfoId());
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        for(int i = 0;i<rentList.size();i++){
            Rent r = rentList.get(i);
            //如果正在确认租户而没开始
            if(r.getStatus() == 1 ){
                info.setStatus(0);
                r.setStatus(2);
            } else if(r.getStatus() == 2){
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
}
