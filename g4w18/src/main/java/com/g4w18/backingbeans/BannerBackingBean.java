package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomBannerController;
import com.g4w18.entities.Banner;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Jephthia
 */
@Named
@RequestScoped
public class BannerBackingBean
{
    @Inject
    private CustomBannerController bannerController;
    
    public List<Banner> getActiveBanners()
    {
        return bannerController.getActiveBanners();
    }
}