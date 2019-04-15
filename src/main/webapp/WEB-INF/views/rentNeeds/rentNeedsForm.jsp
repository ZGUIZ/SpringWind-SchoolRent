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

    </form>

    <div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
        <div id="innerdiv" style="position:absolute;">
            <img id="bigimg" style="border:5px solid #fff;" src="" />
        </div>
    </div>
</article>

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
            }
        });
        var url = $(parent.layer).attr('data-url');
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            rentNeedsVue.rentNeeds = data.data;
        });
    });
</script>
<!--/请在上方写此页面业务相关的脚本-->
<script type="text/row">
    <li><a><img src="{src}" width="500" height="400" /></a></li>
</script>

</body>
</html>