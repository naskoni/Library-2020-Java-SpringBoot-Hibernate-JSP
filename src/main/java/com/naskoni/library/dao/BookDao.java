package com.naskoni.library.dao;

import com.naskoni.library.entity.Book;

import java.util.Set;

public interface BookDao {

  Book findBook(String name);

  Book findBook(Long id);

  Set<Book> findBooks();

  Set<Book> findBooks(String searchedWord, String searchParam);

  Set<Book> findBooks(int searchedNumber, String searchParam);

  void addBook(Book book);

  void deleteBook(Long id);

  void update(Book book);
}
