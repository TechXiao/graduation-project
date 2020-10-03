window.onload = function(){
    $.ajax({
        type: "Get",
        url: "http://120.79.205.109:80/getAllStore/",
        async: false, // 同步请求
        success: function (data) {
            console.log(data);
            stores=data;
        }
    });
    if(stores.length!=0){
        str="";
        for(i=0;i<stores.length;i++){
            str+='<div class="card mb-3">\n' +
                '        <div class="row no-gutters" style="border: 1px solid '+(stores[i].license==0?'red':'aquamarine')+'">\n' +
                '            <div style="text-align: center;width: 100%;">\n' +
                '                <img src="/storePictures/'+stores[i].storePicture+'" class="card-img img-thumbnail picture" title="餐厅照片">\n' +
                '               <img src="/identityPictures/'+stores[i].identityPicture+'" class="card-img img-thumbnail picture" title="老板身份">' +
                '               <img src="/licensePictures/'+stores[i].licensePicture+'" class="card-img img-thumbnail picture" title="营业执照">' +
                '               <img src="/wechatPictures/'+stores[i].wechatPicture+'" class="card-img img-thumbnail picture" title="微信收款码">' +
                '               <img src="/zhifubaoPictures/'+stores[i].zhifubaoPicture+'" class="card-img img-thumbnail picture" title="支付宝收款码">' +
                '           </div>\n' +
                '            <div class="col-md-8" style="float: left;">\n' +
                '                <div class="card-body">\n' +
                '                    <a href="/enterStore/'+stores[i].id+'" class="card-title store-title"><h5>'+stores[i].storeName+'</h5></a>\n' +
                '                    <p class="card-text"><small class="text-muted">邮箱：'+stores[i].email+'</small><small class="text-muted">        手机号码：'+stores[i].phoneNumber+'</small><small class="text-muted">        所在城市：'+stores[i].city+'</small></p>\n' +
                '                    <div class="custom-control custom-radio custom-control-inline">\n' +
                '                        <input type="text" value="'+stores[i].id+'" hidden>' +
                '                       <input type="radio" id="openState'+stores[i].id+'" onclick="openStateChange(this)" name="customRadioInline'+stores[i].id+'" class="custom-control-input"'+(stores[i].license==1?'checked="checked"':'')+'>\n' +
                '                        <label class="custom-control-label" for="openState'+stores[i].id+'">许可</label>\n' +
                '                    </div>\n' +
                '                    <div class="custom-control custom-radio custom-control-inline">\n' +
                '                        <input type="text" value="'+stores[i].id+'" hidden>' +
                '                        <input type="radio" id="restState'+stores[i].id+'" onclick="restStateChange(this)" name="customRadioInline'+stores[i].id+'" class="custom-control-input"'+(stores[i].license==0?'checked="checked"':'')+'>\n' +
                '                        <label class="custom-control-label" for="restState'+stores[i].id+'">未许可</label>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </div>';
        }
        $("#storeList").html(str);
    }
}
function openStateChange(e) {
    var storeId=$(e).prev().val();
    $.ajax({
        url:"http://120.79.205.109:80/changeStoreLicense/"+storeId+"/1",
        type:"Get",
        success: function(data) {
            $("#openState"+storeId).attr("checked","checked");
            $("#restState"+storeId).removeAttr("checked");
        }
    });
}
function restStateChange(e) {
    var storeId=$(e).prev().val();
    $.ajax({
        url:"http://120.79.205.109:80/changeStoreLicense/"+storeId+"/0",
        type:"Get",
        success: function(data) {
            $("#restState"+storeId).attr("checked","checked");
            $("#openState"+storeId).removeAttr("checked");
        }
    });
}