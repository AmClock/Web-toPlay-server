var flag = 0 ;
$("#searchIcon").on('click',function () {
    console.log("if 들어가기전"+flag);
    if(flag==0){
        $("#menuBox").css("left","-1050px");
       /* $("#searchBox input").focus();*/
        flag=1;
        console.log("if 들어가기 후"+flag);
    }else{
        $("#menuBox").css("left","0px");
        flag=0;
        console.log("if 들어가기 후"+flag);
    }
    
});