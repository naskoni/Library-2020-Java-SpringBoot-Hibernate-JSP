package com.naskoni.library.service;

import com.naskoni.library.dto.LibraryUserDto;

import java.util.Set;

public interface UserService {

  Set<LibraryUserDto> findUsers();

  Set<LibraryUserDto> findUsers(String searchedWord, String searchParam);

  LibraryUserDto findUser(String username);

  void addUser(LibraryUserDto user);

  void updateUser(LibraryUserDto user);

  void deactivateUser(Long id);
}
