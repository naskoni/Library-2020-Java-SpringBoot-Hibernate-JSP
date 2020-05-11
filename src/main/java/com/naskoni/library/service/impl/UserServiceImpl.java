package com.naskoni.library.service.impl;

import com.naskoni.library.dao.UserDao;
import com.naskoni.library.dto.LibraryUserDto;
import com.naskoni.library.entity.LibraryUser;
import com.naskoni.library.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashSet;
import java.util.Set;

import static com.naskoni.library.constant.CommonConstants.ASTERISK;
import static com.naskoni.library.constant.UserConstants.ROLE;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserDao userDao;

  @Transactional
  @Override
  public Set<LibraryUserDto> findUsers() {
    var result = new HashSet<LibraryUserDto>();
    Set<LibraryUser> libraryUsers = userDao.findUsers();
    for (var libraryUser : libraryUsers) {
      var libraryUserDto = new LibraryUserDto();
      BeanUtils.copyProperties(libraryUser, libraryUserDto);
      result.add(libraryUserDto);
    }

    return result;
  }

  @Transactional
  @Override
  public LibraryUserDto findUser(String username) {
    LibraryUser libraryUser = userDao.findUser(username);
    var libraryUserDto = new LibraryUserDto();
    BeanUtils.copyProperties(libraryUser, libraryUserDto);

    return libraryUserDto;
  }

  @Transactional
  @Override
  public void addUser(LibraryUserDto libraryUserDto) {
    String enteredPassword = libraryUserDto.getPassword();
    String encryptedPassword = DigestUtils.md5DigestAsHex(enteredPassword.getBytes());
    libraryUserDto.setPassword(encryptedPassword);
    libraryUserDto.setStatus("active");

    var libraryUser = new LibraryUser();
    BeanUtils.copyProperties(libraryUserDto, libraryUser);

    userDao.addUser(libraryUser);
  }

  @Transactional
  @Override
  public void updateUser(LibraryUserDto libraryUserDto) {
    String enteredPassword = libraryUserDto.getPassword();
    String encryptedPassword = DigestUtils.md5DigestAsHex(enteredPassword.getBytes());
    libraryUserDto.setPassword(encryptedPassword);
    libraryUserDto.setStatus("active");

    var libraryUser = new LibraryUser();
    BeanUtils.copyProperties(libraryUserDto, libraryUser);

    userDao.updateUser(libraryUser);
  }

  @Transactional
  @Override
  public void deactivateUser(Long id) {
    LibraryUser user = userDao.findUser(id);
    user.setStatus("deactivated");

    userDao.updateUser(user);
  }

  @Transactional
  @Override
  public Set<LibraryUserDto> findUsers(String searchedWord, String searchParam) {
    var result = new HashSet<LibraryUserDto>();
    Set<LibraryUser> libraryUsers;

    if (searchedWord.isEmpty() || ASTERISK.equals(searchedWord)) {
      libraryUsers = userDao.findUsers();
    } else if (ROLE.equals(searchParam)) {
      libraryUsers = userDao.findUsers(searchedWord.toUpperCase(), searchParam);
    } else {
      libraryUsers = userDao.findUsers(searchedWord, searchParam);
    }

    for (var libraryUser : libraryUsers) {
      var libraryUserDto = new LibraryUserDto();
      BeanUtils.copyProperties(libraryUser, libraryUserDto);
      result.add(libraryUserDto);
    }

    return result;
  }
}
