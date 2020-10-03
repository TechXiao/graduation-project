window.onload = function(){
    if ($("#stateInput").val()=="0"){
        $("#openState").attr("checked","checked");
    }else{
        $("#restState").attr("checked","checked");
    }
    $.ajax({
        type:"Get",
        url:"http://120.79.205.109:80/getStoreFoods/"+$("#storeIdInput").val(),
        async:false, // 同步请求
        success: function(data) {
            foods=data;
        }
    });

    var list=new Array("one","two","three","four","five","six","seven","enght","nine","ten")
    var navStr="",listStr="",j=0;
    try{
        var preFoodGroup=foods[j].foodGroup;
        listStr+='<h4 id='+list[j]+'>#'+foods[j].foodGroup+'</h4><div class="row row-cols-1 row-cols-md-3">';
        navStr+='<li class="nav-item">\n' +
            '                <a class="nav-link" href="#'+list[j]+'">'+foods[j].foodGroup+'</a>\n' +
            '            </li>';
        for(i=0;i<foods.length;i++){
            if(foods[i].foodGroup!=preFoodGroup){
                preFoodGroup=foods[i].foodGroup;
                i--;j++;
                navStr+='<li class="nav-item">\n' +
                    '                <a class="nav-link" href="#'+list[j]+'">'+preFoodGroup+'</a>\n' +
                    '            </li>';
                listStr+='</div><h4 id='+list[j]+'>#'+preFoodGroup+'</h4><div class="row row-cols-1 row-cols-md-3">';
                continue;
            }else{
                listStr+='<div class="col mb-4">\n' +
                    '                    <div class="card">\n' +
                    '                        <img src="/foodPictures/'+foods[i].pictureName+'" class="card-img-top food-img" alt="...">\n' +
                    '                        <div class="card-body">\n' +
                    '                            <h5 class="card-title food-title">'+foods[i].foodName+'</h5>\n' +
                    '                            <div class="presentation-p">\n' +
                    '                                <p class="card-text">'+foods[i].description+'</p>\n' +
                    '                            </div>\n' +
                    '                            <small class="text-muted small-formart">价格：￥'+foods[i].price+'</small>\n' +
                    '                        <div class="custom-control custom-switch available-judge">\n' +
                    '                            <input type="text" class="uuid" value="'+foods[i].uuid+'" hidden>' +
                    '                            <input type="checkbox" class="custom-control-input" onchange="foodOpenChange(this)" id="customSwitch'+i+'"'+(foods[i].state==0?'checked':"")+'>\n' +
                    '                            <label class="custom-control-label" for="customSwitch'+i+'">'+(foods[i].state==0?'有货':'无货')+'</label>\n' +
                    '                            <input type="text" class="uuid" value="'+foods[i].uuid+'" hidden>' +
                    '                            <button type="button" class="btn btn-outline-danger delete-btn" onclick="deleteFood(this)" style="float:right;">删除菜品</button>' +
                    '                        </div>' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '            </div>';
            }
        }
        listStr+='</div>';
    }catch(err){
    }
    $("#foodListDiv").html(listStr);
    $("#foodListUL").html(navStr);
}
function openStateChange() {
    $("#openState").attr("checked","checked");
    $("#restState").removeAttr("checked");
    $.ajax({
        url:"http://120.79.205.109:80/changeState/0",
        type:"Get",
        success: function(data) {
        }
    });
}
function restStateChange() {
    $("#restState").attr("checked","checked");
    $("#openState").removeAttr("checked");
    $.ajax({
        url:"http://120.79.205.109:80/changeState/1",
        type:"Get",
        success: function(data) {
        }
    });
}

function foodOpenChange(e) {
    if(e.checked)
        $(e).next().text("有货")
    else
        $(e).next().text("无货")
    $.ajax({
        url:"http://120.79.205.109:80/changeFoodState/"+$(e).prev().val()+"/"+e.checked,
        type:"Get",
        success: function(data) {
        }
    });
}

function deleteFood(e) {
    $.ajax({
        url:"http://120.79.205.109:80/deleteFood/"+$(e).prev().val(),
        type:"Get",
        success: function(data) {
            if(data==1){
                window.location.reload();
            }else{
                alert("删除菜品失败");
            }
        }
    });
}