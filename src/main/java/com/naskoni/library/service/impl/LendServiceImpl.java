package com.naskoni.library.service.impl;

import com.naskoni.library.dao.BookDao;
import com.naskoni.library.dao.ClientDao;
import com.naskoni.library.dao.LendDao;
import com.naskoni.library.dto.BookDto;
import com.naskoni.library.dto.ClientDto;
import com.naskoni.library.dto.LendDto;
import com.naskoni.library.entity.Book;
import com.naskoni.library.entity.Client;
import com.naskoni.library.entity.Lend;
import com.naskoni.library.service.LendService;
import com.naskoni.library.util.ParseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.naskoni.library.constant.CommonConstants.*;

@Service
public class LendServiceImpl implements LendService {

  @Autowired private LendDao lendDao;

  @Autowired private BookDao bookDao;

  @Autowired private ClientDao clientDao;

  @Transactional
  @Override
  public void addLend(LendDto lendDto) {
    Lend lend = mapLendEntity(lendDto);

    lendDao.addLend(lend);
  }

  @Transactional
  @Override
  public void updateLend(LendDto lendDto) {
    Lend lend = mapLendEntity(lendDto);

    lendDao.update(lend);
  }

  @Transactional
  @Override
  public LendDto findLend(Long id) {
    Lend lend = lendDao.findLend(id);

    return mapLendDto(lend);
  }

  @Transactional
  @Override
  public Set<LendDto> findLends() {
    Set<Lend> lends = lendDao.findLends();

    return mapLendDtos(lends);
  }

  @Transactional
  @Override
  public Set<LendDto> findLends(String searchedWord, String searchParam) {
    Set<Lend> lends;
    if (searchedWord.isEmpty() || ASTERISK.equals(searchedWord)) {
      lends = lendDao.findLends();
    } else if ((LENDING_DATE.equals(searchParam) || RETURN_DATE.equals(searchParam))
        && ParseUtils.tryParseDate(searchedWord)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      try {
        java.util.Date parsed = sdf.parse(searchedWord);
        java.sql.Date searchedDate = new java.sql.Date(parsed.getTime());
        lends = lendDao.findLends(searchedDate, searchParam);
      } catch (ParseException e) {
        // already caught in ParseUtils
        lends = Collections.emptySet();
      }
    } else {
      lends = lendDao.findLends(searchedWord, searchParam);
    }

    return mapLendDtos(lends);
  }

  private Set<LendDto> mapLendDtos(Set<Lend> lends) {
    var result = new HashSet<LendDto>();
    for (var lend : lends) {
      LendDto lendDto = mapLendDto(lend);
      result.add(lendDto);
    }

    return result;
  }

  private LendDto mapLendDto(Lend lend) {
    var lendDto = new LendDto();
    BeanUtils.copyProperties(lend, lendDto);

    var clientDto = new ClientDto();
    BeanUtils.copyProperties(lend.getClient(), clientDto);
    lendDto.setClient(clientDto);

    var bookDto = new BookDto();
    BeanUtils.copyProperties(lend.getBook(), bookDto);
    lendDto.setBook(bookDto);
    return lendDto;
  }

  private Lend mapLendEntity(LendDto lendDto) {
    Lend lend = new Lend();
    BeanUtils.copyProperties(lendDto, lend);

    Book book = bookDao.findBook(lendDto.getBook().getId());
    lend.setBook(book);

    Client client = clientDao.findClient(lendDto.getClient().getId());
    lend.setClient(client);
    return lend;
  }
}
