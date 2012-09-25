/*var mouseX;
var mouseY;
$(document).mousemove(function (e) {
    mouseX = e.pageX;
    mouseY = e.pageY;
});

$(".preview").mouseover(function (element) {
    $('#fullSize').css({'top':mouseY, 'left':mouseX}).fadeIn('slow');
    $('#fullSizeImg').src(element.currentTarget.src);
});   */

$("#fullSize").mouseout(function () {
    $('#fullSize').fadeOut('slow');
});


$('table i.del').click(function () {
    $(this).parent().parent().fadeOut('slow');
});



