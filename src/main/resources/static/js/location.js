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
        try{
            city=data.content.address_detail.city;
        }catch(err){
            console.log("无法通过IP定位获取所在城市，您可手动输入或重启提供网络的设备")
        }
    }
});
window.onload = function(){
    $(document).keydown(function(event){
        if(event.keyCode==13){
            document.getElementById("searchBtn").click();
        }
    });
    try {
        $("#cityInput").val(city);
    }catch(err){}
    $("#searchBtn").click(function () {
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
                var str="";
                res=data.result;
                console.log(res)
                if(res.length!=0){
                    for(var i=0;i<res.length;i++){
                        str+='<a class="list-group-item list-group-item-action" href="/submit-location?positionLat='+res[i].location.lat+'&positionLng='+res[i].location.lng+'&address='+res[i].name+'&city='+$("#cityInput").val()+'">'+res[i].name+'</a>';
                    }
                }else {
                    str+='<a class="list-group-item list-group-item-action" style="cursor: pointer;">未查询到相关地点，请尝试搜索其它地点</a>';
                }
                $("#addressList").html(str);
            }
        });
    });
}
