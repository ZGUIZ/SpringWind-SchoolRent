<%--
  Created by IntelliJ IDEA.
  User: Amia
  Date: 2019/4/3
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <base href="${pageContext.request.contextPath}/">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Bookmark" href="../favicon.ico" >
    <link rel="Shortcut Icon" href="../favicon.ico" />
    <link rel="stylesheet" href="lib/select2/css/select2.css">
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5shiv.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>添加学生</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="student-form">
        <div class="row cl form-label">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>地点：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <select id="province" style="width: 115px;" onchange="loadCity(this)"></select>
                </div>
                <div class="select-inline">
                    <select id="city" style="width: 115px;" onchange="loadSchool(this)"></select>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>学校：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <select id="school" style="width: 240px;" onchange="setSchool(this)"></select>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>学号：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" id="student.studentId" name="student.studentId" v-model="student.studentId" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" id="student.userName" name="student.userName" v-model="student.userName" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>真实姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" id="student.realName" name="student.realName" v-model="student.realName" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <select id="sex" style="width: 240px;" onchange="setSex(this)"></select>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>手机：</label>
            <div class="formControls col-xs-8 col-sm-4">
                <input type="text" class="input-text" value="" placeholder="" id="student.telephone" name="student.telephone" v-model="student.telephone">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>邮箱：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" id="student.email" name="student.email" v-model="student.email" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">信誉：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" readonly id="student.credit" name="student.credit" v-model="student.credit" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="button" onclick="save()" value="保存">
            </div>
        </div>
    </form>
</article>
<style>
    .select-inline{
        display: inline;
        float: left;
        padding-right: 10px;
    }
</style>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="lib/vue.min.js"></script>
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<script src="lib/select2/js/select2.js" type="text/javascript"></script>
<script src="static/js/Select2Extend.js" type="text/javascript"></script>
<script src="static/js/AjaxUtil.js" type="text/javascript"></script>
<script src="static/js/common.js" type="text/javascript"></script>
<!--请在下方写此页面业务相关的脚本-->
<!--<script type="text/javascript" src="/lib/My97DatePicker/4.8/WdatePicker.js"></script> -->
<!--<script type="text/javascript" src="/lib/DataTablesUtil/js/jquery.dataTables.min.js"></script>-->

<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">

    var studentVue;
    $(function(){
        studentVue = new Vue({
            el: '#student-form',
            data: {
                student : {}
            }
        });
        var url = $(parent.layer).attr('data-url');
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            studentVue.student = data;
        });
        debugger;
        Select2Extend.loadSelectAsync('province',APP.WEB_APP_NAME+'/school/getProvince');
        //加载城市信息
        var cityUrl = APP.WEB_APP_NAME+"/school/getCity?province=";
        if(studentVue.student.school && studentVue.student.school.province){
            $('#province').val(studentVue.student.school.province).trigger('change');
            cityUrl = cityUrl + studentVue.student.school.province;
        } else {
            cityUrl = cityUrl + 'undefined';
        }
        Select2Extend.loadSelectAsync('city',cityUrl);

        var schoolUrl = APP.WEB_APP_NAME+'/school/getSchool?city=';
        if(studentVue.student.school && studentVue.student.school.city){
            $('#city').val(studentVue.student.school.city).trigger('change');
            schoolUrl = schoolUrl + studentVue.student.school.city;
        } else {
            schoolUrl = schoolUrl + 'undefined';
        }
        Select2Extend.loadSelectAsync('school',schoolUrl);
        if(studentVue.student.schoolId){
            $('#school').val(studentVue.student.schoolId).trigger('change');
        }

        Select2Extend.loadSelectAsync('sex',APP.WEB_APP_NAME+'/student/getSex');
        if(studentVue.student.sex){
            $('#sex').val(studentVue.student.sex).trigger('change');
        }
    });

    //监听省份变化并改变城市，并设置学生所在省份
    function loadCity(me){
        var province = $(me).val() == null ? 'undefined': $(me).val();
        Select2Extend.loadSelectAsync('city',APP.WEB_APP_NAME+'/school/getCity?province='+province);
        studentVue.student.province = province;
    }
    //监听城市变化并改变学校，并设置学生所在城市
    function loadSchool(me) {
        var city = $(me).val() == null ? 'undefined' : $(me).val();
        Select2Extend.loadSelectAsync('school',APP.WEB_APP_NAME+'/school/getSchool?city='+city);
        studentVue.student.city = city;
    }
    //更新Vue中的学校
    function setSchool(me) {
        studentVue.student.schoolId = $(me).val();
    }

    function setSex(me) {
        studentVue.student.sex = $(me).val();
    }

    function save() {
        var student = copyObject(studentVue.student);
        delete student.school;
        AjaxUtil.ajax(APP.WEB_APP_NAME+'/student/updateStudent','POST',true,student,function (data) {
            if(data.result){
                layer.msg('保存成功!',{icon: 6,time:1000});
            } else{
                layer.msg("修改失效:"+data.msg,{icon:5,time:1000});
            }
        });
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>