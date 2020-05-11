package com.naskoni.library.controller;

import com.naskoni.library.dto.LibraryUserDto;
import com.naskoni.library.service.UserService;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.naskoni.library.constant.CommonConstants.*;
import static com.naskoni.library.constant.UserConstants.*;

@Controller
public class AddEditUserController {

  @Autowired private UserService userService;

  @Secured("ROLE_ADMIN")
  @GetMapping(value = URL_ADD_USER)
  public String addUser(Model model) {
    UserUtils.addUserToModel(model);
    model.addAttribute(URL_REGISTER, URL_USER_REGISTER);
    model.addAttribute(URL_POST, URL_ADD_USER_POST);

    return "addUser";
  }

  @Secured("ROLE_ADMIN")
  @PostMapping(value = URL_ADD_USER_POST)
  public String addUserPost(@ModelAttribute("user") LibraryUserDto user) {
    userService.addUser(user);

    return REDIRECT + USER_REGISTER;
  }

  @GetMapping(value = URL_EDIT_USER)
  public String editUser(Model model) {
    UserDetails loggedUser = UserUtils.getUser();
    LibraryUserDto user = userService.findUser(loggedUser.getUsername());
    model.addAttribute(USER, user);
    model.addAttribute(URL_POST, URL_EDIT_USER_POST);

    return "editUser";
  }

  @PostMapping(value = URL_EDIT_USER_POST)
  public String editUserPost(@ModelAttribute("user") LibraryUserDto user) {
    userService.updateUser(user);

    return "home";
  }
}
