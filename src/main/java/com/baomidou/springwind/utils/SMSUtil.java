package com.baomidou.springwind.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 手机短信服务工具类
 *
 */
public class SMSUtil {
    private static final String HOST="http://mobaitz.market.alicloudapi.com";
    private static final String PATH = "/mobai_notifysms";
    private static final String METHOD = "POST";
    private static final String APPCODE = "51fc6237d9714eb8bd681c8d995d9101";
    private static final Map<String, String> headers = new HashMap<String, String>();
    //指定模板
    private static final String TEMPLATE="TP1807145";

    /**
     * 发送验证信息到指定手机号码
     * @param mobile 发送的手机号码
     * @return 验证码
     */
    public static String sendSMS(String mobile){
        String result=null;
        //生成验证码
        int code=getRandomValue();
        try{
            //设置参数
            headers.put("Authorization","APPCODE "+APPCODE);
            Map<String,Object> params=new HashMap();
            params.put("param","code:"+code);
            params.put("phone",mobile);
            params.put("templateId",TEMPLATE);
            //发送请求
            result=doPost(HOST,PATH,params,headers,METHOD);
            System.out.println(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return String.valueOf(code);
    }

    //生成随机验证码
    private static int getRandomValue(){
        int min=1000;
        int max=9999;
        Random random=new Random();
        return random.nextInt(max)%(max-min+1)+min;
    }

    //发送post请求
    private static String doPost(String url,String path,Map<String,Object> param,Map<String,String> header,String method){
        StringBuffer sb=new StringBuffer();
        DataOutputStream outputStream=null;
        try {
            //建立连接
            URL urlPath = new URL(url + path);
            HttpURLConnection httpConnection = (HttpURLConnection) urlPath.openConnection();
            //设置参数
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setRequestMethod(method);
            //设置请求属性
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setRequestProperty("Charset", "UTF-8");
            //设置头部参数
            for(String key:header.keySet()){
                httpConnection.setRequestProperty(key,header.get(key));
            }

            httpConnection.connect();

            //写入参数并取得返回值
            outputStream = new DataOutputStream(httpConnection.getOutputStream());
            outputStream.writeBytes(urlencode(param));
            outputStream.flush();
            int resultCodde=httpConnection.getResponseCode();
            //如果返回值正确
            if(HttpURLConnection.HTTP_OK==resultCodde){
                String readLine=null;
                BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),"UTF-8"));
                while((readLine=responseReader.readLine())!=null){
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
