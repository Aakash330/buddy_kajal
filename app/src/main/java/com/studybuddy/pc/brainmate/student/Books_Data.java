package com.studybuddy.pc.brainmate.student;

public class Books_Data {
    private Book_data[] book_data;

    public Book_data[] getBook_data() {
        return book_data;
    }

    public void setBook_data(Book_data[] book_data) {
        this.book_data = book_data;
    }

    @Override
    public String toString() {
        return "ClassPojo [book_data = " + book_data + "]";
    }
}
