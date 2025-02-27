package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.Exception.DataBaseUpdatExcepton;
import com.baomidou.springwind.Exception.IllegalAuthroiyException;
import com.baomidou.springwind.Exception.MoneyNotEnoughException;
import com.baomidou.springwind.Exception.PassWordNotSameException;
import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.*;
import com.baomidou.springwind.service.ICheckStatementService;
import com.baomidou.springwind.service.IMessageService;
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
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private IdelPicMapper idelPicMapper;

    @Autowired
    private CheckStatementMapper checkStatementMapper;

    @Autowired
    private ICheckStatementService checkStatementService;

    @Autowired
    private IMessageService messageService;

    @Override
    @Transactional
    public boolean addRent(Rent rent,Student student) throws Exception {
        Date now = new Date();
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
        if(idleInfo.getStatus() != 0 ){
            throw new Exception("您来晚了一步，商品已经被租走....");
        }
        Capital capital = capitalMapper.selectForUpdate(student.getUserId());
        if(capital.getCapital()<idleInfo.getDeposit()){
            throw new MoneyNotEnoughException("余额不足");
        }
        capital.setCapital(capital.getCapital() - idleInfo.getDeposit());
        capitalMapper.update(capital);

        CheckStatement cs = new CheckStatement();
        cs.setStateId(UUIDUtil.getUUID());
        cs.setAmount(idleInfo.getDeposit());
        cs.setType(1);
        cs.setCreateDate(now);
        cs.setMemo("申请租赁暂扣押金");
        cs.setUserId(student.getUserId());
        checkStatementMapper.insert(cs);

        rent.setLastRental(idleInfo.getDeposit());
        rent.setStatus(0);
        rent.setRentId(UUIDUtil.getUUID());
        rent.setCreateTime(now);
        rentMapper.insert(rent);
        addRentMessage(student,idleInfo);
        return true;
    }

    private void addRentMessage(Student student,IdleInfo idleInfo){
        StringBuffer sb = new StringBuffer("@");
        sb.append(student.getUserName()).append("想要租您发布的\"").append(idleInfo.getTitle()).append("\"。");
        messageService.pushMessage("您发布的商品有人请求了租赁",sb.toString(),idleInfo.getUserId());
    }

    @Deprecated
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

    @Transactional
    @Override
    public boolean agreeRent(Student student,Rent rent) throws Exception {
        IdleInfo param = new IdleInfo();
        param.setInfoId(rent.getIdelId());
        IdleInfo idleInfo = idleInfoMapper.selectForUpdate(param);

        if(!idleInfo.getUserId().equals(student.getUserId())){
            throw new Exception("您没有对该商品操作的权限！");
        }

        if(idleInfo.getStatus() != 0 ){
            throw new Exception("商品已经被租走....");
        }
        idleInfo.setStatus(1);
        idleInfoMapper.updateById(idleInfo);
        Rent rentParam = new Rent();
        rentParam.setIdelId(rent.getIdelId());
        List<Rent> rentList = rentMapper.selectForUpdate(rentParam);
        Date now = new Date();
        for(int i = 0;i<rentList.size();i++){
            Rent r = rentList.get(i);
            //如果是同一条则直接修改状态为同意
            if(r.getRentId().equals(rent.getRentId())){
                r.setStatus(1);
                r.setStartDate(now);
                StringBuffer sb = new StringBuffer("您想租的\"");
                sb.append(idleInfo.getTitle()).append("\"发布者已经同意您的请求，请等待发布者和您的联系。");
                messageService.pushMessage("发布者同意您的租赁请求",sb.toString(),r.getUserId());
            } else {
                //如果不同的话修改状态为拒绝并返还押金
                float last = r.getLastRental();
                r.setStatus(2);
                Capital capital = capitalMapper.selectForUpdate(r.getUserId());
                capital.setCapital(capital.getCapital() + r.getLastRental());
                r.setLastRental(0f);
                capitalMapper.update(capital);

                CheckStatement cs = new CheckStatement();
                cs.setStateId(UUIDUtil.getUUID());
                cs.setAmount(last);
                cs.setType(0);
                cs.setCreateDate(now);
                cs.setMemo("拒绝返还押金");
                cs.setUserId(r.getUserId());
                checkStatementMapper.insert(cs);

                StringBuffer sb = new StringBuffer("您想租的\"");
                sb.append(idleInfo.getTitle()).append("\"发布者拒绝了您的请求，您支付的押金已经返回，请及时确认，感谢您对校园租的支持。");
                messageService.pushMessage("发布者拒绝了您的请求",sb.toString(),r.getUserId());
            }
            rentMapper.updateById(r);
        }
        return true;
    }

    @Override
    public List<Rent> selectForPage(RentExtend rentExtend) {
        List<Rent> rentList = rentMapper.selectForPage(rentExtend);
        if(rentList.size() == 0){
            return rentList;
        }
        //获取所有的闲置ID
        List<String> ids = new ArrayList<>();
        for(int i = 0;i<rentList.size();i++){
            Rent rent = rentList.get(i);
            IdleInfo idleInfo = rent.getIdleInfo();
            ids.add(idleInfo.getInfoId());
        }
        //查询图片
        List<IdelPic> picList = idelPicMapper.queryIdlePic(ids);
        for(int i = 0;i<picList.size();i++){
            IdelPic pic = picList.get(i);
            for(int j = 0; j<rentList.size();j++){
                Rent rent = rentList.get(j);
                IdleInfo idleInfo = rent.getIdleInfo();
                if (idleInfo.getInfoId().equals(pic.getIdelId())){
                    List<IdelPic> idelPics = new ArrayList<>();
                    idelPics.add(pic);
                    idleInfo.setPicList(idelPics);
                }
            }
        }
        return rentList;
    }

    @Transactional
    @Override
    public boolean cancelRent(Student student, Rent rent) throws IllegalAuthroiyException {
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        Rent r = rentList.get(0);
        Capital capital = capitalMapper.selectForUpdate(r.getUserId());
        IdleInfo param = new IdleInfo();
        param.setInfoId(r.getIdelId());
        IdleInfo idleInfo = idleInfoMapper.selectForUpdate(param);

        //申请返还
        if(r.getStatus() == 4){
            r.setStatus(8);
            idleInfo.setStatus(8);
            rentMapper.updateById(r);
            idleInfoMapper.updateById(idleInfo);

            StringBuffer sb = new StringBuffer("@");
            sb.append(student.getUserName()).append("想返还您的\"").append(idleInfo.getTitle()).append("\"");
            sb.append("。请及时联系租赁方");
            messageService.pushMessage("返回申请",sb.toString(),idleInfo.getUserId());
            return true;
        }

        if(r == null || (r.getStatus()!=0 && r.getStatus()!=1 && r.getStatus()!= 9)){
            throw new IllegalStateException("当前状态不支持取消！");
        }
        if (!r.getUserId().equals(r.getUserId())) {
            throw new IllegalAuthroiyException();
        }

        CheckStatement cs = new CheckStatement();
        /*if(r.getStatus() == 0 || r.getStatus() == 1 || r.getStatus()!= 9) {*/
        r.setStatus(6);
        cs.setMemo("取消租赁返还押金");
        idleInfo.setStatus(0);
        /*} else {
            //好像没有意义？忘记当时写的目的了
            r.setStatus(3);
            cs.setMemo("拒绝租赁返还押金");
        }*/

        cs.setAmount(r.getLastRental());

        capital.setCapital(capital.getCapital()+r.getLastRental());
        r.setLastRental(0f);

        Date now = new Date();
        cs.setStateId(UUIDUtil.getUUID());
        cs.setType(0);
        cs.setCreateDate(now);
        cs.setUserId(r.getUserId());
        checkStatementMapper.insert(cs);

        rentMapper.updateById(r);
        capitalMapper.updateById(capital);
        idleInfoMapper.updateById(idleInfo);
        return true;
    }

    @Transactional
    @Override
    public boolean startRent(Student student, Rent rent) throws Exception {
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        if(rentList.size()<=0){
            throw new Exception("找不到对应的租赁信息");
        }
        Rent r = rentList.get(0);
        if(!r.getUserId().equals(student.getUserId())){
            throw new IllegalAuthroiyException("您没有对应信息的操作权限！");
        }
        if(r.getStatus()!=1&&r.getStatus()!=9){
            throw new Exception("状态转换异常");
        }
        //开始时自动扣除费用到租出用户的账号上
        IdleInfo param = new IdleInfo();
        param.setInfoId(r.getIdelId());
        IdleInfo idleInfo = idleInfoMapper.selectForUpdate(param);
        Capital capital = capitalMapper.selectForUpdate(idleInfo.getUserId());
        float rental = idleInfo.getRetal();
        capital.setCapital(capital.getCapital()+rental);
        r.setLastRental(r.getLastRental() - rental);
        r.setStatus(4);
        idleInfo.setStatus(2);
        rentMapper.updateById(r);
        capitalMapper.updateById(capital);
        idleInfoMapper.updateById(idleInfo);

        Date now = new Date();
        CheckStatement cs = new CheckStatement();
        cs.setStateId(UUIDUtil.getUUID());
        cs.setAmount(rental);
        cs.setType(0);
        cs.setCreateDate(now);
        cs.setMemo("租赁资金结算");
        cs.setUserId(idleInfo.getUserId());
        checkStatementMapper.insert(cs);

        StringBuffer sb = new StringBuffer("@");
        sb.append(student.getUserName()).append("已经确认收到租赁物品，租赁开始");
        messageService.pushMessage("租赁方已经确认租赁开始",sb.toString(),idleInfo.getUserId());
        return true;
    }

    @Transactional
    @Override
    public boolean startRent(Student student, IdleInfo idleInfo) throws Exception {
        IdleInfo info = idleInfoMapper.selectForUpdate(idleInfo);
        if(info.getStatus() != 5){
            throw new Exception("状态异常！");
        }
        Rent rent = new Rent();
        rent.setIdelId(idleInfo.getInfoId());
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        if(rentList == null || rentList.size() != 1){
            throw new Exception("找不到对应的数据！");
        }

        Rent r = rentList.get(0);
        if(!info.getUserId().equals(student.getUserId())){
            throw new IllegalAuthroiyException("您没有对应信息的操作权限！");
        }
        //开始时自动扣除费用到租出用户的账号上
        Capital capital = capitalMapper.selectForUpdate(info.getUserId());
        float rental = info.getRetal();
        capital.setCapital(capital.getCapital()+rental);
        r.setLastRental(r.getLastRental() - rental);
        r.setStatus(4);
        info.setStatus(2);
        rentMapper.updateById(r);
        capitalMapper.updateById(capital);
        idleInfoMapper.updateById(info);

        Date now = new Date();
        CheckStatement cs = new CheckStatement();
        cs.setStateId(UUIDUtil.getUUID());
        cs.setAmount(rental);
        cs.setType(0);
        cs.setCreateDate(now);
        cs.setMemo("租赁资金结算");
        cs.setUserId(info.getUserId());
        checkStatementMapper.insert(cs);

        StringBuffer sb = new StringBuffer("@");
        sb.append(student.getUserName()).append("已经确认收到租赁物品，租赁开始");
        messageService.pushMessage("租赁方已经确认租赁开始",sb.toString(),info.getUserId());
        return true;
    }

    @Override
    public boolean delRent(Student student, Rent rent) throws Exception {
        Rent r = rentMapper.selectById(rent);
        if(!r.getUserId().equals(student.getUserId())){
            throw new Exception("您没有对该信息操作的权限！");
        }
        r.setStatus(101);
        rentMapper.updateById(r);
        return true;
    }

    @Transactional
    @Override
    public boolean disagreeRent(Student student,Rent rent) throws Exception{
        IdleInfo param = new IdleInfo();
        Rent r = rentMapper.selectForUpdateById(rent);
        param.setInfoId(r.getIdelId());
        IdleInfo idleInfo = idleInfoMapper.selectForUpdate(param);

        if(!idleInfo.getUserId().equals(student.getUserId())){
            throw new IllegalAuthroiyException("你没有执行该操作的权限");
        }
        if(r.getStatus()!=0&&r.getStatus()!=1){
            throw new IllegalStateException("状态异常！");
        }
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

        StringBuffer sb = new StringBuffer("@");
        sb.append(student.getUserName()).append("拒绝了您对\"").append(idleInfo.getTitle()).append("\"");
        sb.append("的租赁请求。您的押金已经退还。");
        messageService.pushMessage("发布者拒绝了您的租赁请求",sb.toString(),r.getUserId());
        return true;
    }

    @Override
    public boolean finishRent(Student student, Rent rent) throws IllegalAuthroiyException {
        List<Rent> rentList = rentMapper.selectForUpdate(rent);
        Rent r = rentList.get(0);
        Capital capital = capitalMapper.selectForUpdate(r.getUserId());
        IdleInfo param = new IdleInfo();
        param.setInfoId(r.getIdelId());
        IdleInfo idleInfo = idleInfoMapper.selectForUpdate(param);

        if(r == null || (r.getStatus()!=4)){
            throw new IllegalStateException("当前状态不支持该操作！");
        }
        if (!idleInfo.getUserId().equals(r.getUserId())) {
            throw new IllegalAuthroiyException();
        }

        CheckStatement cs = new CheckStatement();
        r.setStatus(5);
        cs.setMemo("完成租赁返还押金");

        cs.setAmount(r.getLastRental());

        capital.setCapital(capital.getCapital()+r.getLastRental());
        r.setLastRental(0f);
        idleInfo.setStatus(0);

        Date now = new Date();
        cs.setStateId(UUIDUtil.getUUID());
        cs.setType(0);
        cs.setCreateDate(now);
        cs.setUserId(r.getUserId());
        checkStatementMapper.insert(cs);

        rentMapper.updateById(r);
        capitalMapper.updateById(capital);
        idleInfoMapper.updateById(idleInfo);

        StringBuffer sb = new StringBuffer("@");
        sb.append(student.getUserName()).append("已经确定结束\"").append(idleInfo.getTitle()).append("\"");
        sb.append("的租赁。您的剩余押金已经退还。");
        messageService.pushMessage("租赁完成",sb.toString(),r.getUserId());
        return true;
    }

    /**
     * 每天计算租赁剩余押金
     */
    @Transactional
    @Override
    public void calRentalDaily() {
        List<Rent> rentList = rentMapper.dailyUpdate();
        for(int i = 0;i<rentList.size();i++){
            Rent rent = rentList.get(i);
            IdleInfo idleInfo = rent.getIdleInfo();

            //如果剩余租金小于或等于每日租金，则直接结束租赁
            if(rent.getLastRental()<= idleInfo.getRetal()){
                finishRent(rent);
            } else {
                //结算每日租金到发布者账号
                calRental(rent);
            }
        }
    }

    @Override
    public int getRentCount(String id) {
        Rent rent = new Rent();
        rent.setIdelId(id);
        return rentMapper.getRentCount(rent);
    }

    private void calRental(Rent rent){
        IdleInfo idleInfo = rent.getIdleInfo();

        Capital capital = capitalMapper.selectForUpdate(idleInfo.getUserId());
        capital.setCapital(capital.getCapital() + idleInfo.getRetal());

        rent.setLastRental(rent.getLastRental() - idleInfo.getRetal());
        rentMapper.updateById(rent);
        capitalMapper.updateById(capital);

        //添加到账单中
        checkStatementService.addCheckStatement("租赁资金结算",idleInfo.getRetal(),0,idleInfo.getUserId());
    }

    private void finishRent(Rent rent){
        float amount = rent.getLastRental();

        rent.setLastRental(0f);
        //设置为租赁完成
        rent.setStatus(5);

        //设置商品状态为租赁完成
        IdleInfo idleInfo = idleInfoMapper.selectById(rent.getIdelId());
        idleInfo.setStatus(3);

        //设置账号余额
        Capital capital = capitalMapper.selectForUpdate(idleInfo.getUserId());
        capital.setCapital(capital.getCapital() + amount);

        rentMapper.updateById(rent);
        idleInfoMapper.updateById(idleInfo);
        //添加到账单中
        checkStatementService.addCheckStatement("租赁资金结算",amount,0,idleInfo.getUserId());
    }
}
