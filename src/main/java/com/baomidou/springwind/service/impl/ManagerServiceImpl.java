package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.common.EhcacheHelper;
import com.baomidou.springwind.entity.Manager;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.mapper.ManagerMapper;
import com.baomidou.springwind.service.IManagerService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.utils.MailUtil;
import com.baomidou.springwind.utils.PassWordUtil;
import com.baomidou.springwind.utils.SHA1Util;
import com.baomidou.springwind.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2018-10-30
 */
@Service
public class ManagerServiceImpl extends BaseServiceImpl<ManagerMapper, Manager> implements IManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Manager login(Manager manager) {
        manager.setPassword(SHA1Util.encode(manager.getPassword()));
        return managerMapper.login(manager);
    }

    @Override
    public List<Manager> queryListByPage(RequestInfo requestInfo) {
        requestInfo.setAmmount(managerMapper.queryCount(requestInfo));
        return managerMapper.queryManager(requestInfo);
    }

    private static final String mailTitle = "校园租账号绑定";
    private static final String mailPass = "您的账号已经绑定为校园租的邮箱。登录密码为：";
    private static final String alertUpdate = "。请尽早更新您的密码！";

    private static final String MANAGER_CACHE = "managerMailCache";

    @Transactional
    @Override
    public boolean add(Manager manager) throws Exception {
        //校验验证码
        Object object = EhcacheHelper.get(MANAGER_CACHE, manager.getMail());
        if (!manager.getCode().equals(object)) {
            throw new Exception("邮箱验证信息错误！");
        }

        manager.setUserId(UUIDUtil.getUUID());
        String password = PassWordUtil.getRandomPassword();
        manager.setPassword(SHA1Util.encode(password));
        managerMapper.insert(manager);
        MailUtil.sendMail(manager.getMail(),mailTitle,mailPass+password+alertUpdate);
        EhcacheHelper.remove(MANAGER_CACHE,manager.getMail());
        return true;
    }

    private static final String MAIL_MESSAGE_TITLE = "邮箱验证";
    private static final String MAIL_VAIL_MSG="您的邮箱正在绑定校园租的账号，若非本人操作，请忽略此信息。此验证码有效时间10分钟，验证码为：";
    @Override
    public boolean getValidate(String mail) throws UnsupportedEncodingException, MessagingException {
        String str = PassWordUtil.getRandomPassword(6);
        MailUtil.sendMail(mail,MAIL_MESSAGE_TITLE,MAIL_VAIL_MSG+str);
        EhcacheHelper.put(MANAGER_CACHE,mail,str);
        return true;
    }
}
