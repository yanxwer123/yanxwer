function applayRepair(num,pickwareType,description,province,city,country,town,address,recieveaddr,sprovince,scity,scountry,stown,saddress,addr,customerContactName,customerTel,customerExpect){
    /*returnOrder*/
    var orderid=$('#orderid').text();
    var productcode=$('#productcode').data("productcode")===undefined?"":$('#productcode').data("productcode");
    var suborderid=$('#suborderid').data("suborderid")===undefined?"":$('#suborderid').data("suborderid");
    var thirdorderid=$('#thirdorderid').data("thirdorderid")===undefined?"":$('#thirdorderid').data("thirdorderid");
    var thirdcode=$('#thirdcode').data("thirdcode")===undefined?"":$('#thirdcode').data("thirdcode");

    var picimgurlarray=new Array();
    $("#thelist span").each(function(){
        var srcString = $(this).data("src").replace(/,/g, "\,");
        picimgurlarray.push(srcString);
    });
    console.log(picimgurlarray);
    var picimgurl=picimgurlarray.join("，");//图片的上传地址，可返回多个图片的地址
    /*asSafeDto*/
    var jdOrderId=thirdorderid;

    var questionDesc=description;
    var isNeedDetectionReport=$("#isNeedDetectionReport").data("isNeedDetectionReport")===undefined?"":$("#isNeedDetectionReport").data("isNeedDetectionReport");//是否需要检测报告
    var questionPic=picimgurl
    var isHasPackage=$("#isHasPackage").data("isHasPackage")===undefined?"":$("#isHasPackage").data("isHasPackage");//是否有包装
    var packageDesc=$("#packageDesc").data("packageDesc")===undefined?"":$("#packageDesc").data("packageDesc");//包装描述
    /*asCustomerDto*/

    /*var customerContactName=$("#customerContactName").data("customerContactName")===undefined?"":$("#customerContactName").data("customerContactName");
     var customerMobilePhone=$("#customerMobilePhone").data("customerMobilePhone")===undefined?"":$("#customerMobilePhone").data("customerMobilePhone"); */

    var customerEmail=$("#customerEmail").data("customerEmail")===undefined?"":$("#customerEmail").data("customerEmail");
    var customerPostcode=$("#customerPostcode").data("customerPostcode")===undefined?"":$("#customerPostcode").data("customerPostcode");
    var customerContactName=$("#realname").val();
    var customerMobilePhone=$("#phone").val();
    /*asPickwareDto*/

    var pickwareProvince=$("#sprovince").val();
    var pickwareCity=$("#scity").val();
    var pickwareCounty=$("#scountry").val();
    var pickwareVillage=$("#stown").val();
    var pickwareAddress=$("#saddress").val();
    /*asReturnwareDto*/
    var returnwareType=$("#province").val();
    var returnwareProvince=$("#province").val();
    var returnwareCity=$("#city").val();
    var returnwareCounty=$("#country").val();
    var returnwareVillage=$("#town").val();
    var returnwareAddress=$("#address").val();
    /*asDetailDto*/
    var skuId=thirdcode;
    var skuNum=num;
    /*  var returnOrderStr='{"returnOrder":{"orderid":'+orderid+',"productcode":"'+productcode+'","suborderid":'+suborderid+',"thirdorderid":"'+thirdorderid+'","recieveaddr":"'+recieveaddr+'","addr":"'+addr+'","num":'+num+',' +
     '"description":"'+description+'","picimgurl":"'+picimgurl+'"},"asSafeDto",{"jdOrderId":'+jdOrderId+',"customerExpect":'+customerExpect+',"questionDesc":"'+questionDesc+'",' +
     '"isNeedDetectionReport": '+isNeedDetectionReport+',"questionPic":'+questionPic+',"isHasPackage":'+isHasPackage+',"packageDesc":'+packageDesc+'},"asCustomerDto":{"customerContactName": "'+customerContactName+'",' +
     '"customerTel":"'+customerTel+'","customerMobilePhone":"'+customerMobilePhone+'","customerEmail":"'+customerEmail+'","customerPostcode":"'+customerPostcode+'"},' +
     '"asPickwareDto":{"pickwareType":'+pickwareType+',"pickwareProvince":'+pickwareProvince+',"pickwareCity":'+pickwareCity+',"pickwareCounty":'+pickwareCounty+',"pickwareVillage":'+pickwareVillage+',' +
     '"pickwareAddress":"'+pickwareAddress+'"},"asReturnwareDto":{" returnwareType":'+returnwareType+',"returnwareProvince":'+returnwareProvince+',"returnwareCity":'+returnwareCity+',' +
     '"returnwareCounty":'+returnwareCounty+',"returnwareVillage":'+returnwareVillage+',"returnwareAddress":"'+returnwareAddress+'"},"asDetailDto":{"skuId":'+skuId+',"skuNum":'+skuNum+'}}';*/
    var returnOrderStr='{"returnOrder":{"orderid":"'+orderid+'","productcode":"'+productcode+'","suborderid":"'+suborderid+'","thirdorderid":"'+thirdorderid+'","recieveaddr":"'+recieveaddr+'","addr":"'+addr+'","num":"'+num+'","description":"'+description+'","picimgurl":"'+picimgurl+'"},' +
        '"asSafeDto":{"jdOrderId":"'+jdOrderId+'","questionPic":"'+questionPic+'","customerExpect":"'+customerExpect+'","questionDesc":"'+questionDesc+'"},"asCustomerDto":{"customerContactName":"'+customerContactName+'","customerTel":"'+customerTel+'"},"asPickwareDto":{"pickwareType":'+pickwareType+',' +
        '"pickwareProvince":'+pickwareProvince+',"pickwareCity":'+pickwareCity+',"pickwareCounty":"'+pickwareCounty+'","pickwareVillage":"'+pickwareVillage+'","pickwareAddress":"'+pickwareAddress+'"},"asReturnwareDto":{"returnwareType":"'+returnwareType+'","returnwareProvince":"'+returnwareProvince+'",' +
        '"returnwareCity":"'+returnwareCity+'","returnwareCounty":"'+returnwareCounty+'","returnwareVillage":"'+returnwareVillage+'","returnwareAddress":"'+returnwareAddress+'"},"asDetailDto":{"skuId":'+skuId+',"skuNum":"'+skuNum+'"}}';

    $.ajax({
        url: "/returnorder/saveAfsApply",
        type: "post",
        dataType: "json",
        data: {"returnOrderStr": returnOrderStr},
        success: function (res) {
            if (res.result) {
                alert("申请已成功提交。");
                window.location.href="/order/orderlist";
            }else{
                alert(res.msg);
            }
        }
    });
}

$(function(){
    var t = $("#text_box");
    //购买数量
    var num = parseInt($('#num').html());
    $("#add").click(function(){
        if(isNaN(t.val())){
            t.val(parseInt(0));
        }else if(t.val() < num){
            t.val(parseInt(t.val())+1);
        }
        setTotal();
    })
    $("#min").click(function(){
        if(isNaN(t.val())){t.val(parseInt(0));}
        if(parseInt(t.val())>0) {
            t.val(parseInt(t.val()) - 1);
            setTotal();
        }
    })
    function setTotal(){
        $("#total").html((parseInt(t.val())*3.95).toFixed(2));
    }
    setTotal();
});

(function(){
    //上传控件
    var state = 'pending',
        uploader = WebUploader.create({
            swf: '/../../resource/js/Uploader.swf',
            // 不压缩image
            resize: false,
            // 文件接收服务端。
            server: '/returnorder/uploadPic',
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            accept: { title: 'Images',extensions: 'gif,jpg,jpeg,bmp,png',mimeTypes: 'image/*' },
            pick: { id: '#picker', multiple: false ,innerHTML:'点击选择图片'},
            auto:true //设置自动上传文件
        });

    uploader.on('fileQueued', function (file) {
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                return;
            }
            $("#thelist").append('<img src="' + src + '">');
        }, 90, 90);

    });
    //捕获错误提示
    uploader.on('error', function(reason){
        if(reason=='Q_TYPE_DENIED'){
            $("#msg_upload").empty().html("<font color='red'>文件类型错误</font>");
        }
    })
    // 成功前会派送一个事件uploadAccept，这个事件是用来询问是否上传成功的
    uploader.on('uploadAccept', function (file, response) {
        if (!response.result) {
            // 通过return false来告诉组件，此文件上传有错。
            $("#msg_upload").empty().html("文件上传出错");
            return false;
        }
        else {
            $("#msg_upload").empty().html("文件上传成功!");
           /* $("#picimg").val(response.msg);*/
            $("#thelist").append('<span style="display: none;" data-src="'+response.msg+'"></span>');
            return true;
        }
    });

})();