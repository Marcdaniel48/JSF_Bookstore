(function(){

var $slides = null;
var slideIndex = 0;
var totalSlides = 0;

function nextSlide()
{
    var $currentSlide = $slides.eq(slideIndex);

    //Update slide index
    if(slideIndex >= (totalSlides-1))
        slideIndex = 0;
    else
        slideIndex++;

    var $nextSlide = $slides.eq(slideIndex);

    $currentSlide.css("transform", "scale(1.5)");

    $currentSlide.animate({opacity: "0"}, 500, 'linear', function()
    {
        $currentSlide.hide();
        $currentSlide.css({transform: "scale(1)", opacity: "1"});
        $nextSlide.css("position", "relative");
    });

    $nextSlide.css({
        "opacity": 0,
        "transform": "scale(1.5)",
        "position": "absolute",
        "top": 0,
        "left": 0,
        "right": 0,
        "bottom": 0
    });

    $nextSlide.show();

    setTimeout(function()
    {
        $nextSlide.css("transform", "scale(1)");
        $nextSlide.animate({opacity: "1"}, 500, 'linear');
    }, 100);
}

function initBanner()
{
    var $nextBtn = $("#next");
    $slides = $(".slide-img");
    totalSlides = $slides.length;

    $slides.eq(slideIndex).show();

    $nextBtn.on("click", nextSlide);
}

function init()
{
    console.log('init');
    initBanner();
}

window.onload = init;
})();