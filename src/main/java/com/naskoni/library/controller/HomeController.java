package com.naskoni.library.controller;

import com.naskoni.library.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Slf4j
@Controller
public final class HomeController {

  @GetMapping(value = {"", "/", "/home"})
  public String home(Locale locale, Model model) {
    log.info("Welcome home! The client locale is {}.", locale);

    UserUtils.addUserToModel(model);

    return "home";
  }
}
