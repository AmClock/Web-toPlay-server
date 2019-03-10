	
	//아이디가  첫글자는 영어로, 영어와 숫자로 4~20글자까지라면
	var idReg = /^[a-z|A-Z][\w]{3,19}$/;
	
	var nameReg = /^[a-z|A-Z|가-힣]{3,10}$/;
	
	//비밀번호가 4~32자로 영어, 숫자
	var pwdReg = /^[\w]{4,32}$/;
	
	
	var windowWidth = 0;
    var windowHeight = 0;
    var $form = $("#formWrap form"); 
   
    var $id = $("#id");
    var $idMsg = $("#idMsg");
    var $checkId = false;
    
    var $name = $("#name");
    var $nameMsg=$("#nameMsg");
    var $checkName = false;
    
    
    var $pwd = $("#password");
    var $pwdMsg = $("#pwdMsg");
    var $checkedPwd = false; 
    
    var $checkPwdMsg = $("#checkPwdMsg");
    var $checkPwd = false;
    
    resizeAll();

    $(window).resize(function () {
        resizeAll();
    });

    function resizeAll() {
        windowWidth =$(window).width();
        windowHeight =$(window).height();
        $("#content").css({
            "height":(windowHeight)
        });
        $("#wrap").css({
            "height":(windowHeight)
        });
    } // resizeAll() end


    $(".wrap_input .input_function").focusin(function () {
        $(this).attr("placeholder","");
        $(".label_opcitiy").removeClass("label_opcitiy");
        $(this).prev().addClass('label_opcitiy');
        var labelText = $(this).prev().text();
        $(this).focusout(function () {
            $(".label_opcitiy").removeClass("label_opcitiy");
            $(this).attr("placeholder",labelText);
        });
    });


    $(".wrap_input_twe label i").on("click",function () {
        $(".on_i").removeClass("on_i");
        $(this).addClass("on_i");
    })
    
    $id.on("keyup",checkId).blur(checkId).focus();
    $name.keyup(checkName);
    $pwd.keyup(checkPwd);
	
	$("#passwordCheck").keyup(function(){
		if($pwd.val() == $("#passwordCheck").val()){
			$checkPwdMsg.text("Same Password !!! ").css("color","#16a085");
			$checkPwd = true;
		} else{
			$checkPwdMsg.text("Not Same Password !!! ").css("color","#e74c3c");			
			$checkPwd = false;
		}//if ~ else end
	});
	
	$form.submit(function() {
		if($checkId == false || $checkName == false || 
				$checkPwd == false || $checkedPwd == false){
			return false;
		}
	});
	
	 function checkId() {
		 var $val = $id.val();
		 $checkId = false;
		 if($val!=""){
			 if(idReg.test($val)){
				 $idMsg.text("확인 중 입니다....").css("color","#e74c3c");
			    	$.ajax({
						url:"/ajax/check/id/"+$id.val(),
						type: "GET",
				    	dataType : "json",
						error:function(){
							alert("error !!");
						},
						success:function(json){
								if(json.result == true){
									$idMsg.text("Good ID :)").css("color","#16a085");
									$checkId = true;
								}else{
									$idMsg.text("No !! ID :(").css("color","#e74c3c");
									$checkId = false;
								}
							}
						})//ajax end
			 }else{
					$idMsg.text(" 첫글자는 영어로, 영어와 숫자로 4~20글자까지로 입력 하세요.").css("color","#e74c3c");
		    		$checkId = false;
			 }// if ~ else
		 }else{
			 $idMsg.text("id를 입력 해주세요 !!").css("color","#e74c3c"); 
		 }
	};
	
	
	function checkName() {
		/*nameReg*/
		var $val = $name.val();
		$checkName = false;		
		if($val!=""){
			if(nameReg.test($val)){
				$nameMsg.text("확인 중 입니다...").css("color","#e74c3c");
				$.ajax({
					url:"/ajax/check/name/"+$name.val(),
					type:"GET",
					dataType:"json",
					error:function(){
						alert("error");
					},
					success:function(json){
						if(json.result == true){
							$nameMsg.text("Good ID :)").css("color","#16a085");
							$checkName = true;
						}else{
							$nameMsg.text("No !! ID :(").css("color","#e74c3c");
							$checkName = false;
						}//if ~ else
					}//success end
				})// ajax end
			}else{
				$nameMsg.text("3 ~ 10 자리 영어 또는 한글로 작성 하시오").css("color","#e74c3c");
				$checkName = false;
			}
		}else{
			$nameMsg.text("Name 를 입력해 주세요").css("color","#e74c3c");
			$checkName = false;	
		}
	}//checkName
	
	
	function checkPwd() {
		var $val = $pwd.val();
		$checkedPwd = false;
		if($val != "" && pwdReg.test($val)){
			$pwdMsg.text("Good Password !!").css("color","#16a085");
			$checkedPwd = true;
		}else{
			$pwdMsg.text(" 4~32자로 영어, 숫자로 입력하시오...").css("color","#e74c3c");
			$checkedPwd = false;
		}
	}// chcekPwd end
	
	
	
	
	var $profile = $("#profile");
	var $profileImg = $("#profileImg");
	var $profileName = $("#profileName");
	
	$profile.change(function() {
		profileUpload();
	});
	

	function profileUpload() {
		
		var profile = $profile.get(0);
	
		var file = profile.files[0];
		
		var form = new FormData();
		
		form.append("type","P");//일반적인 데이터
		
		form.append("uploadImg",file,file.name);
		
		//multipart/form-data로 ajax처리
		$.ajax({
			url:"/ajax/upload",
			dataType:"json",
			type:"POST",//multipart/form-data
			processData:false,//multipart/form-data
			contentType:false,//multipart/form-data
			data:form,
			error:function() {
				alert("사진 서버 점검중!");
			},
			success:function(json) {
				console.log("json.src :: " + json.src);
				$profileImg.attr("src","/img/profile/"+json.src);
				$profileName.val(json.src);
			}//success end
		});//ajax() end
	}//profileUpload end