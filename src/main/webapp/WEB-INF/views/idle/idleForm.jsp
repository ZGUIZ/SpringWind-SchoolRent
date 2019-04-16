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

        <div class="cl" style="text-align:center;">
            <div class="rollpic alignCenter">
                <a href="javascript:void(0)" class="prev"></a>
                <div class="rollpicshow">
                    <ul  id="picContainer">
                    </ul>
                </div>
                <a href="javascript:void(0)" class="next"></a>
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

    var idleVue;
    $(function(){
        idleVue = new Vue({
            el: '#idle-form',
            data: {
                idle : {}
            },
            methods: {
                isShow: function(){
                    if(typeof idleVue == 'undefined' || typeof idleVue.idle == 'undefined'){
                        return true;
                    }
                    if(idleVue.idle.status == 100){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
        var url = $(parent.layer).attr('data-url');
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            idleVue.idle = data.data;
            loadInfo(idleVue.idle);
        });
    });

    function loadInfo(idleInfo){
        var $con = $("#picContainer");
        for(var i = 0;i<idleInfo.picList.length;i++){
            var obj = idleInfo.picList[i];

            var temp = $('script[type="text/row"]');
            var html = temp.html();
            html = html.replace(/{src}/g,obj.picUrl);
            $con.append(html);
        }
        $(".rollpicshow").jCarouselLite({
            auto: 2000, /*自动播放间隔时间*/
            speed: 500, /*速度*/
            btnNext:".next",/*向前滚动*/
            btnPrev:".prev",/*向后滚动*/
            visible:1 /*显示数量*/
        })

        $("img").click(function(){
            imgShow("#outerdiv", "#innerdiv", "#bigimg", $(this));
        })
    }

    function imgShow(outerdiv, innerdiv, bigimg, _this){
        var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
        $(bigimg).attr("src", src);//设置#bigimg元素的src属性

        /*获取当前点击图片的真实大小，并显示弹出层及大图*/
        $("<img/>").attr("src", src).load(function(){
            var windowW = $(window).width();//获取当前窗口宽度
            var windowH = $(window).height();//获取当前窗口高度
            var realWidth = this.width;//获取图片真实宽度
            var realHeight = this.height;//获取图片真实高度
            var imgWidth, imgHeight;
            var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

            if(realHeight>windowH*scale) {//判断图片高度
                imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
                imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
                if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                    imgWidth = windowW*scale;//再对宽度进行缩放
                }
            } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
                imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
                imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
            } else {//如果图片真实高度和宽度都符合要求，高宽不变
                imgWidth = realWidth;
                imgHeight = realHeight;
            }
            $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放

            var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
            var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
            $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
            $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
        });

        $(outerdiv).click(function(){//再次点击淡出消失弹出层
            $(this).fadeOut("fast");
        });
    }

    function unShow(){
        layer.confirm('确认要禁止显示该商品吗？',function(index){
            AjaxUtil.ajax( APP.WEB_APP_NAME+'/idleInfo/delByManager/'+idleVue.idle.infoId,'get',true,null,function (data) {
                if(data.result){
                    layer.msg('设置成功!',{icon: 6,time:1000});
                } else{
                    layer.msg("设置失败："+data.msg,{icon:5,time:1000});
                }
                idleTable.ajax.reload();
            });
        });
    }

    function reShow(){
        layer.confirm('确认要恢复该信息正常显示吗？',function(index){
            AjaxUtil.ajax( APP.WEB_APP_NAME+'/idleInfo/reShow/'+idleVue.idle.infoId,'get',true,null,function (data) {
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