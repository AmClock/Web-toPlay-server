<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>main</title>
    <c:import url="/WEB-INF/template/link.jsp"/>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
   <c:import url="/WEB-INF/template/header.jsp"/>
        <div id="wrap">
        <c:if test="${loginUser!=null }">
            <div id="mainBtnWrap">
                <div class="btn_my" data-value="my">My history</div>
                <div class="btn_my" data-value="like">My Like</div>
            </div><!--//mainBtnWrap end-->
        </c:if>
            <div id="searchWrap">
                <div id="searchBox">
                    <input type="text"/>
                    <div id="searchIcon">
                        <i class="fas fa-search"></i>
                    </div><!--//searchIcon end-->
                </div><!--//searchBox end-->
                <ul id="searchOtherWrap"></ul><!--//searchOtherWrap end  -->
            </div><!--//searchWrap end -->

            <div id="writeBtnWrap">
                <a href="/write"><button>Write</button></a>
            </div><!--//writeBtnWrap end-->

            <div id="selectWrap">
                <div id="selectBox">
                    <span>select</span><i class="fas fa-sort-down"></i>
                </div><!--//selectBox end-->
                <div id="optionBox">
                        <div data-value="new">The latest</div>
                        <div data-value="old">Popularity</div>
                </div>
            </div><!--//selectWrap end-->
            <ul id="mainContents"></ul><!--//mainContents end-->
            <div id="paing"></div>
</div><!--// wrap end  -->
<c:import url="/WEB-INF/template/footer.jsp"/>
<c:import url="/WEB-INF/template/template.jsp"/>
<!-- <script src="/js/main.js"></script> -->
<script type="text/javascript">

var $searchInput = $("#searchBox input");
var $search = $("#searchOtherWrap");
var $orderVal = "new";
var $searchNo = 0;
var $loginUser = 0;

$("#selectBox i ").on('click',function () {
    $("#optionBox").slideToggle();
    $("#optionBox div").on("click",function () {
        var order = $(this).text();
        $orderVal =  $(this).data("value");
        $("#selectBox span").text(order);
        getList(1);
    });
});


$('.btn_my').on('click',function () {
    $(".btn_my_on").removeClass("btn_my_on");
    $(this).addClass("btn_my_on");
    if($(this).data("value")=='my'){
		<c:if test="${loginUser!=null}">
		$loginUser = ${loginUser.no};
		</c:if>
		getList(1);
    }else{
    	
    }
    $loginUser=0;
});

$("#searchIcon").on('click',function () {
	$searchInput.focus();
});

$searchInput.on("keyup",function () {
	var searchVal = $(this).val();   
    if(searchVal !=''){
    	searchTitle(searchVal);
    }else{
    	$search.empty();
    }
});
$searchInput.keydown(function(key) {
	if(key.keyCode==13){
	/* var test = $search.children().text(); */
	/* alert(test);  */
	}
})

getList(1);

function getList(pageNo) {
	$.ajax({
		url:"/ajax/list/article",
		type:"POST",
		dataType:"json",
		data:{
			"no":pageNo,
			"order": $orderVal,
			"searchNo":$searchNo,
			"userNo": $loginUser
    	},
		error:function(){
			alert("서버 점검중");
		},
		success:function(json){
		/* 	console.log(json); */
			$("#mainContents").html($articleTmpl({articles:json.list}));
     	   	$("#paing").html(json.paginate); 
		}
	});//ajax end
}


function searchTitle(title) {
	$.ajax({
		url:"/ajax/search/title",
		type:"POST",
		dataType:"json",
		data:{
			"title":title
		},
		error:function(){
			alert("error");
		},
		success:function(json){
			$search.css("display","block");
			if(json.length==0){
				$search.html("<li class='box_other'><i class='fas fa-comment-dots'></i> 제목이 없습니다..</li>");
			}else{
				$search.html($searchTmpl({titles:json})).off("click").on("click",$search.children(),function(){
				$loginUser=0;
				$searchNo = $(this).children().data("value");
				getList(1);
				});
			}
		}
	});
}


$("#paing").on("click",".paginate a",function(e) {
	   	e.preventDefault();
	   	var  no = $(this).attr("href");
	   	var pageNoStr = no.substring(no.lastIndexOf("/")+1 , no.length);
	   	pageNo = pageNoStr;
	   	listReply(pageNo);
	   });

</script>
</body>
</html>