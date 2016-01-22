$.ajax({
    url: "/index/getProvinceList",
    type: "post",
    dataType: "json",
    success:function(res){
        if (res.data!=null) {
            var arr=res.data;
            $("#province").append('<option value="" >请选择</option>');
            for(var i=0;i<arr.length;i++){
                $("#province").append('<option value="'+arr[i].id+'">'+arr[i].name+'</option>');
            }
        }
    }
});

//选择某个省份动态加载该省份的城市列表
$("#province").change(function () {
    var pid = $("#province option:selected").val();
    $("#town").hide();
    $("#city").html("");
    $.ajax({
        url: "/index/getCityList",
        type: "post",
        dataType: "json",
        data: {"pid": pid},
        success: function (res) {
            $("#city").html("");
            $("#city").append('<option value="">请选择</option>');
            if (res.data!=null) {
                var arr=res.data;
                for(var i=0;i<arr.length;i++){
                    $("#city").append('<option value="'+arr[i].id+'">'+arr[i].name+'</option>');
                }
            }
        }
    });
});
$("#city").change(function () {
    var cid = $("#city option:selected").val();
    $("#town").hide();
    $.ajax({
        url: "/index/getCountyList",
        type: "post",
        dataType: "json",
        data: {"cid": cid},
        success: function (res) {
            $("#country").html("");
            $("#country").append('<option value="">请选择</option>');
            if (res.data!=null) {
                var arr=res.data;
                for(var i=0;i<arr.length;i++){
                    $("#country").append('<option value="'+arr[i].id+'">'+arr[i].name+'</option>');
                }
            }
        }
    });
});
//级联四级
$("#country").change(function () {
    var id = $("#country option:selected").val();
    $.ajax({
        url: "/index/getTownList",
        type: "post",
        dataType: "json",
        data: {"id": id},
        success: function (res) {
            $("#town").append('<option value="">请选择</option>');
            if ( res.data!=null) {
                if(res.data.length > 0){
                    $("#town").show();
                    $("#town").html("");
                    var arr=res.data;
                    for(var i=0;i<arr.length;i++){
                        $("#town").append('<option value="'+arr[i].id+'">'+arr[i].name+'</option>');
                    }
                }
            }else{
                $("#town").html("");
            }
        }
    });
});