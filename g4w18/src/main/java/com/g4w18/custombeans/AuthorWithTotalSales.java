package com.g4w18.custombeans;

import com.g4w18.entities.Author;

/**
 * Bean used to represent an author, the total sales his books have made, and the last recorded sale date for one of his works.
 * The author's total sales and last recorded sale date are set and retrieved by extending the Total Sales bean.
 * 
 * @author Marc-Daniel
 */
public class AuthorWithTotalSales extends TotalSalesBean
{
    private Author author;

    /**
     * Getter method. Returns the author.
     * @return author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Setter method. Sets the author.
     * @param author 
     */
    public void setAuthor(Author author) {
        this.author = author;
    }
}
