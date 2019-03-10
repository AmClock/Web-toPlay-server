<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>
    <c:choose>
    <c:when test="${update!=null }">
    update User
    </c:when>
    <c:otherwise>
    signUp
    </c:otherwise>
    </c:choose></title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/signUp.css"/>
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
          integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
          crossorigin="anonymous">
</head>
<body>
<div id="content">
    <div id="wrap">
        <div id="formWrap">
            <div id="logoBox">
                <a href="/"><img src="/img/logo.png"/></a>
            </div><!--//logoBox end-->
            <form action="/signUp" method="POST">
            	<c:if test="${loginUser!=null }">
            	<input type="hidden" name="_method" value="PUT"/>
            	<input type="hidden" name="no" value="${loginUser.no }"/>
            	</c:if>
            	<input type="hidden" name="profile"  id="profileName" 
            	<c:choose>
   				 <c:when test="${loginUser!=null }">
   				 value="${loginUser.profile }"
    			 </c:when>
   				 <c:otherwise>
   				 value="default.png"
    			</c:otherwise>
    			</c:choose>
            	/>
            	<input type="hidden" name="referer" value="${referer }"/>
                <div class="wrap_input">
                    <label for="id">ID</label>
                    <input class="input_function" type="text" id="id" placeholder="ID" name="id" <c:if test="${update!=null }">disabled value="${loginUser.getId() }"</c:if>/>
                    <div class="box_msg" id="idMsg"></div><!-- //idMsg end -->
                </div><!--//wrap_input end-->
                <div class="wrap_input">
                    <label for="name">Name</label>
                    <input class="input_function" type="text" id="name" placeholder="name" name="name" <c:if test="${update!=null }"> value="${loginUser.getName() }"</c:if>/>
               		<div class="box_msg" id="nameMsg"></div><!-- //nameMsg end -->
                </div><!--//wrap_input end-->

                <div class="wrap_input">
                    <label for="password">Password</label>
                    <input class="input_function" type="password" id="password" placeholder="Password" name="pwd" <c:if test="${update!=null }"> value="${loginUser.getPwd() }"</c:if>/>
               		<div class="box_msg" id="pwdMsg"></div><!--//pwdMsg end  -->
                </div><!--//wrap_input end-->

                <div class="wrap_input">
                    <label for="passwordCheck">PasswordCheck</label>
                    <input class="input_function" type="password" id="passwordCheck" placeholder="PasswordCheck"/>
               		<div class="box_msg" id="checkPwdMsg" ></div><!--//checkPwdMsg end  -->
                </div><!--//wrap_input end-->

                <div class="wrap_input_twe">
                    <span id="labelSpan">Gender</span>
                    <div id="titleRadioMan">Man</div>
                    <div id="titleRadioWoman">Woman</div>
                    <label for="man" class="label_gender"><i class="fas fa-male
                    <c:if test="${update!=null && loginUser.gender=='M' }"> on_i</c:if> "></i></label>
                    <label for="woman"  class="label_gender"><i class="fas fa-female
                    <c:if test="${update!=null && loginUser.gender=='W' }"> on_i</c:if>"></i></label>
                    <input type="radio" name="gender" id="man" class="screen_out" value="M" <c:if test="${update!=null && loginUser.gender=='M' }">checked</c:if>/>
                    <input type="radio" name="gender" id="woman" class="screen_out" value="W" <c:if test="${update!=null && loginUser.gender=='W' }">checked</c:if>/>
                </div><!--//wrap_input end-->

                <div id="profileWrap">
                    <span>Profile</span>
                    <label id="profileBox" for="profile">
                    <c:choose>
                    <c:when test="${update!=null }">
                        <img src="/img/profile/${loginUser.getProfile() }" alt="${loginUser.getProfile() }" 
                        title="${loginUser.getProfile() }" id="profileImg"/>
                    </c:when>
                    <c:otherwise>
                        <img src="/img/profile/default.png" alt="기본이미지" title="기본이미지" id="profileImg"/>
                    </c:otherwise>
                    </c:choose>
                    </label>
                    <input type="file" id="profile" class="screen_out"/>
                </div><!--//profileWrap end-->

                <div id="btnBox">
                     <button type="button"  onclick="location.href='/main'">Go Home</button>
                     <button type="submit">
                     <c:choose>
                     <c:when test="${update!=null }">
                     Update
                     </c:when>
                     <c:otherwise>
                     Sign Up
                     </c:otherwise>
                     </c:choose></button>
                 </div><!--//btnBox end-->
            </form><!--//form end-->
        </div><!--///formWrap end-->
    </div><!--//wrap end-->
</div><!--//content end-->
<script src="/js/jquery.js"></script>
<script src="/js/signUp.js"></script>

<script type="text/javascript">
<c:if test="${update!=nul}">
$checkId = true;
$checkName = true;
$checkedPwd = true;
</c:if>
</script>

</body>
</html>