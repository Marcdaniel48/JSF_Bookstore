/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.backingbeans;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import rss.Feed;
import rss.FeedMessage;
import rss.RSSFeedParser;

/**
 * @author Jephthia Louis
 */
@Named("rssBackingBean")
@RequestScoped
public class RssBackingBean
{
   public List<FeedMessage> getArticles()
   {
       //RSSFeedParser parser = new RSSFeedParser("http://www.cbc.ca/cmlink/rss-sports");
       RSSFeedParser parser = new RSSFeedParser("https://www.nasa.gov/rss/dyn/educationnews.rss");
      
       Feed feed = parser.readFeed();
       return feed.getMessages();
   }
}