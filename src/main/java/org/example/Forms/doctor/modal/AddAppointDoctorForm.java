package org.example.Forms.doctor.modal;

import org.example.BD;
import org.example.Consts;
import org.example.entity.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class AddAppointDoctorForm extends JDialog {
    private JPanel panel;
    private JComboBox profComboBox;
    private JButton backButton;
    private JButton okButton;
    private JTextField patientTextField;
    private JLabel errorMessageLabel;
    Patient patient;

    public AddAppointDoctorForm(JFrame parent, Patient patient){
        super(parent, "", true);
        this.patient = patient;
        setContentPane(panel);
        setSize(400, 300);
        errorMessageLabel.setVisible(false);
        patientTextField.setText(patient.name);

        List<Profession> profs = BD.getDoctorByDisease(patient.disease);
        List<String> profsName = profs.stream().map(p -> p.name).collect(Collectors.toList());
        for(String prof : profsName)
            profComboBox.addItem(prof);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addAppointed()) {
                    dispose();
                }
            }
        });
    }

    public void showError(String msg){
        errorMessageLabel.setText(msg);
        errorMessageLabel.setVisible(true);
    }

    public boolean addAppointed(){
        try {
            Appointed appointed = getAppointed();
            if(appointed == null)
                return false;
            BD.addAppointed(appointed);
            InfoAppointForm.showDialog((JFrame)getParent(), appointed);
            return true;
        }catch (DateTimeParseException dateTimeParseException){
            showError("Неверно указана дата");
        }catch (NullPointerException nullPointerException){
        }
        return false;
    }

    public static void showDialog(JFrame parent, Patient patient) {
        AddAppointDoctorForm dialog = new AddAppointDoctorForm(parent, patient);
        dialog.setVisible(true);
    }

    private Appointed getAppointed(){
        Appointed appointed = new Appointed();
        appointed.patient = patient;
        LocalDate localDate = LocalDate.now();
        List<Doctor> doctors = BD.getDoctorsByProfession(BD.getProfessionByName(profComboBox.getSelectedItem().toString()).id);
        if(doctors.isEmpty()){
            showError("На данный момент специалистов этой специальности нет");
            return null;
        }
        //Просмотр, существует ли такая запись
        List<Appointed> aps = BD.getAppointedByPatientAndData(patient, localDate);
        for(Appointed a : aps){
            if(a.doctor.profession.name.equals(profComboBox.getSelectedItem().toString())){
                showError("Запись уже существует");
                return null;
            }

        }
        //Поиск свободного врача и даты
        while(true) {
            localDate = localDate.plusDays(1);
            if(localDate.getDayOfWeek().toString().equals("SATURDAY") || localDate.getDayOfWeek().toString().equals("SUNDAY"))
                continue;
            Doctor freeDoctor = null;
            long countMin = 999;
            for(Doctor doctor : doctors){
                long count = BD.getAppointedCountByDoctorAndDate(doctor, localDate);
                if(count < countMin && count < Consts.MAX_PATIENT_IN_ONE_DAY){
                    freeDoctor = doctor;
                    countMin = count;
                }
            }
            if(freeDoctor == null)
                continue;
            appointed.doctor = freeDoctor;
            appointed.date = localDate;
            return appointed;
        }
    }
}
