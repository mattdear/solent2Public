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
import org.solent.com504.jpaexample1.model.dao.AppointmentDAO;
import org.solent.com504.jpaexample1.model.dto.Appointment;
import org.solent.com504.jpaexample1.model.dto.Person;

/**
 *
 * @author cgallen
 */
public class AppointmentDAOJpaImpl implements AppointmentDAO {

    final static Logger LOG = LogManager.getLogger(PersonDAOJpaImpl.class);

    private EntityManager entityManager;

    public AppointmentDAOJpaImpl(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public Appointment findById(Long id) {
        Appointment appointment = entityManager.find(Appointment.class, id);
        return appointment;
    }

    @Override
    public Appointment save(Appointment appointment) {
        entityManager.getTransaction().begin();
        entityManager.persist(appointment);
        entityManager.getTransaction().commit();
        return appointment;
    }

    @Override
    public List<Appointment> findAll() {
        TypedQuery<Appointment> q = entityManager.createQuery("SELECT a FROM Person a", Appointment.class);
        List<Appointment> appointmentList = q.getResultList();
        return appointmentList;
    }

    @Override
    public Appointment delete(Appointment appointment) {
        Long tempid = appointment.getId();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Appointment WHERE id = ?1").setParameter(1, tempid).executeUpdate();
        entityManager.getTransaction().commit();
        return null;
    }

    @Override
    public void deleteById(Long id) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Appointment WHERE id = ?1").setParameter(1, id).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteAll() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Appointment").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Appointment> findByPersonA(Person personA) {
        TypedQuery<Appointment> q = entityManager.createQuery("SELECT a FROM Appointment a WHERE a.personA = ?1", Appointment.class);
        q.setParameter(1, personA);
        List<Appointment> appointmentList = q.getResultList();
        return appointmentList;
    }

    @Override
    public List<Appointment> findByPersonB(Person personB) {
        TypedQuery<Appointment> q = entityManager.createQuery("SELECT a FROM Appointment a WHERE a.personB = ?1", Appointment.class);
        q.setParameter(1, personB);
        List<Appointment> appointmentList = q.getResultList();
        return appointmentList;
    }

    @Override
    public List<Appointment> findByDate(Integer year, Integer month, Integer hour, Integer minutes) {
        String tempQuery = "SELECT a FROM Appointment a WHERE TRUE=TRUE";
        if (year != null){
            tempQuery = tempQuery + "AND a.yr = " + year;
        }
        if (month != null){
            tempQuery = tempQuery + "AND a.mth = " + month;
        }
        if (hour != null){
            tempQuery = tempQuery + "AND a.hr = " + hour;
        }
        if (minutes != null){
            tempQuery = tempQuery + "AND a.durationMinutes = " + minutes;
        }
        TypedQuery<Appointment> q = entityManager.createQuery(tempQuery, Appointment.class);
        List<Appointment> appointmentList = q.getResultList();
        return appointmentList;
    }

}
