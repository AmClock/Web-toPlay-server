<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>detail</title>
	<c:import url="/WEB-INF/template/link.jsp"/>
    <link rel="stylesheet" href="/css/detail.css"/>
</head>
<body>
<c:import url="/WEB-INF/template/header.jsp"/>
   <div id="wrap">
        <div id="titleWrap">
            <div id="titleBox">
                <span>${article.title }</span>
            </div><!--//titleBox end-->
            <div id="regdateBox">
                <span><i class="fas fa-eye"></i> ${views }</span>
                <span>${article.regdate }</span>
            </div><!--//regdateBox end-->

        </div><!--//titleWrap end-->

        <div id="profileWrap">
                <div id="userProfileBox">
                    <a href="#"><img src="/img/profile/${article.userProfile }" alt="${article.userName }" title="${article.userName }"/></a>
                    <div id="nicknameBox">
                  		<span>${article.userName }</span>
                    </div><!--//nicknameBox end-->
                </div><!--//userProfileBox end-->
        </div><!--//profileWrap end -->
<c:if test="${loginUser.no == article.userNo}">
        <div id="wrapBtn">
            <form action="/delete/${article.no }" method="POST">
                <input type="hidden" name="_method" value="DELETE"/>
                <button type="submit">Delete</button>
            </form>
                <a href="/update/${article.no }"><button>Update</button></a>
        </div><!-- //wrapBtn end -->
</c:if>

<ul id="contentWrap">
    <c:forEach items="${contents }" var="content">
    <li class="wrap_content">
        <div class="box_content">
            <div class="box_img">
                <img src="/img/article/${content.img }">
            </div><!--//box_img end-->
            <div class="wrap_text">
            ${content.content }
            </div><!--//wrap_text end-->
        </div><!--//box_content end-->
    </li><!--//wrap_content end-->
    </c:forEach>
</ul>

<div id="likeBtnWrap">
        <div id="likeBtnBox">
            <button><i class="far fa-heart"></i></button>
            <div id="likeNumBox"><span></span></div><!-- //likeNumBox end  -->
        </div><!--//likeBtnBox end-->
</div><!--//likeBtnWrap end-->

<div id="replyWrap" <c:if test="${loginUser==null }"> style="height: 50px" </c:if>>

<c:if test="${loginUser!=null }">
    <div class="box_reply">
        <div class="box_reply_content">
            <div id="writeUserBox">
                     <img src="/img/profile/${loginUser.profile }" alt="" title="" id="writeImg">
                     <div id="writeNameBox">
                         <span>${loginUser.name }</span>
                     </div><!--//#wrapBtn button end-->
            </div><!--//box_write_user end-->
            <textarea name="content" placeholder="Write to reply..."></textarea>
            <div id="writeBtnWrap">
                <button type="button"><i class="fas fa-pen"></i></button>
            </div><!--//writeBtnWrap end  -->
        </div><!--//box_reply_content end-->
    </div><!--//box_reply edn-->
</c:if>

    <div id="countNumBox">
        <div id="numWrap">
            <div id="numBox">
                <i class="fas fa-comment-alt"></i>
            </div><!--//numBox end-->
            <div id="numText"><span>1000k</span></div><!--/numText end-->
        </div><!--//numWrap end-->
    </div><!--//countNumBox end-->

</div><!--//replyWrap end-->

<ul id="replyBox">
    
    
</ul><!--//replyBox end-  -->
<div id="paging">
</div>
</div><!--wrap end-->
<c:import url="/WEB-INF/template/footer.jsp"/>
<c:import url="/WEB-INF/template/template.jsp"/>
<script type="text/javascript">

var order;
var $likeText = $("#likeNumBox span");
var articleNo = ${article.no};
var clickFlag = false;
var $likeBtn = $("#likeBtnBox button");
var $writeBtn = $("#writeBtnWrap button");
var $textarea = $(".box_reply_content textarea");
var $numText = $("#numWrap #numText span");
var userNo = 0;

<c:if test="${loginUser!=null}">
userNo = ${loginUser.no};
</c:if>

ajaxLike(); 

<c:if test="${loginUser!=null}">
$likeBtn.on("click",function () {
	clickFlag = true;
	ajaxLike();
});

$writeBtn.on('click',function(){
	var $val = $textarea.val();
	if($val!=''){
		writeReply($val);
	}
});

</c:if>

function ajaxLike() {
	 
	$(".on_like").removeClass("on_like");
	 $likeBtn.children().removeClass("fas");
     $likeBtn.children().addClass("far"); 
 	 $.ajax({
	    	url:"/ajax/like/"+clickFlag,
	    	type: "post",
	    	dataType : "json",
	    	data:{
	    		"userNo":userNo,
	    		"articleNo":articleNo
	    	},
	    	  error:function(){
	              alert("서버 정검중!")
	          },
	          success:function(json){
	        	    $likeText.text(json.likeNum);
	        	    if(json.result == true){
	        	    	$likeBtn.addClass("on_like");
	        	    	$likeBtn.children().removeClass("far");
	        	    	$likeBtn.children().addClass("fas"); 
	        	    }
	        	    clickFlag = false;	        	    
	          }//success:function end
	    });//ajaxList end
 }//ajaxLike
 
 function writeReply(content) {
 	 $.ajax({
	    	url:"/ajax/write/reply",
	    	type: "post",
	    	dataType : "json",
	    	data:{
	    		"userNo":userNo,
	    		"articleNo":articleNo,
	    		"content":content
	    	},
	    	  error:function(){
	              alert("서버 정검중!")
	          },
	          success:function(json){
	        	  $textarea.val('');
	        	  listReply(1);
	          }//success:function end
	    });//ajaxList end
 }//writeReply end
 	var pageNo = 1;
 	listReply(pageNo);
 	$("#paging").on("click",".paginate a",function(e) {
	   	e.preventDefault();
	   	var  no = $(this).attr("href");
	   	var pageNoStr = no.substring(no.lastIndexOf("/")+1 , no.length);
	   	pageNo = pageNoStr;
	   	listReply(pageNo);
	   });
 	
 function listReply() {
 	 $.ajax({
	    	url:"/ajax/list/reply/"+pageNo,
	    	type: "post",
	    	dataType : "json",
	    	data:{
	    		"articleNo":articleNo
	    	},
	    	  error:function(){
	              alert("서버 정검중!")
	          },
	          success:function(json){
	        	   $("#replyBox").html($replyTmpl({replies:json.list}));
	        	   $("#paging").html(json.paginate); 
	        	   $numText.text(json.total);
	          }//success:function end
	    });//ajaxList end
 }//writeReply end
 
 
$("#replyBox").on("click" ,".btn_clear", function () {
	$var = $(this).data("value");
	console.log("$var :: "+$var);
	
	$.ajax({
    	url:"/ajax/delete/reply",
    	type:"post",
    	dataType : "json",
    	data:{
    		"no":$var
    	},
    	  error:function(){
              alert("서버 정검중!")
          },
          success:function(json){
          		json == 1 ? listReply(pageNo) : alert("실패");
          }//success:function end
    });//ajaxList end

});
$("#replyBox").on("click" ,".btn_update", function () {
	
	var $contentVal = $(this).parent().next().val();
	var $contentBox = $(this).parent().prev().prev();
	var $updateTextarea = $(this).parent().prev();
	var $updateBtn = $(this).next();
	
	$contentBox.css("display","none");
	$updateTextarea.css("display","block").val($contentVal).focus();
	$(this).css("display","none");
	$updateBtn.css("display","block");
	
	$("#replyBox").on("click" ,".real_update",function(){
	var $val = $(this).data("value");
	var $txtVal = $updateTextarea.val();
	
	$.ajax({
    	url:"/ajax/update/reply",
    	type:"post",
    	dataType : "json",
    	data:{
    		"no":$val,
    		"content":$txtVal
    	},
    	  error:function(){
              alert("서버 정검중!")
          },
          success:function(json){
          		json == 1 ? listReply(pageNo) : alert("실패");
          }//success:function end
  	  });//ajaxList end
  	  
	});
});


</script>
</body>
</html>