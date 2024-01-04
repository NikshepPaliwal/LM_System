package com.ninja_developer.lms;



public class book_data_constructor {
    public  String book_title, publish_place,total_pages,author_name,book_condition,fine;

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getPublish_place() {
        return publish_place;
    }

    public void setPublish_place(String publish_place) {
        this.publish_place = publish_place;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getBook_condition() {
        return book_condition;
    }

    public void setBook_condition(String book_condition) {
        this.book_condition = book_condition;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public book_data_constructor(String book_title, String publish_place, String total_pages, String author_name, String book_condition, String fine) {
        this.book_title = book_title;
        this.publish_place = publish_place;
        this.total_pages = total_pages;
        this.author_name = author_name;
        this.book_condition = book_condition;
        this.fine = fine;
    }



}



