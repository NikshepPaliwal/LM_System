package com.ninja_developer.lmsystem;

public class model {
   String book_Title;
    String author_Name;

    public String getBook_Title() {
        return book_Title;
    }

    public void setBook_Title(String book_Title) {
        this.book_Title = book_Title;
    }

    public String getAuthor_Name() {
        return author_Name;
    }

    public void setAuthor_Name(String author_Name) {
        this.author_Name = author_Name;
    }

    public String getBookBarcode() {
        return bookBarcode;
    }

    public void setBookBarcode(String bookBarcode) {
        this.bookBarcode = bookBarcode;
    }

    public model(String book_Title, String author_Name, String bookBarcode ,String date_Time) {
        this.book_Title = book_Title;
        this.author_Name = author_Name;
        this.bookBarcode = bookBarcode;
        this.date_Time = date_Time;
    }

    public String getDate_Time() {
        return date_Time;
    }

    public void setDate_Time(String date_Time) {
        this.date_Time = date_Time;
    }

    String date_Time;

    String bookBarcode;
    public model(){

    }

}
