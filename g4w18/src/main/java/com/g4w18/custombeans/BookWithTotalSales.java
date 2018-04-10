package com.g4w18.custombeans;

import com.g4w18.entities.Book;
import java.io.Serializable;

/**
 * Bean used to represent a book, the total sales that the book has made, and the last recorded sale date for the book.
 * The book's total sales and last recorded sale date are set and retrieved by extending the Total Sales bean.
 * 
 * @author Marc-Daniel
 */
public class BookWithTotalSales extends TotalSalesBean implements Serializable
{
    private Book book;

    /**
     * Getter method. Returns the book.
     * @return 
     */
    public Book getBook() {
        return book;
    } 
    
    /**
     * Setter method. Sets the book.
     * @param book 
     */
    public void setBook(Book book) {
        this.book = book;
    } 
}
