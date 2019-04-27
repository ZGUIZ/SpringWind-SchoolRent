<%--
  Created by IntelliJ IDEA.
  User: Amia
  Date: 2019/4/3
  Time: 17:37
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
    <title>学生列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户管理 <span class="c-gray en">&gt;</span> 用户列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
<%--
    <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="delStudent()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="student_add('添加学生','/student/toForm','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加学生</a></span></div>
--%>
    <table class="table table-border table-bordered table-bg display" id="student">
        <thead>
        <tr>
            <th scope="col" colspan="15">用户列表</th>
        </tr>
        <tr class="text-c">
<%--
            <th  width="3px" orderable="false" text-align="center"><input type="checkbox" name="allChecked"/></th>
--%>
            <th width="100">学校</th>
            <th width="40">省份</th>
            <th width="40">城市</th>
            <th width="90">学号</th>
            <th width="90">用户名</th>
            <th width="90">真实姓名</th>
            <th width="40">性别</th>
            <th width="100">手机号码</th>
            <th width="150">邮箱</th>
            <th width="40">信誉</th>
            <th width="40">认证等级</th>
            <th width="40">状态</th>
            <th width="100">注册日期</th>
            <th width="100">操作</th>
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
    var host = window.location.host;
    var studentTable;
    $(function(){

        studentTable=$("#student").DataTable({
            ajax:{
                url: APP.WEB_APP_NAME+'/student/queryListByPage'
            },
            "serverSide": true,
            "destroy": true,
            "pageLength": 10,
            "ordering": false,
            "autoWidth": true,
            "stateSave": false,//保持翻页状态，和comTable.fnDraw(false);结合使用
            "searching": true,//datatables默认搜索
            columns:[
                /*{
                    orderable: false,
                    targets: [0],
                    data: "userId",
                    render: function(data, type, full, meta){
                        return '<input type="checkbox" name="userId" value="'+data+'"/>';
                    }
                },*/
                {data: 'school.schoolName'},
                {data: 'school.province'},
                {data: 'school.city'},
                {data: 'studentId'},
                /*{data: 'userName'},*/
                {
                    orderable: false,
                    targets: [0],
                    data: 'userName',
                    render: function(data, type, full, meta){
                        var str = "<a href=javascript:student_edit('详细信息','/student/toForm"+"','"+full.userId+"','','510') title='"+data+"'>"+data+"</a>";
                        return str;
                    }
                },
                {data: 'realName'},
                {data: 'sex'},
                {data: 'telephone'},
                {data: 'email'},
                {data: 'credit'},
                {data: 'authLevel'},
                {
                    orderable: false,
                    targets: [0],
                    data: "status",
                    render: function(data, type, full, meta){
                        switch (data){
                            case 0:
                                return '未认证';
                            case 1:
                                return '正常';
                            case 2:
                                return '禁止登陆';
                        }
                        return '';
                    }
                },
                {data: 'createDate'},
                {
                    orderable: false,
                    targets: [0],
                    data: "status",
                    render: function(data, type, full, meta){
                        return '<a style="text-decoration:none" onClick="studentStart(this,\''+data+'\',\''+full.userId+'\')" href="javascript:;" title="禁止登陆"><i class="Hui-iconfont">&#xe631;</i></a>';
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
    })
    /*
        参数解释：
        title	标题
        url		请求的url
        id		需要操作的数据id
        w		弹出层宽度（缺省调默认值）
        h		弹出层高度（缺省调默认值）
    */
    /*管理员-增加*/
    function student_add(title,url,w,h){
        url =  APP.WEB_APP_NAME + url+"/id/undefined";
        layer_show(title,url,w,h);
        var ajaxUrl =  APP.WEB_APP_NAME+ '/student/add';
        $(window.layer).attr('data-url',ajaxUrl);
    }

    /*管理员-编辑*/
    function student_edit(title,url,id,w,h){
        url =  APP.WEB_APP_NAME+url+"/id/"+id;
        layer_show(title,url,w,h);
        var ajaxUrl =  APP.WEB_APP_NAME+'/student/id/'+id;
        $(window.layer).attr('data-url',ajaxUrl);
    }

    function resetPassword(id){
        layer.confirm("确定要重置密码？",function(index){
            AjaxUtil.ajax( APP.WEB_APP_NAME+'/student/resetPasssWord/id/'+id,'get',true,null,function (data) {
                if(data.result){
                    layer.msg('已经重置密码!',{icon: 6,time:1000});
                } else{
                    layer.msg("重置失败："+data.msg,{icon:5,time:1000});
                }

            });
        });
    }

    function studentStart(obj,status,userId){
        layer.confirm('确认要禁止该用户登录吗？',function(index){
            status = 2;
            var student = {};
            student.userId = userId;
            student.status = status;

            $.ajax({
                type: 'POST',
                url:  APP.WEB_APP_NAME+'/student/updateStudent',
                contentType:'application/json;charset=UTF-8',
                dataType: 'json',
                data: JSON.stringify(student),
                success: function(data){
                    if(data.result){
                        layer.msg('已修改!',{icon: 6,time:1000});
                    } else{
                        layer.msg("修改失效",{icon:5,time:1000});
                    }

                    studentTable.ajax.reload();
                },
                error:function(data) {
                    console.log(data.msg);
                },
            });
        });
    }

    function delStudent() {
        var studentList = new Array();
        var selected =$("input:checkbox[name='userId']:checked");
        for(var i=0;i<selected.length;i++){
            var student = {};
            student.userId = $(selected[i]).val();
            student.status = 100;
            studentList.push(student);
        }

        AjaxUtil.ajax( APP.WEB_APP_NAME+'/student/del','POST',true,studentList,function (data) {
            debugger;
            if(data.result){
                layer.msg('删除成功!',{icon: 6,time:1000});
            } else{
                layer.msg("删除失败:"+data.msg,{icon:5,time:1000});
            }
            studentTable.ajax.reload();
        });
    }
</script>
</body>
</html>