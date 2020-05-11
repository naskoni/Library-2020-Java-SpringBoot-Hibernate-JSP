package com.naskoni.library.service;

import com.naskoni.library.dto.ClientDto;

import java.util.Set;

public interface ClientService {

  ClientDto findClient(String name);

  ClientDto findClient(Long id);

  Set<ClientDto> findClients();

  Set<ClientDto> findClients(String searchedWord, String searchParam);

  void addClient(ClientDto client);

  void deleteClient(Long id);

  void update(ClientDto client);
}
