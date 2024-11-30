import org.example.BD;
import org.example.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SvetaTests {

    @BeforeEach
    public void setup() {
        BD.buildSessionFactory();
    }

    @Test
    //Проверяем, заполнилась ли таблица начальными данными
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
    public void addPatient() {
        Patient patient = new Patient();
        patient.name = "Иванов Иван Иванович";
        patient.phone = "8-800-555-35-35";
        patient.gender = "Ж";

        List<Patient> patientsBeforeAdd = BD.getAllPatient();
        BD.addPatient(patient);
        List<Patient> patientsAfterAdd = BD.getAllPatient();

        Assertions.assertEquals(patientsAfterAdd.size(), patientsBeforeAdd.size() + 1);
    }

    @Test
    public void addPatientWithoutName() {
        Patient patient = new Patient();
        patient.phone = "8-800-555-35-35";
        patient.gender = "Ж";

        List<Patient> patientsBeforeAdd = BD.getAllPatient();
        BD.addPatient(patient);
        List<Patient> patientsAfterAdd = BD.getAllPatient();

        //Пациент не должен добавиться, число пациентов при этом не изменится
        Assertions.assertEquals(patientsAfterAdd.size(), patientsBeforeAdd.size());
    }

    @Test
    public void addNullPatient() {
        List<Patient> patientsBeforeAdd = BD.getAllPatient();
        BD.addPatient(null);
        List<Patient> patientsAfterAdd = BD.getAllPatient();

        //Пациент не должен добавиться, число пациентов при этом не изменится
        Assertions.assertEquals(patientsAfterAdd.size(), patientsBeforeAdd.size());
    }

    @Test
    public void deletePatient() {
        List<Patient> patientsBeforeDelete = BD.getAllPatient();
        //Получаем специального пользователя, которого заранее добавили в бд
        Patient patient = patientsBeforeDelete.stream().filter(p -> p.name.equals("Пользователь Для Удаления")).findFirst().get();
        BD.deletePatient(patient);
        List<Patient> patientsAfterDelete = BD.getAllPatient();

        Assertions.assertEquals(patientsAfterDelete.size(), patientsBeforeDelete.size() - 1);
    }

    @Test
    public void updatePatient() {
        String newName = "Обновлённый пользователь";
        Patient patient = BD.getAllPatient().stream().filter(p -> p.name.equals("Пользователь Для Обновления")).findFirst().get();

        BD.updatePatient(patient, newName);

        Patient updatedPatient = BD.getPatientById(patient.id);
        Assertions.assertEquals(updatedPatient.name, newName);
    }

    @Test
    public void getPatientsByDoctor() {
        Doctor doctor = BD.getDoctorsByProfession("Кардиолог").get(0);
        List<Patient> patients = BD.getPatientsByDoctor(doctor.id);
        Assertions.assertTrue(patients.size() > 0);
    }

    @Test
    public void getDiseaseByName() {
        Disease disease = BD.getDiseaseByName("Аневризма");
        Assertions.assertNotNull(disease);
    }
}
