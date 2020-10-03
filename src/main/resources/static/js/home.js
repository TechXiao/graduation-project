window.onload = function(){
    $(document).keydown(function(event){
        if(event.keyCode==13){
            document.getElementById("searchBtn").click();
        }
    });
    if($("#loginUserAddress").text()=="请先定位"){
        $("#storeList").html(' <div id="errorDiv">\n' +
            '            <h4 id="errorH5" style="color: red">请点击左上角“请先定位”图标进入定位页面，定位完成才能准确获取您附近的餐厅信息</h4>\n' +
            '        </div>');
    }else{
        $.ajax({
            type:"Get",
            url:"http://120.79.205.109:80/getLoginUserInfo/"+$("#loginUserEmailInput").val(),
            async:false, // 同步请求
            success: function(data) {
                user=data;
            }
        });
        $.ajax({
            url:"http://120.79.205.109:80/getAllStoresByCity/"+$("#cityInput").val(),
            type:"Get",
            async:false, // 同步请求
            success: function(data) {
                res=data;
            }
        });
        if(res.length==0){
            $("#storeList").html(' <div id="errorDiv">\n' +
                '            <h4 id="errorH5" style="color: sandybrown;">您所在的城市没有餐厅入驻此平台</h4>' +
                '        </div>');
        }else{
            var map = new BMap.Map("allmap");
            var userPoint = new BMap.Point(user.positionLng,user.positionLat);
            var storePoint1,storePoint2;
            //console.log(res.length);
            for(i=0;i<res.length;i++){
                for(j=i;j<res.length;j++){
                    storePoint1= new BMap.Point(res[i].positionLng,res[i].positionLat);
                    storePoint2= new BMap.Point(res[j].positionLng,res[j].positionLat);
                    var distanceI=(map.getDistance(userPoint,storePoint1)).toFixed(2);
                    var distanceJ=(map.getDistance(userPoint,storePoint2)).toFixed(2);
                    if((distanceI-distanceJ)>0){
                        var temp=res[j];
                        res[j]=res[i];
                        res[i]=temp;
                    }
                }
            }
            var str='<div class="input-group" id="searchDiv">\n' +
                '            <input type="text" class="form-control" id="searchInput">\n' +
                '            <div class="input-group-append"  id="search">\n' +
                '                <select id="searchSelect" class="btn">\n' +
                '                    <option>搜索商家</option>\n'  +
                '                </select>\n' +
                '                <button class="btn btn-outline-secondary" id="searchBtn" onclick="searchBtnClick()" type="button">搜索</button>\n' +
                '            </div>\n' +
                '        </div>';
            for(var i=0;i<res.length;i++){
                var storePoint=new BMap.Point(res[i].positionLng,res[i].positionLat);
                var userPoint = new BMap.Point(user.positionLng,user.positionLat);
                str+='<div class="card card-margin">\n' +
                    '            <h5 class="card-header">' +
                    '餐厅名称：' +res[i].storeName+'</h5>\n' +
                    '            <div class="card-body">\n' +
                    '                <h5 class="card-title">' +
                    '地址：' +res[i].address+'</h5>\n' +
                    '                <p class="card-text notice">' +
                    '公告：' +res[i].notice+ '</p>\n' +
                    '               '+(res[i].state==0?'<a class="btn btn-primary" target="_blank" href="/enterStore/'+res[i].id+'">进入餐厅</a>':'<button class="btn btn-warning" disabled="disabled">餐厅休息中</button>')+'\n' +
                    '            </div>\n' +
                    '            <div class="card-footer text-muted">\n' +
                    '                距离：'+  (map.getDistance(userPoint,storePoint)/1000).toFixed(2)+'千米\n' +
                    '            </div>\n' +
                    '        </div>';
            }
            $("#storeList").html(str)
        }
    }
}
function searchBtnClick() {
    if($("#searchInput").val()!=""){
        $.ajax({
            url:"http://120.79.205.109:80/getStoresByName/"+$("#cityInput").val()+"/"+$("#searchInput").val(),
            type:"Get",
            async:false, // 同步请求
            success: function(data) {
                res=data;
            }
        });
        if(res.length!=0){
            var map = new BMap.Map("allmap");
            var userPoint = new BMap.Point(user.positionLng,user.positionLat);
            var storePoint1,storePoint2;
            for(i=0;i<res.length;i++){
                storePoint1= new BMap.Point(res[i].positionLng,res[i].positionLat);
                for(j=i;j<res.length;j++){
                    storePoint2= new BMap.Point(res[j].positionLng,res[j].positionLat);
                    var distanceI=map.getDistance(userPoint,storePoint1).toFixed(2);
                    var distanceJ=map.getDistance(userPoint,storePoint2).toFixed(2);
                    if((distanceI-distanceJ)>0){
                        var temp=res[j];
                        res[j]=res[i];
                        res[i]=temp;
                    }
                }
            }
            var str='<div class="input-group" id="searchDiv">\n' +
                '            <input type="text" class="form-control" id="searchInput">\n' +
                '            <div class="input-group-append" id="search">\n' +
                '                <select id="searchSelect" class="btn">\n' +
                '                    <option>搜索商家</option>\n'  +
                '                </select>\n' +
                '                <button class="btn btn-outline-secondary" id="searchBtn" onclick="searchBtnClick()" type="button">搜索</button>\n' +
                '            </div>\n' +
                '        </div>';
            for(var i=0;i<res.length;i++){
                var storePoint=new BMap.Point(res[i].positionLng,res[i].positionLat);
                var userPoint = new BMap.Point(user.positionLng,user.positionLat);
                str+='<div class="card card-margin">\n' +
                    '            <h5 class="card-header">' +
                    '餐厅名称：' +res[i].storeName+'</h5>\n' +
                    '            <div class="card-body">\n' +
                    '                <h5 class="card-title">' +
                    '地址：' +res[i].address+'</h5>\n' +
                    '                <p class="card-text notice">' +
                    '公告：' +res[i].notice+ '</p>\n' +
                    '               '+(res[i].state==0?'<a class="btn btn-primary" href="/enterStore/'+res[i].id+'">进入餐厅</a>':'<button class="btn btn-warning" disabled="disabled">餐厅休息中</button>')+'\n' +
                    '            </div>\n' +
                    '            <div class="card-footer text-muted">\n' +
                    '                距离：'+  (map.getDistance(userPoint,storePoint)/1000).toFixed(2)+'千米\n' +
                    '            </div>\n' +
                    '        </div>';
            }
            $("#storeList").html(str);
        }else{
            window.location.reload();
            alert("未查询到餐厅名称与“"+$("#searchInput").val()+"”相似的餐厅信息");
        }
    }else{
        window.location.reload();
    }
}

