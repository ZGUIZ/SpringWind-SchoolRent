<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <base href="${pageContext.request.contextPath}/">
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5shiv.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <![endif]-->
    <link href="static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <link href="static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
    <link href="lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>后台登录 - H-ui.admin v3.1</title>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
    <div id="loginform" class="loginBox">
        <form class="form form-horizontal" id="login_form" method="post">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input id="account" name="account" type="text" placeholder="账户" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L">
                </div>
            </div>
            <%--<div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input class="input-text size-L" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
                    <img src=""> <a id="kanbuq" href="javascript:;">看不清，换一张</a> </div>
            </div>--%>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <label for="online">
                        <input type="checkbox" name="online" id="online" value="">
                        使我保持登录状态</label>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input name="" type="button" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" onclick="login()">
                    <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="footer">Copyright ZGUIZ by H-ui.admin v3.1</div>
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>
<script src="static/plugins/layer-v2.2/layer/layer.js"></script>
<script type="text/javascript">
    function login() {
        var inputElement = new Array();
        //非空校验
        var passwordObject = {};
        passwordObject.value = "#password";
        passwordObject.log = "密码不能为空";
        inputElement.push(passwordObject);
        var accountObject = {};
        accountObject.value = "#account";
        accountObject.log = "账号不能为空";
        inputElement.push(accountObject);
        var r = nullValidate(inputElement);
        if(r){
            submit();
        }
    }

    function submit() {
        var v = {};
        v.account = $("#account").val();
        v.password = $("#password").val();
        var url = APP.WEB_APP_NAME+"/manager/login";
        var json = JSON.stringify(v);
        console.log(json);
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: json,
            url: url,
            success: function (data) {
                console.log(data);
                if(data.result){
                    window.location.href = APP.WEB_APP_NAME+"/manager/index";
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function error(e){
                console.log(e);
            }
        });
    }

    function nullValidate(e) {
        while(e.length>0){
            var o = e.pop();
            var v = $(o.value).val();
            if(v == null || v == ""){
                layer.msg(o.log);
                return false;
            }
        }
        return true;
    }
</script>
</body>
</html>
