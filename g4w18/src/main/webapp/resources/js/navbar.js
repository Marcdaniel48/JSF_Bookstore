(function()
{
    var opened = false;
    var usernameDropdownOpened = false;
    var $navbarMenu = null;
    var $navbarUsernameDropdown = null;
    
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
    
    function toggleUsernameDropdown()
    {
        usernameDropdownOpened = !usernameDropdownOpened;
        
        if(usernameDropdownOpened)
        {//Open Menu
            var autoHeight = $navbarUsernameDropdown.height();

            $navbarUsernameDropdown.stop();
            $navbarUsernameDropdown.css({height: "0px"});
            $navbarUsernameDropdown.show();

            $navbarUsernameDropdown.animate({height: autoHeight+"px"}, 400);
        }
        else
        {//Close Menu
            $navbarUsernameDropdown.stop();
            
            $navbarUsernameDropdown.animate({height: 0}, 400, function()
            {
                $navbarUsernameDropdown.hide();
                $navbarUsernameDropdown.css({height: "auto"});
            });
        }
    }
    
    function init()
    {
        $("#hamburger-icon").on("click", toggleMenu);
        $navbarMenu = $("#navbar-menu");
        
        $navbarUsernameDropdown = $(".navbar-user-menu").eq(0);
        $("#navbarUsername").on("click", toggleUsernameDropdown);
    }
    
window.addEventListener("load", init);
})();