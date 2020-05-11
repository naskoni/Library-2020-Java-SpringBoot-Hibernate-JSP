package com.naskoni.library.service.impl;

import com.naskoni.library.dao.BookDao;
import com.naskoni.library.dto.BookDto;
import com.naskoni.library.entity.Book;
import com.naskoni.library.service.BookService;
import com.naskoni.library.util.ParseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.naskoni.library.constant.CommonConstants.ASTERISK;
import static com.naskoni.library.constant.CommonConstants.YEAR;

@Service
public final class BookServiceImpl implements BookService {

  @Autowired private BookDao bookDao;

  @Override
  public void addBook(BookDto bookDto) {
    Book book = mapBookEntity(bookDto);
    bookDao.addBook(book);
  }

  @Override
  public void updateBook(BookDto bookDto) {
    Book book = mapBookEntity(bookDto);
    bookDao.update(book);
  }

  @Override
  public BookDto findBook(String name) {
    Book book = bookDao.findBook(name);

    return mapBookDto(book);
  }

  @Override
  public BookDto findBook(Long id) {
    Book book = bookDao.findBook(id);

    return mapBookDto(book);
  }

  @Override
  public Set<BookDto> findBooks() {
    Set<Book> books = bookDao.findBooks();

    return mapBookDtos(books);
  }

  @Override
  public Set<BookDto> findBooks(String searchedWord, String searchParam) {
    Set<Book> books;
    if (searchedWord.isEmpty() || ASTERISK.equals(searchedWord)) {
      books = bookDao.findBooks();
    } else if (YEAR.equals(searchParam) && ParseUtils.tryParseNumber(searchedWord)) {
      int searchedNumber = Integer.parseInt(searchedWord);
      books = bookDao.findBooks(searchedNumber, searchParam);
    } else {
      books = bookDao.findBooks(searchedWord, searchParam);
    }

    return mapBookDtos(books);
  }

  @Override
  public void deleteBook(Long id) {
    bookDao.deleteBook(id);
  }

  private Set<BookDto> mapBookDtos(Set<Book> books) {
    var result = new HashSet<BookDto>();
    for (var book : books) {
      BookDto bookDto = mapBookDto(book);
      result.add(bookDto);
    }

    return result;
  }

  private BookDto mapBookDto(Book book) {
    var bookDto = new BookDto();
    BeanUtils.copyProperties(book, bookDto);

    return bookDto;
  }

  private Book mapBookEntity(BookDto bookDto) {
    var book = new Book();
    BeanUtils.copyProperties(bookDto, book);
    return book;
  }
}
