package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.Exception.DataBaseUpdatExcepton;
import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.Exception.MoneyNotEnoughException;
import com.baomidou.springwind.Exception.PassWordNotSameException;
import com.baomidou.springwind.entity.Capital;
import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.entity.Rent;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.CapitalMapper;
import com.baomidou.springwind.mapper.IdleInfoMapper;
import com.baomidou.springwind.mapper.RentMapper;
import com.baomidou.springwind.mapper.StudentMapper;
import com.baomidou.springwind.service.IRentService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.RSAUtil;
import com.baomidou.springwind.utils.SHA1Util;
import com.baomidou.springwind.utils.UUIDUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.List;

/**
 * <p>
 * 租赁信息关联表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class RentServiceImpl extends BaseServiceImpl<RentMapper, Rent> implements IRentService {

    @Autowired
    private IdleInfoMapper idleInfoMapper;
    @Autowired
    private RentMapper rentMapper;
    @Autowired
    private CapitalMapper capitalMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    @Transactional
    public boolean addRent(Rent rent,Student student) throws MoneyNotEnoughException {
        Student s = studentMapper.selectById(student.getUserId());
        //私钥解密
        PrivateKey privateKey=RSAUtil.restorePrivateKey(RSAUtil.getKeys().get(RSAUtil.PRIVATE_KEY));
        String p = RSAUtil.RSADecode(privateKey, Base64.decodeBase64(rent.getPayPassword()));
        p = SHA1Util.encode(p);
        if(!s.getPayPassword().equals(p)){
            throw new PassWordNotSameException();
        }
        IdleInfo param = new IdleInfo();
        param.setInfoId(rent.getIdelId());
        IdleInfo idleInfo = idleInfoMapper.selectForUpdate(param);
        Capital capital = capitalMapper.selectForUpdate(student.getUserId());
        if(capital.getCapital()<idleInfo.getDeposit()){
            throw new MoneyNotEnoughException("余额不足");
        }
        capital.setCapital(capital.getCapital() - idleInfo.getDeposit());
        capitalMapper.update(capital);
        rent.setLastRental(idleInfo.getDeposit());
        rent.setStatus(0);
        rent.setRentId(UUIDUtil.getUUID());
        rentMapper.insert(rent);
        return true;
    }

    @Transactional
    @Override
    public boolean responseRent(String rentId, Integer response,String studentId) throws IllegalAuthroiyException, DataBaseUpdatExcepton {
        Rent rent=rentMapper.selectById(rentId);
        //如果是重复应答，则直接返回
        if(rent.getStatus() == response){
            return true;
        }
        IdleInfo idleInfo=idleInfoMapper.selectById(rent.getIdelId());
        //检查用户是否具有权限
        if(!idleInfo.getUserId().equals(studentId)){
            throw new IllegalAuthroiyException();
        }

        int row;
        switch (response){
            case 0:
                break;
            case 1:  //同意
                //如果租赁信息已经做出响应，或者商品已经被租赁，则抛出异常
                if(rent.getStatus() != 0){
                    throw new IllegalStateException("状态转换异常");
                }
                if(idleInfo.getStatus() != 0){
                    throw new IllegalStateException("该闲置已经被租赁");
                }

                rent.setStatus(1);
                idleInfo.setStatus(1);

                //转账到系统账号
                toSystemCapital(rent,idleInfo);

                //更新数据库
                updateRentAndIdleInfo(rent,idleInfo);
                break;
            case 2: //拒绝
                //如果租赁信息已经做出响应，则抛出异常
                if(rent.getStatus() != 0){
                    throw new IllegalStateException("状态转换异常");
                }
                rent.setStatus(2);

                //更新数据库
                row=rentMapper.updateById(rent);
                if(row<=0){
                    throw new DataBaseUpdatExcepton();
                }
                break;
            case 3: //同意后拒绝
                if(rent.getStatus() != 1){
                    throw new IllegalStateException("状态转换异常");
                }
                rollBackCapital(rent,idleInfo);
                rent.setStatus(3);
                idleInfo.setStatus(0);
                //更新数据库
                updateRentAndIdleInfo(rent,idleInfo);
                break;
            case 4: //开始
                if(rent.getStatus() != 1){
                    throw new IllegalStateException("状态转换异常");
                }
                //设置为开始
                rent.setStatus(4);
                //设置为正在租赁
                idleInfo.setStatus(2);
                //更新数据库
                updateRentAndIdleInfo(rent,idleInfo);
                break;
            case 5: //完成
                if(rent.getStatus() != 4 || idleInfo.getStatus() != 2){
                    throw new IllegalStateException("状态转换异常");
                }
                //设置为完成
                rent.setStatus(5);
                idleInfo.setStatus(3);
                transfer(rent,idleInfo);
                //更新数据库
                updateRentAndIdleInfo(rent,idleInfo);
                break;
        }
        return true;
    }

    @Transactional
    @Override
    public boolean responseFromCustomer(Rent rent, Student student) throws IllegalAuthroiyException, DataBaseUpdatExcepton {
        switch (rent.getStatus()){
            case 6:
                Rent r = rentMapper.selectById(rent.getRentId());
                if(r.getStatus() == 0){
                    rentMapper.updateById(rent);
                } else if(r.getStatus() == 1){
                    IdleInfo idleInfo = idleInfoMapper.selectById(rent.getIdelId());
                    idleInfo.setStatus(0);
                    updateRentAndIdleInfo(rent,idleInfo);
                } else {
                    throw new IllegalAuthroiyException("状态转换异常");
                }
                break;
            default:
                throw new IllegalStateException("状态转换异常");
        }
        return false;
    }

    @Override
    public List<Rent> queryList(IdleInfo idleInfo) {
        return rentMapper.queryList(idleInfo);
    }

    @Override
    public int getCountOfRequest(Rent rent) {
        Integer res = rentMapper.getCountOfRequest(rent);
        return res == null ? 0 : res;
    }

    /**
     * 同时更新租赁关系和闲置商品表
     * @param rent
     * @param idleInfo
     * @return
     * @throws DataBaseUpdatExcepton
     */
    private boolean updateRentAndIdleInfo(Rent rent,IdleInfo idleInfo) throws DataBaseUpdatExcepton {
        int row;
        row=rentMapper.updateById(rent);
        if(row<=0){
            rollBackCapital(rent,idleInfo);
            throw new DataBaseUpdatExcepton();
        }
        row=idleInfoMapper.updateById(idleInfo);
        if(row<=0){
            rollBackCapital(rent,idleInfo);
            throw new DataBaseUpdatExcepton();
        }
        return true;
    }

    /**
     * 转账到系统账号
     * @param rent
     * @param info
     * @return
     */
    private boolean toSystemCapital(Rent  rent,IdleInfo info){
        return false;
    }

    /**
     * 转账到指定账号
     * @param rent
     * @param idleInfo
     * @return
     */
    private boolean transfer(Rent rent,IdleInfo idleInfo){
        return false;
    }

    /**
     * 系统账号押金回退
     * @return
     */
    private boolean rollBackCapital(Rent rent,IdleInfo idleInfo){
        return false;
    }

    @Override
    public Rent getCanRent(Rent rent){
        List<Rent> rentList = rentMapper.getCanRent(rent);

        Rent r = null;
        if(rentList!=null && rentList.size()>0) {
            r = rentList.get(0);
        } else {
            r = new Rent();
            r.setStatus(7);
        }
        return r;
    }
}
