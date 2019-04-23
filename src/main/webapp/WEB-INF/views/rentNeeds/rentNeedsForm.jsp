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

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">标题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="rentNeeds.title" name="rentNeeds.title" v-model="rentNeeds.title" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">内容：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <%--<input type="text" class="input-text disabled" readonly value="" placeholder="" id="idle.idelInfo" name="idle.idelInfo" v-model="idle.idelInfo" />--%>
                    <textarea name="" cols="" rows="" class="textarea radius" readonly>{{rentNeeds.idelInfo}}</textarea>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">发布日期：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="rentNeeds.createDate" name="rentNeeds.createDate" v-model="rentNeeds.createDate" />
                </div>
            </div>
        </div>
        <div class="row cl" style="text-align: center">
            <input class="btn btn-danger radius" type="button" onclick="unShow()" v-if="!isShow()" value="禁止显示">
            <input class="btn btn-primary radius" type="button" onclick="reShow()" v-if="isShow()" value="恢复显示">
        </div>
    </form>

    <div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
        <div id="innerdiv" style="position:absolute;">
            <img id="bigimg" style="border:5px solid #fff;" src="" />
        </div>
    </div>

    <div class="comments" id="responses">

    </div>
</article>

<style>

    html,
    body {
        background-color: #f0f2fa;
        font-family: "PT Sans", "Helvetica Neue", "Helvetica", "Roboto", "Arial", sans-serif;
        color: #555f77;
        -webkit-font-smoothing: antialiased;
    }
    input,
    textarea {
        outline: none;
        border: none;
        display: block;
        margin: 0;
        padding: 0;
        -webkit-font-smoothing: antialiased;
        font-family: "PT Sans", "Helvetica Neue", "Helvetica", "Roboto", "Arial", sans-serif;
        font-size: 1rem;
        color: #555f77;
    }
    input::-webkit-input-placeholder,
    textarea::-webkit-input-placeholder {
        color: #ced2db;
    }
    input::-moz-placeholder,
    textarea::-moz-placeholder {
        color: #ced2db;
    }
    input:-moz-placeholder,
    textarea:-moz-placeholder {
        color: #ced2db;
    }
    input:-ms-input-placeholder,
    textarea:-ms-input-placeholder {
        color: #ced2db;
    }
    p {
        line-height: 1.3125rem;
    }
    .comments {
        margin: 2.5rem auto 0;
        max-width: 60.75rem;
        padding: 0 1.25rem;
    }
    .comment-wrap {
        margin-bottom: 1.25rem;
        display: table;
        width: 100%;
        min-height: 5.3125rem;
    }
    .photo {
        padding-top: 0.625rem;
        display: table-cell;
        width: 3.5rem;
    }
    .photo .avatar {
        height: 2.25rem;
        width: 2.25rem;
        border-radius: 50%;
        background-size: contain;
    }
    .comment-block {
        padding: 1rem;
        background-color: #fff;
        display: table-cell;
        vertical-align: top;
        border-radius: 0.1875rem;
        box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.08);
    }
    .comment-block textarea {
        width: 100%;
        max-width: 100%;
    }
    .comment-text {
        margin-bottom: 1.25rem;
    }
    .bottom-comment {
        color: #acb4c2;
        font-size: 0.875rem;
    }
    .comment-date {
        float: left;
    }
    .comment-actions {
        float: right;
    }
    .comment-actions li {
        display: inline;
    }
    .comment-actions li.complain {
        padding-right: 0.625rem;
        border-right: 1px solid #e1e5eb;
    }
    .comment-actions li.reply {
        padding-left: 0.625rem;
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

    var rentNeedsVue;
    $(function(){
        rentNeedsVue = new Vue({
            el: '#idle-form',
            data: {
                rentNeeds : {}
            },
            methods: {
                isShow: function(){
                    if(typeof rentNeedsVue == 'undefined' || typeof rentNeedsVue.rentNeeds == 'undefined'){
                        return true;
                    }
                    if(rentNeedsVue.rentNeeds.status == 101){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
        var url = $(parent.layer).attr('data-url');
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            rentNeedsVue.rentNeeds = data.data;
            loadResponse(rentNeedsVue.rentNeeds.infoId);
        });
    });

    function loadResponse(id){
        var url = APP.WEB_APP_NAME + "/responseInfo/toList";
        var responseInfo = {};
        responseInfo.infoId = id;
        AjaxUtil.ajax(url,'post',false,responseInfo,function (data) {
            $("#responses").empty();

            var responses = data.data;
            for(var i = 0;i<responses.length;i++){
                var obj = responses[i];

                var temp = $('script[type="text/response"]');
                var html = temp.html();
                html = html.replace(/{url}/g,obj.student.userIcon);
                html = html.replace(/{userName}/g,obj.student.userName);
                html = html.replace(/{content}/g,obj.responseInfo);
                html = html.replace(/{id}/g,obj.responseId);
                html = html.replace(/{date}/g,obj.responseDate);
                debugger;
                var second = loadSecondResponse(obj.secondResponseInfos,html);
                $("#responses").append(html).append(second);
            }
        });
    }

    function loadSecondResponse(secondResponseInfos){
        var html = "";
        for(var i = 0;i<secondResponseInfos.length;i++){
            var obj = secondResponseInfos[i];
            var temp = $('script[type="text/response"]');
            html = html+temp.html();
            html = html.replace(/{url}/g,obj.student.userIcon);
            html = html.replace(/{userName}/g,obj.student.userName);
            html = html.replace(/{content}/g,obj.responseInfo);
            html = html.replace(/{id}/g,obj.responseId);
            html = html.replace(/{date}/g,obj.responseDate);
        }
        return html;
    }

    function reShow(){
        layer.confirm('确认要恢复显示该帖子吗？',function(index){
            AjaxUtil.ajax( APP.WEB_APP_NAME+'/rentNeeds/reShow/'+rentNeedsVue.rentNeeds.infoId,'get',true,null,function (data) {
                if(data.result){
                    layer.msg('设置成功!',{icon: 6,time:1000});
                } else{
                    layer.msg("设置失败："+data.msg,{icon:5,time:1000});
                }
                idleTable.ajax.reload();
            });
        });
    }

    function unShow(){
        layer.confirm('确认要禁止显示该商品吗？',function(index){
            AjaxUtil.ajax( APP.WEB_APP_NAME+'/rentNeeds/delByManager/'+rentNeedsVue.rentNeeds.infoId,'get',true,null,function (data) {
                if(data.result){
                    layer.msg('设置成功!',{icon: 6,time:1000});
                } else{
                    layer.msg("设置失败："+data.msg,{icon:5,time:1000});
                }
                idleTable.ajax.reload();
            });
        });
    }

    function delResponse(id){
        layer.confirm('确认要删除评论吗？',function(index){
            AjaxUtil.ajax( APP.WEB_APP_NAME+'/responseInfo/del/'+id,'get',true,null,function (data) {
                if(data.result){
                    layer.msg('删除成功!',{icon: 6,time:1000});
                    loadResponse(idleVue.idle.infoId);
                } else{
                    layer.msg("删除失败："+data.msg,{icon:5,time:1000});
                }
            });
        });
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
<script type="text/row">
    <li><a><img src="{src}" width="500" height="400" /></a></li>
</script>

<script type="text/response">
    <div class="comment-wrap">
        <div class="photo">
            <div class="avatar" style="background-image: url('{url}')"></div>
            <div>{userName}</div>
        </div>
        <div class="comment-block">
            <p class="comment-text">{content}</p>
            <div class="bottom-comment">
                <div class="comment-date">{date}</div>
                <ul class="comment-actions">
                    <a class="complain" href="javascript:delResponse('{id}')">删除</a>
                </ul>
            </div>
        </div>
    </div>
</script>

</body>
</html>