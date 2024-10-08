package org.example.Forms.doctor.modal;

import org.example.BD;
import org.example.Consts;
import org.example.MainForm;
import org.example.entity.Appointed;
import org.example.entity.Doctor;
import org.example.entity.Patient;
import org.example.entity.Profession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class AddProfDoctorForm extends JDialog {

    Patient patient;
    private JPanel panel;
    private JTextField patientTextField;
    private JButton backButton;
    private JButton okButton;
    private JLabel errorMessageLabel;
    private JTextField dateTextBox;

    public AddProfDoctorForm(JFrame parent, Patient patient){
        super(parent, "", true);
        this.patient = patient;
        setContentPane(panel);
        setSize(400, 300);
        errorMessageLabel.setVisible(false);
        patientTextField.setText(patient.name);
        dateTextBox.setToolTipText("dd.MM.yyyy");

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
            showError("Неверно указана дата. Дата должна быть вида dd.MM.yyyy");
        }catch (NullPointerException nullPointerException){
        }
        return false;
    }

    public static void showDialog(JFrame parent, Patient patient) {
        AddProfDoctorForm dialog = new AddProfDoctorForm(parent, patient);
        dialog.setVisible(true);
    }

    private Appointed getAppointed(){
        Appointed appointed = new Appointed();
        appointed.patient = patient;
        LocalDate localDate = LocalDate.parse(dateTextBox.getText(), Consts.DATE_FORMAT);

        if(localDate.isBefore(LocalDate.now())){
            showError("Нельзя назначить осмотр на прошедшее время");
            return null;
        }

        //Проверка, есть ли место в этот день
        if(localDate.getDayOfWeek().toString().equals("SATURDAY") || localDate.getDayOfWeek().toString().equals("SUNDAY")) {
            showError("Осмотр выпадает на выходной");
            return null;
        }
        Doctor doctor = BD.getDoctorById(MainForm.getCurUser().targetId);
        long count = BD.getAppointedCountByDoctorAndDate(doctor, localDate);
        if(count >= Consts.MAX_PATIENT_IN_ONE_DAY){
            showError("Этот день уже занят");
            return null;
        }
        appointed.doctor = doctor;
        appointed.date = localDate;
        return appointed;
    }
}
