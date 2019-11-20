/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.jpaexample1.impl.dao.jpa.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.solent.com504.jpaexample1.impl.dao.jpa.DAOFactoryJPAImpl;
import org.solent.com504.jpaexample1.model.dao.PersonDAO;
import org.solent.com504.jpaexample1.model.dto.Person;
import org.solent.com504.jpaexample1.model.dto.Role;

/**
 *
 * @author cgallen
 */
public class PersonDAOTest {

    final static Logger LOG = LogManager.getLogger(PersonDAOTest.class);

    private PersonDAO personDao = null;

    private DAOFactoryJPAImpl daoFactory = new DAOFactoryJPAImpl();

    @Before
    public void before() {
        personDao = daoFactory.getPersonDAO();
        assertNotNull(personDao);
    }

    // initialises database for each test
    private void init() {
        // delete all in database
        personDao.deleteAll();
        // add 5 patients
        for (int i = 1; i < 6; i++) {
            Person p = new Person();
            p.setAddress("address_" + i);
            p.setFirstName("firstName_" + i);
            p.setSecondName("secondName_" + i);
            p.setRole(Role.PATIENT);
            personDao.save(p);
        }
    }
    
    @Test
    public void createPersonDAOTest() {
        LOG.debug("start of createPersonDAOTest");
        // this test simply runs the before method
        LOG.debug("end of createPersonDAOTest");
    }

    @Test
    public void saveTest() {
        LOG.debug("start of saveTest()");
        Person p1 = new Person();
        p1.setFirstName("Matthew");
        p1.setSecondName("Dear");
        p1.setAddress("54 Brounmouth Place");
        p1.setRole(Role.DENTIST);
        personDao.save(p1);
        LOG.debug("end of saveTest()");
    }

    @Test
    public void findByIdTest() {
        LOG.debug("start of findByIdTest()");
        init();
        List<Person> listPerson = personDao.findByName("firstName_3", "secondName_3");
        if (listPerson.isEmpty()) {
            LOG.debug("listPerson is empty findByIdTest()");
        }
        if (listPerson.size() != 1) {
            LOG.debug("listPerson is greater than 1 findByIdTest()");
        }
        LOG.debug(listPerson.size());
        Person p = listPerson.get(0);
        Long tempLong = p.getId();
        assertEquals("firstName_3", personDao.findById(tempLong).getFirstName());
        LOG.debug("first name matches findByIdTest()");
        assertEquals("secondName_3", personDao.findById(tempLong).getSecondName());
        LOG.debug("second name matches findByIdTest()");
        assertEquals("address_3", personDao.findById(tempLong).getAddress());
        LOG.debug("address matches findByIdTest()");
        assertEquals(Role.PATIENT, personDao.findById(tempLong).getRole());
        LOG.debug("role matches findByIdTest()");
        LOG.debug("end of findByIdTest()");
    }

    @Test
    public void findAllTest() {
        LOG.debug("start of findAllTest()");

        init();
        List<Person> personList = personDao.findAll();
        assertNotNull(personList);

        // init should insert 5 people
        assertEquals(5, personList.size());

        // print out result
        String msg = "";
        for (Person person : personList) {
            msg = msg + "\n   " + person.toString();
        }
        LOG.debug("findAllTest() retrieved:" + msg);

        LOG.debug("NOT IMPLEMENTED");
        LOG.debug("end of findAllTest()");
    }

    @Test
    public void deleteByIdTest() {
        LOG.debug("start of deleteByIdTest()");
        init();
        List<Person> listPerson = personDao.findByName("firstName_5", "secondName_5");
        if (listPerson.isEmpty()) {
            LOG.debug("listPerson is empty deleteByIdTest()");
        }
        if (listPerson.size() != 1) {
            LOG.debug("listPerson is greater than 1 deleteByIdTest()");
        }
        Person p = listPerson.get(0);
        p.setFirstName(null);
        p.setSecondName(null);
        p.setAddress(null);
        personDao.deleteById(p.getId());
        List<Person> listPerson2 = personDao.findByName("firstName_5", "secondName_5");
        if (listPerson2.size() > 0) {
            LOG.debug("list still contains person deleteByIdTest");
        }
        assertTrue(listPerson2.isEmpty());
        LOG.debug("end of deleteByIdTest()");
    }

    @Test
    public void findByRoleTest() {
        LOG.debug("start of findByIdTest()");
        init();
        List<Person> tempList = personDao.findByRole(Role.PATIENT);
        for (int i = 1; i < 6; i++){
            Person p = tempList.get(i-1);
            assertEquals("firstName_"+i, p.getFirstName());
            assertEquals("secondName_"+i, p.getSecondName());
            assertEquals("address_"+i, p.getAddress());
        }
        LOG.debug("end of findByIdTest()");
    }

    @Test
    public void findByNameTest() {
        LOG.debug("start of findByNameTest()");
        init();
        List<Person> tempList = personDao.findByName("firstName_4", "secondName_4");
        if (tempList.isEmpty()) {
            LOG.debug("list is empty findByNameTest()");
        }
        if (tempList.size() > 1) {
            LOG.debug("list is to long findByNameTest()");
        }
        Person p = tempList.get(0);
        assertEquals("firstName_4", p.getFirstName());
        assertEquals("secondName_4", p.getSecondName());
        assertEquals("address_4", p.getAddress());
        LOG.debug("end of findByNameTest())");

    }

    @Test
    public void deleteTest() {
        LOG.debug("start of deleteTest()");
        init();
        List<Person> tempList = personDao.findByName("firstName_2", "secondName_2");
         if (tempList.isEmpty()) {
            LOG.debug("list is empty findByNameTest()");
        }
        if (tempList.size() > 1) {
            LOG.debug("list is to long findByNameTest()");
        }
        Person p = tempList.get(0);
        personDao.delete(p);
        List<Person> tempList2 = personDao.findByName("firstName_2", "secondName_2");
        assertTrue(tempList2.isEmpty());
        LOG.debug("end ofdeleteTest()");
    }

    @Test
    public void deleteAllTest() {
        LOG.debug("start of deleteAllTest())");
        init();
        personDao.deleteAll();
        List<Person> tempList = personDao.findByRole(Role.PATIENT);
        assertTrue(tempList.isEmpty());
        LOG.debug("end of deleteAllTest()");
    }
}