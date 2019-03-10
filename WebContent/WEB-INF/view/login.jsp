<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/login.css"/>
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
</head>
<body>
<div id="content">
    <div id="wrap">
        <div id="loginWrap">
            <div id="logoBox">
                <a href="/"><img src="img/logo.png"/></a>
            </div><!--//logoBox end-->
            <form action="/login" method="POST">
            <input type="hidden" name="referer" value="${referer }"/>
                <div class="wrap_input">
                    <label for="id">ID</label>
                    <input type="text" id="id" placeholder="ID" name="id"/>
                </div><!--//wrap_input end-->
                <div class="wrap_input">
                    <label for="password">Password</label>
                    <input type="password" id="password" placeholder="Password" name="pwd"/>
                </div><!--//wrap_input end-->
                 <div id="btnBox">
                     <button type="submit">Login</button>
                 </div><!--//btnBox end-->
                <div id="eventWrap">

                    <div class="wrap_comment">
                        <a href="/signUp">Click here to sign up</a>
                    </div><!--//wrap_comment end-->

                    <div class="wrap_comment">
                        <a href="#" id="idCheck">ID check</a>
                        <spna>|</spna>
                        <a href="#" id="pwdCheck">Password check</a>
                    </div><!--//wrap_comment end-->

                </div><!--//eventWrap end-->
            </form><!--//form end-->
        </div><!--///lgoingWrap-->

    </div><!--//wrap end-->
</div><!--//content end-->

<script src="/js/jquery.js"></script>
<script src="/js/login.js"></script>
</body>
</html>