package com.naskoni.library.service;

import com.naskoni.library.dto.LendDto;

import java.util.Set;

public interface LendService {

  LendDto findLend(Long id);

  Set<LendDto> findLends();

  void addLend(LendDto lend);

  void updateLend(LendDto lend);

  Set<LendDto> findLends(String searchedWord, String searchParam);
}
