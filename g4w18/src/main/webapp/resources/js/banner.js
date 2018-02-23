(function(){

var $slides = null;
var $dots = null;
var slideIndex = 0;
var totalSlides = 0;
var inAnimation = false;

function goToSlide(_index)
{
    if(_index === slideIndex) return;
    if(inAnimation) return;

    var $currentSlide = $slides.eq(slideIndex);
    $dots.eq(slideIndex).toggleClass("active");

    var $nextSlide = $slides.eq(_index);
    $dots.eq(_index).toggleClass("active");

    slideIndex = _index;

    showSlide($currentSlide, $nextSlide);
}

function prevSlide()
{
    if(inAnimation) return;

    var $currentSlide = $slides.eq(slideIndex);
    $dots.eq(slideIndex).toggleClass("active");

    //Update slide index
    if(slideIndex <= 0)
        slideIndex = (totalSlides - 1);
    else
        slideIndex--;

    var $nextSlide = $slides.eq(slideIndex);
    $dots.eq(slideIndex).toggleClass("active");

    showSlide($currentSlide, $nextSlide);
}

function nextSlide()
{
    if(inAnimation) return;

    var $currentSlide = $slides.eq(slideIndex);
    $dots.eq(slideIndex).toggleClass("active");

    //Update slide index
    if(slideIndex >= (totalSlides-1))
        slideIndex = 0;
    else
        slideIndex++;

    var $nextSlide = $slides.eq(slideIndex);
    $dots.eq(slideIndex).toggleClass("active");

    showSlide($currentSlide, $nextSlide);
}

function showSlide($currentSlide, $nextSlide)
{
    inAnimation = true;

    $currentSlide.css("transform", "scale(2)");

    $currentSlide.animate({opacity: "0"}, 500, 'linear', function()
    {
        $currentSlide.hide();
        $currentSlide.css("style", "");
        inAnimation = false;
    });

    $nextSlide.css({
        "opacity": 0,
        "transform": "scale(2.8)",
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
        $nextSlide.css("style", "");
    }, 100);
}

function init()
{
    $slides = $(".slide-img");
    totalSlides = $slides.length;

    $slides.eq(slideIndex).show();

    $("#banner .prev").on("click", prevSlide);
    $("#banner .next").on("click", nextSlide);

    var $dotsContainer = $(".dots-container");

    for(var i = 0; i < totalSlides; i++)
    {
        var $dot = $("<span class='dot'></span>");
        $dot.appendTo($dotsContainer);

        registerDotClick($dot, i);
    }

    function registerDotClick($dot, _index)
    {
        $dot.on("click", function()
        {
            goToSlide(_index);
        });
    }

    $dots = $(".dot");
    $dots.eq(slideIndex).toggleClass("active");

    setInterval(function()
    {
        nextSlide();
    }, 5000);
}

window.addEventListener("load", init);
})();