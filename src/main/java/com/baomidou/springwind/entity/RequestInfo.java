package com.baomidou.springwind.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.springwind.entity.interfaces.JSONResolve;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.activation.ActivationGroup_Stub;
import java.util.HashSet;
import java.util.Map;

/**
 * 查询条件
 */
public class RequestInfo<T>{
    /**
     * 页数
     */
    private Integer page = 1;
    private Integer draw;
    /**
     * 单个页面的最大记录数
     */
    private Integer length = 20;
    /**
     * 参数
     */
    private T param;

    /**
     * 请求类型
     */
    private String classType;

    /**
     * 总记录数
     */
    private Integer ammount;

    private Integer start;

    private Map<String,Object> search;

    public RequestInfo(){
        super();
    }

    public RequestInfo(Integer page,T param){
        super();
        this.page = page;
        this.param = param;
        this.length = 20;
    }

    public RequestInfo(Integer page,Integer pageSize,T param){
        this(page,param);
        this.length = pageSize;
    }

    public Integer getLength() {
        return length;
    }

    public void setPageSize(Integer pageSize) {
        this.length = pageSize;
    }

    /*public Integer getStart() {
        return (page-1)*length;
    }*/

    public Integer getEnd() {
        return (page)*length-1;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public Integer getAmmount() {
        return ammount;
    }

    public Integer getPageCount(){
        int num = ammount/length;
        if(ammount%length !=0){
            num += 1;
        }
        return num;
    }

    public void setAmmount(Integer ammount) {
        this.ammount = ammount;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Map<String, Object> getSearch() {
        return search;
    }

    public void setSearch(Map<String, Object> search) {
        this.search = search;
    }

    public Integer getDraw() {
        if(draw==null || draw<=0){
            return 1;
        }
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
        this.page = draw;
    }

    public String getSearchString(){
        return (String) search.get("value");
    }

    /**
     * 从JSON解析数据
     * @param json
     * @return
     */
    public static RequestInfo getObjectFromJson(String json){
        JSONObject object = JSONObject.parseObject(json);
        RequestInfo info = new RequestInfo();
        info.setPage(object.getInteger("page")==null || object.getInteger("page") > 0 ? object.getInteger("page"):20);
        Integer pageSize = object.getInteger("pageSize");
        if(pageSize!=null && pageSize>0){
            info.setPageSize(pageSize);
        }
        info.setClassType(object.getString("classType"));
        JSONObject param=object.getJSONObject("param");
        //如果参数不为空，则从直接反射解析对象
        if(info.getClassType() !=null && !"".equals(info.getClassType()) && param != null){
            String className="com.baomidou.springwind.entity."+info.getClassType();
            try {
                Class paramClass=Class.forName(className);
                Method method = paramClass.getMethod("getObjectFromJsonObject",new Class[]{JSONObject.class});
                Object res=method.invoke(null,param);
                info.setParam(res);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return info;
    }
}
