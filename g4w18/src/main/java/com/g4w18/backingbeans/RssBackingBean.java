package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomRssController;
import com.g4w18.entities.Rss;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.g4w18.util.Feed;
import com.g4w18.util.FeedMessage;
import com.g4w18.util.RSSFeedParser;

/**
 * @author Jephthia Louis
 * @author Sebastian Ramirez
 */

@Named("rssBackingBean")
@RequestScoped
public class RssBackingBean implements Serializable {

    @Inject
    private CustomRssController rssController;

    private List<FeedMessage> articles;

    /**
     * Getter for the Feed Messages. If it is null, it will generate a new list
     * of Feed Messages from the link in the current active RSS entry.
     *
     * @return a list of FeedMessage objects.
     */
    public List<FeedMessage> getArticles() {
        //Sebastian: added the if null check
        if (articles == null) {
            Rss rss = rssController.findActiveRss();
            RSSFeedParser parser = new RSSFeedParser(rss.getRssLink());
            Feed feed = parser.readFeed();
            articles = feed.getMessages();
        }
        return articles;
    }
}
