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
    <title>提现列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 财务管理 <span class="c-gray en">&gt;</span> 提现列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <table class="table table-border table-bordered table-bg display" id="idleTable">
        <thead>
        <tr>
            <th scope="col" colspan="15">提现列表</th>
        </tr>
        <tr class="text-c">
            <th width="90">用户</th>
            <th width="60">提现平台</th>
            <th width="140">账号</th>
            <th width="60">金额</th>
            <th width="40">状态</th>
            <th width="60">请求日期</th>
        </tr>
        </thead>
    </table>
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
    var idleTable;
    $(function(){

        idleTable=$("#idleTable").DataTable({
            ajax:{
                url: APP.WEB_APP_NAME+'/capitalCash/list'
            },
            "serverSide": true,
            "destroy": true,
            "pageLength": 10,
            "ordering": false,
            "autoWidth": true,
            "stateSave": false,//保持翻页状态，和comTable.fnDraw(false);结合使用
            "searching": true,//datatables默认搜索
            columns:[
                {
                    orderable: false,
                    targets: [0],
                    data: 'student.userName',
                    render: function(data, type, full, meta){
                        debugger;
                        var str = "<a href=javascript:chargeEdit(\'详细信息\',\'/capitalCash/toForm"+"\',\'"+full.cashId+"\') title='"+data+"'>"+data+"</a>";
                        return str;
                    }
                },
                {
                    orderable: false,
                    targets: [0],
                    data: 'source',
                    render: function(data, type, full, meta){
                        var str = '';
                        switch (data){
                            case 0:
                                str = '支付宝';
                                break;
                            case 1:
                                str = '微信';
                                break;
                        }
                        return str;
                    }
                },
                {data:'account'},
                {data: 'capital'},
                {
                    orderable: false,
                    targets: [0],
                    data: "status",
                    render: function(data, type, full, meta){
                        var str = "";
                        switch (data){
                            case 0:
                                str = "未处理";
                                break;
                            case 1:
                                str = "通过";
                                break;
                            case 2:
                                str = "未通过";
                                break;
                        }
                        return str;
                    }
                },
                {data: 'requestTime'}
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
    })

    function chargeEdit(title,url,id,w,h){
        url =  APP.WEB_APP_NAME+url;
        layer_show(title,url,500,400);
        var ajaxUrl =  APP.WEB_APP_NAME+'/capitalCash/id/'+id;
        $(window.layer).attr('data-url',ajaxUrl);
    }


</script>
</body>
</html>
