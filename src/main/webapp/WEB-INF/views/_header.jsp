<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/7/3
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-black navbar-fixed-top">
        <div class="container-fluid cl">
            <a class="logo navbar-logo f-l mr-10 hidden-xs" href="javascript:void (0)">校园租后台系统</a>
            <a aria-hidden="false" class="nav-toggle visible-xs" href="javascript:;"></a>
            <nav class="nav navbar-nav">
            </nav>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li>${sessionScope.user.userType.desc}</li>
                    <li class="dropDown dropDown_hover"> <a href="javascript:void(0)" class="dropDown_A">${sessionScope.user.object.name}
                        <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:void(0)" onclick="myselfinfo()">个人信息</a></li>
                            <li><a href="javascript:void(0)" onclick="updatePassword()">修改密码</a></li>
                            <li><a href="/SpringWind/manager/exit">退出</a></li>
                        </ul>
                    </li>
                    <li id="Hui-msg">
                        <a href="javascript:void(0)" onclick="showApply()" title="消息">
                            <span class="badge badge-danger"></span>
                            <i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i>
                        </a>
                    </li>
                    <li id="Hui-skin" class="dropDown right dropDown_hover">
                        <a href="javascript:;" class="dropDown_A" title="换肤">
                            <i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                            <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                            <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                            <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                            <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                            <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<script>

    <c:if test="${sessionScope.user.userType.key == 1}">
        $(function () {
            getApplyInfo();
            setInterval("getApplyInfo()", 10000);
        });

        function getApplyInfo() {
            $.ajax({
                url: "exam/applyCourse/getApplyCourseNumber",
                type: "POST",
                dataType: "json",
                success: function (datas) {
                    var s = $("#Hui-msg").find("span");
                    if (datas.applyNumber == 0)
                        $(s).text("");
                    else
                        $(s).text(datas.applyNumber);
                }
            });
        }

        function showApply() {
            layer.open({
                type: 2,
                shade: false,
                area: ['60%','70%'],
                maxmin: true,
                content: "exam/applyCourse/toApplyCourse",
                success: function (layero) {
                    layer.setTop(layero);
                },
                error: function (layero) {
                    console.log("asd");
                }
            });
        }
    </c:if>

    function myselfinfo() {
        var url = "exam/student/myselfInfo";
        layer.open({
            type: 2,
            shade: false,
            area: ['40%','55%'],
            maxmin: true,
            title: "个人信息",
            content: url,
            success: function (layero) {
                layer.setTop(layero);
            },
            error: function (layero) {
                layer.msg("发生了未知的错误", {icon:2,time:2000})
            }
        });
    }
    
    function updatePassword() {
        layer.open({
            type: 2,
            shade: false,
            area: ['40%','55%'],
            maxmin: true,
            title: "个人信息",
            content: "exam/student/toUpdatePassword",
            success: function (layero) {
                layer.setTop(layero);
            },
            error: function (layero) {
                layer.msg("发生了未知的错误", {icon:2,time:2000})
            }
        });
    }
</script>
</body>
</html>
