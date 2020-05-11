package com.naskoni.library.controller;

import com.naskoni.library.service.LendService;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.naskoni.library.constant.CommonConstants.*;
import static com.naskoni.library.constant.LendConstants.*;

@Controller
public final class LendRegisterController {

  @Autowired private LendService lendService;

  @GetMapping(value = URL_LEND_REGISTER)
  public String loadLendRegister(Model model) {
    UserUtils.addUserToModel(model);
    model.addAttribute("lends", lendService.findLends());
    model.addAttribute(URL_EDIT, URL_EDIT_LEND);
    model.addAttribute(URL_ADD, URL_ADD_LEND);
    model.addAttribute(URL_SEARCH, URL_SEARCH_LEND);

    return LEND_REGISTER;
  }

  @PostMapping(value = URL_SEARCH_LEND)
  public String searchLend(HttpServletRequest request, RedirectAttributes redir) {
    String searchParam = request.getParameter("searchParam");
    String searchedWord = request.getParameter("searchedWord");
    redir.addFlashAttribute("searchedLends", lendService.findLends(searchedWord, searchParam));

    return REDIRECT + LEND_REGISTER;
  }
}
