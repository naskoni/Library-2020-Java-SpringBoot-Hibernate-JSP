package com.naskoni.library.service.impl;

import com.naskoni.library.dao.ClientDao;
import com.naskoni.library.dto.ClientDto;
import com.naskoni.library.entity.Client;
import com.naskoni.library.entity.LibraryUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

  @Mock private ClientDao clientDao;

  @InjectMocks private ClientServiceImpl clientService;

  @Test
  void testFindClients() {
    Set<ClientDto> clientDtos = clientService.findClients();
    Assertions.assertTrue(clientDtos.isEmpty());

    Set<Client> clients = new HashSet<>();
    var client = new Client();
    client.setId(1L);
    client.setName("name");
    client.setBirthdate(new Date(System.currentTimeMillis()));
    client.setPid("164571274");
    client.setCreatedBy(new LibraryUser());
    clients.add(client);

    Mockito.when(clientDao.findClients()).thenReturn(clients);
    clientDtos = clientService.findClients();
    Assertions.assertEquals(1, clientDtos.size());

    ClientDto clientDto = clientDtos.iterator().next();
    Assertions.assertEquals(client.getId(), clientDto.getId());
    Assertions.assertEquals(client.getName(), clientDto.getName());
    Assertions.assertEquals(client.getBirthdate(), clientDto.getBirthdate());
    Assertions.assertEquals(client.getPid(), clientDto.getPid());
  }
}
