package com.naskoni.library.dao.impl;

import com.naskoni.library.dao.UserDao;
import com.naskoni.library.entity.LibraryUser;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.naskoni.library.constant.CommonConstants.ID;

@Repository
public class UserDaoImpl implements UserDao {

  @Autowired private SessionFactory sessionFactory;

  @Override
  public LibraryUser findUser(String username) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(LibraryUser.class);
    criteria.add(Restrictions.like("username", username));
    List<LibraryUser> users = criteria.list();

    return users.isEmpty() ? null : users.get(0);
  }

  @Override
  public LibraryUser findUser(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(LibraryUser.class);
    criteria.add(Restrictions.like(ID, id));
    List<LibraryUser> users = criteria.list();

    return users.isEmpty() ? null : users.get(0);
  }

  @Override
  public Set<LibraryUser> findUsers() {
    Session session = sessionFactory.getCurrentSession();
    String sql = "FROM LibraryUser";
    Query query = session.createQuery(sql);
    List<LibraryUser> users = query.list();

    return new HashSet<>(users);
  }

  @Override
  public Set<LibraryUser> findUsers(String searchedWord, String searchParam) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(LibraryUser.class);
    criteria.add(Restrictions.like(searchParam, "%" + searchedWord + "%"));
    List<LibraryUser> users = criteria.list();

    return new HashSet<>(users);
  }

  @Override
  public void addUser(LibraryUser user) {
    Session session = sessionFactory.getCurrentSession();
    session.save(user);
  }

  @Override
  public void updateUser(LibraryUser user) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(user);
  }
}
