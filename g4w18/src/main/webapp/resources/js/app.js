(function(){
    
function adjustGenrePadding()
{
    var $genreSection = $("#genres");
    var minIconSize = 70;
    var totalGenres = 5;
    var minSectionWidth = minIconSize * totalGenres;
    var $genreTitle = $(".genre-title").first();
    var $genres = $(".genre-btn");
    
    setTimeout(function()
    {
        calculatePadding();
    }, 200);

    window.addEventListener('resize', calculatePadding);
    
    function calculatePadding()
    {
        if($genreSection.width() <= minSectionWidth)
        {
            // How many genres can fit in one row.
            var totalInRow = Math.floor($genreSection.width() / minIconSize);
            // The total space left for padding.
            var totalSpaceForPadding = $genreSection.width() % minIconSize;
            // Padding for each genre.
            var genrePadding = Math.floor(totalSpaceForPadding / (totalInRow * 2));
            
            //top right bottom left
            $genres.css("margin", "0 " + genrePadding + "px " + $genreTitle.height() + "px " + genrePadding + "px");
        }
        else
        {
            $genres.css("margin", "0 0");
        }
    }
}

function init()
{
    console.log('init');
    adjustGenrePadding();
}

window.onload = init;
})();