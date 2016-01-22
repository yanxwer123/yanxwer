/*****************************************************收货地址*****************************************************************/
//初始化省下拉列表
$(function(){
    $("#town").hide();
    $("#province").html("");
    curprovince = $("#provinceid").data("provinceid");
    curcounty = $("#countyid").data("countyid");
    curcity = $("#cityid").data("cityid");
    curtown = $("#townid").data("townid");

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
            back_addrp();
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
                back_addrc(curcity);
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
                back_addrco(curcounty);
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
                back_addrt(curtown);
            }
        });
    });

    //遍历回填级联菜单
    function back_addrp() {


        if (curprovince) {
            var count = $("#province option").length;
            for (var i = 0; i < count; i++) {
                if ($("#province").get(0).options[i].value == curprovince) {
                    $("#province").get(0).options[i].selected = true;
                }
            }
        }
        $("#province").change();
    }
    function back_addrc(curcity) {

        if(curcity){
            var count = $("#city option").length;
            for ( var i = 0; i < count; i++) {
                if ($("#city").get(0).options[i].value == curcity) {
                    $("#city").get(0).options[i].selected = true;
                }
            }
        }
        $("#city").change();
    }
    function back_addrco(curcounty) {

        if(curcounty){
            var count = $("#country option").length;
            for ( var i = 0; i < count; i++) {
                if ($("#country").get(0).options[i].value == curcounty) {
                    $("#country").get(0).options[i].selected = true;
                }
            }
        }
        $("#country").change();
    }
    function back_addrt(curtown) {

        if(curtown){
            var count = $("#town option").length;
            for ( var i = 0; i < count; i++) {
                if ($("#town").get(0).options[i].value == curtown) {
                    $("#town").get(0).options[i].selected = true;
                }
            }
        }
    }
});
/*****************************************************上门服务地址*****************************************************************/
//初始化省下拉列表
$(function(){
    $("#stown").hide();
    $("#sprovince").html("");
    scurprovince = $("#provinceid").data("provinceid");
    scurcounty = $("#countyid").data("countyid");
    scurcity = $("#cityid").data("cityid");
    scurtown = $("#townid").data("townid");

    $.ajax({
        url: "/index/getProvinceList",
        type: "post",
        dataType: "json",
        success:function(res){
            if (res.data!=null) {
                var arr=res.data;
                $("#sprovince").append('<option value="" >请选择</option>');
                for(var i=0;i<arr.length;i++){
                    $("#sprovince").append('<option value="'+arr[i].id+'">'+arr[i].name+'</option>');
                }
                sback_addrp();
            }
        }
    });

    //选择某个省份动态加载该省份的城市列表
    $("#sprovince").change(function () {
        var pid = $("#sprovince option:selected").val();
        $("#stown").hide();
        $("#scity").html("");
        $.ajax({
            url: "/index/getCityList",
            type: "post",
            dataType: "json",
            data: {"pid": pid},
            success: function (res) {
                $("#scity").html("");
                $("#scity").append('<option value="">请选择</option>');
                if (res.data!=null) {
                    var arr=res.data;
                    for(var i=0;i<arr.length;i++){
                        $("#scity").append('<option value="'+arr[i].id+'">'+arr[i].name+'</option>');
                    }
                }
                sback_addrc(scurcity);
            }
        });
    });
    $("#scity").change(function () {
        var cid = $("#scity option:selected").val();
        $("#stown").hide();
        $.ajax({
            url: "/index/getCountyList",
            type: "post",
            dataType: "json",
            data: {"cid": cid},
            success: function (res) {
                $("#scountry").html("");
                $("#scountry").append('<option value="">请选择</option>');
                if (res.data!=null) {
                    var arr=res.data;
                    for(var i=0;i<arr.length;i++){
                        $("#scountry").append('<option value="'+arr[i].id+'">'+arr[i].name+'</option>');
                    }
                }
                sback_addrco(scurcounty);
            }
        });
    });
    //级联四级
    $("#scountry").change(function () {
        var id = $("#scountry option:selected").val();
        $.ajax({
            url: "/index/getTownList",
            type: "post",
            dataType: "json",
            data: {"id": id},
            success: function (res) {
                $("#stown").append('<option value="">请选择</option>');
                if (res.data!=null) {
                    if(res.data.length > 0){
                        $("#stown").show();
                        $("#stown").html("");
                        var arr=res.data;
                        for(var i=0;i<arr.length;i++){
                            $("#stown").append('<option value="'+arr[i].id+'">'+arr[i].name+'</option>');
                        }
                    }
                }else{
                    $("#stown").html("");
                }
                sback_addrt(scurtown);
            }
        });
    });

    //遍历回填级联菜单
    function sback_addrp() {


        if (scurprovince) {
            var count = $("#sprovince option").length;
            for (var i = 0; i < count; i++) {
                if ($("#sprovince").get(0).options[i].value == scurprovince) {
                    $("#sprovince").get(0).options[i].selected = true;
                }
            }
        }
        $("#sprovince").change();
    }
    function sback_addrc(scurcity) {

        if(scurcity){
            var count = $("#scity option").length;
            for ( var i = 0; i < count; i++) {
                if ($("#scity").get(0).options[i].value == scurcity) {
                    $("#scity").get(0).options[i].selected = true;
                }
            }
        }
        $("#scity").change();
    }
    function sback_addrco(scurcounty) {

        if(scurcounty){
            var count = $("#scountry option").length;
            for ( var i = 0; i < count; i++) {
                if ($("#scountry").get(0).options[i].value == scurcounty) {
                    $("#scountry").get(0).options[i].selected = true;
                }
            }
        }
        $("#scountry").change();
    }
    function sback_addrt(scurtown) {

        if(scurtown){
            var count = $("#stown option").length;
            for ( var i = 0; i < count; i++) {
                if ($("#stown").get(0).options[i].value == scurtown) {
                    $("#stown").get(0).options[i].selected = true;
                }
            }
        }
    }
});



