//获取当前请求IP的定位,百度地图API
$.ajax({
    type:"GET",
    url:"https://api.map.baidu.com/location/ip",
    dataType:"jsonp",
    async:false, // 同步请求
    data:{
        ak:"8ZaSszE5BQ92yWkahji7bXs9ioGWXLQ6",
        coor:"bd09ll",
        output:"jsonp"
    },
    success:function (data) {
        flag=false;
        try{
            x=(data.content.point.x).toString();
            y=(data.content.point.y).toString();
            city=data.content.address_detail.city;
        }catch (err){
            console.log("无法通过IP定位获取所在城市，您可“手动输入”或“重启提供网络的设备再刷新网页”");
            flag=true;
        }

    }
});

window.onload = function(){
    if($("#stateInput").val()!=""){
        document.getElementById("stateSelect")[$("#stateInput").val()].selected=true;
    }
    function addMarker(point, index){  // 创建图标对象
        var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
            // 指定定位位置。
            // 当标注显示在地图上时，其所指向的地理位置距离图标左上
            // 角各偏移10像素和25像素。您可以看到在本例中该位置即是
            // 图标中央下端的尖角位置。
            anchor: new BMap.Size(10, 25),
            // 设置图片偏移。
            // 当您需要从一幅较大的图片中截取某部分作为标注图标时，您
            // 需要指定大图的偏移位置，此做法与css sprites技术类似。
            imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移
        });
        // 创建标注对象并添加到地图
        var marker = new BMap.Marker(point, {icon: myIcon});
        map.addOverlay(marker);
    }

    if($("#latInput").val()!=""&&$("#lngInput").val()!=""){
        // 创建地图实例
        var map = new BMap.Map("mapService");
        x=$("#lngInput").val();
        y=$("#latInput").val();
        // 创建点坐标
        var point = new BMap.Point(x,y);
        map.centerAndZoom(point, 14);
        // 创建标注对象并添加到地图
        var marker = new BMap.Marker(point);
        //设置标注弹跳
        marker.setAnimation(BMAP_ANIMATION_BOUNCE);
        map.addOverlay(marker);
    }else{
        $("#latInput").val("");
        $("#lngInput").val("");
        if($("#cityInput").val()==""){
            if(flag){
                $("#cityInput").val("");
                x="112.046141";
                y="28.072623";
            }else{
                $("#cityInput").val(city);
            }
        }
        // 创建地图实例
        var map = new BMap.Map("mapService");
        // 创建点坐标
        var point = new BMap.Point(x,y);
        map.centerAndZoom(point, 14);
    }
    window.setTimeout(function () {
        map.panTo(new BMap.Point(x,y));
    },2000);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    //缩放控件
    var opts = {anchor:BMAP_ANCHOR_TOP_LEFT,type: BMAP_NAVIGATION_CONTROL_LARGE}
    map.addControl(new BMap.NavigationControl(opts));
    //比例尺寸控件
    map.addControl(new BMap.ScaleControl());
    //缩略图控件
    map.addControl(new BMap.OverviewMapControl());



    $("#searchBtn").click(function () {
        map.clearOverlays();
        $.ajax({
            type:"GET",
            url:"http://api.map.baidu.com/place/v2/suggestion",
            dataType:"jsonp",
            async:false, // 同步请求
            data:{
                query:$("#searchInput").val()==""?$("#cityInput").val():$("#searchInput").val(),
                region:$("#cityInput").val(),
                city_limit:true,
                ak:"8ZaSszE5BQ92yWkahji7bXs9ioGWXLQ6",
                output:"json"
            },
            success:function (data) {
                res=data.result;
                if(res.length!=0){
                    point = new BMap.Point(res[0].location.lng,res[0].location.lat);
                    map.centerAndZoom(point, 14);
                    for(var i=0;i<res.length;i++){
                        var p = new BMap.Point(res[i].location.lng,res[i].location.lat);
                        addMarker(p, i);
                    }
                }
            }
        });
    });
    map.addEventListener("click", function(e){
        //清除地图上所有覆盖物
        map.clearOverlays();
        //设置中心点为当前点中区域
        point = new BMap.Point(e.point.lng,e.point.lat);
        $("#latInput").val(e.point.lat);
        $("#lngInput").val(e.point.lng);
        map.centerAndZoom(point, this.getZoom());
        // 创建标注对象并添加到地图
        var marker = new BMap.Marker(point);
        //设置标注弹跳
        marker.setAnimation(BMAP_ANIMATION_BOUNCE);
        map.addOverlay(marker);
    });
}
