<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/template" id="replyTmp">
<@ _.each(replies,function(reply){@>
   <li class="wrap_reply_list">
                <div class="box_reply_list">
                    <div class="wrap_read_user">
                        <img src="/img/profile/<@=reply.userProfile@>" 
						alt="<@=reply.userName@>" title="<@=reply.userName@>"/>
                        <div class="box_nickname">
                            <span><@=reply.userName@></span>
                        </div><!--//wrap_read_user end-->
                    </div><!--//wrap_read_user end-->
                    <div class="reply_content">
					<@=reply.content@>
                    </div><!--//reply_content end-->
			<@if(checkId(reply.userNo)){@>
					<textarea  placeholder="Write to reply..."></textarea>
					<div class="wrap_btn_reply">
                                <button class="btn_clear" data-value="<@=reply.no@>" ><i class="fas fa-times"></i></button>
                                <button class="btn_update"><i class="fas fa-pencil-alt"></i></button>
								<button class="real_update" type="button" data-value="<@=reply.no@>"><i class="fas fa-pencil-alt"></i></button>
                    </div><!--//wrap_btn_reply end-->
					<input type="hidden" value="<@=reply.content@>"/>
			<@}@>
                </div><!--//box_reply_list end-->
    </li><!--//wrap_reply_list end-->
<@}) @>
</script>

<script type="text/template" id="articleTmp">
<@ _.each(articles,function(article){@>
      			<li>
                    <a href="/detail/<@=article.no@>">
                            <div class="box_img">
                                <img src="/img/article/<@=article.img@>"/>
                            </div>
                            <div id="titleBox" class="box_card_text over_flow"><@=article.title@></div>
                            <div id="nicknameBox" class="box_card_text"><@=article.userName@></div>
                        	<div class="box_card_text">
                            <span class="icon_content"><i class="fas fa-heart"></i> <@=article.likes@></span>
                            <span class="icon_content"><i class="fas fa-comment-alt"> <@=article.reply@></i></span>
                            <span class="icon_content"><i class="fas fa-eye"> <@=article.views@></i></span>
                            <span class="regdate_box"><@=moment(article.regdate).format('YYYY년MM월DD일')@></span>
                        </div><!--//box_card_text end-->
                    </a>
                </li><!--//card end-->
<@}) @>
</script>
<script type="text/template" id="searchTmp">
<@_.each(titles,function(title){@>
<li class="box_other" data-value="<@=title.no@>"><@=title.title@></li>
<@}) @>
</script>




<script src="/js/underscore-min.js"></script>
<script src="/js/moment-with-locales.js"></script>
<script type="text/javascript">
	_.templateSettings = {
		interpolate: /\<\@\=(.+?)\@\>/gim,
		evaluate: /\<\@([\s\S]+?)\@\>/gim,
		escape: /\<\@\-(.+?)\@\>/gim
		};
		
		var $replyTmpl = _.template($('#replyTmp').html());		
		var $articleTmpl = _.template($('#articleTmp').html());		
		var $searchTmpl = _.template($('#searchTmp').html());		

		function checkId(x) {
			var num = 0;
			<c:if test="${loginUser!=null}">
			num = ${loginUser.no};
			</c:if>
			return num == x ? true : false;
		}
</script>
