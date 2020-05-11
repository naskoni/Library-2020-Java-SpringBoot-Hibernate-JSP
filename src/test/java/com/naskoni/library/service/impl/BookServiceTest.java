package com.naskoni.library.service.impl;

import com.naskoni.library.dao.BookDao;
import com.naskoni.library.dto.BookDto;
import com.naskoni.library.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

  @Mock private BookDao bookDao;

  @InjectMocks private BookServiceImpl bookService;

  @Test
  void testFindBooks() {
    Set<BookDto> bookDtos = bookService.findBooks();
    Assertions.assertTrue(bookDtos.isEmpty());

    Set<Book> books = new HashSet<>();
    var book = new Book();
    book.setId(1L);
    book.setName("name");
    book.setAuthor("author");
    book.setIsbn("164571274");
    book.setYear(1999);
    books.add(book);

    Mockito.when(bookDao.findBooks()).thenReturn(books);
    bookDtos = bookService.findBooks();
    Assertions.assertEquals(1, bookDtos.size());

    BookDto bookDto = bookDtos.iterator().next();
    Assertions.assertEquals(book.getId(), bookDto.getId());
    Assertions.assertEquals(book.getName(), bookDto.getName());
    Assertions.assertEquals(book.getAuthor(), bookDto.getAuthor());
    Assertions.assertEquals(book.getIsbn(), bookDto.getIsbn());
    Assertions.assertEquals(book.getYear(), bookDto.getYear());
  }

}
