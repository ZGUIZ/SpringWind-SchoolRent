<%--
  Created by IntelliJ IDEA.
  User: Amia
  Date: 2019/4/25
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>

<div class="col-xs-12 col-sm-12">
    <div style="width: 60%;height:50%" id="person">

    </div>
</div>
<script src="lib/echarts/3.4.0/echarts.min.js"></script>
<script type="text/javascript" src="lib/jquery/2.1.1/jquery.min.js"></script>
<script src="static/js/common.js" type="text/javascript"></script>
<script src="static/js/AjaxUtil.js" type="text/javascript"></script>

<script type="text/javascript">

    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('person'));

        var url = APP.WEB_APP_NAME + "/student/userCount"
        AjaxUtil.ajax(url,'get',false,null,function (data) {
            if(data.result == true){
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户数图'
                    },
                    tooltip: {},
                    legend: {
                        data:['人数']
                    },
                    xAxis: {
                        data: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
                    },
                    yAxis: {},
                    series: [{
                        name: '销量',
                        type: 'bar',
                        data: data.data
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });

    })
</script>
</body>
</html>
