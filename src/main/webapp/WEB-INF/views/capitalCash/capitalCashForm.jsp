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
    <title>详细</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="idle-form" style="text-align: center">

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">用户：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="capitalCash.student.userName" name="capitalCash.student.userName" v-model="capitalCash.student.userName" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">提现平台：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly placeholder="" value="支付宝" v-if="capitalCash.source == 0" />
                    <input type="text" class="input-text disabled" readonly placeholder="" value="微信" v-if="capitalCash.source == 1" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">金额（元）：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="capitalCash.capital" name="capitalCash.capital" v-model="capitalCash.capital" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">日期：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="capitalCash.requestTime" name="capitalCash.requestTime" v-model="capitalCash.requestTime" />
                </div>
            </div>
        </div>

        <div class="row cl" v-if="isShow()">
            <input class="btn btn-primary radius" type="button" onclick="showAgree()" value="通过">
            <input class="btn btn-danger radius" type="button" onclick="disagree()" value="不通过">
        </div>
    </form>

    <div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
        <div id="innerdiv" style="position:absolute;">
            <img id="bigimg" style="border:5px solid #fff;" src="" />
        </div>
    </div>
</article>

<style>
    .alignCenter{
        display:inline-block;
        margin:0 auto !important;
    }
    .imagePadding{
        padding: 10px;
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

<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="lib/jcarousellite.min.js"></script>
<script type="text/javascript">

    var capitalCash;
    var $con = $("#picContainer");
    $(function(){
        capitalCash = new Vue({
            el: '#idle-form',
            data: {
                capitalCash : {}
            },
            methods: {
                isShow: function(){
                    if(capitalCash.capitalCash.status == 0){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
        var url = $(parent.layer).attr('data-url');
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            capitalCash.capitalCash = data.data;
        });
    });

    function showAgree() {
        layer.prompt({title: '输入提现金额金额', formType: 3, value: capitalCash.capitalCash.capital}, function(pass, index){
            agree(pass);
            layer.close(index);
        });
    }

    function agree(money) {
        var url = APP.WEB_APP_NAME + "/capitalCash/pass?id=" + capitalCash.capitalCash.cashId + "&money="+money;
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            if(data.result){
                capitalCash.capitalCash.status = 1;
                layer.msg('充值成功!',{icon: 6,time:1000});
            } else {
                layer.msg("充值失败："+data.msg,{icon:5,time:1000});
            }
        });
    }

    function disagree() {
        var url = APP.WEB_APP_NAME + "/capitalCash/unPass?id=" + capitalCash.capitalCash.cashId;
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            if(data.result){
                capitalCash.capitalCash.status = 2;
                layer.msg('拒绝成功!',{icon: 6,time:1000});
            } else {
                layer.msg("拒绝失败："+data.msg,{icon:5,time:1000});
            }
        });
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
<script type="text/row">
    <li><a><img src="{src}" width="500" height="400" /></a></li>
</script>

</body>
</html>