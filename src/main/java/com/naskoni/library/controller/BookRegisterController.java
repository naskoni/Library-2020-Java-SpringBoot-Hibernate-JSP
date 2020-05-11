package com.naskoni.library.controller;

import com.naskoni.library.service.BookService;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.naskoni.library.constant.BookConstants.*;
import static com.naskoni.library.constant.CommonConstants.*;

@Controller
public final class BookRegisterController {

  @Autowired private BookService bookService;

  @GetMapping(value = URL_BOOK_REGISTER)
  public String loadBookRegister(Model model) {
    UserUtils.addUserToModel(model);
    model.addAttribute(BOOKS, bookService.findBooks());
    model.addAttribute(URL_EDIT, URL_EDIT_BOOK);
    model.addAttribute(URL_ADD, URL_ADD_BOOK);
    model.addAttribute(URL_DELETE, URL_DELETE_BOOK);
    model.addAttribute(URL_SEARCH, URL_SEARCH_BOOK);

    return BOOK_REGISTER;
  }

  @GetMapping(value = URL_DELETE_BOOK)
  public String deleteBook(HttpServletRequest request) {
    Long id = Long.parseLong(request.getParameter(ID));
    bookService.deleteBook(id);

    return REDIRECT + BOOK_REGISTER;
  }

  @PostMapping(value = URL_SEARCH_BOOK)
  public String searchBook(HttpServletRequest request, RedirectAttributes redir) {
    String searchParam = request.getParameter("searchParam");
    String searchedWord = request.getParameter("searchedWord");
    redir.addFlashAttribute("searchedBooks", bookService.findBooks(searchedWord, searchParam));

    return REDIRECT + BOOK_REGISTER;
  }
}
