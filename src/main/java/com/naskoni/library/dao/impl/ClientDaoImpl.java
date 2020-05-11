package com.naskoni.library.dao.impl;

import com.naskoni.library.dao.ClientDao;
import com.naskoni.library.entity.Client;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.naskoni.library.constant.CommonConstants.ID;

@Repository
public class ClientDaoImpl implements ClientDao {

  @Autowired private SessionFactory sessionFactory;

  @Override
  public Client findClient(String name) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Client.class);
    criteria.add(Restrictions.like("name", name));
    List<Client> clients = criteria.list();

    return clients.isEmpty() ? null : clients.get(0);
  }

  @Override
  public Client findClient(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Client.class);
    criteria.add(Restrictions.like(ID, id));
    List<Client> clients = criteria.list();

    return clients.isEmpty() ? null : clients.get(0);
  }

  @Override
  public Set<Client> findClients() {
    Session session = sessionFactory.getCurrentSession();
    String sql = "FROM Client";
    Query query = session.createQuery(sql);
    List<Client> clients = query.list();

    return new HashSet<>(clients);
  }

  @Override
  public Set<Client> findClients(String searchedWord, String searchParam) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Client.class);
    criteria.add(Restrictions.like(searchParam, "%" + searchedWord + "%"));
    List<Client> clients = criteria.list();

    return new HashSet<>(clients);
  }

  @Override
  public Set<Client> findClients(Date searchedDate, String searchParam) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Client.class);
    criteria.add(Restrictions.like(searchParam, searchedDate));
    List<Client> clients = criteria.list();

    return new HashSet<>(clients);
  }

  @Override
  public void addClient(Client client) {
    Session session = sessionFactory.getCurrentSession();
    session.save(client);
  }

  @Override
  public void deleteClient(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Client.class);
    criteria.add(Restrictions.eq(ID, id)).uniqueResult();
    List<Client> row = criteria.list();
    session.delete(row.get(0));
  }

  @Override
  public void update(Client client) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(client);
  }
}
