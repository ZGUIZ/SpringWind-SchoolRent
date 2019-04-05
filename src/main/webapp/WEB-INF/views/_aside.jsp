<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/7/3
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<aside class="Hui-aside">
        <div class="menu_dropdown bk_2">
        <c:if test="${sessionScope.manager.role == 1 || sessionScope.manager.role == 2}" >
            <dl id="menu-student-exam">
                <dt><i class="Hui-iconfont">&#xe616;</i> 商品管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="/SpringWind/idleInfo/toList" data-title="商品列表" href="javascript:void(0)">商品列表</a></li>
                        <li><a data-href="/SpringWind/idleInfo/toDel" data-title="禁止显示" href="javascript:void(0)">已删除</a></li>
                    </ul>
                </dd>
            </dl>
            <dl id="menu-student-exam">
                <dt><i class="Hui-iconfont">&#xe616;</i> 帖子管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="/SpringWind/student/toList" data-title="帖子列表" href="javascript:void(0)">帖子列表</a></li>
                        <li><a data-href="/SpringWind/authPicture/toList" data-title="已删除" href="javascript:void(0)">已删除</a></li>
                    </ul>
                </dd>
            </dl>
            <dl id="menu-student-exam">
                <dt><i class="Hui-iconfont">&#xe616;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="/SpringWind/student/toList" data-title="用户列表" href="javascript:void(0)">用户列表</a></li>
                        <li><a data-href="/SpringWind/authPicture/toList" data-title="身份审核" href="javascript:void(0)">身份审核</a></li>
                        <li><a data-href="/SpringWind/student/toUnLogin" data-title="禁止登陆" href="javascript:void(0)">禁止登陆</a></li>
                    </ul>
                </dd>
            </dl>
        </c:if>
        <c:if test="${sessionScope.manager.role == 1 || sessionScope.manager.role == 3}" >
            <dl id="menu-student-exam">
                <dt><i class="Hui-iconfont">&#xe616;</i> 财务管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="" data-title="充值列表" href="javascript:void(0)">充值列表</a></li>
                        <li><a data-href="" data-title="提现列表" href="javascript:void(0)">提现列表</a></li>
                    </ul>
                </dd>
            </dl>
        </c:if>
        <c:if test="${sessionScope.manager.role == 1}" >
            <dl id="menu-student-exam">
                <dt><i class="Hui-iconfont">&#xe616;</i> 管理员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="" data-title="管理员列表" href="javascript:void(0)">管理员列表</a></li>
                        <li><a data-href="" data-title="禁止登录" href="javascript:void(0)">禁止登录</a></li>
                    </ul>
                </dd>
            </dl>
        </c:if>
        <dl id="menu-student-exam">
            <dt><i class="Hui-iconfont">&#xe616;</i> 投诉管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <c:if test="${sessionScope.manager.role == 1 || sessionScope.manager.role ==  2}" >
                        <li><a data-href="" data-title="商品投诉" href="javascript:void(0)">商品投诉</a></li>
                        <li><a data-href="" data-title="身份审核" href="javascript:void(0)">帖子投诉</a></li>
                    </c:if>
                    <c:if test="${sessionScope.manager.role == 1 || sessionScope.manager.role ==  3}" >
                        <li><a data-href="" data-title="租赁投诉" href="javascript:void(0)">租赁投诉</a></li>
                    </c:if>
                </ul>
            </dd>
        </dl>
    </div>
</aside>
</body>
</html>
