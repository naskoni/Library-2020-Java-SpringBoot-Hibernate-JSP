package com.naskoni.library.dao.impl;

import com.naskoni.library.dao.LendDao;
import com.naskoni.library.entity.Lend;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.naskoni.library.constant.CommonConstants.ID;

@Repository
public class LendDaoImpl implements LendDao {

  @Autowired private SessionFactory sessionFactory;

  @Transactional
  @Override
  public Lend findLend(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Lend.class);
    criteria.add(Restrictions.like(ID, id));
    List<Lend> lends = criteria.list();

    return lends.isEmpty() ? null : lends.get(0);
  }

  @Transactional
  @Override
  public Set<Lend> findLends() {
    Session session = sessionFactory.getCurrentSession();
    String sql = "FROM Lend";
    Query query = session.createQuery(sql);
    List<Lend> lends = query.list();

    return new HashSet<>(lends);
  }

  @Transactional
  @Override
  public Set<Lend> findLends(String searchedWord, String searchParam) {
    Session session = sessionFactory.getCurrentSession();
    String sql = String.format("SELECT l from Lend l WHERE l.%s.name=:name", searchParam);
    Query query = session.createQuery(sql);
    query.setParameter("name", searchedWord);
    List<Lend> lends = query.list();

    return new HashSet<>(lends);
  }

  @Transactional
  @Override
  public Set<Lend> findLends(Date searchedDate, String searchParam) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Lend.class);
    criteria.add(Restrictions.like(searchParam, searchedDate));
    List<Lend> lends = criteria.list();

    return new HashSet<>(lends);
  }

  @Transactional
  @Override
  public void addLend(Lend lend) {
    Session session = sessionFactory.getCurrentSession();
    session.save(lend);
  }

  @Transactional
  @Override
  public void update(Lend lend) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(lend);
  }
}
