(function(){

var $books = null;

var $prev = null;
var $next = null;

var leftIndex = 0;
var rightIndex = 2;

var inAnimation = false;

function prevItem()
{
    if(inAnimation) return;
    if(leftIndex === 0) return;

    inAnimation = true;
    leftIndex--;
    rightIndex--;

    if(leftIndex === 0)
        $prev.toggleClass("disabled");

    if(rightIndex === ($books.length - 2))
        $next.toggleClass("disabled");
    
    var $first = $books.eq(leftIndex);

    var $last = $books.eq(rightIndex + 1);

    $last.animate({opacity: 0}, 600);

    $books.slice(leftIndex + 1, rightIndex + 1).animate({"left": $last.outerWidth()+"px"}, 600);

    $first.css(
    {
        position: "absolute",
        top: 0,
        left: 0,
        opacity: 0
    });

    $first.css("display", "inline-block");
    
    $first.animate({opacity: 1}, 600, function()
    {
        $books.slice(leftIndex, rightIndex + 1).attr("style", "display: inline-block;");
        $last.attr("style", "");
        inAnimation = false;
    });
}

function nextItem()
{
    if(inAnimation) return;
    if(rightIndex === ($books.length - 1)) return;

    inAnimation = true;
    leftIndex++;
    rightIndex++;

    if(leftIndex === 1)
        $prev.toggleClass("disabled");

    if(rightIndex === ($books.length - 1))
        $next.toggleClass("disabled");
    
    var $first = $books.eq(leftIndex - 1);
    
    var $last = $books.eq(rightIndex);
    
    $first.animate({opacity: 0}, 600);

    $books.slice(leftIndex, rightIndex).animate({"left": -$first.outerWidth()+"px"}, 600);

    $last.css(
    {
        position: "absolute",
        top: 0,
        right: 0,
        opacity: 0
    });
    
    $last.css("display", "inline-block");
    
    $last.animate({opacity: 1}, 900, function()
    {
        $first.attr("style", "");
        $books.slice(leftIndex, rightIndex + 1).attr("style", "display: inline-block;");
        inAnimation = false;
    });
}

function init()
{
    $prev = $(".recommendations .prev");
    $next = $(".recommendations .next");

    $prev.on("click", prevItem);
    $next.on("click", nextItem);

    $books = $(".recommendations .book");
    
    if($books.length < 3)
    {
        rightIndex = $books.length;
    }
    
    $books.slice(0, 3).css("display", "inline-block");
}

window.addEventListener("load", init);
})();