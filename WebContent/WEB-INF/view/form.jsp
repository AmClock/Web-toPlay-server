<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>
    <c:choose>
    <c:when test="article!=null">
    update Article
    </c:when>
    <c:otherwise>
    write Article
    </c:otherwise>
    </c:choose>
    </title>
   	<c:import url="/WEB-INF/template/link.jsp"/>
    <link rel="stylesheet" href="/css/form.css"/>
</head>
<body>
<c:import url="/WEB-INF/template/header.jsp"/>
    <form action="/write" method="post">
      <c:if test="${article!=null }">
      <input type="hidden" name="_method" value="PUT" />
      <input type="hidden" name="articleNo" value=${article.no } />
      <input type="hidden" name="referer" value="${referer }" />
      </c:if>
      <input type="hidden" value=${loginUser.no } name="userNo"/>
      <div id="wrap">
        <div id="inputWrap">
            <label for="title">Title</label>
            <input type="text" id="title" name="title" 
            <c:if test="${article!=null }">value="${article.title }"</c:if> />
        </div><!--//inputWrap end-->

        <c:choose>
        <c:when test="${article!=null }">
        <c:forEach items="${contents}" var="content" >
        <div class="wrap_textarea">
        	<input type="hidden" name="noTmp" value=${content.no } />
			<input type="hidden" name="seqTmp" value=${content.seq } />
            <div class="box_remove">
                <button type="button"><i class="fas fa-trash"></i></button>
            </div><!--//box_remove end-->
			
            <div class="box_img">
                <label for="img_${content.seq }"><i class="far fa-image"></i></label>
                <input type="file" id="img_${content.seq }" class="screen_out">
                <img src="/img/article/${content.img }" class="content_img" alt="" title="">
        		<input type="hidden" name="imgTmp" value="${content.img }"/>
            </div><!--//box_img end-->
            <textarea name="contentTmp">${content.content }</textarea><!--//textarea end-->
        </div><!--//wrap_textarea end-->        
        </c:forEach>
        </c:when>
        <c:otherwise>
        <div class="wrap_textarea">
			<input type="hidden" name="seqTmp" value=1 />
            <div class="box_remove">
                <button type="button"><i class="fas fa-trash"></i></button>
            </div><!--//box_remove end-->
			
            <div class="box_img">
                <label for="img_1"><i class="far fa-image"></i></label>
                <input type="file" id="img_1" class="screen_out">
                <img src="" class="content_img" alt="" title="">
        		<input type="hidden" name="imgTmp" />
            </div><!--//box_img end-->
            <textarea name="contentTmp"></textarea><!--//textarea end-->
        </div><!--//wrap_textarea end-->
        </c:otherwise>
        </c:choose>
        
        <div id="formBtnWrap">
            <button>
            <c:choose>
            <c:when test="${article!=null }">
            <i class="fas fa-wrench"></i>
            </c:when>
            <c:otherwise>
			<i class="fas fa-pen"></i>
            </c:otherwise>
            </c:choose>
            </button>
        </div><!--//formBtnWrap end-->
     </div><!--//wrap end-->

    </form><!--//form end-->

    <div class="box_more">
        <button type="button">
            <i class="fas fa-plus"></i>
        </button>
        <div id="msg">5개 까지만 </br>사용이 가능합니다!!</div><!--//msg end  -->
    </div><!--//box_more end-->
<c:import url="/WEB-INF/template/footer.jsp"/>
<script src="/js/form.js"></script>

<script type="text/javascript">
	<c:if test="${article!=null}">
		inputNun = ${contents.size() };
		var $contentVal = 420*(inputNun-1);
		wrapHeight($contentVal);
		$checkImg = true;
		$checkContent = true;
	</c:if> 
</script>

</body>
</html>