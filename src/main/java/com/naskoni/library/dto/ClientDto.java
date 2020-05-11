package com.naskoni.library.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ClientDto {

  private Long id;

  private String name;

  private String pid;

  private Date birthdate;

  private LibraryUserDto createdBy;
}
