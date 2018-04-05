(function(){
    var $articles = null;

    var $prev = null;
    var $next = null;

    var leftIndex = 0;
    var rightIndex = 1;

    var inAnimation = false;

    function prevItem()
    {
        if (inAnimation)
            return;
        if (leftIndex === 0)
            return;

        //inAnimation = true;
        leftIndex--;
        rightIndex--;

        if (leftIndex === 0)
            $prev.toggleClass("disabled");

        if (rightIndex === ($articles.length - 2))
            $next.toggleClass("disabled");

        var $current = $articles.eq(rightIndex);

        var $nextArticle = $articles.eq(leftIndex);

        $current.hide();
        $nextArticle.show();

        //leftindex = 1
        //rightindex = 2
    }

    function nextItem()
    {
        if (inAnimation)
            return;
        if (rightIndex === ($articles.length - 1))
            return;

        var $first = $articles.eq(leftIndex);

        var $last = $articles.eq(rightIndex);

        //inAnimation = true;
        leftIndex++;
        rightIndex++;

        if (leftIndex === 1)
            $prev.toggleClass("disabled");

        if (rightIndex === ($articles.length - 1))
            $next.toggleClass("disabled");

        $first.hide();
        $last.show();
    }

    function init()
    {
        $prev = $("#rss-feed .prev");
        $next = $("#rss-feed .next");

        $prev.on("click", prevItem);
        $next.on("click", nextItem);

        $articles = $("#rss-feed .article");

        if ($articles.length < 2)
        {
            rightIndex = $articles.length;
        }

        $articles.eq(0).css("display", "inline-block");
    }

    window.addEventListener("load", init);
})();