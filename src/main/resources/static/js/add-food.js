window.onload = function(){
    $("#hundredsSelect").change(function () {
        var str=$("#hundredsSelect").val()+$("#decadeSelect").val()+$("#unitSelect").val()+"."+$("#oneDecimalSelect").val()+$("#twoDecimalSelect").val();
        $("#priceInput").val(str);
    });
    $("#decadeSelect").change(function () {
        var str=$("#hundredsSelect").val()+$("#decadeSelect").val()+$("#unitSelect").val()+"."+$("#oneDecimalSelect").val()+$("#twoDecimalSelect").val();
        $("#priceInput").val(str);
    });
    $("#unitSelect").change(function () {
        var str=$("#hundredsSelect").val()+$("#decadeSelect").val()+$("#unitSelect").val()+"."+$("#oneDecimalSelect").val()+$("#twoDecimalSelect").val();
        $("#priceInput").val(str);
    });
    $("#oneDecimalSelect").change(function () {
        var str=$("#hundredsSelect").val()+$("#decadeSelect").val()+$("#unitSelect").val()+"."+$("#oneDecimalSelect").val()+$("#twoDecimalSelect").val();
        $("#priceInput").val(str);
    });
    $("#twoDecimalSelect").change(function () {
        var str=$("#hundredsSelect").val()+$("#decadeSelect").val()+$("#unitSelect").val()+"."+$("#oneDecimalSelect").val()+$("#twoDecimalSelect").val();
        $("#priceInput").val(str);
    });
}