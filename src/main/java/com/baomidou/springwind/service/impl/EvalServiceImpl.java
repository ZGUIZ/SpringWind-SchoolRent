package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.entity.*;
import com.baomidou.springwind.mapper.EvalMapper;
import com.baomidou.springwind.mapper.IdelPicMapper;
import com.baomidou.springwind.mapper.IdleInfoMapper;
import com.baomidou.springwind.mapper.StudentMapper;
import com.baomidou.springwind.service.IEvalService;
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
 * 评价信息 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-12-16
 */
@Service
public class EvalServiceImpl extends BaseServiceImpl<EvalMapper, Eval> implements IEvalService {

    @Autowired
    private EvalMapper mapper;

    @Autowired
    private IdelPicMapper picMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private IdleInfoMapper idleInfoMapper;

    @Transactional
    @Override
    public int add(Student student, Eval eval) {
        eval.setEvalId(UUIDUtil.getUUID());
        eval.setEvalDate(new Date());
        eval.setUserId(student.getUserId());
        eval.setStatus(0);

        IdleInfo idleInfo = idleInfoMapper.selectById(eval.getIdleId());

        if(eval.getLevel()!=3){

            Student param = new Student();
            param.setUserId(idleInfo.getUserId());

            Student s = studentMapper.selectForUpdate(param);

            float level = eval.getLevel();
            float score = s.getCredit() + (level - 3) * 5;
            if(score>100){
                score = 100;
            }

            //信誉过低，禁止登陆
            if(score<30){
                s.setStatus(2);
            }
            s.setCredit((int)score);


            studentMapper.updateById(s);
        }

        //设置为已评价
        idleInfo.setStatus(9);
        idleInfoMapper.updateById(idleInfo);

        return mapper.insert(eval);
    }

    @Override
    public List<Eval> getByPage(EvalExtend extend) {
        List<Eval> evals = mapper.selectByPage(extend);
        //查询图标
        List<String> ids = new ArrayList<>();
        for(int i=0;i<evals.size();i++){
            Eval eval = evals.get(i);
            ids.add(eval.getIdleId());
        }
        List<IdelPic> pics = picMapper.queryIdlePic(ids);

        //遍历图片查询结果并连接到对应的商品
        for(int i = 0;i<evals.size();i++){
            Eval eval = evals.get(i);
            List<IdelPic> picList = new ArrayList<>();
            for(IdelPic pic:pics){
                if(pic.getIdelId().equals(eval.getIdleId())){
                    picList.add(pic);
                    break;
                }
            }
            eval.getIdleInfo().setPicList(picList);
        }
        return evals;
    }
}
