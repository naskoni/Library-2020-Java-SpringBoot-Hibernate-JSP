package com.naskoni.library.service;

import com.naskoni.library.dto.BookDto;

import java.util.Set;

public interface BookService {

  BookDto findBook(String name);

  BookDto findBook(Long id);

  Set<BookDto> findBooks();

  void deleteBook(Long id);

  void addBook(BookDto bookDto);

  void updateBook(BookDto bookDto);

  Set<BookDto> findBooks(String searchedWord, String searchParam);
}
