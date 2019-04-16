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
    <form class="form form-horizontal" id="idle-form">

        <div class="row cl" v-if="isIdle()">
            <label class="form-label col-xs-4 col-sm-3">举报商品：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="complain.idleInfo.title" name="complain.idleInfo.title" v-model="complain.idleInfo.title" />
                </div>
            </div>
        </div>
        <div class="row cl" v-if="isRentNeeds()">
            <label class="form-label col-xs-4 col-sm-3">举报帖子：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="complain.rentNeeds.title" name="complain.rentNeeds.title" v-model="complain.rentNeeds.title" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">举报内容：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <%--<input type="text" class="input-text disabled" readonly value="" placeholder="" id="idle.idelInfo" name="idle.idelInfo" v-model="idle.idelInfo" />--%>
                    <textarea name="" cols="" rows="" class="textarea radius" readonly>{{complain.msg}}</textarea>
                </div>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">举报日期：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="complain.complainTime" name="complain.complainTime" v-model="complain.complainTime" />
                </div>
            </div>
        </div>
        <div class="row cl" v-if="isShow()" style="text-align: center">
            <input class="btn btn-primary radius" type="button" onclick="deal(1)" value="已经处理">
            <input class="btn btn-danger radius" type="button" onclick="deal(2)" value="不处理">
        </div>
    </form>

    <div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
        <div id="innerdiv" style="position:absolute;">
            <img id="bigimg" style="border:5px solid #fff;" src="" />
        </div>
    </div>
</article>

<style>
    .rollpic .prev,.rollpic .next{display:block; height:38px; width:38px; cursor:pointer; float:left; background:url(static/img/unslider-arrow.png) no-repeat 0 0; margin-top:205px}
    .rollpic .prev{background-position:0 0; margin-right:5px}
    .rollpic .prev:hover{background-position:0 -38px}
    .rollpic .next{background-position:0 -76px;margin-left:5px}
    .rollpic .next:hover{background-position:0 -114px}
    .rollpicshow{float:left; border:solid 1px #ddd}
    .rollpicshow li{padding:10px}

    .alignCenter{
        display:inline-block;
        margin:0 auto !important;
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
<script type="text/javascript" src="lib/jcarousellite.min.js"></script>
<script type="text/javascript">

    var complainVue;
    $(function(){
        complainVue = new Vue({
            el: '#idle-form',
            data: {
                complain : {}
            },
            methods: {
                isShow: function(){
                    if(typeof complainVue == 'undefined' || typeof complainVue.complain == 'undefined'){
                        return false;
                    }
                    if(complainVue.complain.status == 0){
                        return true;
                    } else {
                        return false;
                    }
                },
                isIdle: function(){
                    if(typeof complainVue == 'undefined' || typeof complainVue.complain == 'undefined'){
                        return false;
                    }
                    if(complainVue.complain.complainType == 1){
                        return true;
                    } else {
                        return false;
                    }
                },
                isRentNeeds:function(){
                    if(typeof complainVue == 'undefined' || typeof complainVue.complain == 'undefined'){
                        return false;
                    }
                    if(complainVue.complain.complainType == 2){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
        var url = $(parent.layer).attr('data-url');
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            complainVue.complain = data.data;
            debugger;
            loadInfo(complainVue.complain);
        });
    });

    function deal(type){
        var str;
        if(type == 1){
            str = "确定已经处理？"
        } else {
            str = "确定不处理？";
        }
        layer.confirm(str,function(index){
            AjaxUtil.ajax( APP.WEB_APP_NAME+'/complain/deal?id='+complainVue.complain.complainId+'&type='+type,'get',true,null,function (data) {
                if(data.result){
                    layer.msg('设置成功!',{icon: 6,time:1000});
                } else{
                    layer.msg("设置失败："+data.msg,{icon:5,time:1000});
                }
                idleTable.ajax.reload();
            });
        });
    }

</script>
<!--/请在上方写此页面业务相关的脚本-->
<script type="text/row">
    <li><a><img src="{src}" width="500" height="400" /></a></li>
</script>

</body>
</html>