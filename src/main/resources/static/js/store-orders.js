window.onload = function(){
    $.ajax({
        type: "Get",
        url: "http://120.79.205.109:80/getAllOrdersByStoreId/" + $("#storeIdInput").val(),
        async: false, // 同步请求
        success: function (data) {
            orders=data;
        }
    });
    ordersLength=orders.length;
    var  str="";
    try {
        preOrdersUuid=orders[0].uuid;
        str+=''+(orders[0].state!=3?'<div class="card order-card" style="border: 1px solid deepskyblue;">':'<div class="card order-card" style="border: 1px solid orange;">')+
                    '        <div class="card-header">\n' +
                    '            <span class="store-name">顾客名：'+orders[0].customerName+'</span>\n' +
                    '            ' +
                    '           <span style="margin-left: 15%;">顾客电话：'+orders[0].customerPhoneNumber+'</span>' +
                    '            <span style="margin-left: 15%;">支付备注/取餐码：'+orders[0].paymentCode+'</span>' +
            '           <span class="time">下单时间：'+orders[0].time.substr(0,16)+'</span>\n' +
                    '        </div>\n' +
                    '        <div class="card-body">\n' +
                    '            <div class="row">\n' ;
        var total=0;
        for (i = 0; i < orders.length; i++) {
            if (orders[i].uuid != preOrdersUuid) {
                str+='            </div>'+
                    '<div class="payment">\n' +
                    '                <h5>共计：'+total.toFixed(2)+'元</h5>' +
                '                '+(orders[i-1].state==1?'<a href="/affirmOrder/'+preOrdersUuid+'" class="btn btn-success" style="margin-right: 20%;">确认订单无误</a>':'<button  class="btn btn-success" style="margin-right: 20%;" disabled="disabled">确认订单无误</button>') +
                    ''+(orders[i-1].state!=3?'<a href="/sendEmail/'+preOrdersUuid+'" class="btn btn-primary">发送用餐邮件</a>':'<button class="btn btn-warning" disabled="disabled">已通过邮件提醒顾客用餐</button>')+
                '            </div>\n' +
                '        </div>' +
                    '</div>';
                str+=''+(orders[i].state!=3?'<div class="card order-card" style="border: 1px solid deepskyblue;">':'<div class="card order-card" style="border: 1px solid orange;">')+
                    '        <div class="card-header">\n' +
                    '            <span class="store-name">顾客名：'+orders[i].customerName+'</span>\n' +
                    '           <span style="margin-left: 15%;">顾客电话：'+orders[i].customerPhoneNumber+'</span>' +
                    '           <span style="margin-left: 15%;">支付备注/取餐码：'+orders[i].paymentCode+'</span>' +
                    '        <span class="time">下单时间：'+orders[i].time.substr(0,16)+'</span>\n' +
                    '        </div>\n' +
                    '        <div class="card-body">\n'+
                    '            <div class="row">\n';
                preOrdersUuid = orders[i].uuid;
                total=0;
                i--;
                continue;
            } else {
                total=total+Number(orders[i].unitPrice)*orders[i].quantity;
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
            '                <h5>共计：'+total.toFixed(2)+'元</h5>' +
            '                '+(orders[orders.length-1].state==1?'<a href="/affirmOrder/'+preOrdersUuid+'" class="btn btn-success" style="margin-right: 20%;">确认订单无误</a>':'<button class="btn btn-success" style="margin-right: 20%;" disabled="disabled">确认订单无误</button>') +
            '               '+(orders[orders.length-1].state!=3?'<a href="/sendEmail/'+preOrdersUuid+'" class="btn btn-primary">发送用餐邮件</a>':'<button class="btn btn-warning" disabled="disabled">已通过邮件提醒顾客用餐</button>')+
            '            </div>\n' +
            '        </div>' +
            '</div>';
    } catch (err) {
        str+=' <div id="errorDiv">\n' +
            '            <h4 id="errorH5" style="color: sandybrown;">您的餐厅还未在此平台接过单</h4>' +
            '        </div>';
    }
    $("#ordersList").html(str);

    var timer = setInterval(function() {
        $.ajax({
            type: "Get",
            url: "http://120.79.205.109:80/getAllOrdersByStoreId/" + $("#storeIdInput").val(),
            async: false, // 同步请求
            success: function (data) {
                orders=data;
            }
        });
        if (ordersLength!=orders.length) {
            window.location.reload();
        }
    },1000);
}