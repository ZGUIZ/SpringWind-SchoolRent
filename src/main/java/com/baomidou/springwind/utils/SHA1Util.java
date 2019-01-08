package com.baomidou.springwind.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Util {

    private static char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encode(String str){
        try {
            MessageDigest digest=MessageDigest.getInstance("SHA1");
            digest.update(str.getBytes("UTF-8"));
            byte[] md=digest.digest();
            int j=md.length;
            char[] buf=new char[j*2];
            int k=0;
            for(int i=0;i<j;i++){
                byte b=md[i];
                buf[k++]=hexDigit[b >>> 4 & 0xf];
                buf[k++]=hexDigit[b & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
