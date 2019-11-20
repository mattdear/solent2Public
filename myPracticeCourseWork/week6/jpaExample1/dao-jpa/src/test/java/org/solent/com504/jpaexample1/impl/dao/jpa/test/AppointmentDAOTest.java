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
import org.solent.com504.jpaexample1.model.dao.AppointmentDAO;
import org.solent.com504.jpaexample1.model.dao.PersonDAO;
import org.solent.com504.jpaexample1.model.dto.Appointment;
import org.solent.com504.jpaexample1.model.dto.Person;
import org.solent.com504.jpaexample1.model.dto.Role;


/**
 *
 * @author cgallen
 */
public class AppointmentDAOTest {

    final static Logger LOG = LogManager.getLogger(AppointmentDAOTest.class);

    private AppointmentDAO appointmentDao = null;
    private PersonDAO personDao = null;

    private DAOFactoryJPAImpl daoFactory = new DAOFactoryJPAImpl();

    @Before
    public void before() {
        appointmentDao = daoFactory.getAppointmentDAO();
        assertNotNull(appointmentDao);
        personDao = daoFactory.getPersonDAO();
        assertNotNull(personDao);
    }
    
    private void init() {
        // delete all people in database
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

        // adds 1 dentist
        Person d = new Person();
        d.setAddress("address_6");
        d.setFirstName("firstName_6");
        d.setSecondName("secondName_6");
        d.setRole(Role.DENTIST);
        personDao.save(d);

        //searches for all patients in the database
        List<Person> tempList = personDao.findByRole(Role.PATIENT);

        // serches for all dentists in the database
        List<Person> tempList2 = personDao.findByRole(Role.DENTIST);
        Person b = tempList2.get(0);
        // delete all appointments in database
        appointmentDao.deleteAll();
        // add 5 appointments
        for (int i = 1; i < 6; i++) {
            Appointment a = new Appointment();
            a.setDescripton("appointment_" + i);
            Person c = tempList.get(i - 1);
            a.setPersonA(c);
            a.setPersonB(b);
            a.setHr(i);
            a.setMth(i);
            a.setYr(i);
            a.setDurationMinutes(i);
            appointmentDao.save(a);
        }
    }

    @Test
    public void createAppointmentDAOTest() {
        LOG.debug("start of createAppointmentDAOTest(");
        // this test simply runs the before method
        LOG.debug("end of createAppointmentDAOTest(");
    }

    @Test
    public void findByIdTest() {
        LOG.debug("start of findByIdTest()");
        init();
        List<Person> personAList = personDao.findByName("firstName_1", "secondName_1");
        if (personAList.isEmpty()) {
            LOG.debug("personA list is empty findByIdTest");
        }
        if (personAList.size()>1) {
            LOG.debug("personA list is to big findByIdTest");
        }
        Person personA = personAList.get(0);
        
        List<Person> personBList = personDao.findByName("firstName_1", "secondName_1");
        if (personBList.isEmpty()) {
            LOG.debug("personB list is empty findByIdTest");
        }
        if (personBList.size()>1) {
            LOG.debug("personB list is to big findByIdTest");
        }
        Person personB = personBList.get(0);
        
        List<Appointment> appointmentList = appointmentDao.findByDate(1,1,1,1);
        if (appointmentList.isEmpty()) {
            LOG.debug("appointment list is empty findByIdTest");
        }
        if (appointmentList.size()>1) {
            LOG.debug("appointment list is to big findByIdTest");
        }
        Appointment a = appointmentList.get(0);
        Long tempid = a.getId();
        
        Appointment testAppointment = appointmentDao.findById(tempid);
        
        assertEquals("appointment_1",testAppointment.getDescripton());
        assertEquals(personA,testAppointment.getPersonA());
        assertEquals(personB,testAppointment.getPersonB());
        assertEquals(a.getHr(),testAppointment.getHr());
        assertEquals(a.getMth(),testAppointment.getMth());
        assertEquals(a.getYr(),testAppointment.getYr());
        assertEquals(a.getDurationMinutes(),testAppointment.getDurationMinutes());
        LOG.debug("end of findByIdTest()");
    }

    @Test
    public void SaveTest() {
        LOG.debug("start of SaveTest()");
        
        Person personA = new Person();
        personA.setAddress("address_1");
        personA.setFirstName("firstName_1");
        personA.setSecondName("secondName_1");
        personA.setRole(Role.PATIENT);
        personDao.save(personA);
        
        Person personB = new Person();
        personA.setAddress("address_2");
        personA.setFirstName("firstName_2");
        personA.setSecondName("secondName_2");
        personA.setRole(Role.DENTIST);
        personDao.save(personB);
        
        Appointment a = new Appointment();
        a.setDescripton("appointment_1");
        a.setPersonA(personA);
        a.setPersonB(personB);
        a.setHr(1);
        a.setMth(1);
        a.setYr(1);
        a.setDurationMinutes(1);
        appointmentDao.save(a);
        
        List<Appointment> appointmentList = appointmentDao.findByPersonA(personA);
        if (appointmentList.isEmpty()) {
            LOG.debug("appointment list is empty findByIdTest");
        }
        if (appointmentList.size()>1) {
            LOG.debug("appointment list is to big findByIdTest");
        }
        Appointment testAppointment = appointmentList.get(0);
        
        assertEquals("appointment_1",testAppointment.getDescripton());
        assertEquals(personA,testAppointment.getPersonA());
        assertEquals(personB,testAppointment.getPersonB());
        assertEquals(a.getHr(),testAppointment.getHr());
        assertEquals(a.getMth(),testAppointment.getMth());
        assertEquals(a.getYr(),testAppointment.getYr());
        assertEquals(a.getDurationMinutes(),testAppointment.getDurationMinutes());
        LOG.debug("end of SaveTest()");
    }

    @Test
    public void FindAllTest() {
        LOG.debug("start of FindAllTest()");
        init();
        List<Appointment> appointmentList = appointmentDao.findAll();
        assertNotNull(appointmentList);

        // init should insert 5 appointments
        assertEquals(5, appointmentList.size());

        // print out result
        String msg = "";
        for (Appointment appointment : appointmentList) {
            msg = msg + "\n   " + appointment.toString();
        }
        LOG.debug("findAllTest() retrieved:" + msg);
        LOG.debug("end of FindAllTest()");
    }

    @Test
    public void DeleteTest() {
        LOG.debug("start of DeleteTest()");
        init();
        List<Appointment> tempList = appointmentDao.findByDate(1,1,1,1);
        if (tempList.isEmpty()) {
            LOG.debug("list is empty findByNameTest()");
        }
        if (tempList.size() > 1) {
            LOG.debug("list is to long findByNameTest()");
        }
        Appointment a = tempList.get(0);
        appointmentDao.delete(a);
        List<Appointment> tempList2 = appointmentDao.findByDate(1,1,1,1);
        assertTrue(tempList2.isEmpty());
        LOG.debug("end of DeleteTest()");
    }

    @Test
    public void deleteByIdTest() {
        LOG.debug("start of deleteByIdTest()");
        init();
        List<Appointment> listAppointment = appointmentDao.findByDate(1,1,1,1);
        if (listAppointment.isEmpty()) {
            LOG.debug("listAppointment is empty deleteByIdTest()");
        }
        if (listAppointment.size() != 1) {
            LOG.debug("listAppointment is greater than 1 deleteByIdTest()");
        }
        Appointment a = listAppointment.get(0);
        appointmentDao.deleteById(a.getId());
        List<Appointment> listAppointment2 = appointmentDao.findByDate(1,1,1,1);
        if (listAppointment2.size() > 0) {
            LOG.debug("listAppointment still contains appointment deleteByIdTest");
        }
        assertTrue(listAppointment2.isEmpty());
        LOG.debug("end of deleteByIdTest()");
    }

    @Test
    public void DeleteAllTest() {
        LOG.debug("start of DeleteAllTest()");
        init();
        LOG.debug("end of DeleteAllTest()");
    }

    @Test
    public void findByPersonATest() {
        LOG.debug("start of findByPersonATest()");
        init();
        LOG.debug("end of findByPersonATest()");
    }

    @Test
    public void findByPersonBTest() {
        LOG.debug("start of findByPersonBTest()");
        init();
        LOG.debug("end of findByPersonBTest()");
    }

    @Test
    public void findByDateTest() {
        LOG.debug("start of findByDateTest()");
        init();
        LOG.debug("end of findByDateTest()");
    }

}
