(function(){

var allItems = [];
var $currentItems = null;

var $bookTemplate = null;

var $prev = null;
var $next = null;

var leftIndex = 0;
var rightIndex = 2;

var inAnimation = false;

function Book(title, cover, price, salePrice)
{
    this.title = title;
    this.cover = cover;
    this.price = price;
    this.salePrice = salePrice;
}

function getPrevItem()
{
    var $item = $bookTemplate.clone();

    var book = allItems[leftIndex];

    $item.find(".book-thumbnail").attr("src", book.cover);
    //$item.find(".book-title").text(book.title);
    //$item.find(".book-price").html("<s>"+book.salePrice+"</s> "+book.price);

    return $item;
}

function getNextItem()
{
    var $item = $bookTemplate.clone();

    var book = allItems[rightIndex];

    $item.find(".book-thumbnail").attr("src", book.cover);
    $item.find(".book-title").text(book.title);
    $item.find(".book-price").html("<s>"+book.salePrice+"</s> "+book.price);

    return $item;
}

function prevItem()
{
    if(inAnimation) return;
    if(leftIndex === 0) return;

    inAnimation = true;
    leftIndex--;
    rightIndex--;

    if(leftIndex === 0)
        $prev.toggleClass("disabled");

    if(rightIndex === (allItems.length - 2))
        $next.toggleClass("disabled");

    $currentItems = $("#book-sales .book");

    var $first = getPrevItem();

    var $last = $currentItems.last();

    $last.animate({opacity: 0}, 600, function()
    {
        $last.remove();
    });

    $currentItems.slice(0,$currentItems.length-1).animate({"left": $last.outerWidth()+"px"}, 600);

    $first.css(
    {
        position: "absolute",
        top: 0,
        left: 0,
        opacity: 0
    });

    $first.prependTo("#book-sales");

    $first.animate({opacity: 1}, 600, function()
    {
        $first.attr("style", "");
        $currentItems.attr("style", "");
        inAnimation = false;
    });
}

function nextItem()
{
    if(inAnimation) return;
    if(rightIndex === (allItems.length - 1)) return;

    inAnimation = true;
    leftIndex++;
    rightIndex++;

    if(leftIndex === 1)
        $prev.toggleClass("disabled");

    if(rightIndex === (allItems.length - 1))
        $next.toggleClass("disabled");

    $currentItems = $("#book-sales .book");

    var $first = $currentItems.first();

    var $last = getNextItem();

    $first.animate({opacity: 0}, 600);

    $currentItems.slice(1).animate({"left": -$first.outerWidth()+"px"}, 600);

    $last.css(
    {
        position: "absolute",
        top: 0,
        right: 0,
        opacity: 0
    });

    $currentItems.last().after($last);

    $last.animate({opacity: 1}, 900, function()
    {
        $last.attr("style", "");
        $currentItems.attr("style", "");
        $first.remove();
        inAnimation = false;
    });
}

function init()
{
    $prev = $("#book-sales .prev");
    $next = $("#book-sales .next");

    $prev.on("click", prevItem);
    $next.on("click", nextItem);

    $bookTemplate = $("#book-sales .book").eq(0).clone();

    for(var i = 0; i < 10; i++)
    {
        allItems.push(new Book(i+"New Title", "/g4w18/javax.faces.resource/book_thumbnail.jpg.xhtml?ln=images", "$"+i+""+i, "$"+i+""+i+""+i));
    }
}

window.addEventListener("load", init);
})();