    var windowWidth = 0;
    var windowHeight = 0;

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


    $(".wrap_input input").focusin(function () {
        $(this).attr("placeholder","");
        $(".label_opcitiy").removeClass("label_opcitiy");
        $(this).prev().addClass('label_opcitiy');
        var labelText = $(this).prev().text();
        $(this).focusout(function () {
            $(".label_opcitiy").removeClass("label_opcitiy");
            $(this).attr("placeholder",labelText);
        });
    });
