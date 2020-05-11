package com.naskoni.library.dao;

import com.naskoni.library.entity.Lend;

import java.sql.Date;
import java.util.Set;

public interface LendDao {

  Lend findLend(Long id);

  Set<Lend> findLends();

  Set<Lend> findLends(String searchedWord, String searchParam);

  Set<Lend> findLends(Date searchedWord, String searchParam);

  void addLend(Lend lend);

  void update(Lend lend);
}
