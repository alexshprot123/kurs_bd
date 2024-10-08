package org.example;

import jakarta.validation.Valid;
import org.example.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.*;

public class BD {
    static SessionFactory sessionFactoryObj;
    public static SessionFactory buildSessionFactory() {
        Configuration configObj = new Configuration();
        configObj.addAnnotatedClass(Appointed.class);
        configObj.addAnnotatedClass(Disease.class);
        configObj.addAnnotatedClass(Doctor.class);
        configObj.addAnnotatedClass(Monitor.class);
        configObj.addAnnotatedClass(Patient.class);
        configObj.addAnnotatedClass(Profession.class);
        configObj.addAnnotatedClass(MedUser.class);
        configObj.addAnnotatedClass(TestEntity.class);
        configObj.configure();

        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }

    public static Session openSession(){
        Session session = sessionFactoryObj.openSession();
        session.beginTransaction();
        return session;
    }

    public static void closeSession(Session session){
        session.getTransaction().commit();
        session.close();
    }

    public static List<Doctor> getAllDoctors(){
        Session session = openSession();
        List<Doctor> doctors = session.createCriteria(Doctor.class).list();
        session.getTransaction().commit();
        session.close();
        return doctors;
    }

    public static List<Doctor> getDoctorsByProfession(long id){
        Session session = openSession();
        Query query = session.createQuery("select p from Doctor p WHERE p.profession = " + id + " ORDER BY p.name");
        List<Doctor> doctors = null;
        try{
            doctors = (List<Doctor>) query.getResultList();
        }catch(Exception ex){

        }
        session.getTransaction().commit();
        session.close();
        return doctors;
    }

    public static Doctor getDoctorById(long id){
        Session session = openSession();
        Criteria criteria = session.createCriteria(Doctor.class)
                .add(Restrictions.eq("id", id));
        Doctor doctor = (Doctor) criteria.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return doctor;
    }

    public static List<Profession> getDoctorByDisease(Disease disease){
        Session session = openSession();
        //Query query = session.createQuery("select d from Doctor d WHERE d.profession IN (select m.profession from Monitor m WHERE m.disease = :disease)");
        Query query = session.createQuery("select m.profession from Monitor m WHERE m.disease = :disease");
        query.setParameter("disease", disease);
        List<Profession> professions = null;
        try{
            professions = (List<Profession>) query.getResultList();
        }catch(Exception ex){

        }
        session.getTransaction().commit();
        session.close();
        return professions;
    }

    public static void addDoctor(@Valid Doctor doctor){
        Session session = openSession();
        try {
            session.save(doctor);
            session.flush();
        }catch(Exception ex){
            System.out.println("Неудалось сохранить врача в бд");
        }
        session.close();
    }

    public static void updateDoctor(Doctor doctor, Profession prof){
        Session session = openSession();
        Query query = session.createQuery("update Doctor set profession = :profession where id = :doctor");
        query.setParameter("profession", prof);
        query.setParameter("doctor", doctor.id);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteDoctor(@Valid Doctor doctor){
        Session session = openSession();
        try {
            session.delete(doctor);
            session.flush();
        }catch(Exception ex){
            System.out.println("Нельзя удалить");
        }
        session.close();
    }

    public static List<Patient> getAllPatient(){
        Session session = openSession();
        List<Patient> patients = session.createCriteria(Patient.class).list();
        session.getTransaction().commit();
        session.close();
        return patients;
    }

    public static List<Patient> getPatientsByDoctor(long doctor){
        Session session = openSession();
        Query query = session.createQuery("select p from Patient p WHERE p.doctor = " + doctor + " ORDER BY p.name");
        List<Patient> patients = null;
        try{
            patients = (List<Patient>) query.getResultList();
        }catch(Exception ex){

        }
        session.getTransaction().commit();
        session.close();
        return patients;
    }

    public static Patient getPatientById(long id){
        Session session = openSession();
        Criteria criteria = session.createCriteria(Patient.class)
                .add(Restrictions.eq("id", id));
        Patient patient = (Patient) criteria.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return patient;
    }

    public static void addPatient(@Valid Patient patient){
        Session session = openSession();
        try {
            session.save(patient);
            session.flush();
        }catch(Exception ex){
            System.out.println("Неудалось сохранить врача в бд");
        }
        session.close();
    }
    public static void updatePatients(Doctor oldD, Doctor newD){
        Session session = openSession();
        Query query = session.createQuery("update Patient set doctor = :newD where doctor = :oldD");
        query.setParameter("oldD", oldD);
        query.setParameter("newD", newD);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
    public static void deletePatient(@Valid Patient patient){
        Session session = openSession();
        try {
            session.delete(patient);
            session.flush();
        }catch(Exception ex){
            System.out.println("Нельзя удалить");
        }
        session.close();
    }
    public static Long getPatientCountByDoctor(Doctor doctor){
        Session session = openSession();
        Criteria crit = session.createCriteria(Patient.class);
        crit.add( Restrictions.eq("doctor", doctor));
        crit.setProjection(Projections.rowCount());
        Long count = (Long)crit.uniqueResult();
        session.getTransaction().commit();
        session.close();
        if(count == null)
            count = 0L;
        return count;
    }

    public static void addUser(@Valid MedUser user){
        Session session = openSession();
        try {
            session.save(user);
            session.flush();
        }catch(Exception ex){
            System.out.println("Неудалось сохранить пользователя в бд");
        }
        session.close();
    }
    public static void deleteDoctorUser(Doctor doctor){
        Session session = openSession();
        try {
            String stringQuery = "DELETE FROM MedUser a where a.role = :role and a.targetId = :targetid";
            Query query = session.createQuery(stringQuery);
            query.setParameter("role", Consts.ROLE_DOCTOR);
            query.setParameter("targetid", doctor.id);
            query.executeUpdate();
        }catch(Exception ex){
            System.out.println("Нельзя удалить "  +ex.getMessage());
        }
        session.close();
    }
    public static void deletePatientUser(Patient patient){
        Session session = openSession();
        try {
            String stringQuery = "DELETE FROM MedUser a where a.role = :role and a.targetId = :targetid";
            Query query = session.createQuery(stringQuery);
            query.setParameter("role", Consts.ROLE_PATIENT);
            query.setParameter("targetid", patient.id);
            query.executeUpdate();
        }catch(Exception ex){
            System.out.println("Нельзя удалить "  +ex.getMessage());
        }
        session.close();
    }
    public static boolean checkLogin(){
        return true;
    }

    public static List<Profession> getAllProfessions(){
        Session session = openSession();
        Query query = session.createQuery("select p from Profession p ORDER BY p.name");
        List<Profession> professions = null;
        try{
            professions = (List<Profession>) query.getResultList();
        }catch(Exception ex){

        }
        session.getTransaction().commit();
        session.close();
        return professions;
    }
    public static Profession getProfessionByName(String name){
        Session session = openSession();
        Criteria criteria = session.createCriteria(Profession.class)
                .add(Restrictions.eq("name", name));
        Profession prof = (Profession) criteria.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return prof;
    }
    public static List<Disease> getAllDisease(){
        Session session = openSession();
        Query query = session.createQuery("select p from Disease p ORDER BY p.name");
        List<Disease> diseases = null;
        try{
            diseases = (List<Disease>) query.getResultList();
        }catch(Exception ex){

        }
        session.getTransaction().commit();
        session.close();
        return diseases;
    }
    public static Disease getDiseaseByName(String name){
        Session session = openSession();
        Criteria criteria = session.createCriteria(Disease.class)
                .add(Restrictions.eq("name", name));
        Disease dis = (Disease) criteria.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return dis;
    }
    public static MedUser getUser(String login, String password){
        Session session = openSession();
        Criteria criteria = session.createCriteria(MedUser.class)
                .add(Restrictions.eq("login", login))
                .add(Restrictions.eq("password", password));
        MedUser user = (MedUser) criteria.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public static List<Monitor> getAllMonitors(){
        Session session = openSession();
        List<Monitor> monitors = session.createCriteria(Monitor.class).list();
        session.getTransaction().commit();
        session.close();
        return monitors;
    }

    public static List<Appointed> getAllAppointed(){
        Session session = openSession();
        List<Appointed> appointeds = session.createCriteria(Appointed.class).list();
        session.getTransaction().commit();
        session.close();
        return appointeds;
    }

    public static void addAppointed(@Valid Appointed appointed){
        Session session = openSession();
        try {
            session.save(appointed);
            session.flush();
        }catch(Exception ex){
            System.out.println("Неудалось сохранить запись в бд");
        }
        session.close();
    }

    public static void updateAppointed(Doctor oldD, Doctor newD){
        Session session = openSession();
        Query query = session.createQuery("update Appointed set doctor = :newD where doctor = :oldD");
        query.setParameter("oldD", oldD);
        query.setParameter("newD", newD);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public static List<Appointed> getAppointedByDoctorID(long id){
        Session session = openSession();
        Query query = session.createQuery("select p from Appointed p WHERE p.doctor = " + id + " ORDER BY p.date");
        List<Appointed> appointeds = null;
        try{
            appointeds = (List<Appointed>) query.getResultList();
        }catch(Exception ex){

        }
        session.getTransaction().commit();
        session.close();
        return appointeds;
    }
    public static List<Appointed> getAppointedByPatientID(long id){
        Session session = openSession();
        Query query = session.createQuery("select p from Appointed p WHERE p.patient = " + id + " ORDER BY p.date");
        List<Appointed> appointeds = null;
        try{
            appointeds = (List<Appointed>) query.getResultList();
        }catch(Exception ex){

        }
        session.getTransaction().commit();
        session.close();
        return appointeds;
    }
    public static List<Appointed> getAppointedByPatientAndData(Patient patient, LocalDate todayDate) {
        try {
            Session session = openSession();
            Criteria crit = session.createCriteria(Appointed.class);
            crit.add(Restrictions.gt("date", todayDate));
            crit.add(Restrictions.eq("patient", patient));
            List<Appointed> appointeds = crit.list();
            session.getTransaction().commit();
            session.close();

            return appointeds;
        }catch(Exception ex){
            return new ArrayList<Appointed>();
        }
    }
    public static List<Appointed> getAppointedByData(LocalDate todayDate) {
        try {
            Session session = openSession();
            Criteria crit = session.createCriteria(Appointed.class);
            crit.add(Restrictions.eq("date", todayDate));
            List<Appointed> appointeds = crit.list();
            session.getTransaction().commit();
            session.close();

            return appointeds;
        }catch(Exception ex){
            return new ArrayList<Appointed>();
        }
    }
    public static void deleteAppointedByPatient(Patient patient){
        Session session = openSession();
        try {
            String stringQuery = "DELETE FROM Appointed a where a.patient = :patient";
            Query query = session.createQuery(stringQuery);
            query.setParameter("patient", patient);
            query.executeUpdate();
        }catch(Exception ex){
            System.out.println("Нельзя удалить "  +ex.getMessage());
        }
        session.close();
    }
    public static void deleteAppointedByDoctor(Doctor doctor){
        Session session = openSession();
        try {
            String stringQuery = "DELETE FROM Appointed a where a.doctor = :doctor";
            Query query = session.createQuery(stringQuery);
            query.setParameter("doctor", doctor);
            query.executeUpdate();
        }catch(Exception ex){
            System.out.println("Нельзя удалить "  +ex.getMessage());
        }
        session.close();
    }
    public static Long getAppointedCountByDoctorAndDate(Doctor doctor, LocalDate localDate){
        Session session = openSession();
        Criteria crit = session.createCriteria(Appointed.class);
        crit.add( Restrictions.eq("date", localDate));
        crit.add( Restrictions.eq("doctor", doctor));
        crit.setProjection(Projections.rowCount());
        Long count = (Long)crit.uniqueResult();
        session.getTransaction().commit();
        session.close();
        if(count == null)
            count = 0L;
        return count;
    }
}
