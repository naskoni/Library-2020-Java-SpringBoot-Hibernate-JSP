package com.naskoni.library.service.impl;

import com.naskoni.library.dao.ClientDao;
import com.naskoni.library.dao.UserDao;
import com.naskoni.library.dto.ClientDto;
import com.naskoni.library.dto.LibraryUserDto;
import com.naskoni.library.entity.Client;
import com.naskoni.library.entity.LibraryUser;
import com.naskoni.library.service.ClientService;
import com.naskoni.library.util.ParseUtils;
import com.naskoni.library.util.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.naskoni.library.constant.CommonConstants.ASTERISK;
import static com.naskoni.library.constant.CommonConstants.BIRTHDATE;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired private ClientDao clientDao;

  @Autowired private UserDao userdao;

  @Transactional
  @Override
  public ClientDto findClient(String name) {
    Client client = clientDao.findClient(name);
    ClientDto clientDto = new ClientDto();
    BeanUtils.copyProperties(client, clientDto);
    return clientDto;
  }

  @Transactional
  @Override
  public ClientDto findClient(Long id) {
    Client client = clientDao.findClient(id);
    ClientDto clientDto = new ClientDto();
    BeanUtils.copyProperties(client, clientDto);
    return clientDto;
  }

  @Transactional
  @Override
  public Set<ClientDto> findClients() {
    var result = new HashSet<ClientDto>();
    Set<Client> clients = clientDao.findClients();
    for (var client : clients) {
      var clientDto = new ClientDto();
      BeanUtils.copyProperties(client, clientDto);

      var libraryUserDto = new LibraryUserDto();
      BeanUtils.copyProperties(client.getCreatedBy(), libraryUserDto);
      clientDto.setCreatedBy(libraryUserDto);

      result.add(clientDto);
    }

    return result;
  }

  @Transactional
  @Override
  public Set<ClientDto> findClients(String searchedWord, String searchParam) {
    var result = new HashSet<ClientDto>();
    Set<Client> clients;

    if (searchedWord.isEmpty() || ASTERISK.equals(searchedWord)) {
      clients = clientDao.findClients();
    } else if (BIRTHDATE.equals(searchParam) && ParseUtils.tryParseDate(searchedWord)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      try {
        java.util.Date parsed = sdf.parse(searchedWord);
        java.sql.Date searchedDate = new java.sql.Date(parsed.getTime());
        clients = clientDao.findClients(searchedDate, searchParam);
      } catch (ParseException e) {
        // already caught in ParseUtils
        clients = Collections.emptySet();
      }
    } else {
      clients = clientDao.findClients(searchedWord, searchParam);
    }

    for (var client : clients) {
      ClientDto clientDto = new ClientDto();
      BeanUtils.copyProperties(client, clientDto);
      result.add(clientDto);
    }

    return result;
  }

  @Transactional
  @Override
  public void addClient(ClientDto clientDto) {
    Client client = new Client();
    BeanUtils.copyProperties(clientDto, client);

    LibraryUser user = userdao.findUser(UserUtils.getUser().getUsername());
    client.setCreatedBy(user);

    clientDao.addClient(client);
  }

  @Transactional
  @Override
  public void update(ClientDto clientDto) {
    Client client = new Client();
    BeanUtils.copyProperties(clientDto, client);

    LibraryUser user = userdao.findUser(UserUtils.getUser().getUsername());
    client.setCreatedBy(user);

    clientDao.update(client);
  }

  @Transactional
  @Override
  public void deleteClient(Long id) {
    clientDao.deleteClient(id);
  }
}
