/**
 * Created by Dan on 2015/7/31.
 */



/** 积分商城后台管理公共引用 **/

var JobDict = [{"value":1,"text":"经理"},{"value":2,"text":"员工"}];
var Gender=[{"value":1,"text":"男"},{"value":2,"text":"女"}];
var ProductState=[{"value":-1,"text":"全部"},{"value":1,"text":"上架"},{"value":0,"text":"下架"}];
var Channel=[{"value":-1,"text":"全部"},{"value":0,"text":"WEB"},{"value":1,"text":"微信"},{"value":2,"text":"APP"}];
var formatChannel=function(value){
    for(var key in Channel){
        if(Channel[key].value==value){
            return Channel[key].text;
        }
    }
    return "";
}
var OrderState=[{"value":0,"text":"全部"},{"value":1,"text":"未支付"},{"value":2,"text":"支付中"},
    {"value":3,"text":"已支付"},{"value":4,"text":"待收货"},{"value":5,"text":"已妥投"},{"value":6,"text":"已拒收"},{"value":7,"text":"已废弃"},{"value":8,"text":"已取消"},{"value":9,"text":"已完成"}];
var formatOrderState =function(value){
    for (var key in OrderState) {
        if (OrderState[key].value == value) {
            return OrderState[key].text;
        }
    }
    return "";
}
var ExceptionCase=[{"value":-1,"text":"全部"},{"value":"1","text":"积分商城未响应"},{"value":"2","text":"卡系统对账文件与交易查询结果不一致"},{"value":"3","text":"交易积分额不一致"},
    {"value":"4","text":"油卡号不一致"},{"value":"5","text":"卡系统对账文件未包含该订单"},{"value":"6","text":"合作商金额与积分商城不同"},{"value":"7","text":"合作商下单失败"},{"value":"8","text":"其它异常"}];
var ExceptionState=[{"value":-1,"text":"全部"},{"value":"1","text":"待处理"},{"value":"2","text":"待审核"},{"value":"3","text":"已完成"},{"value":"4","text":"拒绝"}];
var ExceptionProcessType=[{"value":"1","text":"提交给合作商发货"},{"value":"2","text":"其他异常处理"}];
var OrderExceptionState=[{"value":"5","text":"妥投"},{"value":"6","text":"拒收"}];
var RefuseOrderProcessType=[{"value":"1","text":"补单给合作商"},{"value":"2","text":"修改状态"},{"value":"3","text":"其他处理"}];
var SOrderProcessState=[{"value":"0","text":"未处理"},{"value":"1","text":"已处理"}];
var formatSOrderProcessState =function(value){
    for (var key in SOrderProcessState) {
        if (SOrderProcessState[key].value == value) {
            return SOrderProcessState[key].text;
        }
    }
    return "";
}

var ActType=[{"value":1,"text":"月抽奖"},{"value":2,"text":"积分抽奖"},{"value":3,"text":"砸金蛋"}];
var formatActType =function(value){
    for (var key in ActType) {
        if (ActType[key].value == value) {
            return ActType[key].text;
        }
    }
    return "";
}

var ActState=[{"value":1,"text":"未发布"},{"value":2,"text":"已发布"},{"value":3,"text":"已结束"}];
var formatActState =function(value){
    for (var key in ActState) {
        if (ActState[key].value == value) {
            return ActState[key].text;
        }
    }
    return "";
}


var ActOpenState=[{"value":1,"text":"未抽奖"},{"value":2,"text":"抽奖中"},{"value":3,"text":"已抽奖"},{"value":4,"text":"已发布"}];
var formatActOpenState =function(value){
    for (var key in ActOpenState) {
        if (ActOpenState[key].value == value) {
            return ActOpenState[key].text;
        }
    }
    return "";
}


var PrizeType=[{"value":1,"text":"实体"},{"value":2,"text":"虚拟"}];

var formatPrizeType =function(value){
    for (var key in PrizeType) {
        if (PrizeType[key].value == value) {
            return PrizeType[key].text;
        }
    }
    return "";
}

var formatWinRate=function(val){
    if(val){
        return val+"%";
    }
    return "";
}

//region 时间扩展格式化.
Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
//endregion


$.fn.datebox.defaults.parser = function(s){
    var t = Date.parse(s);
    if(isNaN(t)){
        t=parseInt(s);
    }
    if (!isNaN(t)){
        return new Date(t);
    } else {
        return new Date();
    }
}


//region 日期格式化方法
function formatDate(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
        if (isNaN(dt)) {
            value = value.replace(/\/Date\((-?\d+)\)\//, '$1'); //标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式
            dt = new Date();
            dt.setTime(value);
        }
    }
    format="yyyy/MM/dd";
    return dt.format(format); //这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法，在后面的步骤3定义
}

function formatDateDefined(value,format) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
        if (isNaN(dt)) {
            value = value.replace(/\/Date\((-?\d+)\)\//, '$1'); //标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式
            dt = new Date();
            dt.setTime(value);
        }
    }
    if(format){

    }else{
        format="yyyy/MM/dd";
    }
    return dt.format(format); //这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法，在后面的步骤3定义
}

//endregion

//region 时间添加天数
function GetDateAddDays(date, days) {
    var day = Number(date.getDate()) + days;
    var currentDate = new Date(date.getFullYear(), date.getMonth(), day);
    return currentDate;
}
//endregion


//region 获取查询字符串
function getSearchParam() {
    var sp = "?sf="+Math.random();
    $("#ps input[class^='easyui']").each(function (i, e) {
        var je = $(e);
        if (je && je.hasClass('easyui-textbox')) {
            if (je.textbox('getValue')) {
                sp += '&' + e.id + "=" + escape(je.textbox('getValue'));
            }
        }
        if (je && je.hasClass('easyui-datebox')) {
            if (je.datebox('getValue')) {
                sp += '&' + e.id + "=" + je.datebox('getValue');
            }
        }
        if (je && (je.hasClass('easyui-combobox'))) {
            if (je.combobox('getValue')) {
                sp += '&' + e.id + "=" + je.combobox('getValue');
            }
        }
        if (je && je.hasClass('easyui-combogrid')) {
            if (je.combogrid('getValue')) {
                sp += '&' + e.id + "=" + je.combogrid('getValue');
            }
        }

        if (je && je.hasClass('easyui-combotree')) {
            if (je.combotree('getValue')) {
                sp += '&' + e.id + "=" + je.combotree('getValue');
            }
        }
    });
    return sp;
}
function resetSearch() {
    $("#ps input[class^='easyui']").each(function (i, e) {
        var je = $(e);
        if (je && je.hasClass('easyui-textbox')) {
            if (je.textbox('getValue')) {
                je.textbox('setValue', '')
            }
        }
        if (je && je.hasClass('easyui-datebox')) {
            if (je.datebox('getValue')) {
                je.datebox('setValue', '');
            }
        }
        if (je && (je.hasClass('easyui-combobox'))) {
            if (je.combobox('getValue')) {
                je.combobox('setValue', '');
            }
        }
        if (je && je.hasClass('easyui-combogrid')) {
            if (je.combobox('getValue')) {
                je.combobox('clear');
            }
        }
    });
}

//endregion

//region 正则表达式验证
var regCHWord = /[^\u4e00-\u9fa5]+/;  //验证是否为汉字
var regUsername = /[A-Za-z0-9_]+/;  //验证是否用户名
var regPwd = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,22}$/; //验证密码
$.extend($.fn.validatebox.defaults.rules, {
    username: {
        validator: function(value, param){
           return /^[a-zA-Z]+[A-Za-z0-9_]*$/.test(value);
            //return regUsername.test(value);
        },
        message: '输入内容必须以字母开头，字母、数字和下划线组成'
    },
    phoneRex: {
        validator: function(value){
            var rex=/^1[3-8]+\d{9}$/;
            //var rex=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
            //区号：前面一个0，后面跟2-3位数字 ： 0\d{2,3}
            //电话号码：7-8位数字： \d{7,8
            //分机号：一般都是3位数字： \d{3,}
            //这样连接起来就是验证电话的正则表达式了：/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/
            var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
            if(rex.test(value)||rex2.test(value))
            {
                // alert('t'+value);
                return true;
            }else
            {
                //alert('false '+value);
                return false;
            }

        },
        message: '请输入正确电话或手机格式'
    },
    password:{
        validator: function(value, param){
            return regPwd.test(value);
        },
        message: '密码由6至22位字母、数字或其他特殊符号(!#$%^&*.~@)组成'
    }

});

//endre