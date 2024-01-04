package com.ninja_developer.lms;

public class book_issue_constructor {
    private String bookBarcode;

    public String getBookBarcode() {
        return bookBarcode;
    }

    public void setBookBarcode(String bookBarcode) {
        this.bookBarcode = bookBarcode;
    }

    public String getBook_Title() {
        return Book_Title;
    }

    public void setBook_Title(String book_Title) {
        Book_Title = book_Title;
    }

    public String getRoll_number() {
        return Roll_number;
    }

    public void setRoll_number(String roll_number) {
        Roll_number = roll_number;
    }

    public String getAuthor_Name() {
        return Author_Name;
    }

    public void setAuthor_Name(String author_Name) {
        Author_Name = author_Name;
    }

    public String getBook_condition() {
        return Book_condition;
    }

    public void setBook_condition(String book_condition) {
        Book_condition = book_condition;
    }

    public String getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(String date_Time) {
        Date_Time = date_Time;
    }

    public book_issue_constructor(String bookBarcode, String book_Title, String roll_number, String author_Name, String book_condition, String aClass, String date_Time) {
        this.bookBarcode = bookBarcode;
        Book_Title = book_Title;
        Roll_number = roll_number;
        Author_Name = author_Name;
        Book_condition = book_condition;
        Class = aClass;
        Date_Time = date_Time;
    }

    private String Book_Title;
    private String Roll_number;
    private String Author_Name;
    private String Book_condition;
    private String Class;
    private String Date_Time;

}
