package com.naskoni.library.dto;

import lombok.Data;

@Data
public class LibraryUserDto {

  private Long id;

  private String name;

  private String username;

  private String password;

  private String status;

  private Long createdBy;

  private String role;
}
