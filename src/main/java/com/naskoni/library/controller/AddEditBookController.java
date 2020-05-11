package com.naskoni.library.controller;

import com.naskoni.library.dto.BookDto;
import com.naskoni.library.service.BookService;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

import static com.naskoni.library.constant.BookConstants.*;
import static com.naskoni.library.constant.CommonConstants.*;

@Controller
public final class AddEditBookController {

  @Autowired private BookService bookService;

  @GetMapping(value = URL_ADD_BOOK)
  public String addBook(Model model) {
    UserUtils.addUserToModel(model);
    model.addAttribute(URL_REGISTER, URL_BOOK_REGISTER);
    model.addAttribute(URL_POST, URL_ADD_BOOK_POST);

    return "addEditBook";
  }

  @PostMapping(value = URL_ADD_BOOK_POST)
  public String addBookPost(@ModelAttribute("book") BookDto bookDto) {
    bookService.addBook(bookDto);

    return REDIRECT + BOOK_REGISTER;
  }

  @GetMapping(value = URL_EDIT_BOOK)
  public String editBook(Model model, HttpServletRequest request) {
    UserUtils.addUserToModel(model);
    Long id = Long.parseLong(request.getParameter(ID));
    model.addAttribute("book", bookService.findBook(id));
    model.addAttribute(URL_REGISTER, URL_BOOK_REGISTER);
    model.addAttribute(URL_POST, URL_EDIT_BOOK_POST);
    model.addAttribute(ID, id);

    return "addEditBook";
  }

  @PostMapping(value = URL_EDIT_BOOK_POST)
  public String editBookPost(@ModelAttribute("book") BookDto bookDto) {
    bookService.updateBook(bookDto);

    return REDIRECT + BOOK_REGISTER;
  }
}
