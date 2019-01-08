var Select2Extend = {}

Select2Extend.loadSelectAsync = function(id,url){
    $.ajax({
        type: 'get',
        async : false,
        url: url,
        dataType: "json",
        success: function (data) {
            Select2Extend.loadSelect(id,data);
        },
        error: function (data) {
            console.log(data);
        }
    });
}

Select2Extend.loadSelect = function(id,data){
    $("#"+id).empty()
    $("#"+id).select2({
        placeholder: "请选择",
        allowClear: true,
        data : data
    });
}