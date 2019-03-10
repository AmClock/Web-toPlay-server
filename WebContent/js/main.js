var order;

$("#selectBox i ").on('click',function () {
    $("#optionBox").slideToggle();
    $("#optionBox div").on("click",function () {
        order = $(this).text();
        $("#selectBox span").text(order);
    });
});


$('.btn_my').on('click',function () {
    $(".btn_my_on").removeClass("btn_my_on");
    $(this).addClass("btn_my_on");
});
$("#searchIcon").on('click',function () {
    $('#searchBox input').focus();
});

$("#searchBox").on("keyup",function () {
        $("#searchOtherWrap").css("display","block");
});

getList(1);

function getList(pageNo) {
	$.ajax({
		url:"/ajax/list/article",
		type:"POST",
		dataType:"json",
		data:{
			"no":pageNo
    	},
		error:function(){
			alert("서버 점검중");
		},
		success:function(json){
			console.log(json);
		}
	});//ajax end
}

		