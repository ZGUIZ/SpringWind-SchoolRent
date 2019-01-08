var AjaxUtil = {};
AjaxUtil.ajax = function(url,type,async,param,callback){
    $.ajax({
        type: type,
        url: url,
        async: async,
        contentType:'application/json;charset=UTF-8',
        data: JSON.stringify(param),
        dataType: "json",
        success: callback,
        error: function (data) {
            console.log(data);
        }
    });
}