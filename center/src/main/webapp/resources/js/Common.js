/*
Common.js
Author:NY
*/
function LoadChart(chartid, queryurl, pram) {
    var parm2 = {
        DTime: $('#year').val() + "" + $('#month').val()
    };
    $.ajax({
        type: "GET",
        dataType: (pram == 1 || pram == 2) ? "text" : "json",
        url: queryurl,
        data: parm2,
        success: function (result) {
            if (pram == 1) {
                $("#" + chartid).text(result);
            }
            if (pram == 2) {
                $("#" + chartid).append(result);
            }
            if (pram == 0) {
                var myChart = echarts.init(document.getElementById(chartid));
                myChart.clear();
                myChart.setOption(result);
                //myChart.render();
                window.onresize = myChart.resize;
            }
        }
    });
}

function LoadChart(chartid, queryurl, param, type) {
    $.ajax({
        type: "GET",
        dataType: (type == 1 || type == 2) ? "text" : "json",
        url: queryurl,
        data: param,
        success: function (result) {
            if (type == 1) {
                $("#" + chartid).text(result);
            }
            if (type == 2) {
                $("#" + chartid).html(result);
            }
            if (type == 0) {
                var myChart = echarts.init(document.getElementById(chartid));
                myChart.clear();
                myChart.setOption(result);
                window.onresize = myChart.resize;
                //myChart.render();
            }
        }
    });
}

function LoadYearandMonth(yearctrl, monthctrl) {
    LoadYear(yearctrl);
    LoadMonth(monthctrl);
}
function LoadYear(yearCtrlId) {
    var ac = new Array();
    var d = new Date();
    var y = d.getYear() < 1900 ? (d.getYear() + 1900) : d.getYear();
    var m = d.getMonth();
    for (var i = 2012; i <= y; i++) {
        ac.push(i);
    }
    LoadSelect(ac, y, yearCtrlId);
}

function LoadMonth(monthCtrlId) {
    var ac = new Array();
    var d = new Date();
    var m = d.getMonth();
    for (var i = 1; i <= 12; i++) {
        if (i < 10) {
            i = "0" + i;
        }
        ac.push(i);
    }
    LoadSelect(ac, m, monthCtrlId);
}

function LoadSelect(array, selected, tagert) {
    for (var i = 0; i < array.length; i++) {
        var item;
        if (array[i] == selected) {
            item = '<option selected value=' + array[i] + '>' + array[i] + '</option>';
        } else {
            item = '<option value=\'' + array[i] + '\'>' + array[i] + '</option>';
        }
        $(item).appendTo(tagert);
    }
}

function jsonAjax(url, param, dataType, callback) {
    $.ajax({
        type: "get",
        url: url,
        data: param,
        dataType: dataType,
        success: callback,
        error: function () {
            Log('Ajax Error');
        }
    });
}

function Log(param) {
    console.log(param);
}

/*
* ?????? ???????????????
* @city ?????????id
* @area ?????id
* @station ??????id
* */
function Loadcas(city,area,station){
    var par;
    par='parentoucode=100';
    comboxAjax('cc','/web/basic/getorgunitsbypoucode',par,"oucode","ouname",function(op){
   par='parentoucode='+op.oucode;
    comboxAjax('aa','/web/basic/getorgunitsbypoucode',par,"oucode","ouname",function(op1){
        par='parentoucode='+op1.oucode;
        comboxAjax('ss','/web/basic/getorgunitsbypoucode',par,"oucode","ouname",ablank)
    })
});

}


function LoadOrgunit(city,area,station){
    var par;
    comboxAjax('cc','/web/basic/getcitys',par,"code","name",function(op){
        par='cno='+op.code;
        comboxAjax('aa','/web/basic/getareas',par,"code","name",function(op1){
            par='ano='+op1.code;
            comboxAjax('ss','/web/basic/getstation',par,"stationCode","stationName",ablank)
        })
    });
}

function comboxAjax(cid,ajaxUrl,params,valuefield,textfield,onselect){
    $('#' + cid).combobox({
        valueField: valuefield,
        textField: textfield,
        url: ajaxUrl+'?'+params,
       /* queryParams:params,*/
        onSelect:onselect,
        onLoadError: function () {
            Log('combox ' + cid + 'ajax error');
        }
    });
}

function BindDataGridAjax(cid,title,url,cols,paras,pagination,pageSize,pageList)
{
    if(url==undefined||url==null||url==''){
        //nothing
    }else{
        if(url.indexOf("?")==-1){
            url+=('?'+Math.random());
        }else{
            url+=('&random='+Math.random());
        }
    }
    $('#' + cid).datagrid({
        title: title,
        url:url,
        columns:cols,
        height:"100%",
        width:'100%',
        border: true,
        fitColumns: true,
        method: 'get',
        queryParams: paras,
        pagination:pagination,//????
        striped:true,
        pageSize: pageSize,//?????????????10
        pageList : pageList,// ?????????????
        rownumbers: true,
        singleSelect:true,
        remoteSort: false,
        fit: true   //?????
    });
}

/*
Jquery Easyui DataGrid ???
*/
function dataGridAjax(cid, ajaxUrl, title, params, cols, clickRow, selectRow, loadSuccess) {
    if (clickRow==undefined) {
        clickRow = ablank;
    }
    if (selectRow == undefined) {
        selectRow = ablank;
    }
    if (loadSuccess == undefined) {
        loadSuccess = ablank;
    }
    $('#' + cid).datagrid({
        fit: true,
        title: title,
        fitColumns: true,
        border: true,
        singleSelect: true,
        collapsible: false,
        url: ajaxUrl,
        method: 'get',
        queryParams: params,
        columns: cols,
        pagination: true,
        onClickRow: clickRow,
        onSelect: selectRow,
        onLoadSuccess: loadSuccess,
        onLoadError: function () {
            Log('datagrid ' + cid + 'ajax error');
        }
    });
}

function ablank() {
    //?????
}

function setHtml(cid, content) {
    $('#' + cid).html(content);
}
function insertHtmlBefore(cid, content) {
    $(content).insertBefore("#"+cid);
}
function insertHtmlAfter(cid, content) {
    $(content).insertAfter("#" + cid);
}

function loadEcharts(cid, result, clickcallback, options) {
    var myChart = echarts.init(document.getElementById(cid));
    myChart.clear();
    myChart.setOption(result);
    if (options===null||options==undefined) {
        //nothing, ?????????
    } else {
        myChart.setOption(options);
       // Log('Setoption');
    }
    window.onresize = myChart.resize;
    myChart.on(echarts.config.EVENT.CLICK, clickcallback);
    //myChart.dispose();
}
///begin??????????????????????????????÷??????????????
var myChartSingle;
function loadEchartsSingle(cid, result, clickcallback, options) {
    if (myChartSingle && myChartSingle.dispose) {
        myChartSingle.dispose();
    }
    myChartSingle = echarts.init(document.getElementById(cid));
    myChartSingle.clear();
    myChartSingle.setOption(result);
    if (options === null || options == undefined) {
    } else {
        myChartSingle.setOption(options);
    }
    window.onresize = myChartSingle.resize;
    myChartSingle.on(echarts.config.EVENT.CLICK, clickcallback);
}
///end
function datagridSelect(cid, row) {
    if (row === null || row == undefined) {
        row = 1;
    }
    Log(row);
    $('#' + cid).datagrid('selectRow', row);

}

function getColObjByfield(cols, field) {
    var objs = eval(cols);
    for (var i = 0; i < objs.length; i++) {
        var row = objs[i];
        if (row.field==field) {
            return row;
        }
    }
    Log("In cols, not found the field");
    return undefined;
}