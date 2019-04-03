package test;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springwind.entity.IdleInfo;
import com.baomidou.springwind.entity.RentNeeds;
import com.baomidou.springwind.entity.RequestInfo;
import com.baomidou.springwind.utils.JPushUnits;
import com.baomidou.springwind.utils.MailUtil;
import com.baomidou.springwind.utils.RSAUtil;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

public class RSATest {

    @Test
    public void testRSA(){
        String str="这是一个测试";
        Map<String,byte[]> keyMap = RSAUtil.getKeys();
        PublicKey publicKey = RSAUtil.restorePublicKey(keyMap.get(RSAUtil.PUBLIC_KEY));
        PrivateKey privateKey = RSAUtil.restorePrivateKey(keyMap.get(RSAUtil.PRIVATE_KEY));
        try {
            byte[] encodedText = RSAUtil.RSAEncode(publicKey,str.getBytes("utf-8"));
            String testStr=Base64.encodeBase64String(encodedText);
            System.out.println(testStr);
            encodedText = Base64.decodeBase64(testStr);
            String aaa = RSAUtil.RSADecode(privateKey,encodedText);
            System.out.println(aaa);

            encodedText = RSAUtil.RSAEncode(privateKey,str.getBytes("utf-8"));
            testStr=Base64.encodeBase64String(encodedText);
            System.out.println(testStr);
            encodedText = Base64.decodeBase64(testStr);
            aaa = RSAUtil.RSADecode(publicKey,encodedText);
            System.out.println(aaa);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetRentFromJSON(){
        RequestInfo requestInfo=new RequestInfo();
        requestInfo.setPageSize(5);
        RentNeeds needs=new RentNeeds();
        needs.setStatus(2);
        needs.setIdelInfo("测试测试");
        requestInfo.setClassType("RentNeeds");
        requestInfo.setParam(needs);
        String json=JSONObject.toJSONString(requestInfo);
        RequestInfo<RentNeeds> info=RequestInfo.getObjectFromJson(json);
        //System.out.println(info.getPageSize()+"\t"+info.getParam().getIdelInfo()+"\t"+info.getClassType());

        IdleInfo idleInfo=new IdleInfo();
        idleInfo.setIdelInfo("aaabbb测试");
        requestInfo.setParam(idleInfo);
        requestInfo.setClassType("IdleInfo");
        String json2=JSONObject.toJSONString(requestInfo);
        RequestInfo<IdleInfo> idleInfoRequestInfo=RequestInfo.getObjectFromJson(json2);
        IdleInfo idleInfo1=idleInfoRequestInfo.getParam();
        System.out.println(idleInfo1.getIdelInfo());
    }

    @Test
    public void testEMail(){
        try {
            MailUtil.sendMail("rftoona@outlook.com","密码重置","您在校园租账号的秘密已经被重置,新密码为：aaaa，请尽早修改密码");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMail(){
        try {
            //MailUtil.sentSalMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPush() throws APIConnectionException, APIRequestException {
        JPushUnits jPushUnits = JPushUnits.newInstance();
        jPushUnits.pushForUser("8284984fd6394f2c935506ada97d565c","来自服务器端的测试");
    }
}
