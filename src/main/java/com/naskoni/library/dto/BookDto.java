package com.naskoni.library.dto;

import lombok.Data;

@Data
public class BookDto {

  private Long id;

  private String name;

  private String author;

  private int year;

  private String isbn;
}
