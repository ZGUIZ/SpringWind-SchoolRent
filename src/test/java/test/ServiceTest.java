package test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springwind.entity.Classify;
import com.baomidou.springwind.entity.School;
import com.baomidou.springwind.entity.Select2Bean;
import com.baomidou.springwind.entity.Student;
import com.baomidou.springwind.mapper.SchoolMapper;
import com.baomidou.springwind.service.*;
import com.baomidou.springwind.utils.HttpUtils;
import com.baomidou.springwind.utils.SHA1Util;
import com.baomidou.springwind.utils.UUIDUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceTest {
    private ApplicationContext context;
    @Before
    public void init(){
        context=new ClassPathXmlApplicationContext("/spring/app-datasource.xml","/spring/app-tx.xml","/spring/spring-framework.xml");
    }

    @Test
    public void changeCapital(){
        ICapitalService capitalService = context.getBean(ICapitalService.class);
        Student student=new Student();
        student.setUserId("12345");

        try {
            int row=capitalService.changeCapital(student,4.2f,ICapitalService.add);
            System.out.println(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void changeCapitalWithThread(){
        final ICapitalService capitalService = context.getBean(ICapitalService.class);
        final Student student=new Student();
        student.setUserId("12345");
        try {
            for(int i=0;i<30;i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Thread is running");
                        try {
                            int row = capitalService.changeCapital(student, 4.2f, ICapitalService.add);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thread end");
                    }
                }).run();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTransfer(){
        ICapitalService capitalService = context.getBean(ICapitalService.class);
        Student from=new Student();
        from.setUserId("12345");
        Student to=new Student();
        to.setUserId("2354");
        try {
            int row=capitalService.transfer(from,to,200.0f);
            if(row>0){
                System.out.println("转账成功");
            } else {
                System.out.println("转账失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCity(){
        ISchoolService schoolService = context.getBean(ISchoolService.class);
        List<Select2Bean> cities=schoolService.getCity(null);
        /*for(String city:cities){
            System.out.println(city);
        }*/
    }

    @Test
    public void testSchool(){
        ISchoolService schoolService=context.getBean(ISchoolService.class);
        List<School> schools=schoolService.getSchool("广州市");
        for(School school:schools){
            System.out.println(school.getSchoolName());
        }
    }

    @Test
    public void testStudentRegister() throws Exception{
        IStudentService studentService=context.getBean(IStudentService.class);
        Student student=new Student();
        student.setStudentId("2015874118");
        student.setSchoolId("4111010037");
        student.setUserName("aaa");
        String password=SHA1Util.encode("123456");
        student.setPassword(password);
        student.setPayPassword(password);
        int row=studentService.register(student);
        System.out.println(row);
    }

    @Test
    public void testGetProvid(){
        //String str=AreaQueryUtil.getPrivoid();
        String host = "https://api02.aliyun.venuscn.com";
        String path = "/area/all";
        String path1 = "/area/query";
        String method = "GET";
        String appcode = "";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("level", "0");

        querys.put("size", "50");
        querys.put("page", "1");
        //查询市级
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString());
            //获取response的body
            List<Area> areas=Area.resolveJson(EntityUtils.toString(response.getEntity()));

            SchoolMapper mapper=context.getBean(SchoolMapper.class);
            /*List<String> citis=mapper.getCity(new School());
            Map<String,String> set=new HashMap<>();
            for(String s:citis){
                set.put(s,s);
            }*/
            //System.out.println(area.name);
            for(Area area:areas){
                //请求省份内的市级城市名
                Map<String, String> param = new HashMap<>();
                param.put("parent_id", String.valueOf(area.id));
                HttpResponse newResponse = HttpUtils.doGet(host, path1, method, headers, param);
                List<Area> areasList=Area.resolveJson(EntityUtils.toString(newResponse.getEntity()));
                for(Area a:areasList){
                    System.out.println(a.name);
                    School school=new School();
                    school.setCity(a.name);
                    school.setProvince(area.name);
                    //如果数据库中存在该城市，则更新其省份
                    /*if(set.containsKey(a.name)){
                        mapper.updateProvince(school);
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class Area{
        int id;
        int parent_id;
        String name;

        public static List<Area> resolveJson(String json){
            List<Area> areas=new ArrayList<>();
            JSONObject object=JSONObject.parseObject(json);
            JSONArray jsonArray=object.getJSONArray("data");
            for(int i=0;i<jsonArray.size();i++){
                Area area=new Area();
                JSONObject object1=jsonArray.getJSONObject(i);
                area.id=object1.getInteger("id");
                area.name=object1.getString("name");
                area.parent_id = object1.getInteger("parent_id");
                areas.add(area);
            }
            return areas;
        }
    }

    @Test
    public void testProvince(){
        ISchoolService service=context.getBean(ISchoolService.class);
        List<Select2Bean> provinces=service.getProvince();
        for(Select2Bean str:provinces){
            System.out.println(str.getText());
        }
    }

    @Test
    public void testCity1(){
        ISchoolService service=context.getBean(ISchoolService.class);
        /*List<String> cities=service.getCity("广东省");
        for(String str:cities){
            System.out.println(str);
        }*/
    }

    @Test
    public void testDailyTask(){
        IRentService service = context.getBean(IRentService.class);
        service.calRentalDaily();
    }

    @Test
    public void insertClassify() throws IOException {
        FileReader reader=new FileReader("C:\\Users\\Amia\\Desktop\\商品分类.txt");
        BufferedReader bufferedReader=new BufferedReader(reader);
        String line;
        List<String> list =new ArrayList<>();
        while((line=bufferedReader.readLine())!=null){
            //System.out.println(line);
            String regx="([\\u4e00-\\u9fa5]+)";
            getStringBetweenString(list,line,regx);
        }
        bufferedReader.close();
        reader.close();
        IClassifyService service=context.getBean(IClassifyService.class);

        Date date=new Date();
        String id=String.valueOf(date.getTime());
        for(int i=0;i<list.size();i++){
            String newId=id+i;
            String str=list.get(i);
            System.out.println(str);
            Classify classify=new Classify();
            classify.setClassifyId(newId);
            classify.setClassifyName(str);
            boolean row=service.insert(classify);
            if(!row){
                System.out.println(str+"插入失败");
            }
        }
    }

    private void getStringBetweenString(List<String> arraylist,String line,String regx){
        Pattern pattern = Pattern.compile(regx);
        Matcher m=pattern.matcher(line);
        while(m.find()){
            int i=1;
            arraylist.add(m.group(i));
            i++;
        }
    }
}
