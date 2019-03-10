<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<div id="header">
    <div id="headerWrap">
        <div id="logoWrap">
            <a href="/"><img src="/img/logo.png"/></a>
        </div><!--// logo Wrap-->

        <div id="loginWrap">
        <c:choose>
        <c:when test="${loginUser==null }">
            <div id="logoutBox">
                <a class="btn_login" href="/signUp">
                    <div>Sign Up</div>
                </a>
                <a class="btn_login" href="/login">
                    <div>Login</div>
                </a>
            </div><!--//logoutBox end-->
        </c:when>
        <c:otherwise>
            <div id="lgoinBox">
                <div id="profileBox" class="pos_logout_cont">
                	<a href="/update"><img  alt="${loginUser.profile }" src="/img/profile/${loginUser.profile }"></a>
                </div> <!--//profileBox end  -->
                <form action="/login" method="POST">
                <input type="hidden" name="_method" value="DELETE"/>
                <button id="logoutBtn" type="submit" class="pos_logout_cont">Logout</button>
                </form>
            </div> <!--//loginUser  -->
        </c:otherwise>
        </c:choose>
        </div><!--//loginWrap end-->
    </div><!--headerWrap end-->
</div><!--//header  end-->

<div id="content">