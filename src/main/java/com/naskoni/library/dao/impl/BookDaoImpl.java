package com.naskoni.library.dao.impl;

import com.naskoni.library.dao.BookDao;
import com.naskoni.library.entity.Book;
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
public class BookDaoImpl implements BookDao {

  @Autowired private SessionFactory sessionFactory;

  @Override
  public Book findBook(String name) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Book.class);
    criteria.add(Restrictions.like("name", name));
    List<Book> books = criteria.list();

    return books.isEmpty() ? null : books.get(0);
  }

  @Override
  public Book findBook(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Book.class);
    criteria.add(Restrictions.like(ID, id));
    List<Book> books = criteria.list();

    return books.isEmpty() ? null : books.get(0);
  }

  @Override
  public Set<Book> findBooks() {
    Session session = sessionFactory.getCurrentSession();
    String sql = "FROM Book";
    Query query = session.createQuery(sql);
    List<Book> books = query.list();

    return new HashSet<>(books);
  }

  @Override
  public Set<Book> findBooks(String searchedWord, String searchParam) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Book.class);
    criteria.add(Restrictions.like(searchParam, "%" + searchedWord + "%"));
    List<Book> books = criteria.list();

    return new HashSet<>(books);
  }

  @Override
  public Set<Book> findBooks(int searchedNumber, String searchParam) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Book.class);
    criteria.add(Restrictions.like(searchParam, searchedNumber));
    List<Book> books = criteria.list();

    return new HashSet<>(books);
  }

  @Override
  public void addBook(Book book) {
    Session session = sessionFactory.getCurrentSession();
    session.save(book);
  }

  @Override
  public void deleteBook(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Book.class);
    criteria.add(Restrictions.eq(ID, id)).uniqueResult();
    List<Book> row = criteria.list();
    session.delete(row.get(0));
  }

  @Override
  public void update(Book book) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(book);
  }
}
