package com.example.test.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Description:
 */

public class Book implements Parcelable{
    private int bookId;
    private String bookName;
    private String price;



    public Book(int bookId, String bookName){
        this.bookId = bookId;
        this.bookName = bookName;
    }
    public Book(){
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
        price = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getBookId() {
        return bookId ;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName ;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
        dest.writeString(price);
    }
}
