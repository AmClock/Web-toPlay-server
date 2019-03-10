 	var $form = $("form");
	
	var $moreBtn = $(".box_more button");

	
    var inputNun = 1;

    var $checkImg = false;
    var $checkContent =false;
    
    
    $moreBtn.on('click',function () {

        if(inputNun < 5){
         
         $checkImg = false;
         $checkContent =false;
         $("#msg").css("display","none");

        wrapHeight(+420);
        
        inputNun+=1;
        
        var textArea = $("<div class='wrap_textarea'>" +
        	"<input type='hidden' name='seqTmp' value="+inputNun+" />"+
            "<div class='box_remove'>" +
            "<button type='button'><i class='fas fa-trash'></i></button>" +
            "</div>" +
            "<div class='box_img'><label for='img_"+inputNun+"'><i class='far fa-image'></i></label>" +
            "<input type='file' class='screen_out' id='img_"+inputNun+"'/>" +
            "<img class='content_img' src='' alt='' title=''/>" +
            "<input type='hidden' name='imgTmp' />"+
            "</div>" +
            "<textarea name='contentTmp'></textarea>" +
            "</div>");

        $("#wrap").append(textArea);


        var scmove = $("#footer").offset().top;

        $('html, body').animate( {
            scrollTop : scmove
        }, 1000  );

        }else{
            $("#msg").css("display","block");
        }


    });


    $form.on("click",".box_remove button",function () {
        var num = $(this).parent().next().children("label").attr("for").length;
        var $numFor = $(this).parent().next().children("label").attr("for").substr(num-1,num);

        if($numFor == inputNun && inputNun != 1){
            $("#msg").css("display","none");
            $(this).parent().parent().remove();
            inputNun-=1;
            wrapHeight(-420);
        }else{
            alert("지울 수 없습니다.")
        }
    });


    $form.on("click" ,".box_img label", function () {

        $(this).next().on("change",function () {
        		$checkImg = false;
        		/* $(this).prev().css("display","none");*/
            	var $img = $(this).next();
            	var $imgVal = $(this).next().next();
            	
            	$img.css("display","block");
            	
            	var img = $(this).get(0);
        		console.log("img ::"+img);
        	
        		var file = img.files[0];
        		
        		var form = new FormData();

        		form.append("type","A");//일반적인 데이터
        		
        		form.append("uploadImg",file,file.name);
        		
        		//multipart/form-data로 ajax처리
        		$.ajax({
        			url:"/ajax/upload/img",
        			dataType:"json",
        			type:"POST",//multipart/form-data
        			processData:false,//multipart/form-data
        			contentType:false,//multipart/form-data
        			data:form,
        			error:function() {
        				alert("사진 서버 점검중!");
        				$checkImg = false;
        			},
        			success:function(json) {
        				console.log("json.src :: " + json.src);
        				$img.attr("src","/img/article/"+json.src);
        				$imgVal.val(json.src);
        				$checkImg = true;
        			}//success end
        		});//ajax() end
            
        });//change end

    });//click end

    function wrapHeight(num) {
        var height =$('div#wrap ').css("height");
        var heightNum = parseInt(height.substr(0,height.indexOf('px')))+num;
        $('div#wrap ').css("height",heightNum);
        fixFooter();
    }
    
    $form.submit(function() {
		if($checkImg==false || $checkContent == false){
			return false;
		}
	});
    
    $form.on("keyup","textarea",function(){
    	$checkContent = false;
    	if($("textarea").val() != ''){
    		$checkContent = true;
    	}
    });

