(function(){
    
    var articles = [];
    var currentArticle = null;
    var currentIndex = 0;
    
    function nextArticle()
    {
        if(currentIndex >= articles.length - 1)
            return;
                
        currentArticle = articles[currentIndex++];
        
        
        
        
        
        if(currentIndex === articles.length - 1)
        {
            //disable next button
        }
    }
    
    function init()
    {
        // $.getJSON("https://www.bookbrowse.com/blogs/editor/rss.cfm"+"&callback=?").done(function (data) {
        //     console.log(data);
        // });
        // return;
        var feed = "https://www.bookbrowse.com/blogs/editor/rss.cfm";
        $.ajax({
            type: "get",
            url: "https://api.rss2json.com/v1/api.json?rss_url="+feed,
            dataType: "jsonp",
            success: function(_data)
            {
                var items = _data.items;
                for(var i = 0; i < items.length; i++)
                {
                    var item = items[i];
//                    console.log("------------------------");
//                    console.log("title      : " + item.title);
//                    console.log("link       : " + item.link);
//                    console.log("description: " + item.description);
                }
            }
        });
    }
    window.addEventListener("load", init);
})();