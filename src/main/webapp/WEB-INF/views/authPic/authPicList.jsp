<%--
  Created by IntelliJ IDEA.
  User: Amia
  Date: 2019/4/4
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Bookmark" href="favicon.ico" >
    <link rel="Shortcut Icon" href="favicon.ico" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="../lib/html5shiv.js"></script>
    <script type="text/javascript" src="../lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="../static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="../static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="../lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="../static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="../static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="../lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="../static/css/TableStyle.css">
    <title>身份认证</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 身份认证 <span class="c-gray en">&gt;</span> 身份认证列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <table class="table table-border table-bordered table-bg display table-striped table-hover table-full-width" id="student">
        <thead>
        <tr>
            <th scope="col" colspan="15">商品列表</th>
        </tr>
        <tr class="text-c">
            <th width="40">用户名</th>
            <th width="40">学校</th>
            <th width="40">学号</th>
            <th width="90">姓名</th>
            <th width="90">证件类型</th>
            <th width="90">图片</th>
            <th width="40">状态</th>
            <th width="100">操作</th>
        </tr>
        </thead>
    </table>
    <div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
        <div id="innerdiv" style="position:absolute;">
            <img id="bigimg" style="border:5px solid #fff;" src="" />
        </div>
    </div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="../lib/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="../lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="../static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="../static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<script src="../static/js/AjaxUtil.js" type="text/javascript"></script>
<script src="../static/js/common.js" type="text/javascript"></script>
<!--请在下方写此页面业务相关的脚本-->
<!--<script type="text/javascript" src="/lib/My97DatePicker/4.8/WdatePicker.js"></script> -->
<!--<script type="text/javascript" src="/lib/DataTablesUtil/js/jquery.dataTables.min.js"></script>-->

<script type="text/javascript" src="../lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="../lib/DataTablesUtil/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var studentTable;
    $(function(){

        studentTable=$("#student").DataTable({
            ajax:{
                url: APP.WEB_APP_NAME+'/authPicture/listByPage'
            },
            "serverSide": true,
            "destroy": true,
            "pageLength": 10,
            "ordering": false,
            "autoWidth": true,
            "stateSave": false,//保持翻页状态，和comTable.fnDraw(false);结合使用
            "searching": true,//datatables默认搜索
            columns:[
                {data: 'student.userName'},
                {data: 'student.school.schoolName'},
                {data: 'student.studentId'},
                {data: 'student.realName'},
                {
                    orderable:false,
                    targets:[0],
                    data:"type",
                    render: function(data,type,full,meta){
                        var typeMsg = "";
                        switch (data){
                            case 1:
                                typeMsg = "学生证";
                                break;
                            case 2:
                                typeMsg = "身份证";
                                break;
                            case 3:
                                typeMsg = "个人照片";
                                break;
                        }
                        return typeMsg;
                    }
                },
                {
                    orderable:false,
                    targets:[0],
                    data:"picUrl",
                    render: function(data, type, full, meta){
                        return '<img src="'+data+'" height="100" width="100"/>';
                    }
                },
                {
                    orderable:false,
                    targets:[0],
                    data:"status",
                    render: function(data,type,full,meta) {
                        var typeMsg = "";
                        switch (data) {
                            case 0:
                            case 3:
                                typeMsg = "未认证";
                                break;
                            case 1:
                                typeMsg = "已通过";
                                break;
                            case 2:
                                typeMsg = "不通过";
                                break;
                        }
                        return typeMsg;
                    }
                },
                {
                    orderable: false,
                    targets: [0],
                    data: "status",
                    render: function(data, type, full, meta){
                        if(full.status == 0 || full.status == 3) {
                            return '<a style="text-decoration:none" onClick="pass(\'' + full.picId + '\')" href="javascript:;" title="审核通过"><i class="Hui-iconfont">&#xe6a7;</i></a> <a title="未通过" href="javascript:;" onclick="unPass(\'' + full.picId + '\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6a6;</i></a> ';
                        } else {
                            return '';
                        }
                    }
                }
            ],
            "language": {
                "lengthMenu": "每页 _MENU_ 条记录",
                "processing": "数据加载中...",
                "paginate": {
                    "previous": "上一页",
                    "next": "下一页"
                },
                "zeroRecords": "没有找到符合条件的数据",
                //  "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
                "info": "当前显示第 _START_ 到 _END_ 项, 共 _TOTAL_ 项",
                "infoEmpty": "无记录",
                "infoFiltered": "(从 _MAX_ 条记录过滤)",
                "search": "搜索："
            }
        });

        studentTable.on( 'draw', function () {
            $("img").click(function(){
                imgShow("#outerdiv", "#innerdiv", "#bigimg", $(this));
            })
        } );
    });

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

    function pass(id){
        AjaxUtil.ajax(APP.WEB_APP_NAME+"/authPicture/passVal//"+id,"get",true,null,function (data) {
            if(data.result){
                layer.msg("操作成功");
                studentTable.ajax.reload();
            } else{
                layer.msg("操作失败");
                studentTable.ajax.reload();
            }
        });
    }

    function unPass(id){
        AjaxUtil.ajax(APP.WEB_APP_NAME+"/authPicture/unPassVal/"+id,"get",true,null,function (data) {
            if(data.result){
                layer.msg("操作成功");
                studentTable.ajax.reload();
            } else{
                layer.msg("操作失败");
                studentTable.ajax.reload();
            }
        });
    }
</script>
</body>
</html>
