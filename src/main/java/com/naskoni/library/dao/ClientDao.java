package com.naskoni.library.dao;

import com.naskoni.library.entity.Client;

import java.sql.Date;
import java.util.Set;

public interface ClientDao {

  Client findClient(String name);

  Client findClient(Long id);

  Set<Client> findClients();

  Set<Client> findClients(String searchedWord, String searchParam);

  Set<Client> findClients(Date searchedWord, String searchParam);

  void addClient(Client client);

  void deleteClient(Long id);

  void update(Client client);
}
