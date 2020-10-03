window.onload = function(){
    $.ajax({
        type: "Get",
        url: "http://120.79.205.109:80/getAllOrdersByCustomerId/" + $("#customerIdInput").val(),
        async: false, // 同步请求
        success: function (data) {
            orders=data;
        }
    });
    var  str="";
    try {
        if(orders.length!=0){
            preOrdersUuid=orders[0].uuid;
            str+=''+(orders[0].state!=0?(orders[0].state!=1?'<div class="card order-card" style="border: 1px solid orange;">':'<div class="card order-card" style="border: 1px solid aquamarine;">'):'<div class="card order-card" style="border: 1px solid deepskyblue;">')+
                '        <div class="card-header">\n' +
                '            <a href="/enterStore/'+orders[0].storeId+'" style="text-decoration:none;">\n' +
                '                <span class="customer-name">'+orders[0].storeName+'</span>\n' +
                '                <img src="/images/turnRight.png" width="30" height="30" class="d-inline-block align-top">\n' +
                '            </a>\n' +
                '            ' +
                '           <span style="margin-left: 15%;">商家电话：'+orders[0].storePhoneNumber+'</span>' +
                '<span style="margin-left: 15%;">支付备注/取餐码：'+orders[0].paymentCode+'</span>' +
                '            <input type="text" value="'+orders[0].time+'" class="timeInput" hidden>' +
                ''+(orders[0].state!=0?'<span class="time">下单时间：'+orders[0].time.substr(0,16)+'</span>':'<span class="time" style="color: deepskyblue"></span>')+
                '   <input type="text" value="'+orders[0].state+'" class="stateInput" hidden>'+
                '        </div>\n' +
                '        <div class="card-body">\n' +
                '            <div class="row">\n' ;
            var total=0;
            for (i = 0; i < orders.length; i++) {
                if (orders[i].uuid != preOrdersUuid) {
                    str+='            </div>'+
                        '<div class="payment">\n' +
                        '                <input type="text" class="order-storeId" value="'+orders[i-1].storeId+'" hidden>' +
                        '                <h5>共计：'+total.toFixed(2)+'元</h5>' +
                        '               <input type="text" class="order-uuid" value="'+orders[i-1].uuid+'" hidden>' +
                        '                '+(orders[i-1].state!=0?(orders[i-1].state!=1?'<button class="btn btn-warning" disabled="disabled">商家已确认订单</button>':'<button class="btn btn-success" disabled="disabled">已付款等待商家确认订单</button>'):'<button class="btn btn-primary" onclick="selectPaymentClick(this)">付款</button>')+
                        '                   <input type="text" class="order-wechat" value="'+orders[i-1].wechatPicture+'" hidden>' +
                        '               <input type="text" class="order-zhifubao" value="'+orders[i-1].zhifubaoPicture+'" hidden>' +
                        '               <input type="text" class="order-paymentCode" value="'+orders[i-1].paymentCode+'" hidden>' +
                        '</div>\n' +
                        '        </div>' +
                        '</div>';
                    str+=''+(orders[i].state!=0?(orders[i].state!=1?'<div class="card order-card" style="border: 1px solid orange;">':'<div class="card order-card" style="border: 1px solid aquamarine;">'):'<div class="card order-card" style="border: 1px solid deepskyblue;">')+
                        '        <div class="card-header">\n' +
                        '            <a href="/enterStore/'+orders[i].storeId+'" style="text-decoration:none;">\n' +
                        '                <span class="customer-name">'+orders[i].storeName+'</span>\n' +
                        '                <img src="/images/turnRight.png" width="30" height="30" class="d-inline-block align-top">\n' +
                        '            </a>\n' +
                        '           <span style="margin-left: 15%;">商家电话：'+orders[i].storePhoneNumber+'</span>' +
                        '           <span style="margin-left: 15%;">支付备注/取餐码：'+orders[i].paymentCode+'</span>' +
                        '           <input type="text" value="'+orders[i].time+'" class="timeInput" hidden>' +
                        '           '+(orders[i].state!=0?'<span class="time">下单时间：'+orders[i].time.substr(0,16)+'</span>':'<span class="time" style="color: deepskyblue"></span>')+
                        '   <input type="text" value="'+orders[i].state+'" class="stateInput" hidden>'+
                        '        </div>\n' +
                        '        <div class="card-body">\n'+
                        '            <div class="row">\n';
                    preOrdersUuid = orders[i].uuid;
                    total=0;
                    i--;
                    continue;
                } else {
                    total=total+Number(orders[i].unitPrice)*Number(orders[i].quantity);
                    str+='                <div class="col-sm-6">\n' +
                        '                    <div class="card">\n' +
                        '                        <div class="card-body">\n' +
                        '                            <div class="card-title">\n' +
                        '                                <h5 style="float: right">'+orders[i].unitPrice+'元/份</h5>\n' +
                        '                                <h5>'+orders[i].foodName+'</h5>\n' +
                        '                            </div>\n' +
                        '                            <p class="card-text">×'+orders[i].quantity+'</p>\n' +
                        '                        </div>\n' +
                        '                    </div>\n' +
                        '                </div>\n' ;
                }
            }
            str+='            </div>'+
                '<div class="payment">\n' +
                '               <input type="text" class="order-storeId" value="'+orders[orders.length-1].storeId+'" hidden>' +
                '                <h5>共计：'+total.toFixed(2)+'元</h5>' +
                '               <input type="text" class="order-uuid" value="'+orders[orders.length-1].uuid+'" hidden>' +
                '                '+(orders[orders.length-1].state!=0?(orders[orders.length-1].state!=1?'<button class="btn btn-warning" disabled="disabled">商家已确认订单</button>':'<button class="btn btn-success" disabled="disabled">已付款等待商家确定订单</button>'):'<button class="btn btn-primary" onclick="selectPaymentClick(this)">付款</button>')+
                '                   <input type="text" class="order-wechat" value="'+orders[orders.length-1].wechatPicture+'" hidden>' +
                '               <input type="text" class="order-zhifubao" value="'+orders[orders.length-1].zhifubaoPicture+'" hidden>' +
                '               <input type="text" class="order-paymentCode" value="'+orders[orders.length-1].paymentCode+'" hidden>' +
                '            </div>\n' +
                '        </div>' +
                '</div>';
        }else{
            str+=' <div id="errorDiv">\n' +
                '            <h4 id="errorH5" style="color: sandybrown;">您还未在此平台下过单</h4>' +
                '        </div>';
        }
    } catch (err) {
        str=' <div id="errorDiv">\n' +
            '            <h4 id="errorH5" style="color: sandybrown;">出错了</h4>' +
            '        </div>';
    }
    $("#ordersList").html(str);
    $(".time").each(function () {
        var thisState=$(this).next().val();
        if(thisState==0){
            var thisSpan=$(this);
            var thisTime=$(this).prev().val();
            var now = new Date();
            var time1=(Date.UTC(now.getFullYear(),now.getMonth(),now.getDate(),now.getHours(),now.getMinutes(),now.getSeconds(),0)
                -Date.UTC(Number(thisTime.substring(0,4)),Number(thisTime.substring(5,7)-1),Number(thisTime.substring(8,10)),Number(thisTime.substring(11,13)),Number(thisTime.substring(14,16)),Number(thisTime.substring(17,19)),0))/1000;
            var time =60- Number(time1);
            var timer = setInterval(function() {
                if (time == 0) {
                    window.location.reload();
                } else {
                    $(thisSpan).text("剩"+Math.floor(time/60)+ "分"+time%60+"秒自动关闭订单");
                    time--;
                }
            },1000);
        }
    });
}
function selectPaymentClick(e) {
    var storeId=$(e).prev().prev().prev().val();
    $.ajax({
        type: "Get",
        url: "http://120.79.205.109:80/getStoreById/" + storeId,
        async: false, // 同步请求
        success: function (data) {
            store = data;
        }
    });
    if(store.state==0&&store.license=='1'){
        orderuuid=$(e).prev().val();
        wechatPicture=$(e).next().val();
        zhifubaoPicture=$(e).next().next().val();
        ordertotal=$(e).prev().prev().text();
        var paymentCode=$(e).next().next().next().val();
        $("#selectSpan").text(ordertotal+"，请选择支付方式：");
        $("#totalSpan").text(ordertotal);
        $("#finalReminder").text("支付请备注“"+paymentCode+"”，以便商家确认订单。支付成功必须点击“支付成功”按钮才算成功提交订单。");
        document.getElementById("selectPaymentBtn").click();
    }else{
        alert("商家休息中，不能下单。");
    }
}

function selectWeChat() {
    $("#modeOfPayment").val("wechat");
    $("#submitPayment").attr("href","/payment/"+orderuuid+"/"+ordertotal+"/"+$("#modeOfPayment").val());
    $("#QRcode").attr("src","/wechatPictures/"+wechatPicture);
}

function selectZhiFuBao() {
    $("#modeOfPayment").val("zhifubao");
    $("#submitPayment").attr("href","/payment/"+orderuuid+"/"+ordertotal+"/"+$("#modeOfPayment").val());
    $("#QRcode").attr("src","/zhifubaoPictures/"+zhifubaoPicture);
}