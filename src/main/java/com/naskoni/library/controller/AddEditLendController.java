package com.naskoni.library.controller;

import com.naskoni.library.dto.BookDto;
import com.naskoni.library.dto.ClientDto;
import com.naskoni.library.dto.LendDto;
import com.naskoni.library.service.BookService;
import com.naskoni.library.service.ClientService;
import com.naskoni.library.service.LendService;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

import static com.naskoni.library.constant.BookConstants.BOOKS;
import static com.naskoni.library.constant.BookConstants.BOOK_ID;
import static com.naskoni.library.constant.ClientConstants.CLIENT_ID;
import static com.naskoni.library.constant.CommonConstants.*;
import static com.naskoni.library.constant.LendConstants.*;

@Controller
public class AddEditLendController {

  @Autowired private LendService lendService;

  @Autowired private BookService bookService;

  @Autowired private ClientService clientService;

  @GetMapping(value = URL_ADD_LEND)
  public String addLend(Model model) {
    UserUtils.addUserToModel(model);
    model.addAttribute(URL_REGISTER, URL_LEND_REGISTER);
    model.addAttribute(URL_POST, URL_ADD_LEND_POST);
    model.addAttribute(BOOKS, bookService.findBooks());
    model.addAttribute("clients", clientService.findClients());

    return "addEditLend";
  }

  @PostMapping(value = URL_ADD_LEND_POST)
  public String addLendPost(@ModelAttribute("lend") LendDto lendDto, HttpServletRequest request) {
    Long bookId = Long.parseLong(request.getParameter(BOOK_ID));
    BookDto bookDto = bookService.findBook(bookId);
    Long clientId = Long.parseLong(request.getParameter(CLIENT_ID));
    ClientDto clientDto = clientService.findClient(clientId);
    lendDto.setBook(bookDto);
    lendDto.setClient(clientDto);
    lendService.addLend(lendDto);

    return REDIRECT + LEND_REGISTER;
  }

  @Secured("ROLE_ADMIN")
  @GetMapping(value = URL_EDIT_LEND)
  public String editLend(Model model, HttpServletRequest request) {
    UserUtils.addUserToModel(model);
    Long id = Long.parseLong(request.getParameter(ID));
    model.addAttribute("lend", lendService.findLend(id));
    model.addAttribute(BOOKS, bookService.findBooks());
    model.addAttribute("clients", clientService.findClients());
    model.addAttribute(URL_REGISTER, URL_LEND_REGISTER);
    model.addAttribute(URL_POST, URL_EDIT_LEND_POST);
    model.addAttribute(ID, id);

    return "addEditLend";
  }

  @Secured("ROLE_ADMIN")
  @PostMapping(value = URL_EDIT_LEND_POST)
  public String editLendPost(@ModelAttribute("lend") LendDto lendDto, HttpServletRequest request) {
    Long bookId = Long.parseLong(request.getParameter(BOOK_ID));
    BookDto bookDto = bookService.findBook(bookId);
    Long clientId = Long.parseLong(request.getParameter(CLIENT_ID));
    ClientDto clientDto = clientService.findClient(clientId);
    lendDto.setBook(bookDto);
    lendDto.setClient(clientDto);

    lendService.updateLend(lendDto);

    return REDIRECT + LEND_REGISTER;
  }
}
