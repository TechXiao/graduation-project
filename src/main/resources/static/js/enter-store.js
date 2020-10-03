window.onload = function () {
    $.ajax({
        type: "Get",
        url: "http://120.79.205.109:80/getStoreFoods/" + $("#storeIdInput").val(),
        async: false, // 同步请求
        success: function (data) {
            foods = data;
        }
    });
    $.ajax({
        type: "Get",
        url: "http://120.79.205.109:80/getStoreById/" + $("#storeIdInput").val(),
        async: false, // 同步请求
        success: function (data) {
            store = data;
        }
    });
    var list = new Array("zero","one", "two", "three", "four", "five", "six", "seven", "enght", "nine", "ten"
        ,"eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen")
    var navStr = "", listStr = "", j = 0;
    try {
        if(store.state==0&&store.license=='1'){
            for (k = 0; k < foods.length; k++) {
                if (foods[k].state == 0) {
                    preFoodGroup = foods[k].foodGroup;
                    break;
                }
            }
            listStr += '<h4 id=' + list[j] + '>#' + foods[k].foodGroup + '</h4><div class="row row-cols-1 row-cols-md-3">';
            navStr += '<li class="nav-item">\n' +
                '                <a class="nav-link" href="#' + list[j] + '">' + foods[k].foodGroup + '</a>\n' +
                '            </li>';
            for (i = k; i < foods.length; i++) {
                if (foods[i].foodGroup != preFoodGroup) {
                    if (foods[i].state == 0) {
                        preFoodGroup = foods[i].foodGroup;
                        i--;
                        j++;
                        navStr += '<li class="nav-item">\n' +
                            '                <a class="nav-link" href="#' + list[j] + '">' + preFoodGroup + '</a>\n' +
                            '            </li>';
                        listStr += '</div><h4 id=' + list[j] + '>#' + preFoodGroup + '</h4><div class="row row-cols-1 row-cols-md-3">';
                    }
                    continue;
                } else {
                    if (foods[i].state == 0) {
                        listStr += '<div class="col mb-4">\n' +
                            '                    <div class="card">\n' +
                            '                        <img src="/foodPictures/' + foods[i].pictureName + '" class="card-img-top food-img">\n' +
                            '                        <div class="card-body">\n' +
                            '                            <h5 class="card-title food-title">' + foods[i].foodName + '</h5>\n' +
                            '                            <div class="presentation-p">\n' +
                            '                                <p class="card-text">' + foods[i].description + '</p>\n' +
                            '                            </div>\n' +
                            '                            <small class="text-muted small-formart">    价格：￥' + foods[i].price + '</small>\n' +
                            '                            \n' +
                            '                            ' +
                            '                           <input type="text" class="uuid" value="' + foods[i].uuid + '" hidden>' +
                            '<div class="input-group add-sub-div" align="center" style="margin-right: auto;margin-left: auto">\n' +
                            '    <div class="input-group-append" onclick="subClick(this)">\n' +
                            '        <img src="/images/sub.png" class="add-sub-img">\n' +
                            '    </div>\n' +
                            '    <input type="text" value=0 style="text-align: center;height:30px;width: 60%" class="item-number" onchange="totalChange()">\n' +
                            '    <div class="input-group-append" style="margin-left: 0px"  onclick="addClick(this)">\n' +
                            '        <img src="/images/add.png" class="add-sub-img">\n' +
                            '    </div>\n' +
                            '</div>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '            </div>';
                    }
                }
            }
            listStr += '</div>';
        }
        if(store.license=='0'){
            listStr+=' <div id="errorDiv">\n' +
                '            <h4 id="errorH5" style="color: red">餐厅未通过审核</h4>\n' +
                '        </div>';
        }
        if(store.license=='1'&&store.state==1){
            listStr+=' <div id="errorDiv">\n' +
                '            <h4 id="errorH5" style="color: orange">餐厅休息中</h4>\n' +
                '        </div>';
        }
    } catch (err) {

    }
    $("#foodListDiv").html(listStr);
    $("#foodListUL").html(navStr);

    // 创建地图实例
    var map = new BMap.Map("mapService");
    x = $("#positionLngInput").val();
    y = $("#positionLatInput").val();
    // 创建点坐标
    var point = new BMap.Point(x, y);
    map.centerAndZoom(point, 16);
    // 创建标注对象并添加到地图
    var marker = new BMap.Marker(point);
    //设置标注弹跳
    marker.setAnimation(BMAP_ANIMATION_BOUNCE);
    map.addOverlay(marker);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    //缩放控件
    var opts = {anchor: BMAP_ANCHOR_TOP_LEFT, type: BMAP_NAVIGATION_CONTROL_LARGE}
    map.addControl(new BMap.NavigationControl(opts));
    //比例尺寸控件
    map.addControl(new BMap.ScaleControl());
    //缩略图控件
    map.addControl(new BMap.OverviewMapControl());
}

function subClick(e) {
    if ($(e).next().val() >= 1) {
        $(e).next().val(Number($(e).next().val()) - 1)
    }
    $("#orderQuantity").text(0);
    $(".item-number").each(function () {
        var totalQuantity = $("#orderQuantity").text();
        var thisQuantity = $(this).val();
        $("#orderQuantity").text(Number(totalQuantity) + Number(thisQuantity));
    })
}

function addClick(e) {
    $(e).prev().val(Number($(e).prev().val()) + 1)
    $("#orderQuantity").text(0);
    $(".item-number").each(function () {
        var totalQuantity = $("#orderQuantity").text();
        var thisQuantity = $(this).val();
        $("#orderQuantity").text(Number(totalQuantity) + Number(thisQuantity));
    })
}

function totalChange() {
    $("#orderQuantity").text(0);
    $(".item-number").each(function (index,element) {
        var totalQuantity = $("#orderQuantity").text();
        var thisQuantity = $(this).val();
        if(Number(thisQuantity)>0){
            $("#orderQuantity").text(Number(totalQuantity) + Number(thisQuantity));
        }else{
            $(element).val("0");
        }
    })
}

function submitOrder() {
    var map = {},str="请核对您点的菜单：\n";
    if(Number($("#orderQuantity").text())<0||Number($("#orderQuantity").text())==0){
        alert("请点好菜再下单");
    }else{
        $(".item-number").each(function () {
            if ($(this).val() != 0) {
                map[$(this).parent().prev().val()]= $(this).val();
                str+=$(this).parent().parent().children(".food-title").text()+":"+$(this).val()+"份"+"\n";
            }
        });
        var r=confirm(str);
        if(r){
            $.ajax({
                type: "POST",
                url: "http://120.79.205.109:80/submit-order",
                async: false, // 同步请求,
                data:map,
                dataType:"json",
                success: function (data) {
                    window.open("http://120.79.205.109:80/customer-orders","_blank");
                },
                error:function () {
                    alert("下单失败");
                }
            });
        }
    }
}