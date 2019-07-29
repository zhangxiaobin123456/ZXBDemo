package com.example.test.aidl;
import com.example.test.aidl.Book;

interface IBookManager {
     List<Book> getBookList();
     void addBook(in Book book);
}