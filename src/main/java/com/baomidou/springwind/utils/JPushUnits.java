package com.baomidou.springwind.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUnits {

    private String MASTER_SECRET = "af67b8f440373b745187353d";
    private String APP_KEY = "6e1ccacd3045abfe76528a94";

    private static JPushUnits jPushUnits;
    private JPushClient pushClient;

    public static JPushUnits newInstance(){
        if(jPushUnits == null){
            synchronized (JPushUnits.class){
                if(jPushUnits == null){
                    jPushUnits = new JPushUnits();
                }
            }
        }
        return jPushUnits;
    }


    public JPushUnits() {
        pushClient = new JPushClient(MASTER_SECRET, APP_KEY);
    }

    private PushResult push(PushPayload msg) throws APIConnectionException, APIRequestException {
        PushResult result = null;
        result = pushClient.sendPush(msg);
        return result;
    }

    private PushPayload buildPushObject(String id,String msg){
        return PushPayload.newBuilder().setPlatform(Platform.all())
                .setAudience(Audience.alias(id))
                .setNotification(Notification.alert(msg)).build();
    }

    public PushResult pushForUser(String id,String msg) throws APIConnectionException, APIRequestException {
        PushPayload pushPayload = buildPushObject(id,msg);
        return push(pushPayload);
    }
}
