/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.jpaexample1.impl.dao.jpa.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.solent.com504.jpaexample1.impl.dao.jpa.DAOFactoryJPAImpl;
import org.solent.com504.jpaexample1.model.dao.AppointmentDAO;
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

    private DAOFactoryJPAImpl daoFactory = new DAOFactoryJPAImpl();

    @Before
    public void before() {
        appointmentDao = daoFactory.getAppointmentDAO();
        assertNotNull(appointmentDao);
    }
    
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
    
    person d = new Person();
    p.setaddress();
    
    // delete all in database
    appointmentDao.deleteAll();
    // add 5 appointments
    for (int i = 1; i < 6; i++) {
        Appointment a = new Appointment();
        a.setDescripton("Appointment_" + i);
        a.setPersonA("personA_" + i);
        a.setPersonB("personB_" + i);
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
    
    
    
    
    
    
    
}
