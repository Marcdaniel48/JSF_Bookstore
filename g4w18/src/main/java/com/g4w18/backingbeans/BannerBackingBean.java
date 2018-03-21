package com.g4w18.backingbeans;

import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.customcontrollers.CustomBannerController;
import com.g4w18.entities.Banner;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

@Named
@RequestScoped
public class BannerBackingBean implements Serializable
{
    @Inject
    private CustomBannerController bannerController;
    
    private Part uploadedBannerFile;
    private String uploadedBannerLink;
    private boolean uploadedBannerStatus;
    
    private Logger logger = Logger.getLogger(getClass().getName());
    
    /**
     * @author Jephthia
     * @return The uploaded file
     */
    public Part getUploadedBannerFile()
    {
        return uploadedBannerFile;
    }
    
    /**
     * @author Jephthia
     * @param uploadedBannerFile The file that was uploaded
     */
    public void setUploadedBannerFile(Part uploadedBannerFile)
    {
        this.uploadedBannerFile = uploadedBannerFile;
    }
    
    /**
     * @author Jephthia
     * @return The uploaded banner link
     */
    public String getUploadedBannerLink()
    {
        return uploadedBannerLink;
    }
    
    /**
     * @author Jephthia
     * @param uploadedBannerLink The link that was uploaded
     */
    public void setUploadedBannerLink(String uploadedBannerLink)
    {
        this.uploadedBannerLink = uploadedBannerLink;
    }
    
    /**
     * @author Jephthia
     * @return The uploaded banner active status
     */
    public boolean getUploadedBannerStatus()
    {
        return uploadedBannerStatus;
    }
    
    /**
     * @author Jephthia
     * @param uploadedBannerStatus The status of the uploaded banner
     */
    public void setUploadedBannerStatus(boolean uploadedBannerStatus)
    {
        this.uploadedBannerStatus = uploadedBannerStatus;
    }
    
    /**
     * @author Jephthia
     * @return A list of all the currently active banners
     */
    public List<Banner> getActiveBanners()
    {
        return bannerController.getActiveBanners();
    }
    
    /**
     * @author Jephthia
     * @return A list of all the banners in the database
     */
    public List<Banner> getAllBanners()
    {
        return bannerController.findBannerEntities();
    }
    
    public void listen(ValueChangeEvent e)
    {
        Banner banner = (Banner)((UIInput)e.getSource()).getAttributes().get("banner");

        logger.log(Level.INFO, LocalDateTime.now() + " >>> test");

        try
        {
            banner.setIsActive((boolean)e.getNewValue());
            bannerController.edit(banner);
        }
        catch(Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    public void addNewBanner()
    {
        logger.log(Level.INFO, LocalDateTime.now() + " >>> file {0}", uploadedBannerFile);
        logger.log(Level.INFO, LocalDateTime.now() + " >>> link {0}", uploadedBannerLink);
        logger.log(Level.INFO, LocalDateTime.now() + " >>> status {0}", uploadedBannerStatus);

        try (InputStream input = uploadedBannerFile.getInputStream())
        {
            String fileName = uploadedBannerFile.getSubmittedFileName();
            
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            ServletContext sc = (ServletContext)context.getContext();
            String path = sc.getRealPath("/resources/images/");
            
            logger.log(Level.INFO, LocalDateTime.now() + " >>> file {0}", path);
             
            Files.copy(input, new File(path, fileName).toPath());
            
            Banner newBanner = new Banner();
            newBanner.setBannerName(fileName);
            newBanner.setBannerLink(uploadedBannerLink);
            newBanner.setIsActive(uploadedBannerStatus);
            
            bannerController.create(newBanner);
        }
        catch(IOException e)
        {
            logger.log(Level.SEVERE, LocalDateTime.now() + " >>> Couldn't save the uploaded image", e);
        }
        catch(Exception e)
        {
            logger.log(Level.SEVERE, LocalDateTime.now() + " >>> Couldn't create a new banner", e);
        }
        
        //Reset
        uploadedBannerFile = null;
        uploadedBannerLink = null;
        uploadedBannerStatus = false;
    }
}