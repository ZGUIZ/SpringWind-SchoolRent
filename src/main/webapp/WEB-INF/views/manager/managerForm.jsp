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
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>账号：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" id="manager.account" name="manager.account" v-model="manager.account" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" id="manager.userName" name="manager.userName" v-model="manager.userName" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>邮箱：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" id="manager.mail" name="manager.mail" v-model="manager.mail" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>验证码：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <input type="text" class="input-text" value="" placeholder="" id="manager.code" name="manager.code" v-model="manager.code" />
                    <input id="send_val_btn" class="btn btn-default radius" type="button" value="获取验证码" onclick="validateMail()">
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="select-inline">
                    <select id="role" style="width: 240px;" onchange="setRole(this)"></select>
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

    var manager;
    $(function(){
        manager = new Vue({
            el: '#student-form',
            data: {
                manager : {}
            }
        });
        var url = $(parent.layer).attr('data-url');
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            manager.manager = data;
        });
        Select2Extend.loadSelectAsync('role',APP.WEB_APP_NAME+'/managerRole/list');
        $('#role').val(manager.manager.role).trigger('change');
    });

    function setRole(me) {
        manager.manager.role = $(me).val();
    }

    function save() {
        AjaxUtil.ajax(APP.WEB_APP_NAME+'/manager/update','POST',true,manager.manager,function (data) {
            if(data.result){
                layer.msg('保存成功!',{icon: 6,time:1000});
            } else{
                layer.msg("修改失效:"+data.msg,{icon:5,time:2000});
            }
        });
    }

    function validateMail() {

        var $btn = $("#send_val_btn");
        if($btn.hasClass("disabled")){
            return;
        }

        var mail = manager.manager.mail;
        if(mail == null || mail == ""){
            layer.msg('邮箱不能为空!',{icon: 6,time:1000});
            return;
        }
        AjaxUtil.ajax(APP.WEB_APP_NAME+"/manager/validate","POST",true,manager.manager, function(data) {
            if(data.result){
                layer.msg('发送成功!',{icon: 6,time:1000});
                validateSendSuccess();
            } else{
                layer.msg(data.msg,{icon:5,time:2000});
            }
        });
    }

    function validateSendSuccess(){
        var $btn = $("#send_val_btn");
        $btn.addClass("disabled");
        var lastTime = 30;
        var r = setInterval(function(){
            if(lastTime>0){
                $btn.val("剩余"+lastTime+"s");
                lastTime --;
            } else {
                $btn.val("获取验证码");
                clearInterval(r);
                $btn.removeClass("disabled");
            }
        },1000);
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>