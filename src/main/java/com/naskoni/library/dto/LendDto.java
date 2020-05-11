package com.naskoni.library.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class LendDto {

  private Long id;

  private BookDto book;

  private ClientDto client;

  private Date lendingDate;

  private Date returnDate;
}
