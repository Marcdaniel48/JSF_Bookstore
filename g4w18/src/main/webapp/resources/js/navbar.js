(function()
{
    var opened = false;
    var $navbarMenu = null;
    
    function toggleMenu()
    {
        opened = !opened;
        
        $("#hamburger-icon").toggleClass("opened");
        
        if(opened)
        {//Open Menu
            var autoHeight = $navbarMenu.height();

            $navbarMenu.stop();
            $navbarMenu.css({height: "0px"});
            $navbarMenu.show();

            $navbarMenu.animate({height: autoHeight+"px"}, 400);
        }
        else
        {//Close Menu
            $navbarMenu.stop();
            
            $navbarMenu.animate({height: 0}, 400, function()
            {
                $navbarMenu.hide();
                $navbarMenu.css({height: "auto"});
            });
        }
    }
    
    function init()
    {
        $("#hamburger-icon").on("click", toggleMenu);
        $navbarMenu = $("#navbar-menu");
    }
    
window.addEventListener("load", init);
})();