/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.jpaexample1.impl.dao.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.jpaexample1.model.dao.PersonDAO;
import org.solent.com504.jpaexample1.model.dto.Person;
import org.solent.com504.jpaexample1.model.dto.Role;

/**
 *
 * @author cgallen
 */
public class PersonDAOJpaImpl implements PersonDAO {

    final static Logger LOG = LogManager.getLogger(PersonDAOJpaImpl.class);

    private EntityManager entityManager;

    public PersonDAOJpaImpl(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public Person findById(Long id) {
        Person person = entityManager.find(Person.class, id);
        return person;
    }

    @Override
    public Person save(Person person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person);  // NOTE merge(animal) differnt semantics
        // entityManager.flush() could be used
        entityManager.getTransaction().commit();
        return person;
    }

    @Override
    public List<Person> findAll() {
        TypedQuery<Person> q = entityManager.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> personList = q.getResultList();
        return personList;
    }

    @Override
    public void deleteById(long id) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Person WHERE id = ?1").setParameter(1, id).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public Person delete(Person person) {
        Long tempid = person.getId();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Person WHERE id = ?1").setParameter(1, tempid).executeUpdate();
        entityManager.getTransaction().commit();
        return null;
    }

    @Override
    public void deleteAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Person ").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Person> findByRole(Role role) {
        TypedQuery<Person> q = entityManager.createQuery("SELECT p FROM Person p WHERE p.role = ?1", Person.class);
        q.setParameter(1, role);
        List<Person> personList = q.getResultList();
        return personList;
    }

    @Override
    public List<Person> findByName(String firstName, String secondName) {
        TypedQuery<Person> q = entityManager.createQuery("SELECT p FROM Person p WHERE p.firstName = ?1 AND p.secondName = ?2", Person.class);
        q.setParameter(1, firstName);
        q.setParameter(2, secondName);
        List<Person> personList = q.getResultList();
        return personList;
    }

}
