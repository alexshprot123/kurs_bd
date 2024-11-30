import org.example.BD;
import org.example.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class StepTests {

    @BeforeEach
    public void setup() {
        BD.buildSessionFactory();
    }

    @Test
    public void testInitData() {
        List<Disease> diseases = BD.getAllDisease();
        List<Profession> professions = BD.getAllProfessions();
        List<Monitor> monitors = BD.getAllMonitors();
        List<Doctor> doctors = BD.getAllDoctors();
        List<Patient> patients = BD.getAllPatient();

        Assertions.assertTrue(diseases.size() > 0);
        Assertions.assertTrue(professions.size() > 0);
        Assertions.assertTrue(monitors.size() > 0);
        Assertions.assertTrue(doctors.size() > 0);
        Assertions.assertTrue(patients.size() > 0);
    }

    @Test
    //Проверяет добавление и удаления доктора
    public void addAndDeleteDoctor() {
        Doctor doctor = new Doctor();
        doctor.name = "Иванов Иван Иванович";
        doctor.phone = "8-800-555-35-35";
        doctor.gender = "М";
        doctor.profession = BD.getProfessionByName("Окулист");

        //Добавляем доктора
        BD.addDoctor(doctor);

        //Проверяем, что доктор добавился
        List<Doctor> doctors = BD.getAllDoctors();
        Doctor doctorFromBD = doctors.stream().filter(d -> d.name.equals("Иванов Иван Иванович")).findFirst().get();
        Assertions.assertNotNull(doctorFromBD);
        Assertions.assertEquals(doctorFromBD.name, "Иванов Иван Иванович");
        Assertions.assertEquals(doctorFromBD.phone, "8-800-555-35-35");
        Assertions.assertEquals(doctorFromBD.gender, "М");

        //Удаляем доктора
        BD.deleteDoctor(doctorFromBD);
        List<Doctor> doctorsAfterDelete = BD.getAllDoctors();
        Doctor doctorAfterDelete = BD.getDoctorById(doctorFromBD.id);
        Assertions.assertNull(doctorAfterDelete);
    }

    @Test
    public void addEmptyDoctor() {  //Доктор с пустыми данными не должен добавиться
        Doctor doctor = new Doctor();

        List<Doctor> doctors = BD.getAllDoctors();
        BD.addDoctor(doctor);
        List<Doctor> doctorsAfter = BD.getAllDoctors();

        Assertions.assertEquals(doctors.size(), doctorsAfter.size());
    }

    @Test
    public void addNullDoctor() {  //Если передать null, доктор не должен добавиться
        List<Doctor> doctors = BD.getAllDoctors();
        BD.addDoctor(null);
        List<Doctor> doctorsAfter = BD.getAllDoctors();
        Assertions.assertEquals(doctors.size(), doctorsAfter.size());
    }

    @Test
    public void getDoctorsByProfession() {
        List<Doctor> doctors = BD.getDoctorsByProfession("Окулист");
        Assertions.assertTrue(doctors.size() > 0);
    }

    @Test
    public void addAppointed () {
        List<Doctor> doctors = BD.getDoctorsByProfession("Окулист");
        List<Patient> patients = BD.getPatientsByDisease("Астигматизм");

        Appointed appointed = new Appointed();
        appointed.doctor = doctors.get(0);
        appointed.patient = patients.get(0);
        appointed.date = LocalDate.now();

        BD.addAppointed(appointed);

        List<Appointed> appointeds = BD.getAllAppointed();
        Assertions.assertTrue(appointeds.size() > 0);
    }

    @Test
    public void addEmptyAppointed () { //Запись с пустыми данными не должна добавиться
        Appointed appointed = new Appointed();

        List<Appointed> before = BD.getAllAppointed();
        BD.addAppointed(appointed);
        List<Appointed> after = BD.getAllAppointed();
        Assertions.assertEquals(after.size(), before.size());
    }

    @Test
    public void getPatientsByDoctor() {
        Doctor doctor = BD.getDoctorsByProfession("Окулист").get(0);
        List<Patient> patients = BD.getPatientsByDoctor(doctor.id);
        Assertions.assertTrue(patients.size() > 0);
    }
}
