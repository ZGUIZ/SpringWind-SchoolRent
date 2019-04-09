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
        <div class="carousel" style="width: 800px;height: 378px;">
            <ul class="carousel-imgs" id="picContainer">
            </ul>
            <div class="carousel-btns">
                <button type="button" class="carousel-btn-left">&lt;</button>
                <button type="button" class="carousel-btn-right">&gt;</button>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">标题：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="idle.title" name="idle.title" v-model="idle.title" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">内容：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <%--<input type="text" class="input-text disabled" readonly value="" placeholder="" id="idle.idelInfo" name="idle.idelInfo" v-model="idle.idelInfo" />--%>
                    <textarea name="" cols="" rows="" class="textarea radius" readonly>{{idle.idelInfo}}</textarea>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">损毁描述：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <%--<input type="text" class="input-text disabled" readonly value="" placeholder="" id="idle.idelInfo" name="idle.idelInfo" v-model="idle.idelInfo" />--%>
                    <textarea name="" cols="" rows="" class="textarea radius" readonly>{{idle.destoryInfo}}</textarea>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">最低押金：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="idle.deposit" name="idle.deposit" v-model="idle.deposit" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">租金（元/天）：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="idle.retal" name="idle.retal" v-model="idle.retal" />
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">发布日期：</label>
            <div class="formControls col-xs-7 col-sm-8">
                <div class="select-inline">
                    <input type="text" class="input-text disabled" readonly value="" placeholder="" id="idle.createDate" name="idle.createDate" v-model="idle.createDate" />
                </div>
            </div>
        </div>
    </form>
</article>

<style type="text/css">
    .carousel {
        width: 800px;
        height: 378px;
        padding: 0px;
        margin: 0px auto;
        position: relative;
        overflow: hidden;
    }

    .carousel-imgs {
        width: 500%;
        height: 100%;
        padding: 0px;
        margin: 0px;
        list-style: none;
        position: absolute;
    }

    .carousel-imgs li {
        width: 20%;
        height: 100%;
        float: left;
    }

    .carousel-imgs a {
        text-decoration: none;
    }

    .carousel-imgs img {
        width: 100%;
        height: 100%;
    }

    .carousel-btns {
        width: 100%;
        position: absolute;
        top: 45%;
    }

    .carousel-btns button {
        border: 0px;
        outline: none;
        padding: 5px;
        background: rgba(0, 0, 0, 0.4);
        text-align: center;
        color: white;
        font-size: 34px;
        font-family: "microsoft yahei";
    }

    .carousel-btns button:hover {
        background: rgba(0, 0, 0, 0.5);
    }

    .carousel-btn-left {
        float: left;
    }

    .carousel-btn-right {
        float: right;
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

    var idleVue;
    $(function(){
        idleVue = new Vue({
            el: '#idle-form',
            data: {
                idle : {}
            }
        });
        var url = $(parent.layer).attr('data-url');
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            idleVue.idle = data.data;
            loadInfo(idleVue.idle);
            carousel("43%", "71%");
        });
    });

    function loadInfo(idleInfo){
        var $con = $("#picContainer");
        for(var i = 0;i<idleInfo.picList.length;i++){
            var obj = idleInfo.picList[i];

            var temp = $('script[type="text/row"]');
            var html = temp.html();
            html = html.replace(/{href}/g,obj.picUrl);
            html = html.replace(/{src}/g,obj.picUrl);
            debugger;
            $con.append(html);
        }
    }

</script>
<!--/请在上方写此页面业务相关的脚本-->
<script type="text/row">
    <li><a href="{href}"><img src="{src}" /></a></li>
</script>

<script type="text/javascript">
    function carousel(left, top) {
        /* 获取元素对象 */
        var $carousel = $(".carousel");
        var $imgs = $(".carousel-imgs li");
        var $btnL = $(".carousel-btn-left");
        var $btnR = $(".carousel-btn-right");
        /* 创建导航按钮 */
        var $item_group = $("<ul></ul>");
        $item_group.attr("class", "carousel-items");
        $imgs.each(function() {
            $item_group.append("<li></li>");
        });
        $carousel.append($item_group);
        /* 样式初始化 */
        $item_group.css({
            "padding": "0px",
            "margin": "0px",
            "list-style": "none",
            "width": "auto",
            "position": "absolute",
            "left": left,
            "top": top
        });
        $item_group.children().css({
            "width": "10px",
            "height": "10px",
            "padding": "0px",
            "margin": "5px",
            "background": "white",
            "opacity": "0.6",
            "border-radius": "5px",
            "box-shadow": "0 0 5px black",
            "cursor": "pointer",
            "float": "left"
        });
        var index = 0; // 初始展示位置
        var $items = $(".carousel-items li");
        $items.eq(index).css("background", "gray");
        /* 按钮点击动作 */
        $btnL.click(function() {
            imgAnimator(false);
        });

        $btnR.click(function() {
            imgAnimator(true);
        });

        $items.click(function() {
            imgAnimator(true, $items.index($(this)));
        });
        /* 图像动画 */
        function imgAnimator(toNext, select) {
            if(select != null) {
                index = select;
            } else if(toNext == true) {
                index = ($imgs.length + index + 1) % $imgs.length;
            } else if(toNext == false) {
                index = ($imgs.length + index - 1) % $imgs.length;
            }
            $items.css("background", "white");
            $items.eq(index).css("background", "grey");
            var position = index * -($imgs.outerWidth());
            $imgs.parent().animate({
                "left": position + "px"
            }, "fast");
        }
    }
</script>

</body>
</html>