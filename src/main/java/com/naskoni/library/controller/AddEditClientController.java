package com.naskoni.library.controller;

import com.naskoni.library.dto.ClientDto;
import com.naskoni.library.service.ClientService;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

import static com.naskoni.library.constant.ClientConstants.*;
import static com.naskoni.library.constant.CommonConstants.*;

@Controller
public final class AddEditClientController {

  @Autowired private ClientService clientService;

  @GetMapping(value = URL_ADD_CLIENT)
  public String addClient(Model model) {
    UserUtils.addUserToModel(model);
    model.addAttribute(URL_REGISTER, URL_CLIENT_REGISTER);
    model.addAttribute(URL_POST, URL_ADD_CLIENT_POST);

    return "addEditClient";
  }

  @PostMapping(value = URL_ADD_CLIENT_POST)
  public String addClientPost(@ModelAttribute("client") ClientDto clientDto) {
    clientService.addClient(clientDto);

    return REDIRECT + CLIENT_REGISTER;
  }

  @GetMapping(value = URL_EDIT_CLIENT)
  public String editClient(Model model, HttpServletRequest request) {
    UserUtils.addUserToModel(model);
    Long id = Long.parseLong(request.getParameter(ID));
    model.addAttribute("client", clientService.findClient(id));
    model.addAttribute(URL_REGISTER, URL_CLIENT_REGISTER);
    model.addAttribute(URL_POST, URL_EDIT_CLIENT_POST);
    model.addAttribute(ID, id);

    return "addEditClient";
  }

  @PostMapping(value = URL_EDIT_CLIENT_POST)
  public String editClientPost(@ModelAttribute("client") ClientDto clientDto) {
    clientService.update(clientDto);

    return REDIRECT + CLIENT_REGISTER;
  }
}
