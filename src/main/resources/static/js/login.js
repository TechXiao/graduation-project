window.onload = function(){
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for(var i = 0; i < arrCookie.length; i++){
        if(arrCookie[i].substring(0,10)=="loginEmail"){
            $("#emailInput").val(arrCookie[i].substring(11,arrCookie[i].length-7));
        }
    }
    $("[data-toggle='popover']").popover();
    $("#produceSecurity").click(function(){
        $.ajax({
            url:"http://120.79.205.109:80/produceSecurityCode",
            type:"POST",
            data:{email:$("#emailInput").val()+"@qq.com"},
            dataType:"text",
            success: function(data) {
                if(data=="false"){
                    $("#errorDiv").html('<h4>'+ '<span style="color: #FF0000;">您输入的QQ号码有误，或您的QQ号码未注册QQ邮箱</span>' +'</h4>');
                    clearInterval(timer);
                    $("#produceSecurity").attr("disabled", false);
                    $("#produceSecurity").val("获取验证码");
                }
            }
        });
        //禁用当前按钮
        $(this).attr("disabled", true);
        var time = 120;
        var timer = setInterval(function() {
            if (time == 0) {
                clearInterval(timer);
                $("#produceSecurity").attr("disabled", false);
                $("#produceSecurity").val("获取验证码");
            } else {
                $("#produceSecurity").val(time + "秒");
                time--;
            }
        }, 1000);
    })
}