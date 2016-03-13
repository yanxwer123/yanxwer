function addTab(title, url, enClose,height) {
    closable = enClose == undefined ? true : false;

    var setheight=height;
    var a = arguments[0] ? arguments[0] : 1;
    var tt = $('#tt');
    if (!tt.hasClass("easyui-tabs")) {
        tt = $('#tt', parent.document);
    }
    if (tt.tabs('exists', title)) {
        tt.tabs('close', title);
    }
    var _height = "auto";
    var _width = "auto";
    var framehtght=400;
    var cc = tt.find("div[class='tabs-panels']");
    if (cc.length > 0) {
        _height = $(cc[0]).innerHeight();
        framehtght=$(cc[0]).innerHeight();
        //alert($(cc[0]).innerHeight());
        _height -= 6; //去掉border宽度
        _width = $(cc[0]).innerWidth();
    }
    if(height==undefined||height==""){
        //nothing
    }else{
        _height=setheight;
    }

    framehtght -= 10;  //修正高度
   // _height -= 115;  //修正高度

    Log("height:"+setheight);
    Log("_height:"+_height);
    var content = '<iframe id="iframepage"   scrolling="yes" frameborder="0"  src="' + url + '" style="width:' + _width + 'px;height:' + framehtght + 'px;"></iframe>';
    tt.tabs('add', {
        title: title,
        // href:url,
        content: content,
        closable: closable
    });
}



//#endregion
//#region 关闭tab页
function closeTab(index) {
    var tt = $("#tt");
    if (typeof index === "undefined") {
        //参数为空关闭当前tab
        index = tt.tabs("getTabIndex", tt.tabs("getSelected"));
    }
    tt.tabs("close", index);
}

function activeTab(index) {
    var tt = $("#tt");
    if (typeof index === "undefined") {
        //参数为空关闭当前tab
        index = tt.tabs("getTabIndex", tt.tabs("getSelected"));
    }
    tt.tabs("select", index);
}

//#endregion

//#region 刷新tab页
function refreshTab(index) {
    var tt = $("#tt");
    if (typeof index === "undefined") {
        //参数为空刷新当前tab
        index = tt.tabs("getTabIndex", tt.tabs("getSelected"));
    }
    var tab = tt.tabs("getTab", index);
    var title = tab.panel('options').title, src = $(tab.panel('options').content).attr("src");
    closeTab(index);
    addTab(title, src);
}
var eventsArr = {};
function addListner(selectIndex, callback) {
    eventsArr["e" + selectIndex] = callback;
}
function onSelectedTab() {
    $('#tt').tabs({
        onSelect: function (title, index) {
            if (typeof eventsArr["e" + index] === "function") {
                eventsArr["e" + index]();
            }
        }
    });
}
//获取当前tab索引
function getCurTabIndex() {
    var tt = $("#tt");
    return tt.tabs("getTabIndex", tt.tabs("getSelected"));
}
/*$(function(){
        //addTab('首页', '/testview/getOrders', false);
       // window.parent.onSelectedTab();
});*/