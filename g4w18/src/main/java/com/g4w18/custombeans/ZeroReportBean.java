package com.g4w18.custombeans;

/**
 *
 * @author Salman Haidar
 */
public class ZeroReportBean {
    private String title;
    private String isbn;

    public ZeroReportBean(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
