package com.naskoni.library.controller;

import com.naskoni.library.dto.ClientDto;
import com.naskoni.library.service.ClientService;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static com.naskoni.library.constant.ClientConstants.*;
import static com.naskoni.library.constant.CommonConstants.*;

@Controller
public final class ClientRegisterController {

  @Autowired private ClientService clientService;

  @GetMapping(value = URL_CLIENT_REGISTER)
  public String loadClientRegister(Model model) {
    UserUtils.addUserToModel(model);
    model.addAttribute("clients", clientService.findClients());
    model.addAttribute(URL_EDIT, URL_EDIT_CLIENT);
    model.addAttribute(URL_ADD, URL_ADD_CLIENT);
    model.addAttribute(URL_DELETE, URL_DELETE_CLIENT);
    model.addAttribute(URL_SEARCH, URL_SEARCH_CLIENT);

    return CLIENT_REGISTER;
  }

  @GetMapping(value = URL_DELETE_CLIENT)
  public String deleteClient(HttpServletRequest request) {
    Long id = Long.parseLong(request.getParameter(ID));
    clientService.deleteClient(id);

    return REDIRECT + CLIENT_REGISTER;
  }

  @PostMapping(value = URL_SEARCH_CLIENT)
  public String searchClient(HttpServletRequest request, RedirectAttributes redir) {
    String searchParam = request.getParameter("searchParam");
    String searchedWord = request.getParameter("searchedWord");
    Set<ClientDto> clients = clientService.findClients(searchedWord, searchParam);
    redir.addFlashAttribute("searchedClients", clients);

    return REDIRECT + CLIENT_REGISTER;
  }
}
