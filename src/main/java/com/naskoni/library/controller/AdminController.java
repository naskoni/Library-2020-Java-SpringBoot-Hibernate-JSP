package com.naskoni.library.controller;

import com.naskoni.library.service.UserService;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.naskoni.library.constant.CommonConstants.*;
import static com.naskoni.library.constant.UserConstants.*;

@Controller
public class AdminController {

  @Autowired private UserService userService;

  @Secured("ROLE_ADMIN")
  @GetMapping(value = USER_REGISTER)
  public String loadAdminPage(Model model) {
    UserUtils.addUserToModel(model);
    model.addAttribute("users", userService.findUsers());
    model.addAttribute(URL_ADD, URL_ADD_USER);
    model.addAttribute("url_deactivate", URL_DEACTIVATE_USER);
    model.addAttribute("url_search", URL_SEARCH_USER);

    return "admin";
  }

  @Secured("ROLE_ADMIN")
  @GetMapping(value = URL_DEACTIVATE_USER)
  public String deleteBook(HttpServletRequest request) {
    Long id = Long.parseLong(request.getParameter(ID));
    userService.deactivateUser(id);

    return REDIRECT + USER_REGISTER;
  }

  @PostMapping(value = URL_SEARCH_USER)
  public String searchBook(HttpServletRequest request, RedirectAttributes redir) {
    String searchParam = request.getParameter("searchParam");
    String searchedWord = request.getParameter("searchedWord");
    redir.addFlashAttribute("searchedUsers", userService.findUsers(searchedWord, searchParam));

    return REDIRECT + USER_REGISTER;
  }
}
