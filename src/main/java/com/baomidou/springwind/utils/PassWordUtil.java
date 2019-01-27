package com.baomidou.springwind.utils;

import java.util.Random;

public class PassWordUtil {
    private static final String[] str={"0","1","2","3","4","5","6","7","8","9",
        "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
        "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };

    public static String getRandomPassword(int length){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            Random random=new Random();
            int value=random.nextInt(str.length);
            sb.append(str[value]);
        }
        return sb.toString();
    }

    public static String getRandomPassword(){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            Random random=new Random();
            int value=random.nextInt(str.length);
            sb.append(str[value]);
        }
        return sb.toString();
    }
}
